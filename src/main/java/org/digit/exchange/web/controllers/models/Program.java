package org.digit.exchange.web.controllers.models;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Getter
@Setter
public class Program extends FiscalMessage {

    @JsonProperty("name")
    private String name;
    @JsonProperty("parent")
    private String parent;
    @JsonProperty("objectives")
    private String[] objectives;    
    @JsonProperty("programs")
    private List<Program> programs;
    @JsonProperty("meta")
    private Map<String,String> meta;    


    @JsonIgnore
    public BigDecimal getTotalAmount() {
        if (programs != null && !programs.isEmpty()) {
            return programs.stream()
                           .map(Program::getAmount)
                           .reduce(BigDecimal.ZERO, BigDecimal::add);
        } else {
            return getAmount();
        }
    }

}
