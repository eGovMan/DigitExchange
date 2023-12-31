package org.digit.exchange.controllers;

import org.digit.exchange.exceptions.ResourceNotFoundException;
import org.digit.exchange.models.*;
import org.digit.exchange.service.ExchangeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/exchange/v1")
@CrossOrigin(origins = "http://localhost:52546")
public class ExchangeController{

    private final ExchangeService service;

    public ExchangeController(ExchangeService service) {
        this.service = service;
    }

    // @Operation(summary = "Send program related Requests", description = "Send request to create or update a program.")
    @RequestMapping(value = "/program", method = RequestMethod.POST)
    public ResponseEntity<RequestMessage> program(@RequestBody RequestMessage message) {
        try {
            return ResponseEntity.ok(service.send(message, false));            
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // @Operation(summary = "Send responses for program related Requests.", description = "Send responses to create or update a program.")
    @RequestMapping(value = "/on-program", method = RequestMethod.POST)
    public ResponseEntity<RequestMessage> onprogram(@RequestBody RequestMessage message) {
        try {
            return ResponseEntity.ok(service.send(message,true));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Send estimate related Requests", description = "Send request to create or update a estimate.")
    @RequestMapping(value = "/estimate", method = RequestMethod.POST)
    public ResponseEntity<RequestMessage> estimate(@RequestBody RequestMessage message) {
        try {
            return ResponseEntity.ok(service.send(message,false));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Send responses for estimate related Requests.", description = "Send responses to create or update a estimate.")
    @RequestMapping(value = "/on-estimate", method = RequestMethod.POST)
    public ResponseEntity<RequestMessage> onestimate(@RequestBody RequestMessage message) {
        try {
            return ResponseEntity.ok(service.send(message,true));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Send sanction related Requests", description = "Send request to create or update a sanction.")
    @RequestMapping(value = "/sanction", method = RequestMethod.POST)
    public ResponseEntity<RequestMessage> sanction(@RequestBody RequestMessage message) {
        try {
            return ResponseEntity.ok(service.send(message,false));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Send responses for sanction related Requests.", description = "Send responses to create or update a sanction.")
    @RequestMapping(value = "/on-sanction", method = RequestMethod.POST)
    public ResponseEntity<RequestMessage> onsanction(@RequestBody RequestMessage message) {
        try {
            return ResponseEntity.ok(service.send(message,true));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }    
    
    // @Operation(summary = "Send allocation related Requests", description = "Send request to create or update a allocation.")
    @RequestMapping(value = "/allocation", method = RequestMethod.POST)
    public ResponseEntity<RequestMessage> allocation(@RequestBody RequestMessage message) {
        try {
            return ResponseEntity.ok(service.send(message,false));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Send responses for allocation related Requests.", description = "Send responses to create or update a allocation.")
    @RequestMapping(value = "/on-allocation", method = RequestMethod.POST)
    public ResponseEntity<RequestMessage> onallocation(@RequestBody RequestMessage message) {
        try {
            return ResponseEntity.ok(service.send(message,true));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Send disburesment related Requests", description = "Send request to create or update a disburesment.")
    @RequestMapping(value = "/disburse", method = RequestMethod.POST)
    public ResponseEntity<RequestMessage> disburse(@RequestBody RequestMessage message) {
        try {
            return ResponseEntity.ok(service.send(message,false));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Send responses for disbursement related Requests.", description = "Send responses to create or update a disbursement.")
    @RequestMapping(value = "/on-disburse", method = RequestMethod.POST)
    public ResponseEntity<RequestMessage> ondisburse(@RequestBody RequestMessage message) {
        try {
            return ResponseEntity.ok(service.send(message,true));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Send responses for demand related Requests.", description = "Send responses to create or update a demand.")
    @RequestMapping(value = "/demand", method = RequestMethod.POST)
    public ResponseEntity<RequestMessage> demand(@RequestBody RequestMessage message) {
        try {
            return ResponseEntity.ok(service.send(message,false));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Send responses for demand related Requests.", description = "Send responses to create or update a demand.")
    @RequestMapping(value = "/on-demand", method = RequestMethod.POST)
    public ResponseEntity<RequestMessage> ondemand(@RequestBody RequestMessage message) {
        try {
            return ResponseEntity.ok(service.send(message,true));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Send receipt related Requests", description = "Send request to create or update a receipt.")
    @RequestMapping(value = "/receipt", method = RequestMethod.POST)
    public ResponseEntity<RequestMessage> receipt(@RequestBody RequestMessage message) {
        try {
            return ResponseEntity.ok(service.send(message,false));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Send responses for receipt related Requests.", description = "Send responses to create or update a receipt.")
    @RequestMapping(value = "/on-receipt", method = RequestMethod.POST)
    public ResponseEntity<RequestMessage> onreceipt(@RequestBody RequestMessage message) {
        try {
            return ResponseEntity.ok(service.send(message,true));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @RequestMapping(value = "/individual", method = RequestMethod.POST)
    public ResponseEntity<RequestMessage> individual(@RequestBody RequestMessage message) {
        try {
            return ResponseEntity.ok(service.send(message,false));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Send responses for individual related Requests.", description = "Send responses to create or update a individual.")
    @RequestMapping(value = "/on-individual", method = RequestMethod.POST)
    public ResponseEntity<RequestMessage> onindividual(@RequestBody RequestMessage message) {
        try {
            return ResponseEntity.ok(service.send(message,true));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }    

    @RequestMapping(value = "/organisation", method = RequestMethod.POST)
    public ResponseEntity<RequestMessage> organisation(@RequestBody RequestMessage message) {
        try {
            return ResponseEntity.ok(service.send(message,false));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Send responses for individual related Requests.", description = "Send responses to create or update a individual.")
    @RequestMapping(value = "/on-organisation", method = RequestMethod.POST)
    public ResponseEntity<RequestMessage> onorganisation(@RequestBody RequestMessage message) {
        try {
            return ResponseEntity.ok(service.send(message,true));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }    

    // @Operation(summary = "Retrieves request messages for a user.", description = "Retrieves request messages for a user.")
    @RequestMapping(value = "/inbox", method = RequestMethod.POST)
    public ResponseEntity<Page<RequestMessage>> inbox(@RequestBody InboxRequest message) {
        try {
            Page<RequestMessage> result = service.findByReceiverId(message);
            return ResponseEntity.ok(result);
        } catch (ResourceNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Retrieves request messages sent by a user.", description = "Retrieves request messages sent by a user.")
    @RequestMapping(value = "/sentitems", method = RequestMethod.POST)
    public ResponseEntity<Page<RequestMessage>> sentItems(String senderId, Pageable pageable) {
        return ResponseEntity.ok(service.findBySenderId(senderId, pageable));
    }

}
