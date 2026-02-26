package BusinessLogicLayer.PatternBO;

import DAOLayer.DAOFacade;
import DAOLayer.PatternDAO;
import DTOLayer.PatternDTO;
import DTOLayer.WordDTO;

import java.util.List;

public class PatternBO implements  IPatternBO  {

    private final DAOFacade patternDAO;

    public PatternBO(DAOFacade patternDAO) {
        this.patternDAO = patternDAO;
    }

    // Add a new pattern
    public int addPattern(PatternDTO pattern) {
        return patternDAO.addPattern(pattern);
    }


    public PatternDTO getPatternById(int id) {
        return patternDAO.getPatternById(id);
    }


    public List<PatternDTO> getAllPatterns() {
        return patternDAO.getAllPatterns();
    }

    // Get patterns for a specific word
    public List<PatternDTO> getPatternsByWordId(int wordId) {
        return patternDAO.getPatternsByWordId(wordId);
    }

    // Update pattern
    public boolean updatePattern(PatternDTO pattern) {
        return patternDAO.updatePattern(pattern);
    }

    // Delete pattern
    public boolean deletePattern(int id) {
        return patternDAO.deletePattern(id);
    }
    public List<WordDTO> getWordsByPatternName(String patternName)
    {
        return  patternDAO.getWordsByPatternName(patternName);
    }
    public String getPatternId(String patternText)
    {
        return patternDAO.getPatternId(patternText);
    }
    public List<PatternDTO> getPatternsByText(String text)
    {
        return  patternDAO.getPatternsByText(text);
    }
    public PatternDTO getPatternByExactText(String text)
    {
        return   patternDAO.getPatternByExactText(text);
    }
}
