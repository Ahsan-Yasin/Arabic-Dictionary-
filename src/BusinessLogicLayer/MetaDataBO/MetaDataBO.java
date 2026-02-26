package BusinessLogicLayer.MetaDataBO;


import DAOLayer.DAOFacade;
import DAOLayer.MetaDataDAO;
import DTOLayer.IMetaData;
import DTOLayer.NounMetaDataDTO;
import DTOLayer.VerbMetaDataDTO;

import java.util.List;

public class MetaDataBO implements IMetaDataBO {

    private final DAOFacade metaDataDAO;


    public MetaDataBO(DAOFacade metaDataDAO) {
        this.metaDataDAO = metaDataDAO;
    }


    public void addMetaData(IMetaData metaData) {
        metaDataDAO.addMetaData(metaData);
    }




    @Override
    public NounMetaDataDTO getNounMetaDataByWordId(int wordId) {
        return metaDataDAO.getNounMetaDataByWordId(wordId);
    }

    @Override
    public VerbMetaDataDTO getVerbMetaDataByWordId(int wordId) {
        return metaDataDAO.getVerbMetaDataByWordId(wordId);
    }



    // ---------------- UPDATE ----------------
    @Override
    public boolean updateNounMetaData(NounMetaDataDTO noun) {
        return metaDataDAO.updateNounMetaData(noun);
    }

    @Override
    public boolean updateVerbMetaData(VerbMetaDataDTO verb) {
        return metaDataDAO.updateVerbMetaData(verb);
    }

    // ---------------- DELETE ----------------
    @Override
    public void deleteNounMetaData(int id) {
        metaDataDAO.deleteMetaDataNoun(id);
    }

    @Override
    public void deleteVerbMetaData(int id) {
        metaDataDAO.DeleteMetaDataVerb(id);
    }
    public List<NounMetaDataDTO> getAllNounMetaData() {
        return metaDataDAO.getAllNounMetaData();
    }

    public List<VerbMetaDataDTO> getAllVerbMetaData()
    {
        return metaDataDAO.getAllVerbMetaData();
    }
    public boolean deleteMetaDataVerb(int wordId)
    {
        return metaDataDAO.deleteMetaDataVerb(wordId);
    }
}