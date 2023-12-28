package org.digit.exchange.controllers;

import org.digit.exchange.constants.Action;
import org.digit.exchange.models.*;
import org.digit.exchange.models.fiscal.Allocation;
import org.digit.exchange.models.fiscal.Bill;
import org.digit.exchange.models.fiscal.Estimate;
import org.digit.exchange.models.fiscal.Payee;
import org.digit.exchange.models.fiscal.Program;
import org.digit.exchange.models.fiscal.Sanction;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

// import jakarta.servlet.http.HttpServletRequest;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.PostMapping;

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

    private final WebClient webClient;
    

    public LineDepartmentController() {
        this.webClient = WebClient.create();
    }

    //Send Request to Finance Department via DIGIT Exchange
    @RequestMapping(value = "/program/create", method = RequestMethod.POST)
    public ResponseEntity<String> program_create(@RequestBody Program program) {
        logger.info("Sending request for creating program : " + program.getName().toString());

        String to = "finance@http://127.0.0.1:8080";
        String from ="line@http://127.0.0.1:8080";
        Action action = Action.create;
        RequestMessage exchangeMessage = new RequestMessage(to,from,program,action);

        //Ask exchange to send the program
        Mono<String> responseMono = webClient.post()
                    .uri("http://127.0.0.1:8080/exchange/v1/program")
                    .body(Mono.just(exchangeMessage), RequestMessage.class)
                    .retrieve()
                    .bodyToMono(String.class);
        responseMono.block();
        return ResponseEntity.ok("");
    }

    //on_program Create Handler
    @RequestMapping(value = "on-program/create", method = RequestMethod.POST)
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
        
        String to = "finance@http://127.0.0.1:8080";
        String from ="line@http://127.0.0.1:8080";
        Action action = Action.create;
        RequestMessage exchangeMessage = new RequestMessage(to,from,estimate,action);

        //Make the request
        Mono<String> responseMono = webClient.post()
                    .uri("http://127.0.0.1:8080/exchange/v1/estimate")
                    .body(Mono.just(exchangeMessage), RequestMessage.class)
                    .retrieve()
                    .bodyToMono(String.class);
        responseMono.block();

        return ResponseEntity.ok("");
    }

    //on_estimate Create Handler
    @RequestMapping(value = "on-estimate/create", method = RequestMethod.POST)
    public ResponseEntity<String> onestimate_create(@RequestBody Estimate estimate) {
        logger.info("Estimate created with Id:" + estimate.getId());
        
        BigDecimal netAmount = new BigDecimal(10);
        BigDecimal grossAmount = new BigDecimal(10);

        Sanction sanction = new Sanction(estimate,netAmount,grossAmount);
        
        String to = "finance@http://127.0.0.1:8080";
        String from ="line@http://127.0.0.1:8080";
        Action action = Action.create;
        RequestMessage exchangeMessage = new RequestMessage(to,from,sanction,action);

        //Make the request
        Mono<String> responseMono = webClient.post()
                    .uri("http://127.0.0.1:8080/exchange/v1/sanction")
                    .body(Mono.just(exchangeMessage), RequestMessage.class)
                    .retrieve()
                    .bodyToMono(String.class);
        responseMono.block();

        return ResponseEntity.ok("");
    }

    //on_sanction Create Handler
    @RequestMapping(value = "on-sanction/create", method = RequestMethod.POST)
    public ResponseEntity<String> onsanction_create(@RequestBody Sanction sanction) {
        logger.info("Sanction created with Id:" + sanction.getId());
        
        BigDecimal netAmount = new BigDecimal(10);
        BigDecimal grossAmount = new BigDecimal(10);
        Allocation allocation = new Allocation(sanction,netAmount,grossAmount);
        
        String to = "finance@http://127.0.0.1:8080";
        String from ="line@http://127.0.0.1:8080";
        Action action = Action.create;
        RequestMessage exchangeMessage = new RequestMessage(to,from,allocation,action);

        //Make the request
        Mono<String> responseMono = webClient.post()
                    .uri("http://127.0.0.1:8080/exchange/v1/allocation")
                    .body(Mono.just(exchangeMessage), RequestMessage.class)
                    .retrieve()
                    .bodyToMono(String.class);
        responseMono.block();

        return ResponseEntity.ok("");
    }

    //on_allocation Create Handler
    @RequestMapping(value = "on-allocation/create", method = RequestMethod.POST)
    public ResponseEntity<String> onallocation_create(@RequestBody Allocation allocation) {
        logger.info("Sanction created with Id:" + allocation.getId());
        
        BigDecimal netAmount = new BigDecimal(10);
        BigDecimal grossAmount = new BigDecimal(10);
        Bill bill = new Bill(allocation,netAmount,grossAmount);
        bill.setAllocation(allocation);
        List<Bill> bills = new ArrayList<Bill>();
        for(int i=0;i<=10;i++){
            Bill childBill = new Bill();
            Payee payee = new Payee();
            payee.setId(String.valueOf(i));
            payee.setName("Shailesh");
            childBill.setNetAmount(new BigDecimal("100"));
            childBill.setPayee(payee);
            bills.add(childBill);
        }
        bill.setBills(bills);
        
        String to = "finance@http://127.0.0.1:8080";
        String from ="line@http://127.0.0.1:8080";
        Action action = Action.create;
        RequestMessage exchangeMessage = new RequestMessage(to,from,bill,action);

        //Make the request
        Mono<String> responseMono = webClient.post()
                    .uri("http://127.0.0.1:8080/exchange/v1/disburse")
                    .body(Mono.just(exchangeMessage), RequestMessage.class)
                    .retrieve()
                    .bodyToMono(String.class);
        responseMono.block();
        return ResponseEntity.ok("");
    }


}
