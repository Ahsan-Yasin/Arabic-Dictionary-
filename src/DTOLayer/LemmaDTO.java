package DTOLayer;

public class LemmaDTO {

    private int id;
    private int wordId;
    private String lemmaText;
    private String partOfSpeech;

    public LemmaDTO(int id, int wordId, String lemmaText, String partOfSpeech) {
        this.id = id;
        this.wordId = wordId;
        this.lemmaText = lemmaText;
        this.partOfSpeech = partOfSpeech;
    }

    public LemmaDTO(int wordId, String lemmaText, String partOfSpeech) {
        this.wordId = wordId;
        this.lemmaText = lemmaText;
        this.partOfSpeech = partOfSpeech;
    }

    public int getId() {
        return id;
    }

    public int getWordId() {
        return wordId;
    }

    public String getLemmaText() {
        return lemmaText;
    }

    public String getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setLemmaText(String lemmaText) {
        this.lemmaText = lemmaText;
    }

    public void setPartOfSpeech(String partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    public void setId(int id) {
        this.id=id ;
    }

    public String getLemma() {
        return lemmaText;
    }
}
