package org.digit.exchange.model.messages;

import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

import org.digit.exchange.exceptions.CustomException;

import jakarta.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Getter
@Setter
@Embeddable
public class Estimate extends ExchangeMessage {
    @JsonProperty("estimates")
    private List<Estimate> estimates;

    public Estimate(){
    }

    public Estimate(Program program, ZonedDateTime startDate, ZonedDateTime endDate, BigDecimal netAmount, BigDecimal grossAmount){
        super.copy(program);
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.setNetAmount(netAmount);        
        this.setGrossAmount(netAmount);        
    }

    @JsonIgnore
    public BigDecimal getTotalNetAmount() {
        if (estimates != null && !estimates.isEmpty()) {
            return estimates.stream()
                           .map(Estimate::getNetAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getNetAmount();
        }
    }

    @JsonIgnore
    public BigDecimal getTotalGrossAmount() {
        if (estimates != null && !estimates.isEmpty()) {
            return estimates.stream()
                           .map(Estimate::getGrossAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getGrossAmount();
        }
    }

    static public Estimate fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, Estimate.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing Estimate fromString", e);
		}
	}
}
