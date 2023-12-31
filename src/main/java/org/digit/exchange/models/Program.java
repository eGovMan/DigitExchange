package org.digit.exchange.models;

import lombok.*;

import org.digit.exchange.constants.MessageType;
import org.digit.exchange.exceptions.CustomException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class Program extends ExchangeMessage {

    @JsonProperty("name")
    private String name;
    @JsonProperty("parent")
    private Program parent;
    @JsonProperty("objectives")
    private String[] objectives;    
    @JsonProperty("audit_details")
    private AuditDetails auditDetails;
    @JsonProperty("additional_details")
    private JsonNode additionalDetails;

    public Program(){
        this.setMessageType(MessageType.PAYMENT);
    }

    static public Program fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, Program.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing Program fromString", e);
		}
	}
}
