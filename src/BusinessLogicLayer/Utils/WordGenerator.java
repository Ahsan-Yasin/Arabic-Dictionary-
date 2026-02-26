package BusinessLogicLayer.Utils;

import net.oujda_nlp_team.entity.Formulas;
import net.oujda_nlp_team.impl.VerbalDerivedImpl;
import net.oujda_nlp_team.util.ArabicStringUtil;

import java.util.List;
import java.util.Map;

public class WordGenerator{
    public static String normalizePattern(String userPattern) {
        String[] segments = userPattern.split("-");
        StringBuilder formula = new StringBuilder();
        char[] radicals = {'ف', 'ع', 'ل', 'م', 'ن', 'ص', 'ق', 'ر', 'ب', 'ت', 'ث', 'خ', 'ح', 'د', 'ذ', 'س', 'ش', 'ص', 'ض', 'ط', 'ظ', 'ع', 'غ', 'ف', 'ق', 'ك', 'ل', 'م', 'ن', 'ه', 'و', 'ي'};

        for (int i = 0; i < segments.length; i++) {
            char placeholder = i < radicals.length ? radicals[i] : 'X';
            String segment = segments[i];
            if (!segment.isEmpty()) {
                formula.append(placeholder);
                if (segment.length() > 1) {
                    formula.append(segment.substring(1));
                }
            }
        }

        return formula.toString();
    }

    public String getWord(String root , String pattern)
    {
        ArabicStringUtil arabicUtil = ArabicStringUtil.getInstance();

        pattern =normalizePattern(pattern);
        String word = arabicUtil.getWordFromRootAndPattern(root, pattern);
        return word ;
    }

}
