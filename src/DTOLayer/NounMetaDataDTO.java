package DTOLayer;


public class NounMetaDataDTO implements IMetaData {
    private int wordId;
    private String gender;      // Masculine / Feminine
    private String number;      // Singular / Dual / Plural
    private String grammaticalCase; // Nominative / Accusative / Genitive

    public NounMetaDataDTO() {
    }

    public NounMetaDataDTO(int wordId, String gender, String number, String grammaticalCase) {
        this.wordId = wordId;
        this.gender = gender;
        this.number = number;
        this.grammaticalCase = grammaticalCase;
    }

    @Override
    public int getWordId() {
        return wordId;
    }

    @Override
    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getGrammaticalCase() {
        return grammaticalCase;
    }

    public void setGrammaticalCase(String grammaticalCase) {
        this.grammaticalCase = grammaticalCase;
    }
}
