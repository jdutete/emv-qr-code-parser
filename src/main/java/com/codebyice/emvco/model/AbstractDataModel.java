package com.codebyice.emvco.model;

import com.codebyice.emvco.InvalidTagValueException;
import com.codebyice.emvco.InvalidValueException;
import com.codebyice.emvco.MissingTagException;
import com.codebyice.emvco.ValidationUtils;
import com.codebyice.emvco.tags.ITag;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractDataModel<T extends ITag> implements Serializable {
    private Pattern pattern;
    private ITag[] allTags;
    private String parent;
    protected Map<String, Serializable> map = new TreeMap<>();


    AbstractDataModel(Class<T> typeParameterClass, String regex) {
        this.allTags = typeParameterClass.getEnumConstants();
        if (!regex.isEmpty()) {
            this.pattern = Pattern.compile(regex);
        }
    }

    AbstractDataModel(Class<T> typeParameterClass, String regex, String parent) {
        this(typeParameterClass, regex);
        this.parent = parent;
    }

    abstract ITag getTag(String key);

    public boolean isSet(ITag tag){
        return isSet(tag.getTag());
    }

    private boolean isSet(String tag) {
        return this.map.containsKey(tag);
    }

    private Serializable getTagValue(String tag) {
        return this.map.get(tag);
    }

    public Serializable getValue(T tag) {
        return this.getTagValue(tag.getTag());
    }

    public String getStringValue(String tag)  {
        Serializable value = getTagValue(tag);
        return value == null ? null : value.toString();
    }

    public String getStringValue(T tag) {
        return getStringValue(tag.getTag());
    }

    private AbstractDataModel setValueWithTagString(String tagString, Serializable value) {
        ValidationUtils.notNull(tagString);
        this.map.put(tagString, value);
        return this;
    }

    protected AbstractDataModel setValue(String tagString, Serializable value)  {
        ValidationUtils.validateTagString(tagString);
        return this.setValueWithTagString(tagString, value);
    }

    protected AbstractDataModel setValue(T tag, Serializable value) {
        if(!tag.getPattern().asPredicate().test(value.toString())){
            throw new InvalidValueException(value, tag);
        }
        this.setValueWithTagString(tag.getTag(), value);
        return this;
    }

    protected void removeTag(T tag) {
        removeTag(tag.getTag());
    }

    protected void removeTag(String tag) {
        this.map.remove(tag);
    }

    protected String getParent() {
        return this.parent;
    }

    public String toString(){
        return print();
    }

    public String prettyPrint(){
        ArrayList<String> keys = new ArrayList(map.keySet());
        keys.sort(Comparator.comparingInt(Integer::parseInt));
        StringBuffer sb =new StringBuffer();
        for (String key: keys) {
            ITag tag = getTag(key);
            Serializable serializable = map.get(key);
            String value = String.valueOf(serializable);
            if(value != null && value.length() > 0){
                sb.append(key)
                        .append(": ").append(String.format("[%02d]", value.length()))
                        .append(": ").append(String.format("%20s", value))
                        .append(" (").append(tag.getDescription()).append(")")
                        .append(tag.getValueExplainer().apply(value))
                        .append(" ").append("\n");
            }
            if(serializable instanceof AdditionalData){
                String additionalDataDecoded = ((AdditionalData) serializable).prettyPrint()
                        .replace("\n", "\n\t");
                sb.append("\t" + additionalDataDecoded.substring(0, additionalDataDecoded.length()-1));
            }
        }
        return sb.toString();
    }

    public String print(){
        return this.map.keySet().stream()
                .map(t -> {
                    String value = getStringValue(t);
                    if(value != null && value.length() > 0){
                        return t + String.format("%02d", value.length()) + value;
                    } else {
                        return null;
                    }
                }).collect(Collectors.joining());

//        ArrayList<String> keys = new ArrayList(map.keySet());
//        StringBuffer sb =new StringBuffer();
//        for (String key: keys) {
//            ITag tag = getTag(key);
//            Serializable serializable = map.get(key);
//            String value = String.valueOf(serializable);
//            if(value != null && value.length() > 0){
//                sb.append(key)
//                        .append(String.format("%02d", value.length()))
//                    .append(value);
//            }
//        }
//        return sb.toString();
    }

    public void validate() {
        Stream.of(allTags)
                .forEach(this::validateTag);
    }

    private void validateTag(ITag tag) {
        int tagNumber = Integer.parseInt(tag.getTag());
        Serializable serializable = getTagValue(tag.getTag());
        if (serializable == null && tag.isMandatory()) {
            throw new MissingTagException(tag);
        }

        if(serializable != null){
            if (serializable instanceof AbstractDataModel) {
                ((AbstractDataModel)serializable).validate();
            }

            String value = serializable == null ? null : String.valueOf(serializable);
            Pattern pattern = tag.getPattern();
            if (pattern != null) {
                Matcher matcher = pattern.matcher(value);
                if (!matcher.matches()) {
                    throw new InvalidTagValueException(tag, value);
                }
            } else if ((!(this instanceof QrDetail) || 1 >= tagNumber || tagNumber >= 52) && (value.length() > 99 || value.length() == 0)) {
                if (this instanceof QrDetail) {
                    throw new InvalidTagValueException(tag, value);
                } else {
                    throw new InvalidTagValueException(tag, value);
                }
            }
        }
    }

}
