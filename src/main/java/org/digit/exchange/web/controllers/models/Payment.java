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
public class Payment extends FiscalMessage {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @NotNull
    private ZonedDateTime startDate;
    @NotNull
    private ZonedDateTime endDate;
    @JsonProperty("payments")
    private List<Payment> payments;


    public Payment(){}

    public Payment(Estimate estimate, BigDecimal amount){
        super.copy(estimate);
        this.setType("payment");
        this.setAmount(amount);        
    }

    @JsonIgnore
    public BigDecimal getTotalAmount() {
        if (payments != null && !payments.isEmpty()) {
            return payments.stream()
                           .map(Payment::getAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getAmount();
        }
    }
}
