package org.digit.exchange.model.messages;

import lombok.*;

import org.digit.exchange.exceptions.CustomException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class Program extends ExchangeMessage {

    @JsonProperty("objectives")
    private String[] objectives;    

    public Program(){
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
