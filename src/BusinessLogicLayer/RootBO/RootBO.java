package BusinessLogicLayer.RootBO;


import DAOLayer.DAOFacade;
import DAOLayer.RootDAO;
import DTOLayer.RootDTO;

import java.util.List;

public class RootBO implements IRootBO {

    private final DAOFacade rootDAO;

    public RootBO(DAOFacade rootDAO) {
        this.rootDAO = rootDAO;
    }

    @Override
    public int addRoot(String letters) {
        return rootDAO.addRoot(letters);
    }

    @Override
    public RootDTO getRoot(int id) {
        return rootDAO.getRoot(id);
    }

    @Override
    public List<RootDTO> getAllRoots() {
        return rootDAO.getAllRoots();
    }

    @Override
    public boolean updateRoot(int id, String newLetters) {
        return rootDAO.updateRoot(id, newLetters);
    }

    @Override
    public boolean deleteRoot(int id) {
        return rootDAO.deleteRoot(id);
    }
    public int getRootId(String s) {
        return  rootDAO.getRootId(s);
    }
    public List<RootDTO> getRootsByText(String text)
    {
        return rootDAO.getRootsByText(text);
    }

}
