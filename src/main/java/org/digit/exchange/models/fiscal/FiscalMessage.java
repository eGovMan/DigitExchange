package org.digit.exchange.models.fiscal;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Currency;
import java.util.UUID;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import org.digit.exchange.utils.CurrencyConverter;
import org.digit.exchange.utils.ZonedDateTimeConverter;


@Getter
@Setter
@Embeddable
public class FiscalMessage {
    @JsonProperty("id")
    @Id
    private String id;
    @NotNull
    @JsonProperty("schema_version")
    private String schemaVersion;
    @JsonProperty("fiscal_message_type")
    private String fiscalMessageType;
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

    public FiscalMessage(){
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.schemaVersion = "1.0.0";
        this.setFiscalMessageType( this.getClass().getSimpleName().toLowerCase());
    }

    public void copy(FiscalMessage other){
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
        this.setFiscalMessageType( this.getClass().getSimpleName().toLowerCase());
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
}
