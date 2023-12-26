package org.digit.exchange.web.controllers.models;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Currency;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FiscalMessage {
    @NotNull
    @JsonProperty("version")
    private String version;
    @NotNull
    @JsonProperty("type")
    private String type;
    @JsonProperty("function")
    private String function;
    @JsonProperty("administration")
    private String administration;
    @JsonProperty("location")
    private String location;
    @JsonProperty("program")
    private String program;
    @JsonProperty("recipient_segment")
    private String recipientSegment;
    @JsonProperty("economic_segment")
    private String economicSegment;
    @JsonProperty("source_of_found")
    private String sourceOfFund;
    @JsonProperty("target_segment")
    private String targetSegment;
    @JsonProperty("start-date")
    private ZonedDateTime startDate;
    @JsonProperty("end-date")
    private ZonedDateTime endDate;
    @JsonProperty("amount")
    private BigDecimal amount;
    @JsonProperty("currency")
    private Currency currency;

    public FiscalMessage(){
        this.version = "1.0.0";
    }

    public void copy(FiscalMessage other){
        this.version = other.version;
        this.type = other.type;
        this.function = other.function;
        this.administration = other.administration;
        this.location = other.location;
        this.program = other.program;
        this.recipientSegment = other.recipientSegment;
        this.economicSegment = other.economicSegment;
        this.sourceOfFund = other.sourceOfFund;
        this.targetSegment = other.targetSegment;
        this.startDate = other.startDate;
        this.amount=other.amount;
        this.currency=other.currency;
    }
}
