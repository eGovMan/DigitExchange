package org.digit.exchange.models.fiscal;

import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

@Getter
@Setter
public class Sanction extends FiscalMessage {
    @JsonProperty("name")
    private String name;
    @NotNull
    private ZonedDateTime startDate;
    @NotNull
    private ZonedDateTime endDate;
    @JsonProperty("program")
    private Program program;
    @JsonProperty("sanctions")
    private List<Sanction> sanctions;
    @JsonProperty("audit_details")
    private AuditDetails auditDetails;
    @JsonProperty("additional_details")
    private JsonNode additionalDetails;

    public Sanction(){
        this.setFiscalMessageType( this.getClass().getSimpleName().toLowerCase());
    }

    public Sanction(Estimate estimate, BigDecimal netAmount, BigDecimal grossAmount){
        super.copy(estimate);
        this.setFiscalMessageType( this.getClass().getSimpleName().toLowerCase());
        this.setProgram(estimate.getProgram());
        this.setNetAmount(netAmount);        
        this.setGrossAmount(grossAmount);        
    }

    @JsonIgnore
    public BigDecimal getTotalNetAmount() {
        if (sanctions != null && !sanctions.isEmpty()) {
            return sanctions.stream()
                           .map(Sanction::getNetAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getNetAmount();
        }
    }

    @JsonIgnore
    public BigDecimal getTotalGrossAmount() {
        if (sanctions != null && !sanctions.isEmpty()) {
            return sanctions.stream()
                           .map(Sanction::getGrossAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getNetAmount();
        }
    }

}
