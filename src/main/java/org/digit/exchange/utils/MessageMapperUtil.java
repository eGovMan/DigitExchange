package org.digit.exchange.utils;

import org.digit.exchange.models.Allocation;
import org.digit.exchange.models.Disbursement;
import org.digit.exchange.models.Demand;
import org.digit.exchange.models.Estimate;
import org.digit.exchange.models.ExchangeMessage;
import org.digit.exchange.models.Individual;
import org.digit.exchange.models.Program;
import org.digit.exchange.models.Receipt;
import org.digit.exchange.models.Sanction;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.JsonProcessingException;


@Service
public class MessageMapperUtil {

    public MessageMapperUtil() {
    }

    public ExchangeMessage formatMessage(String messageType, String message) {
        
        if(messageType.equalsIgnoreCase("program")){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            try {
                Program program = mapper.readValue(message, Program.class);
                return program;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else if(messageType.equals("estimate")){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            try {
                Estimate estimate = mapper.readValue(message, Estimate.class);
                return estimate;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else if(messageType.equals("sanction")){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            try {
                Sanction sanction = mapper.readValue(message, Sanction.class);
                return sanction;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else if(messageType.equals("allocation")){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            try {
                Allocation allocation = mapper.readValue(message, Allocation.class);
                return allocation;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else if(messageType.equals("bill")){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            try {
                Disbursement bill = mapper.readValue(message, Disbursement.class);
                return bill;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else if(messageType.equals("demand")){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            try {
                Demand demand = mapper.readValue(message, Demand.class);
                return demand;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else if(messageType.equals("receipt")){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            try {
                Receipt receipt = mapper.readValue(message, Receipt.class);
                return receipt;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
