package za.co.icefactor.emvco;

import za.co.icefactor.emvco.model.AdditionalData;
import za.co.icefactor.emvco.model.Parser;
import za.co.icefactor.emvco.model.QrDetail;
import org.junit.Test;


public class Test1 {

    @Test
    public void one(){

       AdditionalData additionalData = new AdditionalData();
        additionalData.setBillNumber("1234567898")
                .setMobileNumber("0837759889")
                .setTerminalId("000425")
        ;

        System.out.println("AdditionalData: " + additionalData.toString());

        QrDetail qrDetail = new QrDetail();
        qrDetail.setCountryCode("ZA")
                .setPayloadFormatIndicator("01")
                .setMcc("4512")
                .setTransactionCurrencyCode("710")
                .setTransactionAmount(99.95)
                .setMerchantName("Ice Stores")
                .setMerchantCity("Cape")
                .setTipOrConvenienceIndicator("01")
                .setValueOfConvenienceFeeFixed(10)
                .setValueOfConvenienceFeePercentage(1.5)
                .setAdditionalData(additionalData);

        String qrString = qrDetail.toQrString();
        System.out.println("QR: " + qrString);

        QrDetail qr = Parser.parse(qrString);
//        System.out.println(qr);
        String s = qr.prettyPrint();
        System.out.println(s);

    }

//    @Test
//    public void t(){
//
//        String qrString = "0005UMPQR0102115204343053037105802ZA5907DIGITAL6012JOHANNESBURG62800127STATIC_200431200720070110350520200431200720070110350706200431150121606000318";
//        System.out.println("QR: " + qrString);
//
//        QrDetail qr = Parser.parse(qrString);
////        System.out.println(qr);
//        String s = qr.prettyPrint();
//        System.out.println(s);
//
//    }


}
