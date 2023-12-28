package org.digit.exchange.models.fiscal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payee {
    @JsonProperty("id")
    String id;
    @JsonProperty("name")
    String name;
    @JsonProperty("email_id")
    String email_id;
    @JsonProperty("mobile_number")
    String mobile_number;
    @JsonProperty("address")
    Address address;
    @JsonProperty("account")
    Address account;
}
