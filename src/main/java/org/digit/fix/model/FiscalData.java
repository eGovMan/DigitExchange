package org.digit.fix.model;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.List;
import java.util.UUID;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.digit.exchange.utils.CurrencyConverter;
import org.digit.exchange.utils.ZonedDateTimeConverter;
import org.digit.exchange.constant.Error;
import org.digit.exchange.constant.ExchangeMessageAction;
import org.digit.exchange.exceptions.CustomException;

@Getter
@Setter
@Embeddable
public class FiscalData {
    @JsonProperty("id")
    @Id
    @NotBlank(message = Error.INVALID_ID)
    private String id;
    @JsonProperty("name")
    @Id
    @NotBlank(message = Error.INVALID_NAME)
    private String name;
    @NotNull
    @JsonProperty("schema_version")
    private String schemaVersion;
    @JsonProperty("action")
    private ExchangeMessageAction action;
    @JsonProperty("account_code")
    private String accountCode;
    @JsonProperty("function_code")
    private String functionCode;
    @JsonProperty("administration_code")
    private String administrationCode;
    @JsonProperty("location_code")
    private String locationCode;
    @JsonProperty("program_code")
    private String programCode;
    @JsonProperty("recipient_segment_code")
    private String recipientSegmentCode;
    @JsonProperty("economic_segment_code")
    private String economicSegmentCode;
    @JsonProperty("source_of_found_code")
    private String sourceOfFundCode;
    @JsonProperty("target_segment_code")
    private String targetSegmentCode;
    @JsonProperty("start_date")
    @Convert(converter = ZonedDateTimeConverter.class)
    private ZonedDateTime startDate;
    @JsonProperty("end_date")
    @Convert(converter = ZonedDateTimeConverter.class)
    private ZonedDateTime endDate;
    @JsonProperty("net_amount")
    private BigDecimal netAmount;
    @JsonProperty("gross_amount")
    private BigDecimal grossAmount;
    @JsonProperty("currency_code")
    @Convert(converter = CurrencyConverter.class)
    private Currency currencyCode;
    @JsonProperty("locale_code")
    private String localeCode;
    @JsonProperty("children")
    private List<FiscalData> children;
    @JsonProperty("status")
    private Status status;
    // @JsonProperty("audit_details")
    // private AuditDetails auditDetails;
    @JsonProperty("additional_details")
    private JsonNode additionalDetails;


    public FiscalData(){
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.schemaVersion = "1.0.0";
    }

    public void copy(FiscalData other){
        this.schemaVersion = other.schemaVersion;
        this.functionCode= other.functionCode;
        this.administrationCode = other.administrationCode;
        this.locationCode = other.locationCode;
        this.programCode = other.programCode;
        this.recipientSegmentCode = other.recipientSegmentCode;
        this.economicSegmentCode = other.economicSegmentCode;
        this.sourceOfFundCode = other.sourceOfFundCode;
        this.targetSegmentCode = other.targetSegmentCode;
        this.startDate = other.startDate;
        this.netAmount=other.netAmount;
        this.grossAmount=other.grossAmount;
        this.currencyCode=other.currencyCode;
    }

    static public FiscalData fromString(String json){
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		try {
			return mapper.readValue(json, FiscalData.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new CustomException("Error parsing ExchangeMessage fromString", e);
		}
	}
}
