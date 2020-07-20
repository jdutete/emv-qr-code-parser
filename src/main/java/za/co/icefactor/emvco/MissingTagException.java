package za.co.icefactor.emvco;

import za.co.icefactor.emvco.tags.ITag;

public class MissingTagException extends RuntimeException {
    public MissingTagException(ITag tag){
        super(tag.getTag() + " is required");
    }
}
