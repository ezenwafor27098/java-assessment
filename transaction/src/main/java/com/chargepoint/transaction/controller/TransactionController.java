package com.chargepoint.transaction.controller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chargepoint.transaction.model.AuthenticationResponse;
import com.chargepoint.transaction.model.TransactionRequest;

@RestController
@RequestMapping("/api/v1/transaction")
public class TransactionController {
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);
    private CompletableFuture<AuthenticationResponse> authenticationFuture;

    public TransactionController(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/authorize")
    public String processTransaction(@RequestBody TransactionRequest request) {
        logger.info("This is the input ==================================================>");
        if (request != null) logger.info(request.getStationUuid()+", "+request.getDriverIdentifier().getId());
        authenticationFuture = new CompletableFuture<>();
        kafkaTemplate.send("authentication_requests", request);

        try {
            AuthenticationResponse response = authenticationFuture.get(5, TimeUnit.SECONDS);
            if ("Accepted".equals(response.getAuthenticationStatus())) {
                return "Transaction Successful";
            } else {
                return "Transaction Failed: " + response.getAuthenticationStatus();
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {

            return "Transaction Failed: Authentication timeout"+ e.getMessage();
        }
    }

    @KafkaListener(topics = "authentication_responses", groupId = "transaction_group")
    public void consume(AuthenticationResponse response) {
        authenticationFuture.complete(response);
    }
}
