package za.co.icefactor.emvco.tags;

import java.io.Serializable;
import java.util.function.Function;
import java.util.regex.Pattern;

public interface ITag extends Serializable {

    String getTag();

    String getDescription();

    Pattern getPattern();

    boolean isMandatory();

    Function<String, String> getValueExplainer();

}
