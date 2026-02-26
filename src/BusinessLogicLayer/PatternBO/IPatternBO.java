package BusinessLogicLayer.PatternBO;
import DTOLayer.PatternDTO;
import DTOLayer.WordDTO;

import java.util.List;
public interface IPatternBO {

    int addPattern(PatternDTO pattern);

    PatternDTO getPatternById(int id);

    List<PatternDTO> getAllPatterns();

    List<PatternDTO> getPatternsByWordId(int wordId);

    boolean updatePattern(PatternDTO pattern);

    boolean deletePattern(int id);

    List<WordDTO> getWordsByPatternName(String patternName);

    String getPatternId(String patternText);

    List<PatternDTO> getPatternsByText(String text);

    PatternDTO getPatternByExactText(String text);
}
