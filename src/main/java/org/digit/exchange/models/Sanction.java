package org.digit.exchange.models;

import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import org.digit.exchange.constants.MessageType;
import org.digit.exchange.exceptions.CustomException;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Getter
@Setter
@Embeddable
public class Sanction extends ExchangeMessage {
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
        this.setMessageType(MessageType.SANCTION);
    }

    public Sanction(Estimate estimate, BigDecimal netAmount, BigDecimal grossAmount){
        super.copy(estimate);
        this.setMessageType(MessageType.SANCTION);
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

    static public Sanction fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, Sanction.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing Sanction fromString", e);
		}
	}

}
