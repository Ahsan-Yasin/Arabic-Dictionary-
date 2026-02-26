package DAOLayer;

import DTOLayer.*;
import java.util.List;

public class DAOFacade {

    private final ExampleDAO exampleDAO;
    private final LemmatizationDAO lemmatizationDAO;
    private final MetaDataDAO metaDataDAO;
    private final PatternDAO patternDAO;

    private final RootDAO rootDAO;
    private final SegmentationDAO segmentationDAO;
    private final WordDAO wordDAO ;
    public DAOFacade(
            ExampleDAO exampleDAO,
            LemmatizationDAO lemmatizationDAO,
            MetaDataDAO metaDataDAO,
            PatternDAO patternDAO,
            RootDAO rootDAO,
            SegmentationDAO segmentationDAO ,
            WordDAO wDao
    ) {
        this.exampleDAO = exampleDAO;
        this.lemmatizationDAO = lemmatizationDAO;
        this.metaDataDAO = metaDataDAO;
        this.patternDAO = patternDAO;
        this.rootDAO = rootDAO;
        this.segmentationDAO = segmentationDAO;
        this.wordDAO=wDao;
    }

    // -------- ExampleDAO Methods --------
    public int addExample(ExampleDTO example) {
        return exampleDAO.addExample(example);
    }

    public ExampleDTO getExampleById(int id) {
        return exampleDAO.getExampleById(id);
    }

    public List<ExampleDTO> getExamplesByWordId(int wordId) {
        return exampleDAO.getExamplesByWordId(wordId);
    }

    public boolean updateExample(ExampleDTO example) {
        return exampleDAO.updateExample(example);
    }

    public boolean deleteExample(int id) {
        return exampleDAO.deleteExample(id);
    }
    public List<ExampleDTO> getExamplesByText(String text)
    {
        return exampleDAO.getExamplesByText(text );
    }
    public List<ExampleDTO> getAllExamples()
    {
        return exampleDAO.getAllExamples() ;
    }
    // -------- LemmatizationDAO Methods --------
    public int addLemma(LemmaDTO lemma) {
        return lemmatizationDAO.addLemma(lemma);
    }

    public LemmaDTO getLemmaById(int id) {
        return lemmatizationDAO.getLemmaById(id);
    }

    public List<LemmaDTO> getLemmasByWordId(int wordId) {
        return lemmatizationDAO.getLemmasByWordId(wordId);
    }

    public List<LemmaDTO> getAllLemmas() {
        return lemmatizationDAO.getAllLemmas();
    }

    public boolean updateLemma(LemmaDTO lemma) {
        return lemmatizationDAO.updateLemma(lemma);
    }

    public boolean deleteLemma(int id) {
        return lemmatizationDAO.deleteLemma(id);
    }

    public List<WordDTO> getWordsByLemma(String lemmaText) {
        return lemmatizationDAO.getWordsByLemma(lemmaText);
    }

    // -------- MetaDataDAO Methods --------
    public void addMetaData(IMetaData metaData) {
        metaDataDAO.addMetaData(metaData);
    }

    public NounMetaDataDTO getNounMetaDataByWordId(int wordId) {
        return metaDataDAO.getNounMetaDataByWordId(wordId);
    }

    public VerbMetaDataDTO getVerbMetaDataByWordId(int wordId) {
        return metaDataDAO.getVerbMetaDataByWordId(wordId);
    }

    public boolean updateNounMetaData(NounMetaDataDTO noun) {
        return metaDataDAO.updateNounMetaData(noun);
    }

    public boolean updateVerbMetaData(VerbMetaDataDTO verb) {
        return metaDataDAO.updateVerbMetaData(verb);
    }

    public void DeleteMetaDataVerb(int id) {
        metaDataDAO.DeleteMetaDataVerb(id);
    }

    public void deleteMetaDataNoun(int id) {
        metaDataDAO.deleteMetaDataNoun(id);
    }
    public List<NounMetaDataDTO> getAllNounMetaData() {
        return metaDataDAO.getAllNounMetaData();
    }

