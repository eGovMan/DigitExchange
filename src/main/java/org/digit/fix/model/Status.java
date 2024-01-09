package org.digit.fix.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import org.digit.exchange.constant.StatusCode;
import org.digit.exchange.exceptions.CustomException;


@Getter
@Setter
@Embeddable
public class Status{
    @Id
    @JsonProperty("id")
	String id;
	
    @JsonProperty("status_code")
	StatusCode statusCode;

    @JsonProperty("status_message")
	String statusMessage;

    @JsonProperty("status")
    List<Status> status;

	static public Status fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, Status.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing VerificationRequest fromString", e);
		}
	}
}
