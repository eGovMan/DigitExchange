package org.digit.exchange.model;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import org.digit.exchange.constant.Error;
import org.digit.exchange.constant.OrganisationRole;
import org.digit.exchange.exceptions.CustomException;


@Getter
@Setter
@Entity
@Table(name="organisation")
public class Organisation{
    @JsonProperty("id")
	@NotBlank(message = Error.INVALID_ID)
	@Id
	String id;

	@JsonProperty("organisation_roles")
	@ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING) // or EnumType.ORDINAL if you prefer numbers
	Collection<OrganisationRole> orgRoles;

    @JsonProperty("name")
	@NotBlank(message = Error.INVALID_NAME)
	String name;

	@JsonProperty("address")
    @NotNull
    @Embedded
	@Size(min = 1, message = Error.INVALID_ADDRESS)
	Address address;

	@JsonProperty("is_endpoint_digit_exchange")
	String isDigitExchange;

    @JsonProperty("endpoint_url")
	String endpointUrl;

	@JsonProperty("agency_api_key")
	String agencyApiKey;

	@JsonProperty("api_key")
	String apiKey;

	@JsonProperty("is_active")
	Boolean isActive;

	@NotNull
	@JsonProperty("administrator_id")
	String administratorId;

	

    static public Organisation fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, Organisation.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing Organisation fromString", e);
		}
	}

}
