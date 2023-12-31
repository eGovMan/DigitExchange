package org.digit.exchange.utils;

import org.digit.exchange.constants.MessageType;
import org.digit.exchange.models.ExchangeMessage;
import org.digit.exchange.models.RequestMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class ExchangeClient {
    private final WebClient webClient;

    ExchangeClient(){
        this.webClient = WebClient.create();
    }

    public ResponseEntity<String> send(String url, String to, String from, ExchangeMessage message, MessageType messageType){
        //Tell line that program has been created.
        RequestMessage request;
        try {
            request = new RequestMessage(to, from, message, messageType);
        } catch (RuntimeException e) {
            // Return a ResponseEntity with an appropriate message and HTTP status
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error sending message");
        }
        
        //Make the request
        Mono<String> responseMono = webClient.post()
                    .uri(url)
                    .body(Mono.just(request), RequestMessage.class)
                    .retrieve()
                    .bodyToMono(String.class);
        String result = responseMono.block();
        return ResponseEntity.ok(result);
    }
    
}
