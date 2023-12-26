package org.digit.exchange.web.controllers.models;

import lombok.*;

import org.digit.exchange.constants.Action;
import org.digit.exchange.web.controllers.ExchangeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@Getter
@Setter
public class RequestMessage {

    private static final Logger logger = LoggerFactory.getLogger(ExchangeController.class);

    @JsonProperty("signature")
    private String signature;
    @JsonProperty("header")
    private RequestHeader header;
    @JsonProperty("message")
    private String message;    

    public RequestMessage(){

    }

    public RequestMessage(String to, String from, FiscalMessage message,Action action){
        this.header = new RequestHeader(to,from,message,action);
        if(message instanceof Program){
            Program program = (Program)message;
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                String jsonProgram = mapper.writeValueAsString(program);
                this.message = jsonProgram;            
            } catch (JsonProcessingException e) {
                logger.error("Error while converting FiscalMessage to JSON");
                throw new RuntimeException("Failed to process JSON", e);
            }
        } else if(message instanceof Estimate){
            Estimate esimate = (Estimate)message;
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                String jsonProgram = mapper.writeValueAsString(esimate);
                this.message = jsonProgram;            
            } catch (JsonProcessingException e) {
                logger.error("Error while converting FiscalMessage to JSON");
                throw new RuntimeException("Failed to process JSON", e);
            }
        } else if(message instanceof Sanction){
            Sanction sanction = (Sanction)message;
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                String jsonProgram = mapper.writeValueAsString(sanction);
                this.message = jsonProgram;            
            } catch (JsonProcessingException e) {
                logger.error("Error while converting FiscalMessage to JSON");
                throw new RuntimeException("Failed to process JSON", e);
            }
        }
    }
}
