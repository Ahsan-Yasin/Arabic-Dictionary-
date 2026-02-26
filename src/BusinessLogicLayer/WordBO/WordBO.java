package BusinessLogicLayer.WordBO;


import BusinessLogicLayer.Utils.*;
import DAOLayer.DAOFacade;
import DAOLayer.WordDAO;
import DTOLayer.*;

import java.sql.SQLException;
import java.util.List;

public class WordBO implements IWordBO {

    private final DAOFacade wordDAO;
    private final RootSuggestionService rootSuggestionService;

    private final RegexSearch regex ;
    private final SegmentationService segmentationService;
    private final LemmatizationService lemmatizationService;
    private final NLPRootService nlpRootService;

    public WordBO(DAOFacade wordDAO, RegexSearch res,
                  RootSuggestionService rootSuggestionService,
                  SegmentationService segmentationService,
                  LemmatizationService lemmatizationService,
                  NLPRootService nlpRootService) {

        this.wordDAO = wordDAO;
        this.rootSuggestionService = rootSuggestionService;
        this.segmentationService = segmentationService;
        this.lemmatizationService = lemmatizationService;
        this.nlpRootService = nlpRootService;
        this.regex = res;
    }
    @Override
    public  List<WordDTO> getSubstringWords(String s)
    {
      return wordDAO.getWordsBySubstring(s);
    }
    @Override
    public List<RootDTO> getRootSuggestions(String baseForm) {
        return rootSuggestionService.suggestRoots(baseForm, 3);
    }
    @Override
    public List<WordDTO> getWordsByRegexSearch(String substring) {
        return  regex.regexSearch(  substring);
    }

    @Override
    public int addWord(WordDTO word) throws SQLException {
        int id =  wordDAO.addWord(word);
        segmentationService.segmentAndSave(word.getArabicForm(),id);
        lemmatizationService.lemmatizeAndSave(word.getArabicForm(), id);
        return  id ;
    }


    @Override
    public WordDTO getWordById(int id) {
        return wordDAO.getWordById(id);
    }

    @Override
    public WordDTO getWordByBaseForm(String baseForm) {
        return wordDAO.getWordByBaseForm(baseForm);
    }

    @Override
    public WordDTO getWordByUrduMeaning(String urdu) {
        return wordDAO.getWordByUrduMeaning(urdu);
    }

    @Override
    public List<WordDTO> getWordsByRoot(int rootId) {
        return wordDAO.getWordsByRoot(rootId);
    }

    @Override
    public List<WordDTO> getAllWords() {
        return wordDAO.getAllWords();
    }

    @Override
    public boolean updateWord(WordDTO word) {
        return wordDAO.updateWord(word);
    }

    @Override
    public boolean deleteWord(int id) {
        return wordDAO.deleteWord(id);
    }

    @Override
    public boolean deleteWordByBaseForm(String baseForm) {
        return wordDAO.deleteWordByBaseForm(baseForm);
    }
    //segmetation ;;
    public List<SegmentationDTO> segmentAndSaveWord(String word, int wordId) throws SQLException {
        return segmentationService.segmentAndSave(word, wordId);
    }
    public List<LemmaDTO> lemmatizeAndSaveWord(String word, int wordId) {
        return lemmatizationService.lemmatizeAndSave(word, wordId);
    }
    public List<SegmentationDTO> getAllSegments ( )
    {
        return segmentationService .getAllSegments();
    }
    public List<String> getLemmas(String word) {
        return lemmatizationService.getLemma(word);
    }

    public List<String> getRootsFromNLP(String word) {
        return nlpRootService.getRoots(word);
    }
    // In WordBO
    public List<WordDTO> getWordsByLemma(String lemma) {
        return lemmatizationService.searchWordsByLemma(lemma);
    }

    public List<WordDTO> getWordsBySegment(String segment) {
        return segmentationService.searchWordsBySegment(segment);
    }
    public List<LemmaDTO> getAllLemmas()
    {
        return lemmatizationService.getAllLemmas();
    }
    public int getWordIdByArabicForm(String arabicForm)
    {
        return wordDAO.getWordIdByArabicForm(arabicForm);
    }
}
