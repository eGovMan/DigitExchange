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
public class Demand extends FiscalMessage {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @NotNull
    private ZonedDateTime startDate;
    @NotNull
    private ZonedDateTime endDate;
    @JsonProperty("demands")
    private List<Demand> demands;


    public Demand(){}

    public Demand(Estimate estimate, BigDecimal amount){
        super.copy(estimate);
        this.setType("demand");
        this.setAmount(amount);        
    }

    @JsonIgnore
    public BigDecimal getTotalAmount() {
        if (demands != null && !demands.isEmpty()) {
            return demands.stream()
                           .map(Demand::getAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getAmount();
        }
    }
}
