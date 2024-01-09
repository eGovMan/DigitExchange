package org.digit.fix.controller;

import org.digit.exchange.exceptions.ResourceNotFoundException;
import org.digit.exchange.model.Message;
import org.digit.fix.service.FIXService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/fix/v1")
public class FIXController{

    private final FIXService service;

    public FIXController(FIXService service) {
        this.service = service;
    }

    // @Operation(summary = "Send program related Messages", description = "Send message to create or update a program.")
    @RequestMapping(value = "/program", method = RequestMethod.POST)
    public ResponseEntity<Message> program(@RequestBody Message message) {
        try {
            return ResponseEntity.ok(service.send(message, false));            
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // @Operation(summary = "Send responses for program related Messages.", description = "Send responses to create or update a program.")
    @RequestMapping(value = "/on-program", method = RequestMethod.POST)
    public ResponseEntity<Message> onprogram(@RequestBody Message message) {
        try {
            return ResponseEntity.ok(service.send(message,true));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Send estimate related Messages", description = "Send message to create or update a estimate.")
    @RequestMapping(value = "/estimate", method = RequestMethod.POST)
    public ResponseEntity<Message> estimate(@RequestBody Message message) {
        try {
            return ResponseEntity.ok(service.send(message,false));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Send responses for estimate related Messages.", description = "Send responses to create or update a estimate.")
    @RequestMapping(value = "/on-estimate", method = RequestMethod.POST)
    public ResponseEntity<Message> onestimate(@RequestBody Message message) {
        try {
            return ResponseEntity.ok(service.send(message,true));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Send sanction related Messages", description = "Send message to create or update a sanction.")
    @RequestMapping(value = "/sanction", method = RequestMethod.POST)
    public ResponseEntity<Message> sanction(@RequestBody Message message) {
        try {
            return ResponseEntity.ok(service.send(message,false));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Send responses for sanction related Messages.", description = "Send responses to create or update a sanction.")
    @RequestMapping(value = "/on-sanction", method = RequestMethod.POST)
    public ResponseEntity<Message> onsanction(@RequestBody Message message) {
        try {
            return ResponseEntity.ok(service.send(message,true));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }    
    
    // @Operation(summary = "Send allocation related Messages", description = "Send message to create or update a allocation.")
    @RequestMapping(value = "/allocation", method = RequestMethod.POST)
    public ResponseEntity<Message> allocation(@RequestBody Message message) {
        try {
            return ResponseEntity.ok(service.send(message,false));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Send responses for allocation related Messages.", description = "Send responses to create or update a allocation.")
    @RequestMapping(value = "/on-allocation", method = RequestMethod.POST)
    public ResponseEntity<Message> onallocation(@RequestBody Message message) {
        try {
            return ResponseEntity.ok(service.send(message,true));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Send disburesment related Messages", description = "Send message to create or update a disburesment.")
    @RequestMapping(value = "/disburse", method = RequestMethod.POST)
    public ResponseEntity<Message> disburse(@RequestBody Message message) {
        try {
            return ResponseEntity.ok(service.send(message,false));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Send responses for disbursement related Messages.", description = "Send responses to create or update a disbursement.")
    @RequestMapping(value = "/on-disburse", method = RequestMethod.POST)
    public ResponseEntity<Message> ondisburse(@RequestBody Message message) {
        try {
            return ResponseEntity.ok(service.send(message,true));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Send responses for demand related Messages.", description = "Send responses to create or update a demand.")
    @RequestMapping(value = "/demand", method = RequestMethod.POST)
    public ResponseEntity<Message> demand(@RequestBody Message message) {
        try {
            return ResponseEntity.ok(service.send(message,false));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Send responses for demand related Messages.", description = "Send responses to create or update a demand.")
    @RequestMapping(value = "/on-demand", method = RequestMethod.POST)
    public ResponseEntity<Message> ondemand(@RequestBody Message message) {
        try {
            return ResponseEntity.ok(service.send(message,true));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Send receipt related Messages", description = "Send message to create or update a receipt.")
    @RequestMapping(value = "/receipt", method = RequestMethod.POST)
    public ResponseEntity<Message> receipt(@RequestBody Message message) {
        try {
            return ResponseEntity.ok(service.send(message,false));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // @Operation(summary = "Send responses for receipt related Messages.", description = "Send responses to create or update a receipt.")
    @RequestMapping(value = "/on-receipt", method = RequestMethod.POST)
    public ResponseEntity<Message> onreceipt(@RequestBody Message message) {
        try {
            return ResponseEntity.ok(service.send(message,true));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @RequestMapping(value = "/function", method = RequestMethod.POST)
    public ResponseEntity<Message> function(@RequestBody Message message) {
        try {
            return ResponseEntity.ok(service.send(message,false));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @RequestMapping(value = "/administration", method = RequestMethod.POST)
    public ResponseEntity<Message> administration(@RequestBody Message message) {
        try {
            return ResponseEntity.ok(service.send(message,false));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/location", method = RequestMethod.POST)
    public ResponseEntity<Message> location(@RequestBody Message message) {
        try {
            return ResponseEntity.ok(service.send(message,false));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/economic-segment-code", method = RequestMethod.POST)
    public ResponseEntity<Message> economicSegmentCode(@RequestBody Message message) {
        try {
            return ResponseEntity.ok(service.send(message,false));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/recipient-segment-code", method = RequestMethod.POST)
    public ResponseEntity<Message> recipientSegmentCode(@RequestBody Message message) {
        try {
            return ResponseEntity.ok(service.send(message,false));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/target-segment-code", method = RequestMethod.POST)
    public ResponseEntity<Message> targetSegmentCode(@RequestBody Message message) {
        try {
            return ResponseEntity.ok(service.send(message,false));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/source-of-fund-code", method = RequestMethod.POST)
    public ResponseEntity<Message> sourceOfFundCode(@RequestBody Message message) {
        try {
            return ResponseEntity.ok(service.send(message,false));   
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
