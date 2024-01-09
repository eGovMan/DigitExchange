package org.digit.fix.model;

import lombok.*;

import java.math.BigDecimal;
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
public class Demand extends FiscalData {
    @JsonProperty("demands")
    private List<Demand> demands;


    public Demand(){
    }

    public Demand(Estimate estimate, BigDecimal netAmount, BigDecimal grossAmount){
        super.copy(estimate);
        this.setNetAmount(netAmount);        
        this.setGrossAmount(grossAmount);        
    }

    @JsonIgnore
    public BigDecimal getTotalNetAmount() {
        if (demands != null && !demands.isEmpty()) {
            return demands.stream()
                           .map(Demand::getNetAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getNetAmount();
        }
    }

    @JsonIgnore
    public BigDecimal getTotalGrossAmount() {
        if (demands != null && !demands.isEmpty()) {
            return demands.stream()
                           .map(Demand::getGrossAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getGrossAmount();
        }
    }

    static public Demand fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, Demand.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing Demand fromString", e);
		}
	}
}
