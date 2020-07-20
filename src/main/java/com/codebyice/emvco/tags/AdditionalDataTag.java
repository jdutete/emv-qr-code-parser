package com.codebyice.emvco.tags;

import com.codebyice.emvco.UnknownTagException;

import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public enum AdditionalDataTag implements ITag {
    _01_BILL_NUMBER("01","Bill Number", "^.{1,26}$", false),
    _02_MOBILE_NUMBER("02", "Mobile Number",  "^.{1,26}$", false),
    _03_STORE_LABEL("03", "Store Label", "^.{1,26}$", false),
    _04_LOYALTY_NUMBER("04", "Loyalty Number",  "^.{1,26}$", false),
    _05_REFERENCE_LABEL("05", "Reference Label", "^.{1,26}$", false),
    _06_CONSUMER_LABEL("06", "Consumer Label", "^.{1,26}$", false),
    _07_TERMINAL_LABEL("07", "Terminal Label", "^.{1,26}$", false),
    _08_PURPOSE("08", "Purpose of Transaction", "^.{1,26}$", false),
    _09_ADDITIONAL_CONSUMER_DATA("09", "Additional Consumer Data", "^.{1,26}$", false),
    _15_ACQUIRER_ID("15", "Acquirer Id",  "^.{1,26}$", false),
    _16_MERCHANT_ID("16", "Merchant Id", "^.{1,26}$", false),
    _17_ACQUIRER_BIN("17", "Acquirer Id", "^.{1,26}$", false);

    private String tag;
    private Pattern pattern;
    private boolean isMandatory;
    private String description;
    private Function<String, String>  valueExplainer;

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
    public Function<String, String> getValueExplainer() {
        return valueExplainer;
    }

    AdditionalDataTag(String tag, String description, String regex, boolean isMandatory) {
        this(tag, description, regex, isMandatory, v -> "");
    }
    AdditionalDataTag(String tag, String description, String regex, boolean isMandatory, Function<String, String>  valueExplainer) {
        this.tag = tag;
        this.isMandatory = isMandatory;
        this.description = description;
        if (regex != null) {
            this.pattern = Pattern.compile(regex);
        }
        this.valueExplainer = valueExplainer;

    }

    public static AdditionalDataTag getTag(String tag) throws UnknownTagException {
        return Stream.of(values()).filter(t -> t.getTag().equalsIgnoreCase(tag))
                .findFirst().orElseThrow(() -> new RuntimeException("Unknown tag: " + tag));
    }

    public String getDescription() {
        return description;
    }
}