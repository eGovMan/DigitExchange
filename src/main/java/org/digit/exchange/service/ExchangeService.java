// package org.digit.exchange.service;

// import java.util.List;

// import org.digit.exchange.web.controllers.models.RequestMessage;
// import org.springframework.beans.factory.annotation.Autowired;

// public class ExchangeService {

//     @Autowired
//     private RequestMessageRepository requestMessageRepository;

//     // Create
//     public RequestMessage createRequestMessage(RequestMessage requestMessage) {
//         return requestMessageRepository.save(requestMessage);
//     }

//     // Read
//     public Optional<RequestMessage> getRequestMessageById(Long id) {
//         return requestMessageRepository.findById(id);
//     }

//     public List<RequestMessage> getAllRequestMessages() {
//         return requestMessageRepository.findAll();
//     }

//     // Update
//     public RequestMessage updateRequestMessage(RequestMessage requestMessage) {
//         // Assuming the requestMessage has a valid ID
//         return requestMessageRepository.save(requestMessage);
//     }

//     // Delete
//     public void deleteRequestMessage(Long id) {
//         requestMessageRepository.deleteById(id);
//     }
    
// }
