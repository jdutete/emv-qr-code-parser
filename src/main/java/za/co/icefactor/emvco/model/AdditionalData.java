package za.co.icefactor.emvco.model;

import za.co.icefactor.emvco.UnknownTagException;
import za.co.icefactor.emvco.tags.AdditionalDataTag;
import za.co.icefactor.emvco.tags.ITag;
import za.co.icefactor.emvco.tags.Tag;

import java.io.Serializable;

public final class AdditionalData extends AbstractDataModel<AdditionalDataTag> {
    public AdditionalData() {
        super(AdditionalDataTag.class, "", Tag._62_ADDITIONAL_DATA_FIELD.getTag());
    }

    public AdditionalData setValue(String tagString, Serializable value) throws UnknownTagException {
        super.setValue(tagString, value);
        return this;
    }

    public void validate()  {
        super.validate();
    }
    

    public String getBillNumber() {
        return this.getStringValue(AdditionalDataTag._01_BILL_NUMBER);
    }

    public AdditionalData setBillNumber(String billNumber) {
        this.setValue(AdditionalDataTag._01_BILL_NUMBER, billNumber);
        return this;
    }

    public String getMobileNumber() {
        return this.getStringValue(AdditionalDataTag._02_MOBILE_NUMBER);
    }

    public AdditionalData setMobileNumber(String mobileNumber) {
        this.setValue(AdditionalDataTag._02_MOBILE_NUMBER, mobileNumber);
        return this;
    }

    public String getStoreId() {
        return this.getStringValue(AdditionalDataTag._03_STORE_LABEL);
    }

    public AdditionalData setStoreId(String storeId) {
        this.setValue(AdditionalDataTag._03_STORE_LABEL, storeId);
        return this;
    }

    public String getLoyaltyNumber() {
        return this.getStringValue(AdditionalDataTag._04_LOYALTY_NUMBER);
    }

    public AdditionalData setLoyaltyNumber(String loyaltyNumber) {
        this.setValue(AdditionalDataTag._04_LOYALTY_NUMBER, loyaltyNumber);
        return this;
    }

    public String getReferenceId() {
        return this.getStringValue(AdditionalDataTag._05_REFERENCE_LABEL);
    }

    public AdditionalData setReferenceId(String referenceId) {
        this.setValue(AdditionalDataTag._05_REFERENCE_LABEL, referenceId);
        return this;
    }

    public String getConsumerId() {
        return this.getStringValue(AdditionalDataTag._06_CONSUMER_LABEL);
    }

    public AdditionalData setConsumerId(String consumerId) {
        this.setValue(AdditionalDataTag._06_CONSUMER_LABEL, consumerId);
        return this;
    }

    public String getTerminalId() {
        return this.getStringValue(AdditionalDataTag._07_TERMINAL_LABEL);
    }

    public AdditionalData setTerminalId(String terminalId) {
        this.setValue(AdditionalDataTag._07_TERMINAL_LABEL, terminalId);
        return this;
    }

    public String getPurpose() {
        return this.getStringValue(AdditionalDataTag._08_PURPOSE);
    }

    public AdditionalData setPurpose(String purpose) {
        this.setValue(AdditionalDataTag._08_PURPOSE, purpose);
        return this;
    }

    public ITag getTag(String key) {
        return AdditionalDataTag.getTag(key);
    }
}