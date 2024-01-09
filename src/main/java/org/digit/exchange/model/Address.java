package org.digit.exchange.model;

import org.digit.exchange.exceptions.CustomException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
public class Address {
    
    @JsonProperty("door_no")
    private String doorNo = null;

    @JsonProperty("latitude")
    private Double latitude = null;

    @JsonProperty("longitude")
    private Double longitude = null;

    @JsonProperty("location_accuracy")
    private Double locationAccuracy = null;

    @JsonProperty("address_line_1")
    private String addressLine1 = null;

    @JsonProperty("address_line_2")
    private String addressLine2 = null;

    @JsonProperty("landmark")
    private String landmark = null;

    @JsonProperty("city")
    private String city = null;

    @JsonProperty("pincode")
    private String pincode = null;

    @JsonProperty("building_name")
    private String buildingName = null;

    @JsonProperty("street")
    private String street = null;

    @JsonProperty("boundary_type")
    private String boundaryType = null;

    @JsonProperty("boundary")
    private String boundary = null;

    static public Address fromString(String json){
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      try {
        return mapper.readValue(json, Address.class);
      } catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing Address fromString", e);
		}
	}
}
