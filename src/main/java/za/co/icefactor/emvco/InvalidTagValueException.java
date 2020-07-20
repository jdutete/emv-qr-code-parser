package za.co.icefactor.emvco;

import za.co.icefactor.emvco.tags.ITag;

public class InvalidTagValueException extends RuntimeException {
    public InvalidTagValueException(ITag tag, String vString) {
        super(String.format("Invalid value %s for tag %s [%s]", vString, tag.getTag(), tag.getDescription()));
    }
}
