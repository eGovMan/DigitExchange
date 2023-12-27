package org.digit.exchange.models;

import lombok.*;

import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;


@Getter
@Setter
@Entity
public class RequestMessage2{

    @Id
    @NotNull
    private String id;
    @JsonProperty("signature")
    private String signature;

    public RequestMessage2(){
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
    }
}
