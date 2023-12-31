package org.digit.exchange.controllers.line;

import org.digit.exchange.constants.MessageType;
import org.digit.exchange.controllers.ExchangeController;
import org.digit.exchange.models.*;
import org.digit.exchange.utils.ExchangeClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/line")
public class LineDepartmentController{

    private static final Logger logger = LoggerFactory.getLogger(ExchangeController.class);

    private final ExchangeClient exchange;

    public LineDepartmentController(ExchangeClient exchange) {
        this.exchange = exchange;
    }

    //Send Request to Finance Department via DIGIT Exchange
    @RequestMapping(value = "/program", method = RequestMethod.POST)
    public ResponseEntity<String> program(@RequestBody Program program) {
        logger.info("Sending request for creating program : " + program.getName().toString());

        try {
            String to = "finance@http://127.0.0.1:8080";
            String from ="line@http://127.0.0.1:8080";
            MessageType messageType = MessageType.PROGRAM;
            String url = "http://127.0.0.1:8080/exchange/v1/program";
            return exchange.send(url,to,from, program, messageType);

        } catch (RuntimeException e) {
            // Return a ResponseEntity with an appropriate message and HTTP status
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("LineDepartment:program:Error processing Program Request");
        }
    }

    //on_program Create Handler
    @RequestMapping(value = "on-program", method = RequestMethod.POST)
    public ResponseEntity<String> onprogram_create(@RequestBody Program program) {
        logger.info("Program created with Code:" + program.getProgramCode());
        
        ZoneId zoneId = ZoneId.of("Asia/Kolkata"); // Set the desired time zone
        ZonedDateTime startDate = LocalDateTime.of(2023, 1, 1, 0, 0, 0)
                                               .atZone(zoneId);
        ZonedDateTime endDate = LocalDateTime.of(2023, 12, 31, 23, 59, 59)
                                               .atZone(zoneId);
    
        //Request for Budget by submitting an Estimate
        BigDecimal netAmount = new BigDecimal(100);
        BigDecimal grossAmount = new BigDecimal(100);
        Estimate estimate = new Estimate(program,startDate,endDate,netAmount,grossAmount);
        
        try {
            String to = "finance@http://127.0.0.1:8080";
            String from ="line@http://127.0.0.1:8080";
            MessageType messageType = MessageType.ESTIMATE;
            String url = "http://127.0.0.1:8080/exchange/v1/estimate";
            return exchange.send(url,to,from, estimate, messageType);

        } catch (RuntimeException e) {
            // Return a ResponseEntity with an appropriate message and HTTP status
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("LineDepartment:Error processing OnProgram Request");
        }
    }

    //on_estimate Create Handler
    @RequestMapping(value = "on-estimate", method = RequestMethod.POST)
    public ResponseEntity<String> onestimate_create(@RequestBody Estimate estimate) {
        logger.info("Estimate created with Id:" + estimate.getId());
        
        BigDecimal netAmount = new BigDecimal(10);
        BigDecimal grossAmount = new BigDecimal(10);

        Sanction sanction = new Sanction(estimate,netAmount,grossAmount);
        
        try {
            String to = "finance@http://127.0.0.1:8080";
            String from ="line@http://127.0.0.1:8080";
            MessageType messageType = MessageType.SANCTION;
            String url = "http://127.0.0.1:8080/exchange/v1/sanction";
            return exchange.send(url,to,from, sanction, messageType);

        } catch (RuntimeException e) {
            // Return a ResponseEntity with an appropriate message and HTTP status
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("LineDepartment:Error processing OnEstimate Request");
        }
    }

    //on_sanction Create Handler
    @RequestMapping(value = "on-sanction", method = RequestMethod.POST)
    public ResponseEntity<String> onsanction_create(@RequestBody Sanction sanction) {
        logger.info("Sanction created with Id:" + sanction.getId());
        
        BigDecimal netAmount = new BigDecimal(10);
        BigDecimal grossAmount = new BigDecimal(10);
        Allocation allocation = new Allocation(sanction,netAmount,grossAmount);
        
        try {
            String to = "finance@http://127.0.0.1:8080";
            String from ="line@http://127.0.0.1:8080";
            MessageType messageType = MessageType.ALLOCATION;
            String url = "http://127.0.0.1:8080/exchange/v1/allocation";
            return exchange.send(url,to,from, allocation, messageType);

        } catch (RuntimeException e) {
            // Return a ResponseEntity with an appropriate message and HTTP status
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("LineDepartment:Error processing OnSanction Request");
        }
    }

    //on_allocation Create Handler
    @RequestMapping(value = "on-allocation", method = RequestMethod.POST)
    public ResponseEntity<String> onallocation_create(@RequestBody Allocation allocation) {
        logger.info("Sanction created with Id:" + allocation.getId());
        
        BigDecimal netAmount = new BigDecimal(10);
        BigDecimal grossAmount = new BigDecimal(10);
        Disbursement bill = new Disbursement(allocation,netAmount,grossAmount);
        bill.setAllocation(allocation);
        List<Disbursement> bills = new ArrayList<Disbursement>();
        for(int i=0;i<=10;i++){
            Disbursement childBill = new Disbursement();
            Individual individual = new Individual();
            individual.setId(String.valueOf(i));
            List<LanguageValue> names = new ArrayList<LanguageValue>();
            LanguageValue lv = new LanguageValue();
            lv.setLanguage("eng");
            lv.setValue("Shailesh");
            names.add(lv);
            individual.setName(names);
            childBill.setNetAmount(new BigDecimal("100"));
            childBill.setIndividual(individual);
            bills.add(childBill);
        }
        bill.setBills(bills);
        
        try {
            String to = "finance@http://127.0.0.1:8080";
            String from ="line@http://127.0.0.1:8080";
            MessageType messageType = MessageType.DISBURSEMENT;
            String url = "http://127.0.0.1:8080/exchange/v1/disburse";
            return exchange.send(url,to,from, bill, messageType);

        } catch (RuntimeException e) {
            // Return a ResponseEntity with an appropriate message and HTTP status
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("LineDepartment: Error processing OnAllocation Request");
        }
    }


}
