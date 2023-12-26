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
public class Receipt extends FiscalMessage {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @NotNull
    private ZonedDateTime startDate;
    @NotNull
    private ZonedDateTime endDate;
    @JsonProperty("reciepts")
    private List<Receipt> reciepts;


    public Receipt(){}

    public Receipt(Demand demand, BigDecimal amount){
        super.copy(demand);
        this.setType("reciept");
        this.setAmount(amount);        
    }

    @JsonIgnore
    public BigDecimal getTotalAmount() {
        if (reciepts != null && !reciepts.isEmpty()) {
            return reciepts.stream()
                           .map(Receipt::getAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getAmount();
        }
    }
}
