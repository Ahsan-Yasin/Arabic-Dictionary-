package BusinessLogicLayer.WordBO;


import DTOLayer.*;

import java.sql.SQLException;
import java.util.List;

public interface IWordBO {

    int addWord(WordDTO word) throws SQLException;

    WordDTO getWordById(int id);

    WordDTO getWordByBaseForm(String baseForm);

    WordDTO getWordByUrduMeaning(String urdu);

    List<WordDTO> getWordsByRoot(int rootId);

    List<WordDTO> getAllWords();

    boolean updateWord(WordDTO word);

    boolean deleteWord(int id);

    boolean deleteWordByBaseForm(String baseForm);

    List<WordDTO> getSubstringWords(String s);
    List<WordDTO> getWordsByRegexSearch(String substring);
    List<RootDTO> getRootSuggestions(String baseForm);

    public List<SegmentationDTO> segmentAndSaveWord(String word, int wordId) throws SQLException;
    public List<WordDTO> getWordsByLemma(String lemma);
    public List<WordDTO> getWordsBySegment(String segment);
    public List<String> getRootsFromNLP(String word);
    public List<SegmentationDTO> getAllSegments ( );
    public List<LemmaDTO> getAllLemmas();

    int getWordIdByArabicForm(String arabicForm);
}