package org.digit.fix.service;

import org.digit.exchange.config.AppConfig;
import org.digit.exchange.constant.ExchangeMessageAction;
import org.digit.exchange.constant.MessageType;
import org.digit.exchange.model.Message;
import org.digit.fix.model.Allocation;
import org.digit.fix.model.Demand;
import org.digit.fix.model.Disbursement;
import org.digit.fix.model.Estimate;
import org.digit.fix.model.FiscalData;
import org.digit.fix.model.Program;
import org.digit.fix.model.Sanction;
import org.digit.exchange.utils.DispatcherUtil;
// import org.slf4j.LoggerFactory;
// import org.slf4j.Logger;

import org.springframework.stereotype.Service;

@Service
public class FIXService{
    
    private final AppConfig config;
    private final DispatcherUtil dispatcher;

    public FIXService(DispatcherUtil dispatcher, AppConfig config){
        this.dispatcher = dispatcher;
        this.config = config;
    }

    public String[] parseDID(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }
        String[] parts = input.split("@", 2); // Limit to 2 parts in case there are multiple '@' in the string
        return parts; // The first part before the '@'
    }

    String getReceiverEndPoint(String domain, MessageType messageType, ExchangeMessageAction action, Boolean isReply){
        
        if( domain.equalsIgnoreCase(config.getDomain())){
            //Retrieve url from config
            if(isReply)
                return config.getRoutes().get(("ON-" + messageType.toString() + "." + action.toString()).toLowerCase());
            else
                return config.getRoutes().get((messageType.toString() + "." + action.toString()).toLowerCase());
        }else{
            return domain + "/exchange/v1/" + messageType.toString().toLowerCase();
        }
    }

    // Route Message
    public Message send(Message message, Boolean isReply) {
        String recieverDomain = parseDID(message.getHeader().getReceiverId())[1];
        MessageType messageType = message.getHeader().getMessageType();
        switch (message.getHeader().getMessageType()) {            
                case PROGRAM:
                    Program program = Program.fromString(message.getMessage());
                    ExchangeMessageAction action = program.getAction();
                    String url = getReceiverEndPoint(recieverDomain, messageType, action, isReply);
                    if(url==null)
                        return null;
                    FiscalData programResult = dispatcher.dispatch(url, program);
                    break;

                case ESTIMATE:
                    Estimate estimate = Estimate.fromString(message.getMessage());
                    action = estimate.getAction();
                    url = getReceiverEndPoint(recieverDomain, messageType, action, isReply);
                    if(url==null)
                        return null;
                    dispatcher.dispatch(url, estimate);
                    break;

                case SANCTION:
                    Sanction sanction = Sanction.fromString(message.getMessage());
                    action = sanction.getAction();
                    url = getReceiverEndPoint(recieverDomain, messageType, action, isReply);
                    if(url==null)
                        return null;
                    dispatcher.dispatch(url, sanction);
                    break;

                case ALLOCATION:
                    Allocation allocation = Allocation.fromString(message.getMessage());
                    action = allocation.getAction();
                    url = getReceiverEndPoint(recieverDomain, messageType, action, isReply);                    
                    if(url==null)
                        return null;
                    dispatcher.dispatch(url, allocation);
                    break;

                case DISBURSEMENT:
                    Disbursement disbursement = Disbursement.fromString(message.getMessage());
                    action = disbursement.getAction();
                    url = getReceiverEndPoint(recieverDomain, messageType, action, isReply);
                    if(url==null)
                        return null;
                    dispatcher.dispatch(url, disbursement);
                    break;

                case DEMAND:
                    Demand demand = Demand.fromString(message.getMessage());
                    action = demand.getAction();
                    url = getReceiverEndPoint(recieverDomain, messageType, action, isReply);
                    if(url==null)
                        return null;
                    dispatcher.dispatch(url, demand);
                    break;

                case COLLECTION:
                    org.digit.fix.model.Collection collection = org.digit.fix.model.Collection.fromString(message.getMessage());
                    action = collection.getAction();
                    url = getReceiverEndPoint(recieverDomain, messageType, action, isReply);
                    if(url==null)
                        return null;
                    dispatcher.dispatch(url, collection);
                    break;  
                
                case FUNCTION:
                case ADMINISTRATION:
                case ECONOMICSEGMENT:
                case RECIPIENTSEGMENT:
                case TARGETSEGMENT:
                case SOURCEOFFUND:
                case LOCATION:
                    org.digit.fix.model.FiscalData fiscalData = org.digit.fix.model.FiscalData.fromString(message.getMessage());
                    action = fiscalData.getAction();
                    url = getReceiverEndPoint(recieverDomain, messageType, action, isReply);
                    if(url==null)
                        return null;
                    dispatcher.dispatch(url, fiscalData);
                    break;  
            default:
                return null;
        }
        return null;
    }    
}
