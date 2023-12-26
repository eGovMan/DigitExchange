package org.digit.exchange.web.controllers.models;

import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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


    public Allocation(){}

    public Allocation(Sanction sanction, BigDecimal amount){
        super.copy(sanction);
        this.setType("allocation");
        this.setAmount(amount);        
    }

    @JsonIgnore
    public BigDecimal getTotalAmount() {
        if (allocations != null && !allocations.isEmpty()) {
            return allocations.stream()
                           .map(Allocation::getAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getAmount();
        }
    }
}
