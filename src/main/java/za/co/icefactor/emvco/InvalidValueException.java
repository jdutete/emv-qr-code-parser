package za.co.icefactor.emvco;

import za.co.icefactor.emvco.tags.ITag;

import java.io.Serializable;

public class InvalidValueException extends RuntimeException{

    public InvalidValueException(Serializable value, ITag tag){
        super(value + " does not match pattern " + tag.getPattern() + " for tag " + tag.getTag() + " [" + tag.getDescription()+"]");
    }
}
