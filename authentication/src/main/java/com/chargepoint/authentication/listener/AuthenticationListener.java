package com.chargepoint.authentication.listener;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.chargepoint.authentication.model.AuthenticationResponse;
import com.chargepoint.authentication.model.StatusEnum;
import com.chargepoint.authentication.model.TransactionRequest;

@Service
public class AuthenticationListener {
    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationListener.class);
    private final List<String> validIDs;

    public AuthenticationListener(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.validIDs = Arrays.asList(
            "1000",
            "2000",
            "3000",
            "4000",
            "5000",
            "6000",
            "7000",
            "8000",
            "9000",
            "10000"
        );
    }

    @KafkaListener(topics = "authentication_requests", groupId = "auth_group")
    public void handleAuthenticationRequest(TransactionRequest request) {
        logger.info("This is the sample request =========================================================>");
        logger.info(request.getStationUuid()+", "+request.getDriverIdentifier().getId());
        String status;
        if (validIDs.contains(request.getDriverIdentifier().getId())) {
            status = StatusEnum.Accepted.name();
        } else {
            status = StatusEnum.Rejected.name();
        }
        AuthenticationResponse response = new AuthenticationResponse(request.getStationUuid(), status);
        kafkaTemplate.send("authentication_responses", response);
    }

}
