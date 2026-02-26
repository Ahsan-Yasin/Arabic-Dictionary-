package DTOLayer;


public class RootDTO {
    private int id;
    private String rootLetters;

    public RootDTO() {}

    public RootDTO(int id ,  String rootLetters) {
        this.rootLetters = rootLetters;
        this.id=id ;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRootLetters() {
        return rootLetters;
    }

    public void setRootLetters(String rootLetters) {
        this.rootLetters = rootLetters;
    }
}
