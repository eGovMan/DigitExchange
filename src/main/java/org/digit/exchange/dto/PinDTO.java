package org.digit.exchange.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import org.digit.exchange.exceptions.CustomException;


@Getter
@Setter
public class PinDTO{
    @Id
    @JsonProperty("user_id")
	String userId;
	
    @JsonProperty("pin")
	String pin;

	static public PinDTO fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, PinDTO.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing VerificationRequest fromString", e);
		}
	}
}
