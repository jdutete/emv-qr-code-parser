package za.co.icefactor.emvco;

public class UnknownTagException extends RuntimeException {
    public UnknownTagException(String tagString) {
        super(tagString);
    }
}
