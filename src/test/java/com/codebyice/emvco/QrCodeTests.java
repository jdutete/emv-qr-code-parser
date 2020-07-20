package com.codebyice.emvco;

import com.codebyice.emvco.model.AdditionalData;
import com.codebyice.emvco.model.Parser;
import com.codebyice.emvco.model.QrDetail;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;


class QrCodeTests {

    private static Logger logger = LoggerFactory.getLogger(QrCodeTests.class);
    @Test
    void generateQrCodeString(){

        String billNumber = "BIL1234";
        String mobileNumber = "0110210325";
        String terminalId = "004215";
        String countryCode = "ZA";
        String mcc = "0412";
        String merchantName = "Spar Eagle's Landing";

       AdditionalData additionalData = new AdditionalData();
        additionalData.setBillNumber(billNumber)
                .setMobileNumber(mobileNumber)
                .setTerminalId(terminalId);
        QrDetail qrDetail = new QrDetail();
        qrDetail.setCountryCode(countryCode)
                .setPayloadFormatIndicator("01")
                .setMcc(mcc)
                .setTransactionCurrencyCode("710")
                .setTransactionAmount(99.95)
                .setMerchantName(merchantName)
                .setMerchantCity("Johannesburg")
                .setTipOrConvenienceIndicator("01")
                .setValueOfConvenienceFeeFixed(10)
                .setValueOfConvenienceFeePercentage(1.5)
                .setAdditionalData(additionalData);

        String qrString = qrDetail.toQrString();
        logger.info("QR: {}", qrString);
        QrDetail qr = Parser.parse(qrString);
        assertEquals(billNumber, qr.getAdditionalData().getBillNumber());
        assertEquals(mobileNumber, qr.getAdditionalData().getMobileNumber());
        assertEquals(terminalId, qr.getAdditionalData().getTerminalId());
        assertEquals(mcc, qr.getMcc());
        assertEquals(merchantName, qr.getMerchantName());
        String s = qr.prettyPrint();
        logger.info("QR code printed prettily: {}", s);
    }
}
