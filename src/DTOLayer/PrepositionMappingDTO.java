package DTOLayer;


public class PrepositionMappingDTO {
    private int wordId;
    private String preposition;
    private String urduMeaning;

    public PrepositionMappingDTO() {
    }

    public PrepositionMappingDTO(int wordId, String preposition, String urduMeaning) {
        this.wordId = wordId;
        this.preposition = preposition;
        this.urduMeaning = urduMeaning;
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public String getPreposition() {
        return preposition;
    }

    public void setPreposition(String preposition) {
        this.preposition = preposition;
    }

    public String getUrduMeaning() {
        return urduMeaning;
    }

    public void setUrduMeaning(String urduMeaning) {
        this.urduMeaning = urduMeaning;
    }
}
