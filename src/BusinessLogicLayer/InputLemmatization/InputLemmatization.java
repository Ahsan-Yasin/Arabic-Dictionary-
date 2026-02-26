package BusinessLogicLayer.InputLemmatization;

import BusinessLogicLayer.WordBO.IWordBO;
import DTOLayer.WordDTO;
import BusinessLogicLayer.Utils.LemmatizationService;
import BusinessLogicLayer.Utils.NLPRootService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InputLemmatization  implements  IInputLemmatization {

    private final IWordBO wordBO;
    private final LemmatizationService lemmatizationService;
    private final NLPRootService nlpRootService;

    public InputLemmatization(IWordBO wordBO, LemmatizationService lemmatizationService, NLPRootService nlpRootService) {
        this.wordBO = wordBO;
        this.lemmatizationService = lemmatizationService;
        this.nlpRootService = nlpRootService;
    }

    public Map<String, Boolean> processText(String text) {
        Map<String, Boolean> wordStatus = new HashMap<>();
        if (text == null || text.isEmpty()) return wordStatus;

        // Split text into words (simple whitespace split)
        String[] words = text.split("\\s+");

        for (String word : words) {
            // Skip empty strings
            if (word.trim().isEmpty()) continue;

            // Perform lemmatization
            List<String> lemmas = lemmatizationService.getLemma(word);

            // Perform root extraction
            List<String> roots = nlpRootService.getRoots(word);

            // Check if the word or its lemmas already exist in DB
            boolean exists = false;
            for (String lemma : lemmas) {
                List<WordDTO> dbWords = wordBO.getWordsByLemma(lemma);
                if (dbWords != null && !dbWords.isEmpty()) {
                    exists = true;
                    break;
                }
            }

            // If no lemma matched, check root-based words
            if (!exists) {
                for (String root : roots) {
                    List<WordDTO> dbWords = wordBO.getWordsByRoot(root.hashCode()); // or your rootId mapping logic
                    if (dbWords != null && !dbWords.isEmpty()) {
                        exists = true;
                        break;
                    }
                }
            }

            // Add to result map
            wordStatus.put(word, exists);
        }

        return wordStatus;
    }


    public List<String> getAllLemmasFromText(String text) {
        List<String> allLemmas = new ArrayList<>();
        if (text == null || text.isEmpty()) return allLemmas;

        String[] words = text.split("\\s+");
        for (String word : words) {
            List<String> lemmas = lemmatizationService.getLemma(word);
            allLemmas.addAll(lemmas);
        }

        return allLemmas;
    }


    public List<String> getAllRootsFromText(String text) {
        List<String> allRoots = new ArrayList<>();
        if (text == null || text.isEmpty()) return allRoots;

        String[] words = text.split("\\s+");
        for (String word : words) {
            allRoots.addAll(nlpRootService.getRoots(word));
        }

        return allRoots;
    }
}
