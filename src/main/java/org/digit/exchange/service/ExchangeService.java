package org.digit.exchange.service;

import java.util.Optional;

import org.digit.exchange.config.AppConfig;
import org.digit.exchange.constant.ExchangeMessageAction;
import org.digit.exchange.constant.MessageType;
import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.model.messages.Allocation;
import org.digit.exchange.model.messages.Demand;
import org.digit.exchange.model.messages.Disbursement;
import org.digit.exchange.model.messages.Estimate;
import org.digit.exchange.model.messages.InboxRequest;
import org.digit.exchange.model.messages.Program;
import org.digit.exchange.model.messages.RequestMessage;
import org.digit.exchange.model.messages.Sanction;
import org.digit.exchange.repository.RequestMessageRepository;
import org.digit.exchange.utils.DispatcherUtil;
// import org.slf4j.LoggerFactory;
// import org.slf4j.Logger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ExchangeService{
    
    private final AppConfig config;
    private final DispatcherUtil dispatcher;
    private final RequestMessageRepository requestMessageRepository;

    public ExchangeService(RequestMessageRepository requestMessageRepository, DispatcherUtil dispatcher, AppConfig config){
        this.requestMessageRepository = requestMessageRepository;
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
    public RequestMessage send(RequestMessage requestMessage, Boolean isReply) {
        String recieverDomain = parseDID(requestMessage.getHeader().getReceiverId())[1];
        MessageType messageType = requestMessage.getHeader().getMessageType();
        switch (requestMessage.getHeader().getMessageType()) {            
                case PROGRAM:
                    Program program = Program.fromString(requestMessage.getMessage());
                    ExchangeMessageAction action = program.getAction();
                    String url = getReceiverEndPoint(recieverDomain, messageType, action, isReply);
                    if(url==null)
                        return null;
                    dispatcher.dispatch(url, program);
                    break;

                case ESTIMATE:
                    Estimate estimate = Estimate.fromString(requestMessage.getMessage());
                    action = estimate.getAction();
                    url = getReceiverEndPoint(recieverDomain, messageType, action, isReply);
                    if(url==null)
                        return null;
                    dispatcher.dispatch(url, estimate);
                    break;

                case SANCTION:
                    Sanction sanction = Sanction.fromString(requestMessage.getMessage());
                    action = sanction.getAction();
                    url = getReceiverEndPoint(recieverDomain, messageType, action, isReply);
                    if(url==null)
                        return null;
                    dispatcher.dispatch(url, sanction);
                    break;

                case ALLOCATION:
                    Allocation allocation = Allocation.fromString(requestMessage.getMessage());
                    action = allocation.getAction();
                    url = getReceiverEndPoint(recieverDomain, messageType, action, isReply);                    
                    if(url==null)
                        return null;
                    dispatcher.dispatch(url, allocation);
                    break;

                case DISBURSEMENT:
                    Disbursement disbursement = Disbursement.fromString(requestMessage.getMessage());
                    action = disbursement.getAction();
                    url = getReceiverEndPoint(recieverDomain, messageType, action, isReply);
                    if(url==null)
                        return null;
                    dispatcher.dispatch(url, disbursement);
                    break;

                case DEMAND:
                    Demand demand = Demand.fromString(requestMessage.getMessage());
                    action = demand.getAction();
                    url = getReceiverEndPoint(recieverDomain, messageType, action, isReply);
                    if(url==null)
                        return null;
                    dispatcher.dispatch(url, demand);
                    break;

                case COLLECTION:
                    org.digit.exchange.model.messages.Collection collection = org.digit.exchange.model.messages.Collection.fromString(requestMessage.getMessage());
                    action = collection.getAction();
                    url = getReceiverEndPoint(recieverDomain, messageType, action, isReply);
                    if(url==null)
                        return null;
                    dispatcher.dispatch(url, collection);
                    break;        
            default:
                return null;
        }
        return requestMessageRepository.save(requestMessage);
    }

    // Read
    public Optional<RequestMessage> getRequestMessageById(String id) {
        return requestMessageRepository.findById(id);
    }

    // Find by ReceiverId
    public Page<RequestMessage> findByReceiverId(InboxRequest message) {
        try{
            String receiverId = message.getReceiverId();
            int page = message.getPage();
            int size = message.getSize();
            Pageable pageable = PageRequest.of(page, size);
            
            Page<RequestMessage> result = requestMessageRepository.findByHeaderReceiverId(receiverId,pageable);
            return result;
        } catch (Exception e) {
            throw new CustomException("Error processing message object.", e);
        }
    }

    // Find by Sender Id
    public Page<RequestMessage> findBySenderId(String id, Pageable pageable) {
        return requestMessageRepository.findByHeaderSenderId(id,pageable);
    }
    
}
