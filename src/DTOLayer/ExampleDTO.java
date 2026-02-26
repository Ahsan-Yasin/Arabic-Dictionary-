package DTOLayer;

public class ExampleDTO {
    private int id;
    private int wordId;
    private String sentence;
    private String source;
    private String reference;


    public ExampleDTO(int id, int wordId, String sentence, String source, String reference) {
        this.id = id;
        this.wordId = wordId;
        this.sentence = sentence;
        this.source = source;
        this.reference = reference;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWordId() {
        return wordId;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}
