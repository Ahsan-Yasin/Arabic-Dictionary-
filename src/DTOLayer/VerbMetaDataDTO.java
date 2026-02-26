package DTOLayer;


public class VerbMetaDataDTO implements IMetaData {
    private int wordId;
    private String verbForm;     // Form I–X
    private String tense;        // Perfect / Imperfect / Imperative
    private String transitivity; // Transitive / Intransitive

    public VerbMetaDataDTO() {
    }

    public VerbMetaDataDTO(int wordId, String verbForm, String tense, String transitivity) {
        this.wordId = wordId;
        this.verbForm = verbForm;
        this.tense = tense;
        this.transitivity = transitivity;
    }

    @Override
    public int getWordId() {
        return wordId;
    }

    @Override
    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public String getVerbForm() {
        return verbForm;
    }

    public void setVerbForm(String verbForm) {
        this.verbForm = verbForm;
    }

    public String getTense() {
        return tense;
    }

    public void setTense(String tense) {
        this.tense = tense;
    }

    public String getTransitivity() {
        return transitivity;
    }

    public void setTransitivity(String transitivity) {
        this.transitivity = transitivity;
    }
}