    public List<VerbMetaDataDTO> getAllVerbMetaData()
    {
        return metaDataDAO.getAllVerbMetaData();
    }
    public boolean deleteMetaDataVerb(int wordId)
    {
        return metaDataDAO.DeleteMetaDataVerb(wordId);
    }
    // -------- PatternDAO Methods --------
    public int addPattern(PatternDTO pattern) {
        return patternDAO.addPattern(pattern);
    }

    public String getPatternId(String patternText) {
        return patternDAO.getPatternId(patternText);
    }

    public PatternDTO getPatternById(int id) {
        return patternDAO.getPatternById(id);
    }

    public List<PatternDTO> getPatternsByWordId(int wordId) {
        return patternDAO.getPatternsByWordId(wordId);
    }

    public List<PatternDTO> getAllPatterns() {
        return patternDAO.getAllPatterns();
    }

    public boolean updatePattern(PatternDTO pattern) {
        return patternDAO.updatePattern(pattern);
    }
    public List<PatternDTO> getPatternsByText(String text)
    {
        return  patternDAO.getPatternsByText(text);
    }
    public PatternDTO getPatternByExactText(String text)
    {
        return   patternDAO.getPatternByExactText(text);
    }
    public List<WordDTO> getWordsByPatternName(String patternName) {
        return patternDAO.getWordsByPatternName(patternName);
    }

    public boolean deletePattern(int id) {
        return patternDAO.deletePattern(id);
    }



    // -------- RootDAO Methods --------
    public int addRoot(String rootLetters) {
        return rootDAO.addRoot(rootLetters);
    }
    public List<RootDTO> getRootsByText(String text)
    {
        return rootDAO.getRootsByText(text);
    }
    public RootDTO getRoot(int id) {
        return rootDAO.getRoot(id);
    }

    public int getRootId(String s) {
        return rootDAO.getRootId(s);
    }

    public List<RootDTO> getAllRoots() {
        return rootDAO.getAllRoots();
    }

    public boolean updateRoot(int id, String newLetters) {
        return rootDAO.updateRoot(id, newLetters);
    }

    public boolean deleteRoot(int id) {
        return rootDAO.deleteRoot(id);
    }

    // -------- SegmentationDAO Methods --------
    public int addSegmentation(SegmentationDTO seg) {
        return segmentationDAO.addSegmentation(seg);
    }

    public SegmentationDTO getSegmentationById(int id) {
        return segmentationDAO.getSegmentationById(id);
    }

    public List<SegmentationDTO> getSegmentationsByWordId(int wordId) {
        return segmentationDAO.getSegmentationsByWordId(wordId);
    }

    public List<SegmentationDTO> getAllSegmentations() {
        return segmentationDAO.getAllSegmentations();
    }

    public boolean updateSegmentation(SegmentationDTO seg) {
        return segmentationDAO.updateSegmentation(seg);
    }

    public boolean deleteSegmentation(int id) {
        return segmentationDAO.deleteSegmentation(id);
    }

    public List<WordDTO> getWordsBySegment(String segmentPart) {
        return segmentationDAO.getWordsBySegment(segmentPart);
    }

    // -------- WordDAO Methods --------
    public int addWord(WordDTO word) {
        return wordDAO.addWord(word);
    }
    public int getWordIdByArabicForm(String arabicForm)
    {
        return wordDAO.getWordIdByArabicForm(arabicForm);
    }

    public List<WordDTO> getWordsBySubstring(String substring) {
        return wordDAO.getWordsBySubstring(substring);
    }

    public WordDTO getWordById(int id) {
        return wordDAO.getWordById(id);
    }

    public WordDTO getWordByBaseForm(String baseForm) {
        return wordDAO.getWordByBaseForm(baseForm);
    }

    public WordDTO getWordByUrduMeaning(String urdu) {
        return wordDAO.getWordByUrduMeaning(urdu);
    }

    public List<WordDTO> getAllWords() {
        return wordDAO.getAllWords();
    }

    public List<WordDTO> getWordsByRoot(int rootId) {
        return wordDAO.getWordsByRoot(rootId);
    }

    public boolean updateWord(WordDTO word) {
        return wordDAO.updateWord(word);
    }

    public boolean deleteWord(int id) {
        return wordDAO.deleteWord(id);
    }

    public boolean deleteWordByBaseForm(String baseForm) {
        return wordDAO.deleteWordByBaseForm(baseForm);
    }

}
