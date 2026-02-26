package BusinessLogicLayer.Utils;

import DAOLayer.DAOFacade;
import DAOLayer.SegmentationDAO;
import DTOLayer.SegmentationDTO;
import DTOLayer.WordDTO;
import net.oujda_nlp_team.entity.Segment;
import net.oujda_nlp_team.util.Stemming;
import org.jdom2.adapters.DOMAdapter;

import java.util.ArrayList;
import java.util.List;

public class SegmentationService {

    private final Stemming stemmer;
    private final DAOFacade segmentationDAO;

    public SegmentationService(DAOFacade segmentationDAO) {
        this.stemmer = Stemming.getInstance();
        this.segmentationDAO = segmentationDAO;
    }
    public List<SegmentationDTO> getAllSegments ( )
    {
         return  segmentationDAO.getAllSegmentations();
    }
    public List<Segment> segmentWord(String word) {
        return stemmer.getListsSegment(word);
    }

    public List<SegmentationDTO> segmentAndSave(String word, int wordId) {
        List<Segment> segments = segmentWord(word);
        List<SegmentationDTO> segDTOs = new ArrayList<>();

        for (Segment seg : segments) {
            SegmentationDTO segDTO = new SegmentationDTO(
                    wordId,
                    seg.getProclitic().getUnvoweledform(),
                    seg.getStem(),
                    seg.getEnclitic().getUnvoweledform(),
                    seg.getProclitic().getUnvoweledform() + "+" + seg.getStem() + "+" + seg.getEnclitic().getUnvoweledform(),
                    "AlKhalil"
            );
            int id = segmentationDAO.addSegmentation(segDTO);
            segDTO.setId(id);
            segDTOs.add(segDTO);
        }

        return segDTOs;
    }

    public void clear() {
        stemmer.clear();
    }
    public List<WordDTO> searchWordsBySegment(String segment) {
        return segmentationDAO.getWordsBySegment(segment);
    }

}
