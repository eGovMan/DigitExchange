package org.digit.exchange.model.messages;

import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.utils.ZonedDateTimeConverter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Getter
@Setter
@Embeddable
public class Disbursement extends ExchangeMessage {
    @JsonProperty("individual")
    private Individual individual;
    @NotNull
    @Convert(converter = ZonedDateTimeConverter.class)
    @JsonProperty("disbursement_date")
    private ZonedDateTime disbursementDate;
    @JsonProperty("allocation_id")
    private String allocationId;
    @JsonProperty("disbursement_count")
    private int disbursementCount;
    @JsonProperty("disbursements")
    private List<Disbursement> disbursements;

    public Disbursement(){
    }

    public Disbursement(Allocation allocation, BigDecimal netAmount, BigDecimal grossAmount){
        super.copy(allocation);
        this.setDisbursementCount(0);
        this.setNetAmount(netAmount);        
        this.setNetAmount(grossAmount);        
    }

    @JsonIgnore
    public BigDecimal getTotalNetAmount() {
        if (disbursements != null && !disbursements.isEmpty()) {
            return disbursements.stream()
                           .map(Disbursement::getNetAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getNetAmount();
        }
    }

    @JsonIgnore
    public BigDecimal getTotalGrossAmount() {
        if (disbursements != null && !disbursements.isEmpty()) {
            return disbursements.stream()
                           .map(Disbursement::getGrossAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getGrossAmount();
        }
    }

    @JsonIgnore
    public int getBillCount() {
        if (disbursements != null && !disbursements.isEmpty()) {
            return 1;//getBills().length;
        } else {
            return disbursementCount;
        }
    }

    static public Disbursement fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, Disbursement.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing Bill fromString", e);
		}
	}
}
