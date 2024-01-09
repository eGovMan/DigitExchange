package org.digit.exchange.dto;

import lombok.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;


@Getter
@Setter
public class NewMessageDTO{
    @NotNull
    @JsonProperty("from")
    private String from;    

    @NotNull
    @JsonProperty("to")
    private String to;    

    @NotNull
    @JsonProperty("message")
    private String message;    

    public NewMessageDTO(){
    }
}
