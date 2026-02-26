package BusinessLogicLayer.Utils;

import DAOLayer.DAOFacade;
import DAOLayer.LemmatizationDAO;
import DTOLayer.LemmaDTO;
import DTOLayer.WordDTO;
import net.oujda_nlp_team.AlKhalil2Analyzer;
import net.oujda_nlp_team.entity.ResultList;

import java.util.ArrayList;
import java.util.List;

public class LemmatizationService {

    private final DAOFacade lemmatizationDAO;

    public LemmatizationService(DAOFacade lemmatizationDAO) {
        this.lemmatizationDAO = lemmatizationDAO;
    }

    /**
     * Get lemmas for a word using AlKhalil2Analyzer and save them in the database.
     * @param word The Arabic word
     * @param wordId The ID of the word in DB
     * @return List of saved LemmaDTOs
     */
    public List<LemmaDTO> lemmatizeAndSave(String word, int wordId) {
        ResultList result = AlKhalil2Analyzer.getInstance().processToken(word);
        List<String> lemmas = result.getAllLemmas();
        List<LemmaDTO> lemmaDTOs = new ArrayList<>();

        for (String lemmaText : lemmas) {
            // Optional: You could extract part-of-speech if available
            String pos = "";
            LemmaDTO lemmaDTO = new LemmaDTO(wordId, lemmaText, pos);
            int id = lemmatizationDAO.addLemma(lemmaDTO);
            lemmaDTO.setId(id);
            lemmaDTOs.add(lemmaDTO);
        }

        return lemmaDTOs;
    }

    /**
     * Existing method to just get lemmas without saving
     */
    public List<String> getLemma(String word) {
        ResultList result = AlKhalil2Analyzer.getInstance().processToken(word);
        return result.getAllLemmas();
    }

    /**
     * Lemmatize a file (existing functionality)
     */
    public void lemmatizeFile(String fileIn, String encodingIn, String fileOut, String encodingOut) {
        net.oujda_nlp_team.ADATAnalyzer.getInstance().processLemmatization(fileIn, encodingIn, fileOut, encodingOut);
    }
    public List<WordDTO> searchWordsByLemma(String lemma) {
        return lemmatizationDAO.getWordsByLemma(lemma);
    }
    public List<LemmaDTO> getAllLemmas()
    {
        return  lemmatizationDAO.getAllLemmas();
    }
}
