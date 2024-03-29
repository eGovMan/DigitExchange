package org.digit.exchange.model;

import java.time.ZonedDateTime;
import java.util.UUID;

import org.digit.exchange.constant.MessageType;
import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.utils.ZonedDateTimeConverter;
import org.digit.fix.model.FiscalData;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Embeddable
@Getter
@Setter
public class Header{ 
    @Id
    private String id;
    @NotNull
    @JsonProperty("version")
    private String version;
    @NotNull
    @JsonProperty("message_id")
    @NotNull
    private String messageId;
    @JsonProperty("message_ts")
    @NotNull
    @Convert(converter = ZonedDateTimeConverter.class)
    private ZonedDateTime messageTs;
    @JsonProperty("message_type")
    @NotNull
    private MessageType messageType;
    @JsonProperty("sender_id")
    @NotNull
    private String senderId;
    @JsonProperty("senderUri")
    private String senderUri;
    @NotNull
    @JsonProperty("receiver_id")
    private String receiverId;
    @JsonProperty("total_count")
    private int totalCount;
    @JsonProperty("is_msg_encrypted")
    private boolean isMsgEncrypted;
    // // @OneToOne(cascade = CascadeType.ALL)
    // @JsonProperty("meta")
    // @Embedded
    // private ExchangeMessage exchangeMessage;    

    public Header(){
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.version = "1.0.0";
        this.messageTs = ZonedDateTime.now();
        //for new messages messageId is same as id. for reply messageId is same as original message id.
        this.messageId = this.id;
    }

    public Header(String to, String from, FiscalData message, MessageType messageType){
        this.senderId = from;
        this.receiverId = to;
        this.messageType = messageType;
        // this.exchangeMessage = message;
        //Set MessageID
        UUID uuid = UUID.randomUUID();
        this.messageId = uuid.toString();
        this.messageTs = ZonedDateTime.now();
    }

    static public Header fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, Header.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing RequestHeader fromString", e);
		}
	}
}
