package org.digit.fix.model;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import org.digit.exchange.constant.Error;
import org.digit.exchange.constant.Role;
import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.model.Address;

@Getter
@Setter
@Embeddable
public class Individual extends FiscalData{
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
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
        name = "roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
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
