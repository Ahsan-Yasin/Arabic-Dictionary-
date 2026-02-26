package BusinessLogicLayer.RootBO;

import DTOLayer.RootDTO;

import java.util.List;

public interface IRootBO {
    int addRoot(String letters);
    RootDTO getRoot(int id);
    List<RootDTO> getAllRoots();
    boolean updateRoot(int id, String newLetters);
    boolean deleteRoot(int id);

    int getRootId(String s);

    List<RootDTO> getRootsByText(String text);
}