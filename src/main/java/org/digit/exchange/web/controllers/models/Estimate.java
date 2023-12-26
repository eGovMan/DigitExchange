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
public class Estimate extends FiscalMessage {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("parent")
    private String parent;
    @NotNull
    private ZonedDateTime startDate;
    @NotNull
    private ZonedDateTime endDate;
    @JsonProperty("estimates")
    private List<Estimate> estimates;

    public Estimate(){}

    public Estimate(Program program, ZonedDateTime startDate, ZonedDateTime endDate, BigDecimal amount){
        super.copy(program);
        this.setType("estimate");
        this.startDate = startDate;
        this.endDate = endDate;
        this.setAmount(amount);        
    }

    @JsonIgnore
    public BigDecimal getTotalAmount() {
        if (estimates != null && !estimates.isEmpty()) {
            return estimates.stream()
                           .map(Estimate::getAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getAmount();
        }
    }
}
