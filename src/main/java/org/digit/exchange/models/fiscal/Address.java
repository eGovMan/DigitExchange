package org.digit.exchange.models.fiscal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {

    @JsonProperty("id")
    private String id = null;

    @JsonProperty("doorNo")
    private String doorNo = null;

    @JsonProperty("latitude")
    private Double latitude = null;

    @JsonProperty("longitude")
    private Double longitude = null;

    @JsonProperty("locationAccuracy")
    private Double locationAccuracy = null;

    @JsonProperty("type")
    private String type = null;

    @JsonProperty("addressNumber")
    private String addressNumber = null;

    @JsonProperty("addressLine1")
    private String addressLine1 = null;

    @JsonProperty("addressLine2")
    private String addressLine2 = null;

    @JsonProperty("landmark")
    private String landmark = null;

    @JsonProperty("city")
    private String city = null;

    @JsonProperty("pincode")
    private String pincode = null;

    @JsonProperty("detail")
    private String detail = null;

    @JsonProperty("buildingName")
    private String buildingName = null;

    @JsonProperty("street")
    private String street = null;

    @JsonProperty("boundaryType")
    private String boundaryType = null;

    @JsonProperty("boundary")
    private String boundary = null;
}
