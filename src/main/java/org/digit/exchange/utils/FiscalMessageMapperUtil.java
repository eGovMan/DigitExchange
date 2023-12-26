package org.digit.exchange.utils;

import org.springframework.stereotype.Service;
import org.digit.exchange.web.controllers.models.Allocation;
import org.digit.exchange.web.controllers.models.Demand;
import org.digit.exchange.web.controllers.models.Estimate;
import org.digit.exchange.web.controllers.models.FiscalMessage;
import org.digit.exchange.web.controllers.models.Program;
import org.digit.exchange.web.controllers.models.Receipt;
import org.digit.exchange.web.controllers.models.Sanction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.core.JsonProcessingException;


@Service
public class FiscalMessageMapperUtil {

    public FiscalMessageMapperUtil() {
    }

    public FiscalMessage formatMessage(String fiscalMessageType, String fiscalMessage) {
        
        if(fiscalMessageType.equalsIgnoreCase("program")){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            try {
                Program program = mapper.readValue(fiscalMessage, Program.class);
                return program;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else if(fiscalMessageType.equals("estimate")){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            try {
                Estimate estimate = mapper.readValue(fiscalMessage, Estimate.class);
                return estimate;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else if(fiscalMessageType.equals("sanction")){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            try {
                Sanction sanction = mapper.readValue(fiscalMessage, Sanction.class);
                return sanction;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else if(fiscalMessageType.equals("allocation")){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            try {
                Allocation allocation = mapper.readValue(fiscalMessage, Allocation.class);
                return allocation;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else if(fiscalMessageType.equals("demand")){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            try {
                Demand demand = mapper.readValue(fiscalMessage, Demand.class);
                return demand;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }else if(fiscalMessageType.equals("receipt")){
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());

            try {
                Receipt receipt = mapper.readValue(fiscalMessage, Receipt.class);
                return receipt;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
