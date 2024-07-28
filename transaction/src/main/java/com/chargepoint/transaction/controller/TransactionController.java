package com.chargepoint.transaction.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chargepoint.transaction.model.APIResponse;
import com.chargepoint.transaction.model.AuthenticationRequest;
import com.chargepoint.transaction.model.AuthenticationResponse;
import com.chargepoint.transaction.model.StatusEnum;
import com.chargepoint.transaction.model.TransactionRequest;

@RestController
@RequestMapping("/api/v1/transaction")
@Validated
public class TransactionController {
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final List<AuthenticationResponse> authenticationResponses;
    private CompletableFuture<ResponseEntity<APIResponse>> authenticationFuture;

    public TransactionController(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.authenticationResponses = new ArrayList<>();
    }

    @PostMapping("/authorize")
    public ResponseEntity<APIResponse> processTransaction(@Valid @RequestBody TransactionRequest request) {
        return transactionService(request);
    }

    @KafkaListener(topics = "authentication_responses", groupId = "transaction_group")
    public void consume(AuthenticationResponse response) {
        authenticationResponses.add(response);
    }

    //Logic should be in the transactionService class, but since its a simple one, doing it here.
    private ResponseEntity<APIResponse> transactionService(TransactionRequest request) {
        try {
            authenticationFuture = new CompletableFuture<>();
            AuthenticationRequest authenticationRequest = new AuthenticationRequest(
                UUID.randomUUID().toString(), 
                request.getDriverIdentifier().getId()
            );
            kafkaTemplate.send("authentication_requests", authenticationRequest);
            pollForAuthenticationResponse(authenticationFuture, authenticationRequest.getRequestUuid());
            return authenticationFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(StatusEnum.Unknown.name()));
        }
    }

    private void pollForAuthenticationResponse(CompletableFuture<ResponseEntity<APIResponse>> future, String requestUuid) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            Optional<AuthenticationResponse> authResponse = authenticationResponses.stream().filter(a -> a.getRequestUuid().equalsIgnoreCase(requestUuid)).findAny();
            if (authResponse.isPresent()) {
                AuthenticationResponse receivedResponse = authResponse.get();
                if (receivedResponse.getAuthenticationStatus().equalsIgnoreCase(StatusEnum.Accepted.name()))
                    future.complete(ResponseEntity.ok(new APIResponse(authResponse.get().getAuthenticationStatus())));
                else future.complete(ResponseEntity.badRequest().body(new APIResponse(receivedResponse.getAuthenticationStatus())));
            }
        }, 0, 200, TimeUnit.MILLISECONDS);
        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            if (!future.isDone()) future.complete(ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body(new APIResponse(StatusEnum.Unknown.name())));
        }, 5, TimeUnit.SECONDS); 
    }
}
