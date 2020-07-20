package com.codebyice.emvco.model;

import com.codebyice.emvco.InvalidTagValueException;
import com.codebyice.emvco.Util;
import com.codebyice.emvco.ValidationUtils;
import com.codebyice.emvco.tags.Tag;

import java.io.Serializable;

public class Parser {
    private static final int TL_WIDTH = 2;

    public static QrDetail parse(String rawData) {
        QrDetail ppData = parseWithoutTagValidation(rawData);
        ppData.validate();
        return ppData;
    }

    public static QrDetail parseWithoutTagValidation(String rawData) {
        ValidationUtils.notNull(rawData);
        if (!Util.validateChecksumCRC16(rawData)) {
            String crc = null;
            if (rawData.length() > 4) {
                crc = rawData.substring(rawData.length() - 4);
            }

            throw new InvalidTagValueException(Tag._63_CRC, crc);
        } else {
            return parseWithoutTagValidationAndCRC(rawData);
        }
    }

    public static QrDetail parseWithoutTagValidationAndCRC(String qrString)  {
        ValidationUtils.notNull(qrString);
        int index = 0;

        QrDetail qrDetail = new QrDetail();

        while (index < qrString.length()) {
            TLV tlv = readNextTLV(qrString, index);
            String tag = tlv.getTag();
            if (tag.equals(Tag._62_ADDITIONAL_DATA_FIELD.getTag())) {
                AdditionalData additionalData = parseAdditionalData(tlv.getValue());
                setParsedValue(qrDetail, tag, additionalData);
            }  else {
                setParsedValue(qrDetail, tag, tlv.getValue());
            }
            index = index + tlv.getValue().length() + 4;
        }

//        for(qrDetail = new QrDetail(); index < qrString.length(); index = index + tlv.getValue().length() + 4) {
//            tlv = readNextTLV(qrString, index);
//            String tag = tlv.getTag();
//            int tagvalue = Integer.parseInt(tag);
//            if (tag.equals(Tag._62_ADDITIONAL_DATA_FIELD.getTag())) {
//                AdditionalData additionalData = parseAdditionalData(tlv.getValue());
//                setParsedValue(qrDetail, tag, additionalData);
//            }  else {
//                setParsedValue(qrDetail, tag, tlv.getValue());
//            }
//        }
        return qrDetail;
    }

    private static AdditionalData parseAdditionalData(String rawData)  {
        AdditionalData additionalData = new AdditionalData();
        return parseDataForSubDataModels(rawData, additionalData);
    }

    private static <A extends AbstractDataModel> A parseDataForSubDataModels(String rawData, A dataModel) {
        TLV tlv;
        for(int index = 0; index < rawData.length(); index = index + tlv.getValue().length() + 4) {
            tlv = readNextTLV(rawData, index);
            String tag = tlv.getTag();
            setParsedValue(dataModel, tag, tlv.getValue());
        }
        return dataModel;
    }

    private static TLV readNextTLV(String string, int start) {
        String tag = readSubstring(string, start, start + 2);
        ValidationUtils.validateTagString(tag);
        int index = start + 2;
        String lengthString = readSubstring(string, index, index + 2);
        int length;
        try{
           length  = Integer.parseInt(lengthString);
        } catch (NumberFormatException nfe){
            throw new RuntimeException("Invalid length value for tag " + tag);
        }

        index += 2;
        String value = readSubstring(string, index, index + length);
        return new TLV(tag, length, value);

    }

    private static String readSubstring(String string, int start, int end)  {
        if (string.length() < end) {
            throw new RuntimeException("few characters to read");
        } else {
            return string.substring(start, end);
        }
    }

    private static <A extends AbstractDataModel> A setParsedValue(A data, String tagString, Serializable value) {
        data.setValue(tagString, value);
        return data;
    }

    private Parser() {
    }
}