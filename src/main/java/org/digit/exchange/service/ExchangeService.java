package org.digit.exchange.service;

import java.util.Optional;

import org.digit.exchange.config.AppConfig;
import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.models.*;
import org.digit.exchange.repository.RequestMessageRepository;
import org.digit.exchange.utils.DispatcherUtil;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class ExchangeService {
    
    private final AppConfig config;
    private final DispatcherUtil dispatcher;
    private final RequestMessageRepository requestMessageRepository;
    private static final Logger logger = LoggerFactory.getLogger(ExchangeService.class);

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

    String getReceiverEndPoint(RequestMessage message, Boolean isReply){
        String recieverDomain = parseDID(message.getHeader().getReceiverId())[1];
        if( recieverDomain.equalsIgnoreCase(config.getDomain())){
            //Retrieve url from config
            if(isReply)
                return config.getRoutes().get("ON-" + message.getHeader().getMessageType().toString());
            else
                return config.getRoutes().get(message.getHeader().getMessageType().toString());
        }else{
            return recieverDomain + "/exchange/v1/" + message.getHeader().getMessageType().toString().toLowerCase();
        }
    }

    // Route Message
    public RequestMessage send(RequestMessage requestMessage, Boolean isReply) {
        
        String url = getReceiverEndPoint(requestMessage,isReply);
        if(url==null)
            return null;
        switch (requestMessage.getHeader().getMessageType()) {            
                case INDIVIDUAL:
                    Individual individual = Individual.fromString(requestMessage.getMessage());
                    //Verify the content
                    //Sign the message
                    //Send the message
                    dispatcher.dispatch(url, individual);
                    break;

                case ORGANISATION:
                    Organisation organisation = Organisation.fromString(requestMessage.getMessage());
                    dispatcher.dispatch(url, organisation);
                    break;

                case PROGRAM:
                    Program program = Program.fromString(requestMessage.getMessage());
                    dispatcher.dispatch(url, program);
                    break;

                case ESTIMATE:
                    Estimate estimate = Estimate.fromString(requestMessage.getMessage());
                    dispatcher.dispatch(url, estimate);
                    break;

                case SANCTION:
                    Sanction sanction = Sanction.fromString(requestMessage.getMessage());
                    dispatcher.dispatch(url, sanction);
                    break;

                case ALLOCATION:
                    Allocation allocation = Allocation.fromString(requestMessage.getMessage());
                    dispatcher.dispatch(url, allocation);
                    break;

                case DISBURSEMENT:
                    Disbursement bill = Disbursement.fromString(requestMessage.getMessage());
                    dispatcher.dispatch(url, bill);
                    break;

                case PAYMENT:
                    Payment payment = Payment.fromString(requestMessage.getMessage());
                    dispatcher.dispatch(url, payment);
                    break;

                case DEMAND:
                    Demand demand = Demand.fromString(requestMessage.getMessage());
                    dispatcher.dispatch(url, demand);
                    break;

                case RECEIPT:
                    Receipt receipt = Receipt.fromString(requestMessage.getMessage());
                    dispatcher.dispatch(url, receipt);
                    break;        
            default:
                break;
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
