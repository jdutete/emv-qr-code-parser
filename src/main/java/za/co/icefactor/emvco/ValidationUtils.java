package za.co.icefactor.emvco;

import java.util.Objects;
import java.util.regex.Pattern;

public class ValidationUtils {
    private static Pattern TAG_PATTERN = Pattern.compile("^\\d{2}$");
    private ValidationUtils() {
    }

    public static <T> void notNull(T object) {
        Objects.requireNonNull(object);
    }

    public static void validateTagString(String tag) throws UnknownTagException {

        if (!TAG_PATTERN.matcher(tag).matches()) {
            throw new UnknownTagException(tag);
        }
    }
}
