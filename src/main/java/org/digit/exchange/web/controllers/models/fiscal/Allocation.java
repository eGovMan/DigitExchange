package org.digit.exchange.web.controllers.models;

import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

@Getter
@Setter
public class Allocation extends FiscalMessage {
    @JsonProperty("id")
    private String id;
    @JsonProperty("sanction_id")
    private String sanctionId;
    @JsonProperty("allotment_type")
    private String allotmentType;
    @JsonProperty("parent")
    private String parent;
    @NotNull
    private ZonedDateTime startDate;
    @NotNull
    private ZonedDateTime endDate;
    @JsonProperty("allocations")
    private List<Allocation> allocations;
    @JsonProperty("audit_details")
    private AuditDetails auditDetails;
    @JsonProperty("additional_details")
    private JsonNode additionalDetails;



    public Allocation(){}

    public Allocation(Sanction sanction, BigDecimal netAmount, BigDecimal grossAmount){
        super.copy(sanction);
        this.setType("allocation");
        this.setNetAmount(netAmount);    
        this.setGrossAmount(grossAmount);        
    }

    @JsonIgnore
    public BigDecimal getTotalNetAmount() {
        if (allocations != null && !allocations.isEmpty()) {
            return allocations.stream()
                           .map(Allocation::getNetAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getNetAmount();
        }
    }

    @JsonIgnore
    public BigDecimal getTotalGrossAmount() {
        if (allocations != null && !allocations.isEmpty()) {
            return allocations.stream()
                           .map(Allocation::getGrossAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getGrossAmount();
        }
    }
}
