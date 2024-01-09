package org.digit.exchange.model;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.persistence.EnumType;

import lombok.Getter;
import lombok.Setter;

import org.digit.exchange.constant.Error;
import org.digit.exchange.constant.Role;
import org.digit.exchange.exceptions.CustomException;

@Getter
@Setter
@Entity
@Table(name="individual")
public class Individual{
    @Id
    @JsonProperty("id")
	String Id;
	
    @JsonProperty("name")
    @NotNull
	String name;

	@JsonProperty("address")
    @NotNull
    @Embedded
	@Size(min = 1, message = Error.INVALID_ADDRESS)
	Address address;

    @JsonProperty("email")
	String email;

    @JsonProperty("phone")
	String phone;

    @JsonProperty("pin")
	String pin;

    @JsonProperty("roles")
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING) // or EnumType.ORDINAL if you prefer numbers
    private Collection<Role> roles;

    @JsonProperty("is_active")
	Boolean isActive;
	
	
	static public Individual fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, Individual.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing User fromString", e);
		}
	}
}
