// package org.digit.line.utils;

// import org.digit.exchange.constant.MessageType;
// import org.digit.exchange.exceptions.CustomException;
// import org.digit.exchange.model.Message;
// import org.digit.fix.model.FiscalData;
// import org.digit.fix.model.Program;
// import org.springframework.stereotype.Service;
// import org.springframework.web.reactive.function.client.WebClient;

// import reactor.core.publisher.Mono;

// @Service
// public class FIXClient {
//     private final WebClient webClient;

//     FIXClient(){
//         this.webClient = WebClient.create();
//     }

//     public Program program(String url, String to, String from, FiscalData fiscalData, MessageType messageType, Class<T> responseType){
//         Message request;
//         try {
//             request = new Message(to, from, fiscalData, messageType);
//         } catch (RuntimeException e) {
//             throw new CustomException("Unable to send message to Exchange Server", e);
//         }
//         return webClient.post()
//                     .uri(url)
//                     .body(Mono.just(request), responseType)
//                     .retrieve()
//                     .bodyToMono(responseType);
//     }
// }
