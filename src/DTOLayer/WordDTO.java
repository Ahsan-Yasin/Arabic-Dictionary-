package DTOLayer;

import java.util.List;

public class WordDTO {
    private int id;
    private String arabicForm;
    private String baseForm;
    private String urduMeaning;
    private String partOfSpeech;
    private int rootId;
    int PatternID;
    private List<ExampleDTO> examples;
    public WordDTO() {
    }

    public WordDTO(int pid , int id, String arabicForm, String baseForm, String urduMeaning, String partOfSpeech, int rootId) {
        this.PatternID = pid ;
        this.id = id;
        this.arabicForm = arabicForm;
        this.baseForm = baseForm;
        this.urduMeaning = urduMeaning;
        this.partOfSpeech = partOfSpeech;
        this.rootId = rootId;
    }

    public List<ExampleDTO> getExamples() {
        return examples; }
    public void setExamples(List<ExampleDTO> examples) {
        this.examples = examples; }
    @Override
    public String toString() {
        return arabicForm + " (" + urduMeaning  + ")";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArabicForm() {
        return arabicForm;
    }

    public void setArabicForm(String arabicForm) {
        this.arabicForm = arabicForm;
    }

    public String getBaseForm() {
        return baseForm;
    }

    public void setBaseForm(String baseForm) {
        this.baseForm = baseForm;
    }

    public String getUrduMeaning() {
        return urduMeaning;
    }

    public void setUrduMeaning(String urduMeaning) {
        this.urduMeaning = urduMeaning;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public int getRootId() {
        return rootId;
    }

    public void setRootId(int rootId) {
        this.rootId = rootId;
    }

    public int getPatternId() {
        return PatternID;
    }
}
