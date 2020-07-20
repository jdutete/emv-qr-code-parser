package za.co.icefactor.emvco.model;

import za.co.icefactor.emvco.InvalidTagValueException;
import za.co.icefactor.emvco.InvalidValueException;
import za.co.icefactor.emvco.MissingTagException;
import za.co.icefactor.emvco.ValidationUtils;
import za.co.icefactor.emvco.tags.ITag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public abstract class AbstractDataModel<T extends ITag> implements Serializable {
    private Pattern pattern;
    private ITag[] allTags;
    private String parent;
    protected HashMap<String, Serializable> map;


    AbstractDataModel(Class<T> typeParameterClass, String regex) {
        this.pattern = null;
        this.parent = null;
        this.map = new HashMap<>();
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

    private boolean checkValueAgainstTagString(String tagString) {
        ValidationUtils.notNull(tagString);
        return this.map.containsKey(tagString);
    }

    public boolean hasValue(String tagString)  {
        ValidationUtils.validateTagString(tagString);
        return this.checkValueAgainstTagString(tagString);
    }

    public boolean hasValue(T tag) {
        return this.checkValueAgainstTagString(tag.getTag());
    }

    private Serializable getValueFromTagString(String tagString) {
        ValidationUtils.notNull(tagString);
        Serializable value = this.map.get(tagString);
        return value;
    }

    public Serializable getValue(String tagString) {
        ValidationUtils.validateTagString(tagString);
        return this.getValueFromTagString(tagString);
    }

    public Serializable getValue(T tag) {
        return this.getValueFromTagString(tag.getTag());
    }

    public String getStringValue(String tagString)  {
        Serializable value = this.getValue(tagString);
        return value == null ? null : value.toString();
    }

    public String getStringValue(T tag) {
        Serializable value = this.getValue(tag);
        return value == null ? null : value.toString();
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

    protected AbstractDataModel setValueInTagRange(Serializable value, int low, int high) {
        for(int i = low; i < high + 1; ++i) {
            String currentTag = String.valueOf(i);
            if (!this.hasValue(currentTag)) {
                this.setValueWithTagString(currentTag, value);
                break;
            }
        }
        return this;
    }

    protected void removeValue(T tag) {
        ValidationUtils.notNull(tag);
        this.map.remove(tag.getTag());
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
        ArrayList<String> keys = new ArrayList(map.keySet());
        keys.sort(Comparator.comparingInt(Integer::parseInt));
        StringBuffer sb =new StringBuffer();
        for (String key: keys) {
            ITag tag = getTag(key);
            Serializable serializable = map.get(key);
            String value = String.valueOf(serializable);
            if(value != null && value.length() > 0){
                sb.append(key)
                        .append(String.format("%02d", value.length()))
                    .append(value);
            }
        }
        return sb.toString();
    }

    public void validate() {
        Stream.of(allTags)
                .forEach(this::validateTag);
    }

    private void validateTag(ITag tag) {
        int tagNumber = Integer.parseInt(tag.getTag());
        Serializable serializable = this.map.get(tag.getTag());
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
