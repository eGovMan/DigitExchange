package org.digit.exchange.dto;

import lombok.*;

import org.digit.exchange.constant.MessageType;
import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.model.Message;
import org.digit.fix.model.Allocation;
import org.digit.fix.model.Collection;
import org.digit.fix.model.Demand;
import org.digit.fix.model.Disbursement;
import org.digit.fix.model.Estimate;
import org.digit.fix.model.FiscalData;
import org.digit.fix.model.Program;
import org.digit.fix.model.Sanction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;


@Getter
@Setter
public class MessageDTO{

    private static final Logger logger = LoggerFactory.getLogger(MessageDTO.class);

    private String id;
    @JsonProperty("signature")

    private String signature;
    @JsonProperty("header")

    @NotNull
    private HeaderDTO header;

    @NotNull
    @JsonProperty("message")
    private String message;    

    public MessageDTO(){
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
    }

    public MessageDTO(Message message) {
        this.id = message.getId();
        this.signature = message.getSignature();
        this.header = new HeaderDTO(message.getHeader()); // Assuming HeaderDTO has a constructor that accepts a Header object
        this.message = message.getMessage();
    }

    public Message toMessage() {
        Message message = new Message();

        message.setId(this.id);
        message.setSignature(this.signature);
        message.setHeader(this.header.toHeader()); // Assuming HeaderDTO has a method toHeader() that returns a Header object
        message.setMessage(this.message);

        return message;
    }

    public MessageDTO(String to, String from, FiscalData message,MessageType action){
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.header = new HeaderDTO(to,from,message,action);
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
        } else if(message instanceof Allocation){
            Allocation allocation = (Allocation)message;
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                String jsonProgram = mapper.writeValueAsString(allocation);
                this.message = jsonProgram;            
            } catch (JsonProcessingException e) {
                logger.error("Error while converting FiscalMessage to JSON");
                throw new RuntimeException("Failed to process JSON", e);
            }
        } else if(message instanceof Disbursement){
            Disbursement bill = (Disbursement)message;
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                String jsonProgram = mapper.writeValueAsString(bill);
                this.message = jsonProgram;            
            } catch (JsonProcessingException e) {
                logger.error("Error while converting FiscalMessage to JSON");
                throw new RuntimeException("Failed to process JSON", e);
            }
        } else if(message instanceof Demand){
            Demand demand = (Demand)message;
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                String jsonProgram = mapper.writeValueAsString(demand);
                this.message = jsonProgram;            
            } catch (JsonProcessingException e) {
                logger.error("Error while converting FiscalMessage to JSON");
                throw new RuntimeException("Failed to process JSON", e);
            }
        } else if(message instanceof Collection){
            Collection receipt = (Collection)message;
            try {
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                String jsonProgram = mapper.writeValueAsString(receipt);
                this.message = jsonProgram;            
            } catch (JsonProcessingException e) {
                logger.error("Error while converting FiscalMessage to JSON");
                throw new RuntimeException("Failed to process JSON", e);
            }
        }
    }

    static public MessageDTO fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, MessageDTO.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing RequestMessage fromString", e);
		}
	}
}
