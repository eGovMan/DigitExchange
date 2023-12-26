package org.digit.exchange.web.controllers.models;

import lombok.*;

import java.time.ZonedDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class AuditDetails {
    @NotNull
    @JsonProperty("created_by")
    @NotNull
    private String createdBy;
    @JsonProperty("created_on")  
    private ZonedDateTime createdOn;

    public AuditDetails(){}
}
