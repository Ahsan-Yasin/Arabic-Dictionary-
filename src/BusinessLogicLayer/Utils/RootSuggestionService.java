package BusinessLogicLayer.Utils;

import DAOLayer.DAOFacade;
import DAOLayer.RootDAO;
import DTOLayer.RootDTO;

import java.util.ArrayList;
import java.util.List;

public class RootSuggestionService  {

    private final DAOFacade rootDAO;

    public RootSuggestionService(DAOFacade rootDAO) {
        this.rootDAO = rootDAO;
    }


    public List<RootDTO> suggestRoots(String word, int limit) {

        List<RootDTO> allRoots = rootDAO.getAllRoots();
        List<ScoredRoot> scored = new ArrayList<>();

        for (RootDTO r : allRoots) {
            int score = similarity(word, r.getRootLetters());
            scored.add(new ScoredRoot(r, score));
        }

        return scored.stream()
                .sorted((a,b) -> b.score - a.score)  // highest score first
                .limit(limit)
                .map(sr -> sr.root)
                .toList();
    }

    public int similarity(String a, String b) {
        return longestCommonSubsequence(a, b);
    }

    private int longestCommonSubsequence(String a, String b) {
        int[][] dp = new int[a.length()+1][b.length()+1];

        for (int i=1; i<=a.length(); i++) {
            for (int j=1; j<=b.length(); j++) {
                if (a.charAt(i-1) == b.charAt(j-1))
                    dp[i][j] = dp[i-1][j-1] + 1;
                else
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
            }
        }
        return dp[a.length()][b.length()];
    }

    private static class ScoredRoot {
        RootDTO root;
        int score;
        ScoredRoot(RootDTO r, int score) {
            this.root = r;
            this.score = score;
        }
    }
}
