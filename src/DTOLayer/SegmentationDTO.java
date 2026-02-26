package DTOLayer;

public class SegmentationDTO {

    private int id;
    private int wordId;
    private String prefix;
    private String stem;
    private String suffix;
    private String fullSegmentation;
    private String source;

    public SegmentationDTO(int id, int wordId, String prefix, String stem,
                           String suffix, String fullSegmentation, String source) {
        this.id = id;
        this.wordId = wordId;
        this.prefix = prefix;
        this.stem = stem;
        this.suffix = suffix;
        this.fullSegmentation = fullSegmentation;
        this.source = source;
    }

    public SegmentationDTO(int wordId, String prefix, String stem,
                           String suffix, String fullSegmentation, String source) {
        this.wordId = wordId;
        this.prefix = prefix;
        this.stem = stem;
        this.suffix = suffix;
        this.fullSegmentation = fullSegmentation;
        this.source = source;
    }

    public int getId() {
        return id;
    }

    public int getWordId() {
        return wordId;
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

    public String getFullSegmentation() {
        return fullSegmentation;
    }

    public String getSource() {
        return source;
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

    public void setFullSegmentation(String fullSegmentation) {
        this.fullSegmentation = fullSegmentation;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setId(int id) {
        this.id=id ;
    }

    public String getSegment() {
        return  stem;
    }
}
