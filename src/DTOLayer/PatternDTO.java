package DTOLayer;

public class PatternDTO {

    private int id;
    private int wordId;
    private String patternName;


    public PatternDTO(int id, int wordId, String patternName) {
        this.id = id;
        this.wordId = wordId;
        this.patternName = patternName;

    }

    public PatternDTO(int wordId, String patternName) {
        this.wordId = wordId;
        this.patternName = patternName;

    }

    public int getId() {
        return id;
    }

    public int getWordId() {
        return wordId;
    }

    public String getPatternName() {
        return patternName;
    }



    public void setPatternName(String patternName) {
        this.patternName = patternName;
    }


    public String getPattern() {
        return patternName ;
    }
}
