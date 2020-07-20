package com.codebyice.emvco.model;

import com.codebyice.emvco.UnknownTagException;
import com.codebyice.emvco.tags.ITag;
import com.codebyice.emvco.tags.Tag;
import com.codebyice.emvco.tags.AdditionalDataTag;

import java.io.Serializable;

public final class AdditionalData2 extends AbstractDataModel<AdditionalDataTag> {
    public AdditionalData2() {
        super(AdditionalDataTag.class, "", Tag._62_ADDITIONAL_DATA_FIELD.getTag());
    }

    @Override
    ITag getTag(String key) {
        return null;
    }

    public AdditionalData2 setValue(String tagString, Serializable value) throws UnknownTagException {
        super.setValue(tagString, value);
        return this;
    }

    public void validate()  {
        super.validate();
    }
    

    public String getBillNumber() {
        return this.getStringValue(AdditionalDataTag._01_BILL_NUMBER);
    }

    public AdditionalData2 setBillNumber(String billNumber) {
        this.setValue(AdditionalDataTag._01_BILL_NUMBER, billNumber);
        return this;
    }

    public String getMobileNumber() {
        return this.getStringValue(AdditionalDataTag._02_MOBILE_NUMBER);
    }

    public AdditionalData2 setMobileNumber(String mobileNumber) {
        this.setValue(AdditionalDataTag._02_MOBILE_NUMBER, mobileNumber);
        return this;
    }

    public String getStoreId() {
        return this.getStringValue(AdditionalDataTag._03_STORE_LABEL);
    }

    public AdditionalData2 setStoreId(String storeId) {
        this.setValue(AdditionalDataTag._03_STORE_LABEL, storeId);
        return this;
    }

    public String getLoyaltyNumber() {
        return this.getStringValue(AdditionalDataTag._04_LOYALTY_NUMBER);
    }

    public AdditionalData2 setLoyaltyNumber(String loyaltyNumber) {
        this.setValue(AdditionalDataTag._04_LOYALTY_NUMBER, loyaltyNumber);
        return this;
    }

    public String getReferenceId() {
        return this.getStringValue(AdditionalDataTag._05_REFERENCE_LABEL);
    }

    public AdditionalData2 setReferenceId(String referenceId) {
        this.setValue(AdditionalDataTag._05_REFERENCE_LABEL, referenceId);
        return this;
    }

    public String getConsumerId() {
        return this.getStringValue(AdditionalDataTag._06_CONSUMER_LABEL);
    }

    public AdditionalData2 setConsumerId(String consumerId) {
        this.setValue(AdditionalDataTag._06_CONSUMER_LABEL, consumerId);
        return this;
    }

    public String getTerminalId() {
        return this.getStringValue(AdditionalDataTag._07_TERMINAL_LABEL);
    }

    public AdditionalData2 setTerminalId(String terminalId) {
        this.setValue(AdditionalDataTag._07_TERMINAL_LABEL, terminalId);
        return this;
    }

    public String getPurpose() {
        return this.getStringValue(AdditionalDataTag._08_PURPOSE);
    }

    public AdditionalData2 setPurpose(String purpose) {
        this.setValue(AdditionalDataTag._08_PURPOSE, purpose);
        return this;
    }
}