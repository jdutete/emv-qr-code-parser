package com.codebyice.emvco.tags;

import com.codebyice.emvco.UnknownTagException;
import com.codebyice.emvco.model.Poi;
import com.codebyice.emvco.model.TipIndicator;

import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;


public enum Tag implements ITag {

    _00_PAYLOAD_FORMAT_INDICATOR("00", "Payload Format Indicator",  "^.{1,5}$", true),
    _01_POINT_INITIATION_METHOD("01", "Point Of Initiation",  "^\\d{2}$", false, a -> getPoi(a)),
    _52_MERCHANT_CATEGORY_CODE("52", "Merchant Category Code", "^\\d{4}$", true),
    _53_TRANSACTION_CURRENCY_CODE("53", "Transaction Currency", "^\\d{3}$", true),
    _54_TRANSACTION_AMOUNT("54", "Transaction Amount", "^(?!.{14,})(([1-9]\\d*|0)(\\.\\d+)?)$", false),
    _55_TIP_INDICATOR("55", "Tip Indicator",  "^(01|02|03)$", false, t -> getTipIndicator(t)),
    _56_CONVENIENCE_FEE_FIXED("56", "Convenience Fee (Fixed)",  "^(?!.{14,})(\\d+(\\.\\d+)?)$", false),
    _57_CONVENIENCE_FEE_PERCENTAGE("57",  "Convenience Fee (%)", "^(?!.{6,})0*(\\d{1,2}(\\.\\d+)?|100)$", false),
    _58_COUNTRY_CODE("58", "Country Code",  "^[a-zA-Z]{2}$", true),
    _59_MERCHANT_NAME("59", "Merchant Name", "^.{1,25}$", true),
    _60_MERCHANT_CITY("60", "Merchant City", "^.{1,15}$", true),
    _61_POSTAL_CODE("61", "Postal Code", "^.{1,10}$", false),
    _62_ADDITIONAL_DATA_FIELD("62", "Additional Data", "^.{1,99}$", false),
    _63_CRC("63", "CRC", "^.{4}$", false);

    private String tag;
    private Pattern pattern = null;
    private String description;
    private boolean isMandatory;
    private Function<String, String> valueExplainer;

    public String getTag() {
        return this.tag;
    }

    public Pattern getPattern() {
        return this.pattern;
    }

    public boolean isMandatory() {
        return this.isMandatory;
    }

    @Override
    public Function<String, String>  getValueExplainer() {
        return valueExplainer;
    }

    Tag(String tag, String description, String regex, boolean isMandatory) {
        this(tag, description, regex, isMandatory, v -> "");
    }

    Tag(String tag, String description, String regex, boolean isMandatory, Function<String, String>  valueExplainer) {
        this.tag = tag;
        this.isMandatory = isMandatory;
        this.description = description;
        if (regex != null) {
            this.pattern = Pattern.compile(regex);
        }
        this.valueExplainer = valueExplainer;
    }

    public String getDescription() {
        return description;
    }

    public static Tag getTag(String tag) {
        return Stream.of(values()).filter(t -> t.tag.equalsIgnoreCase(tag))
                .findFirst().orElseThrow(() -> new UnknownTagException(tag));
    }

    private static String getPoi(String a) {
        return Poi.fromValue(a).map(v -> " [" + v.getDescription() + "]").orElseGet(() -> "Unknown value " + a);
    }

    private static String getTipIndicator(String a) {
        return TipIndicator.fromValue(a).map(v -> " [" + v.getDescription() + "]").orElseGet(() -> "Unknown value " + a);
    }

}
