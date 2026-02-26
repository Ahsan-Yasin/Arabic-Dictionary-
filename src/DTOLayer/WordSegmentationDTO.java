package DTOLayer;

import java.util.List;

public class WordSegmentationDTO {
    private int id;
    private int wordId;
    private String originalWord;
    private String prefix;
    private String stem;
    private String suffix;
    private String lemma;

    public WordSegmentationDTO(int id, int wordId, String originalWord, String prefix, String stem, String suffix, String lemma) {
        this.id = id;
        this.wordId = wordId;
        this.originalWord = originalWord;
        this.prefix = prefix;
        this.stem = stem;
        this.suffix = suffix;
        this.lemma = lemma;
    }



    public int getId() {
        return id;
    }

    public int getWordId() {
        return wordId;
    }

    public String getOriginalWord() {
        return originalWord;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getStem() {
        return stem;
    }

    public String getSuffix() {
        return suffix;
    }

    public String getLemma() {
        return lemma;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWordId(int wordId) {
        this.wordId = wordId;
    }

    public void setOriginalWord(String originalWord) {
        this.originalWord = originalWord;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setStem(String stem) {
        this.stem = stem;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }
}
