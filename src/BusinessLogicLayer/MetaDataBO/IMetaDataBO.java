package BusinessLogicLayer.MetaDataBO;


import DTOLayer.IMetaData;
import DTOLayer.NounMetaDataDTO;
import DTOLayer.VerbMetaDataDTO;

import java.util.List;

public interface IMetaDataBO {

    void addMetaData(IMetaData metaData);


    NounMetaDataDTO getNounMetaDataByWordId(int wordId);
    VerbMetaDataDTO getVerbMetaDataByWordId(int wordId);


    boolean updateNounMetaData(NounMetaDataDTO noun);
    boolean updateVerbMetaData(VerbMetaDataDTO verb);


    void deleteNounMetaData(int id);
    void deleteVerbMetaData(int id);

    List<NounMetaDataDTO> getAllNounMetaData();

    List<VerbMetaDataDTO> getAllVerbMetaData();

    boolean deleteMetaDataVerb(int wordId);
}