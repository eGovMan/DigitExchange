package org.digit.exchange.controllers.finance;

import org.digit.exchange.constants.MessageType;
import org.digit.exchange.controllers.ExchangeController;
import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.models.*;
import org.digit.exchange.utils.ExchangeClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.UUID;


@Controller
@RequestMapping("/finance")
public class FinanceDepartmentController{
    private static final Logger logger = LoggerFactory.getLogger(ExchangeController.class);
    private final ExchangeClient exchange;

    public FinanceDepartmentController(ExchangeClient exchange) {
        this.exchange = exchange;
    }

    //Request to Create a Program
    @RequestMapping(value = "/program", method = RequestMethod.POST)
    public ResponseEntity<String> program(@RequestBody Program program) {
        logger.info("Creating new program");

        //
        Program programReply = new Program();
        programReply.copy(program);
        
        //Here is where trigger the creation of the Program.
        //For now assume program is created and program code is assigned
        //Set Program Code indicating Program has been created
        programReply.setProgramCode(program.getName().toUpperCase());

        //Tell line department that program has been created.
        try {
            String to = "line@http://127.0.0.1:8080";
            String from ="finance@http://127.0.0.1:8080";
            MessageType messageType = MessageType.PROGRAM;
            String url = "http://127.0.0.1:8080/exchange/v1/on-program";
            return exchange.send(url,to,from, programReply, messageType);

        } catch (RuntimeException e) {
            // Return a ResponseEntity with an appropriate message and HTTP status
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing Program Request");
        }
    }

    //Request to Create a Estimate
    @RequestMapping(value = "/estimate", method = RequestMethod.POST)
    public ResponseEntity<String> estimate(@RequestBody Estimate estimate) {
        logger.info("Creating new estimate");

        //Set Estimate Id indicating Estimate has been created
        UUID estimateId =  UUID.randomUUID();
        estimate.setId(estimateId.toString());

        Estimate estimateReply = new Estimate();
        estimateReply.copy(estimate);

        try {
            String to = "line@http://127.0.0.1:8080";
            String from ="finance@http://127.0.0.1:8080";
            MessageType messageType = MessageType.ESTIMATE;
            String url = "http://127.0.0.1:8080/exchange/v1/on-estimate";
            return exchange.send(url,to,from, estimateReply, messageType);

        } catch (RuntimeException e) {
            // Return a ResponseEntity with an appropriate message and HTTP status
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing Estimate Request");
        }

    }

    //Request to Create a Sanction
    @RequestMapping(value = "/sanction", method = RequestMethod.POST)
    public ResponseEntity<String> sanction(@RequestBody Sanction sanction) {
        logger.info("Creating new sanction");

        Sanction sanctionReply= new Sanction();
        sanctionReply.copy(sanction);

        try {
            String to = "line@http://127.0.0.1:8080";
            String from ="finance@http://127.0.0.1:8080";
            MessageType messageType = MessageType.SANCTION;
            String url = "http://127.0.0.1:8080/exchange/v1/on-sanction";
            return exchange.send(url,to,from, sanctionReply, messageType);

        } catch (RuntimeException e) {
            // Return a ResponseEntity with an appropriate message and HTTP status
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing Sanction Request");
        }

    }

    //Request to Create a Allocation
    @RequestMapping(value = "/allocation", method = RequestMethod.POST)
    public ResponseEntity<String> allocation(@RequestBody Allocation allocation) {
        logger.info("Creating new sanction");

        Allocation allocationReply= new Allocation();
        allocationReply.copy(allocation);

        ObjectMapper objectMapper = new ObjectMapper();
        // Create a new ObjectNode (which is a type of JsonNode)
        ObjectNode jsonNode = objectMapper.createObjectNode();
        // Add a note attribute
        jsonNode.put("note", "Allocation is approved");
        allocation.setAdditionalDetails(jsonNode);

        try {
            String to = "line@http://127.0.0.1:8080";
            String from ="finance@http://127.0.0.1:8080";
            MessageType messageType = MessageType.ALLOCATION;
            String url = "http://127.0.0.1:8080/exchange/v1/on-allocation";
            return exchange.send(url,to,from, allocationReply, messageType);

        } catch (RuntimeException e) {
            // Return a ResponseEntity with an appropriate message and HTTP status
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing Allocation Request");
        }
    }

    //Request to Create a Disbursement
    @RequestMapping(value = "/disburse", method = RequestMethod.POST)
    public ResponseEntity<String> disburse(@RequestBody Disbursement bill) {
        logger.info("Creating new Disburse Request");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String billAsString = objectMapper.writeValueAsString(bill);
            return ResponseEntity.ok(billAsString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new CustomException("Internal Server Error.", e);
        }    
    }

}