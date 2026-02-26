package BusinessLogicLayer.Utils;

import net.oujda_nlp_team.AlKhalil2Analyzer;
import net.oujda_nlp_team.entity.ResultList;
import net.oujda_nlp_team.util.Stemming;
import net.oujda_nlp_team.entity.Segment;

import java.util.ArrayList;
import java.util.List;

public class NLPRootService {

    public List<String> getRoots(String word) {
        ResultList result = AlKhalil2Analyzer.getInstance().processToken(word);
        List<String> roots = new ArrayList<>();

        if (result.isAnalyzed()) {
            roots.addAll(result.getAllRoots());
        } else {
            List<Segment> segments = Stemming.getInstance().getListsSegment(word);
            for (Segment segment : segments) {
                String root = segment.getStem();
                if (root != null) {
                    roots.add(root);
                }
            }
        }

        return roots;
    }
}
