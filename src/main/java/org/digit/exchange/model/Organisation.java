package org.digit.exchange.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import org.digit.exchange.constant.Error;
import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.model.messages.Address;
import org.digit.exchange.model.messages.LanguageValue;
import org.digit.exchange.utils.LanguageValueListConverter;;


@Getter
@Setter
@Entity
@Table(name="organisation")
public class Organisation{
    @JsonProperty("id")
	@NotBlank(message = Error.INVALID_ID)
	@Id
	String id;

	@JsonProperty("organisation_type")
	@NotBlank(message = Error.INVALID_ORGANISATION_TYPE)
	String orgType;

    @Convert(converter = LanguageValueListConverter.class)
    @JsonProperty("name")
	@Size(min = 1, message = Error.INVALID_NAME)
	List<LanguageValue> name;

	@JsonProperty("address")
    @NotNull
    @Embedded
	@Size(min = 1, message = Error.INVALID_ADDRESS)
	Address address;

    @JsonProperty("digit_exchange_uri")
	String digit_exchange_url;

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
