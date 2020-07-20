package com.codebyice.emvco.model;

import com.codebyice.emvco.Util;
import com.codebyice.emvco.tags.ITag;
import com.codebyice.emvco.tags.Tag;

import java.io.Serializable;

import static com.codebyice.emvco.model.Poi.DYNAMIC;
import static com.codebyice.emvco.model.Poi.STATIC;

public final class QrDetail extends AbstractDataModel<Tag> {

    public QrDetail() {
        super(Tag.class, "^(6[5-9]|7[0-9])$");
    }

    public void validate() {
        super.validate();
    }

    @Override
    ITag getTag(String key) {
        return Tag.getTag(key);
    }

    public QrDetail setValue(String tagString, Serializable value) {
        super.setValue(tagString, value);
        return this;
    }

    public String toQrString() {
        this.populatePoi();
        this.validate();
        if (this.isSet(Tag._62_ADDITIONAL_DATA_FIELD)) {
//            this.getAdditionalData().validateDataForGeneration();
        }
        this.removeTag(Tag._63_CRC); //we dont print out the CRC
        String content = this.toString() + "6304";
        String checksum = Util.generateChecksumCRC16(content);
        this.setValue(Tag._63_CRC, checksum);
        return content + checksum;
    }


    public String getPayloadFormatIndicator() {
        return this.getStringValue(Tag._00_PAYLOAD_FORMAT_INDICATOR);
    }

    public QrDetail setPayloadFormatIndicator(String indicator) {
        this.setValue(Tag._00_PAYLOAD_FORMAT_INDICATOR, indicator);
        return this;
    }

    public QrDetail setPointOfInitiationMethod(Poi poi){
        setValue(Tag._01_POINT_INITIATION_METHOD, poi.getValue());
        return this;
    }
    public String getPointOfInitiationMethod() {
        return this.getStringValue(Tag._01_POINT_INITIATION_METHOD);
    }

    private QrDetail populatePoi() {
        Poi poi = isSet(Tag._54_TRANSACTION_AMOUNT) ? DYNAMIC : STATIC;
        setPointOfInitiationMethod(poi);
        return this;
    }

    public String getMcc() {
        return this.getStringValue(Tag._52_MERCHANT_CATEGORY_CODE);
    }

    public QrDetail setMcc(String categoryCode) {
        this.setValue(Tag._52_MERCHANT_CATEGORY_CODE, categoryCode);
        return this;
    }

    public String getTransactionCurrencyCode() {
        return this.getStringValue(Tag._53_TRANSACTION_CURRENCY_CODE);
    }

    public QrDetail setTransactionCurrencyCode(String currencyCode) {
        this.setValue(Tag._53_TRANSACTION_CURRENCY_CODE, currencyCode);
        return this;
    }

    public Double getTransactionAmount() {
        String amount = this.getStringValue(Tag._54_TRANSACTION_AMOUNT);
        return amount == null ? null : Double.valueOf(amount);
    }

    public QrDetail setTransactionAmount(double amount) {
        String amountString = String.valueOf(amount);
        this.setValue(Tag._54_TRANSACTION_AMOUNT, amountString);
        this.populatePoi();
        return this;
    }

    public String getTipOrConvenienceIndicator() {
        return this.getStringValue(Tag._55_TIP_INDICATOR);
    }

    //TODO use enum
    public QrDetail setTipOrConvenienceIndicator(String indicator) {
        this.setValue(Tag._55_TIP_INDICATOR, indicator);
        return this;
    }

    public Double getValueOfConvenienceFeeFixed() {
        String amountString = this.getStringValue(Tag._56_CONVENIENCE_FEE_FIXED);
        return amountString == null ? null : Double.valueOf(amountString);
    }

    public QrDetail setValueOfConvenienceFeeFixed(double fee) {
        String feeString = String.valueOf(fee);
        this.setValue(Tag._56_CONVENIENCE_FEE_FIXED, feeString);
        return this;
    }

    public Double getValueOfConvenienceFeePercentage() {
        String amountString = this.getStringValue(Tag._57_CONVENIENCE_FEE_PERCENTAGE);
        return amountString == null ? null : Double.valueOf(amountString);
    }

    public QrDetail setValueOfConvenienceFeePercentage(double percentage) {
        String percentageString = String.valueOf(percentage);
        this.setValue(Tag._57_CONVENIENCE_FEE_PERCENTAGE, percentageString);
        return this;
    }

    public String getCountryCode() {
        return this.getStringValue(Tag._58_COUNTRY_CODE);
    }

    public QrDetail setCountryCode(String countryCode) {
        this.setValue(Tag._58_COUNTRY_CODE, countryCode);
        return this;
    }

    public String getMerchantName() {
        return this.getStringValue(Tag._59_MERCHANT_NAME);
    }

    public QrDetail setMerchantName(String merchantName) {
        this.setValue(Tag._59_MERCHANT_NAME, merchantName);
        return this;
    }

    public String getMerchantCity() {
        return this.getStringValue(Tag._60_MERCHANT_CITY);
    }

    public QrDetail setMerchantCity(String merchantCity) {
        this.setValue(Tag._60_MERCHANT_CITY, merchantCity);
        return this;
    }

    public String getPostalCode() {
        return this.getStringValue(Tag._61_POSTAL_CODE);
    }

    public QrDetail setPostalCode(String postalCode) {
        this.setValue(Tag._61_POSTAL_CODE, postalCode);
        return this;
    }

    public AdditionalData getAdditionalData() {
        Serializable data = this.getValue(Tag._62_ADDITIONAL_DATA_FIELD);
        return data == null ? null : (AdditionalData) data;
    }

    public QrDetail setAdditionalData(AdditionalData additionalData) {
        this.setValue(Tag._62_ADDITIONAL_DATA_FIELD, additionalData);
        return this;
    }


    public String getCRC() {
        return this.getStringValue(Tag._63_CRC);
    }

    //TODO use enum
    private boolean isDynamic() {
        String poi = this.getPointOfInitiationMethod();
        return poi != null && poi.endsWith("2");
    }
}