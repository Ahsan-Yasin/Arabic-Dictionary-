package BusinessLogicLayer.Utils;

import DAOLayer.DAOFacade;
import DAOLayer.WordDAO;
import DTOLayer.WordDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexSearch {
    private final DAOFacade wordDao;

    public RegexSearch(DAOFacade w) {
        this.wordDao = w;
    }

    public List<WordDTO> regexSearch(String regex) {
        List<WordDTO> allWords = wordDao.getAllWords();
        List<WordDTO> matchedWords = new ArrayList<>();

        String regexWithWildcards = regex;
        Pattern pattern = Pattern.compile(regexWithWildcards, Pattern.UNICODE_CASE | Pattern.UNICODE_CHARACTER_CLASS);

        for (WordDTO word : allWords) {
            Matcher matcher = pattern.matcher(word.getArabicForm());
            if (matcher.find()) {
                matchedWords.add(word);
            }
        }

        return matchedWords;
    }

}
