package BusinessLogicLayer;

import BusinessLogicLayer.ExampleBO.ExampleBO;
import BusinessLogicLayer.ExampleBO.IExampleBO;
import BusinessLogicLayer.InputLemmatization.IInputLemmatization;
import BusinessLogicLayer.MetaDataBO.IMetaDataBO;
import BusinessLogicLayer.MetaDataBO.MetaDataBO;
import BusinessLogicLayer.PatternBO.IPatternBO;
import BusinessLogicLayer.RootBO.IRootBO;
import BusinessLogicLayer.RootBO.RootBO;
import BusinessLogicLayer.Utils.GlossaryGenerator;
import BusinessLogicLayer.Utils.WordGenerator;
import BusinessLogicLayer.WordBO.IWordBO;
import BusinessLogicLayer.WordBO.WordBO;
import DAOLayer.MetaDataDAO;

import DTOLayer.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BOFacade {

    private final IRootBO rootBO;
    private final IWordBO wordBO;
    private final IExampleBO exampleBO;
    private final IMetaDataBO metaDataBO;
    private final IPatternBO patternBO ;
     private final IInputLemmatization textLemmatization;
     private final WordGenerator wordGenerator ;
     private final GlossaryGenerator glossaryGenerator ;
    public BOFacade(IRootBO rootBO, IWordBO wordBO, IExampleBO exampleBO, IMetaDataBO metaDataBO,IPatternBO patternbo,IInputLemmatization lemeTextService , WordGenerator wGen , GlossaryGenerator glGen) {
        this.rootBO = rootBO;
        this.wordBO = wordBO;
        this.exampleBO = exampleBO;
        this.metaDataBO = metaDataBO;
        this .patternBO = patternbo ;
        this.textLemmatization=lemeTextService ;
        this.wordGenerator= wGen  ;
        this.glossaryGenerator= glGen;
    }


    public int addRoot(String rootLetters) {
        return rootBO.addRoot(rootLetters);
    }

    public boolean updateRoot(int id, String newLetters) {
        return rootBO.updateRoot(id, newLetters);
    }

    public RootDTO getRootById(int id) {
        return rootBO.getRoot(id);
    }

    public boolean deleteRoot(int id) {
        return rootBO.deleteRoot(id);
    }
    public List<RootDTO> getRootsByText(String text)
    {
        return rootBO.getRootsByText(text);
    }
    public List<RootDTO> getAllRoots() {
        return rootBO.getAllRoots();
    }
    public int getRootId(String s) {
        return  rootBO.getRootId(s);
    }
    // ----- WORDS -----
    public int addWord(WordDTO wordDTO) throws SQLException {
        return wordBO.addWord(wordDTO);
    }

    public List<WordDTO> getSubStringsWords(String s) {
       return  wordBO.getSubstringWords(s);
    }

    public WordDTO getWordById(int id) {
        return wordBO.getWordById(id);
    }

    public WordDTO getWordByBaseForm(String base) {
        return wordBO.getWordByBaseForm(base);
    }

    public WordDTO getWordByUrduMeaning(String urdu) {
        return wordBO.getWordByUrduMeaning(urdu);
    }

    public List<WordDTO> getAllWords() {

        return wordBO.getAllWords();
    }
    public List<WordDTO> getWordsByRegexSearch(String substring)
    {
        return wordBO.getWordsByRegexSearch(substring);
    }
    public boolean updateWord(WordDTO wordDTO) {

        return wordBO.updateWord(wordDTO);
    }

    public List<WordDTO> getWordsByRoot(int rootId) {
        return wordBO.getWordsByRoot(rootId);
    }

    public boolean deleteWord(int id) {
        return wordBO.deleteWord(id);
    }

    // ----- EXAMPLES -----
    public int addExample(ExampleDTO exampleDTO) {
        return exampleBO.addExample(exampleDTO);
    }

    public ExampleDTO getExampleById(int id) {
        return exampleBO.getExampleById(id);
    }

    public List<ExampleDTO> getExamplesByWordId(int wordId) {
        return exampleBO.getExamplesByWordId(wordId);
    }

    public boolean updateExample(ExampleDTO exampleDTO) {
        return exampleBO.updateExample(exampleDTO);
    }

    public boolean deleteExample(int id) {
        return exampleBO.deleteExample(id);
    }
    public List<ExampleDTO> getExamplesByText(String text)
    {
        return exampleBO.getExamplesByText(text );
    }
    public List<ExampleDTO> getAllExamples()
    {
        return exampleBO.getAllExamples() ;
    }
    // ----- METADATA -----
    public void addMetaData(IMetaData metaDataDTO) {
        metaDataBO.addMetaData(metaDataDTO);
    }

    public void deleteNounMetaData(int id) {
        metaDataBO.deleteNounMetaData(id);
    }

    public void deleteVerbMetaData(int id) {
        metaDataBO.deleteVerbMetaData(id);
    }

    public boolean updateNounMetaData(DTOLayer.NounMetaDataDTO noun) {
        return metaDataBO.updateNounMetaData(noun);
    }

    public boolean updateVerbMetaData(DTOLayer.VerbMetaDataDTO verb) {
        return metaDataBO.updateVerbMetaData(verb);
    }

    public DTOLayer.NounMetaDataDTO getNounMetaDataByWordId(int wordId) {
        return metaDataBO.getNounMetaDataByWordId(wordId);
    }

    public DTOLayer.VerbMetaDataDTO getVerbMetaDataByWordId(int wordId) {
        return metaDataBO.getVerbMetaDataByWordId(wordId);
    }
    public List<NounMetaDataDTO> getAllNounMetaData() {
        return metaDataBO.getAllNounMetaData();
    }

    public List<VerbMetaDataDTO> getAllVerbMetaData()
    {
        return metaDataBO.getAllVerbMetaData();
    }
    public boolean deleteMetaDataVerb(int wordId)
    {
        return metaDataBO.deleteMetaDataVerb(wordId);
    }
    public List<WordDTO> getWordsByLemma(String lemma) {
        return wordBO.getWordsByLemma(lemma);
    }
    public int getWordIdByArabicForm(String arabicForm)
    {
        return wordBO.getWordIdByArabicForm(arabicForm);
    }
    public List<WordDTO> getWordsBySegment(String segment) {
        return wordBO.getWordsBySegment(segment);
    }
    public List<String> getRootsFromNLP(String word)
    {
     return wordBO.getRootsFromNLP(word) ;
    }
    public List<SegmentationDTO> getAllSegments ( )
    {
        return wordBO.getAllSegments();
    }
    public List<LemmaDTO> getAllLemmas()
    {
        return wordBO.getAllLemmas();
    }


    public int addPattern(PatternDTO pattern) {
        return patternBO.addPattern(pattern);
    }


    public PatternDTO getPatternById(int id) {
        return patternBO.getPatternById(id);
    }


    public List<PatternDTO> getAllPatterns() {
        return patternBO.getAllPatterns();
    }


    public List<PatternDTO> getPatternsByWordId(int wordId) {
        return patternBO.getPatternsByWordId(wordId);
    }

    public String getPatternId(String patternText)
    {
        return patternBO.getPatternId(patternText);
    }
    public boolean updatePattern(PatternDTO pattern) {
        return patternBO.updatePattern(pattern);
    }
    public List<PatternDTO> getPatternsByText(String text)
    {
        return  patternBO.getPatternsByText(text);
    }
    public PatternDTO getPatternByExactText(String text)
    {
        return   patternBO.getPatternByExactText(text);
    }

    public boolean deletePattern(int id) {
        return patternBO .deletePattern(id);
    }

    public List<WordDTO> getWordsByPatternName(String patternName)
    {
        return  patternBO.getWordsByPatternName(patternName);
    }
    //----------- lemmetization  of text funtiosn below -----------
    public List<String> getAllLemmasFromText(String text)
    {
        return textLemmatization.getAllLemmasFromText(text);
    }
    public Map<String, Boolean> processText(String text)
    {
        return textLemmatization.processText(text);
    }
    public List<String> getAllRootsFromText(String text)
    {
        return textLemmatization.getAllRootsFromText(text);
    }
    //----------------- root + pattern  -> word generation ----------------
    public String getWord(String root , String pattern)
    {
        return wordGenerator.getWord(root,pattern);
    }
    //---------------------- glossory generator :
    public Map<String, Set<String>> generateGlossaryFromFile(String filePath)
    {
     return glossaryGenerator.generateGlossaryFromFile(filePath);
    }

    public void exportToCSV(Map<String, Set<String>> glossary, String filePath)
    {
         glossaryGenerator.exportToCSV(glossary,filePath);
    }
}
