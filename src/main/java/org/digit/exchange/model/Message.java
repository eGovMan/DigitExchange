package org.digit.exchange.model;

import lombok.*;

import org.digit.exchange.constant.MessageType;
import org.digit.exchange.exceptions.CustomException;
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

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Getter
@Setter
@Entity
@Table(name="exchange_message")
public class Message{

    private static final Logger logger = LoggerFactory.getLogger(Message.class);

    @Id
    private String id;
    @JsonProperty("signature")
    private String signature;
    @JsonProperty("header")
    @Embedded
    @NotNull
    private Header header;
    @NotNull
    @JsonProperty("message")
    @Column(columnDefinition = "TEXT")
    private String message;    

    public Message(){
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.header = new Header();
    }

    public Message(String to, String from, FiscalData message,MessageType action){
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.header = new Header(to,from,message,action);
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

    static public Message fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, Message.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing RequestMessage fromString", e);
		}
	}
}
