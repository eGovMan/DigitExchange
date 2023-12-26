package org.digit.exchange.web.controllers.models;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

import org.digit.exchange.constants.Action;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
public class RequestHeader { 
    @NotNull
    @JsonProperty("version")
    private String version;
    @NotNull
    @JsonProperty("message_id")
    @NotNull
    private String messageId;
    @JsonProperty("message_ts")
    @NotNull
    private ZonedDateTime messageTs;
    @JsonProperty("action")
    @NotNull
    private Action action;
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
    @JsonProperty("meta")
    private FiscalMessage fiscalMessage;    

    public RequestHeader(){
        this.version = "1.0.0";
    }

    public RequestHeader(String to, String from, FiscalMessage message, Action action){
        this.senderId = from;
        this.receiverId = to;
        this.action = action;
        this.fiscalMessage = message;
        //Set MessageID
        UUID uuid = UUID.randomUUID();
        this.messageId = uuid.toString();
        //Set Timestamp
        ZoneId zoneId = ZoneId.of("Asia/Kolkata");
        ZonedDateTime now = LocalDateTime.now().atZone(zoneId);
        this.messageTs = now;
 
    }
}
