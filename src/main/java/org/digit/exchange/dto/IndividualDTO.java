package org.digit.exchange.dto;

import java.util.Collection;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import org.digit.exchange.constant.Error;
import org.digit.exchange.constant.Role;
import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.model.Individual;

@Getter
@Setter
public class IndividualDTO{
    @JsonProperty("id")
	String id;
	
    @JsonProperty("name")
	String name;

	@JsonProperty("address")
	AddressDTO address;

    @JsonProperty("email")
	String email;

    @JsonProperty("phone")
	String phone;

    @JsonProperty("pin")
	String pin;

    @JsonProperty("roles")
    private Collection<Role> roles;

    @JsonProperty("is_active")
	Boolean isActive;

	 public IndividualDTO(){
    }

	public IndividualDTO(Individual individual) {
		this.id = individual.getId();
		this.name = individual.getName();
		if(individual.getAddress()!=null)
			this.address = new AddressDTO(individual.getAddress()); // Convert Address to AddressDTO
		this.email = individual.getEmail();
		this.phone = individual.getPhone();
		this.pin = individual.getPin();
		this.roles = individual.getRoles(); // Direct assignment, ensure enum compatibility
		this.isActive = individual.getIsActive();
	}
	
	public Individual toIndividual() {
        Individual individual = new Individual();

        individual.setId(this.id);
        individual.setName(this.name);
		if(this.address!=null)
	        individual.setAddress(this.address.toAddress()); // Assuming AddressDTO has a toAddress() method
        individual.setEmail(this.email);
        individual.setPhone(this.phone);
        individual.setPin(this.pin);
        individual.setRoles(this.roles); // Direct assignment, ensure enum compatibility
        individual.setIsActive(this.isActive);

        return individual;
    }
	
	static public IndividualDTO fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, IndividualDTO.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing User fromString", e);
		}
	}
}
