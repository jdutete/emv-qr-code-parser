package com.codebyice.emvco;

import com.codebyice.emvco.tags.ITag;

public class MissingTagException extends RuntimeException {
    public MissingTagException(ITag tag){
        super(tag.getTag() + " is required");
    }
}
