package org.digit.exchange.dto;

import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.model.Address;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    
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

    // Constructor for converting Address to AddressDTO
    public AddressDTO(Address address) {
      this.doorNo = address.getDoorNo();
      this.latitude = address.getLatitude();
      this.longitude = address.getLongitude();
      this.locationAccuracy = address.getLocationAccuracy();
      this.addressLine1 = address.getAddressLine1();
      this.addressLine2 = address.getAddressLine2();
      this.landmark = address.getLandmark();
      this.city = address.getCity();
      this.pincode = address.getPincode();
      this.buildingName = address.getBuildingName();
      this.street = address.getStreet();
      this.boundaryType = address.getBoundaryType();
      this.boundary = address.getBoundary();
  }

  public Address toAddress() {
    Address address = new Address();

    address.setDoorNo(this.doorNo);
    address.setLatitude(this.latitude);
    address.setLongitude(this.longitude);
    address.setLocationAccuracy(this.locationAccuracy);
    address.setAddressLine1(this.addressLine1);
    address.setAddressLine2(this.addressLine2);
    address.setLandmark(this.landmark);
    address.setCity(this.city);
    address.setPincode(this.pincode);
    address.setBuildingName(this.buildingName);
    address.setStreet(this.street);
    address.setBoundaryType(this.boundaryType);
    address.setBoundary(this.boundary);

    return address;
}

    static public AddressDTO fromString(String json){
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      try {
        return mapper.readValue(json, AddressDTO.class);
      } catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing Address fromString", e);
		}
	}
}
