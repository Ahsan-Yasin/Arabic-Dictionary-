package BusinessLogicLayer.InputLemmatization;

import java.util.List;
import java.util.Map;

public interface IInputLemmatization {
    public Map<String, Boolean> processText(String text) ;

    List<String> getAllLemmasFromText(String text);

    List<String> getAllRootsFromText(String text);
}
