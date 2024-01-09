package org.digit.exchange.dto;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import org.digit.exchange.constant.Error;
import org.digit.exchange.constant.OrganisationRole;
import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.model.Organisation;


@Getter
@Setter
public class OrganisationDTO{
    @JsonProperty("id")
	@NotBlank(message = Error.INVALID_ID)
	String id;

	@JsonProperty("organisation_roles")
	Collection<OrganisationRole> orgRoles;

    @JsonProperty("name")
	@NotBlank(message = Error.INVALID_NAME)
	String name;

	@JsonProperty("address")
    @NotNull
	@Size(min = 1, message = Error.INVALID_ADDRESS)
	AddressDTO address;

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

	public OrganisationDTO(Organisation organisation) {
        this.id = organisation.getId();
        this.orgRoles = organisation.getOrgRoles(); // Direct assignment assuming the enums are the same
        this.name = organisation.getName();
        this.address = organisation.getAddress()!=null? new AddressDTO(organisation.getAddress()):null; // Assuming AddressDTO constructor takes an Address object
        this.isDigitExchange = organisation.getIsDigitExchange();
        this.endpointUrl = organisation.getEndpointUrl();
        this.agencyApiKey = organisation.getAgencyApiKey();
        this.apiKey = organisation.getApiKey();
        this.isActive = organisation.getIsActive();
        this.administratorId = organisation.getAdministratorId();
    }

	public Organisation toOrganisation() {
        Organisation organisation = new Organisation();

        organisation.setId(this.id);
        organisation.setOrgRoles(this.orgRoles); // Direct assignment, ensuring the enums are compatible
        organisation.setName(this.name);
        organisation.setAddress(this.address.toAddress()); // Assuming AddressDTO has a toAddress() method
        organisation.setIsDigitExchange(this.isDigitExchange);
        organisation.setEndpointUrl(this.endpointUrl);
        organisation.setAgencyApiKey(this.agencyApiKey);
        organisation.setApiKey(this.apiKey);
        organisation.setIsActive(this.isActive);
        organisation.setAdministratorId(this.administratorId);

        return organisation;
    }

    static public OrganisationDTO fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, OrganisationDTO.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing Organisation fromString", e);
		}
	}

}
