package org.digit.exchange.model;

import org.digit.exchange.exceptions.CustomException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {
    
    @JsonProperty("search_string")
    private String searchString;

    @JsonProperty("page")
    private int page;

    @JsonProperty("size")
    private int size;
    
    static public SearchRequest fromString(String json){
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
        return mapper.readValue(json, SearchRequest.class);
        } catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing Address fromString", e);
	    }
    }
}
