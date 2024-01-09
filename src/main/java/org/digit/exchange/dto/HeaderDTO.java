package org.digit.exchange.dto;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import org.digit.exchange.constant.MessageType;
import org.digit.exchange.exceptions.CustomException;
import org.digit.exchange.model.Header;
import org.digit.exchange.utils.ZonedDateTimeConverter;
import org.digit.fix.model.FiscalData;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Getter
@Setter
public class HeaderDTO{     
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

    public HeaderDTO(){
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.version = "1.0.0";
    }

    // Constructor that takes a Header object
    public HeaderDTO(Header header) {
        this.id = header.getId(); // Assuming Header has a getId() method
        this.version = header.getVersion(); // Assuming getVersion() method, etc.
        this.messageId = header.getMessageId();
        this.messageTs = header.getMessageTs();
        this.messageType = header.getMessageType();
        this.senderId = header.getSenderId();
        this.senderUri = header.getSenderUri();
        this.receiverId = header.getReceiverId();
        this.totalCount = header.getTotalCount();
        this.isMsgEncrypted = header.isMsgEncrypted();
    }

    public HeaderDTO(String to, String from, FiscalData message, MessageType messageType){
        this.senderId = from;
        this.receiverId = to;
        this.messageType = messageType;
        // this.exchangeMessage = message;
        //Set MessageID
        UUID uuid = UUID.randomUUID();
        this.messageId = uuid.toString();
        //Set Timestamp
        ZoneId zoneId = ZoneId.of("Asia/Kolkata");
        ZonedDateTime now = LocalDateTime.now().atZone(zoneId);
        this.messageTs = now;
 
    }

    // Method to convert HeaderDTO to Header
    public Header toHeader() {
        Header header = new Header();

        header.setId(this.id); // Assuming Header has a setId() method
        header.setVersion(this.version); // Assuming setVersion() method, etc.
        header.setMessageId(this.messageId);
        header.setMessageTs(this.messageTs);
        header.setMessageType(this.messageType);
        header.setSenderId(this.senderId);
        header.setSenderUri(this.senderUri);
        header.setReceiverId(this.receiverId);
        header.setTotalCount(this.totalCount);
        header.setMsgEncrypted(this.isMsgEncrypted);

        return header;
    }

    static public HeaderDTO fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, HeaderDTO.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing RequestHeader fromString", e);
		}
	}
}
