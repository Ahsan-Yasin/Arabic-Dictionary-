package BusinessLogicLayer.Utils;

import BusinessLogicLayer.InputLemmatization.InputLemmatization;
import BusinessLogicLayer.WordBO.IWordBO;
import BusinessLogicLayer.Utils.LemmatizationService;
import BusinessLogicLayer.Utils.NLPRootService;
import DTOLayer.WordDTO;

import java.io.*;
import java.util.*;

public class GlossaryGenerator {

    private final InputLemmatization inputLemmatization;

    public GlossaryGenerator(IWordBO wordBO, LemmatizationService lemmatizationService, NLPRootService nlpRootService) {
        this.inputLemmatization = new InputLemmatization(wordBO, lemmatizationService, nlpRootService);
    }

    public Map<String, Set<String>> generateGlossaryFromFile(String filePath) {
        StringBuilder text = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                text.append(line).append(" ");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyMap();
        }

        return generateGlossary(text.toString());
    }

    public Map<String, Set<String>> generateGlossary(String text) {
        Map<String, Set<String>> rootToWords = new HashMap<>();
        if (text == null || text.isEmpty()) return rootToWords;

        String[] words = text.split("\\s+");
        for (String word : words) {
            if (word.trim().isEmpty()) continue;

            List<String> roots = inputLemmatization.getAllRootsFromText(word);
            for (String root : roots) {
                rootToWords.computeIfAbsent(root, k -> new HashSet<>()).add(word);
            }
        }

        return rootToWords;
    }


    public void exportToCSV(Map<String, Set<String>> glossary, String filePath) {
        try (Writer writer = new OutputStreamWriter(
                new FileOutputStream(filePath), "UTF-8")) {

            // Write BOM for Excel
            writer.write('\ufeff');

            writer.write("Root,Words\n");
            for (Map.Entry<String, Set<String>> entry : glossary.entrySet()) {
                writer.write(entry.getKey() + "," + String.join(" | ", entry.getValue()) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
