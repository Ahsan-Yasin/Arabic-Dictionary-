package BusinessLogicLayer.Utils;

import BusinessLogicLayer.ExampleBO.ExampleBO;
import BusinessLogicLayer.ExampleBO.IExampleBO;
import BusinessLogicLayer.Utils.GlossaryGenerator;
import BusinessLogicLayer.InputLemmatization.IInputLemmatization;
import BusinessLogicLayer.InputLemmatization.InputLemmatization;
import BusinessLogicLayer.MetaDataBO.IMetaDataBO;
import BusinessLogicLayer.MetaDataBO.MetaDataBO;
import BusinessLogicLayer.PatternBO.IPatternBO;
import BusinessLogicLayer.PatternBO.PatternBO;
import BusinessLogicLayer.RootBO.IRootBO;
import BusinessLogicLayer.RootBO.RootBO;
import BusinessLogicLayer.WordBO.IWordBO;
import BusinessLogicLayer.WordBO.WordBO;
import DAOLayer.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainNLPLocalTest {

    public static void main(String[] args) {
        // Test NLPRootService
//        NLPRootService rootService = new NLPRootService();
//        String testWord1 = "كتب"; // example Arabic word
//        List<String> roots = rootService.getRoots(testWord1);
//        System.out.println("Roots for '" + testWord1 + "': " + roots);
//
//        // Test LemmatizationService
//        LemmatizationService lemmaService = new LemmatizationService(new LemmatizationDAO());
//        String testWord2 = "مكتوب"; // example Arabic word
//        List<String> lemmas = lemmaService.getLemma(testWord2);
//        System.out.println("Lemmas for '" + testWord2 + "': " + lemmas);
        // Dummy implementations, replace with real BO and services
        RootDAO rootDAO = new RootDAO();
        WordDAO wordDAO = new WordDAO();
        ExampleDAO exampleDAO = new ExampleDAO();
        MetaDataDAO metaDataDAO = new MetaDataDAO();
        SegmentationDAO segmentDAO = new SegmentationDAO();
        LemmatizationDAO lemmaDAO = new LemmatizationDAO();
        PatternDAO patternDAO = new PatternDAO();
        DAOFacade daoFacade= new DAOFacade( exampleDAO,lemmaDAO,metaDataDAO,patternDAO,rootDAO,segmentDAO,wordDAO);
        // Services
        SegmentationService segmentService = new SegmentationService(daoFacade);
        LemmatizationService lemmatizationService = new LemmatizationService(daoFacade);
        NLPRootService nlpRootService = new NLPRootService();
        RootSuggestionService rootService = new RootSuggestionService(daoFacade);
        RegexSearch regexService = new RegexSearch(daoFacade);

        // BOs
        IRootBO rootBO = new RootBO(daoFacade);
        IWordBO wordBO = new WordBO(daoFacade, regexService, rootService, segmentService, lemmatizationService, nlpRootService);
        IExampleBO exampleBO = new ExampleBO(daoFacade);
        IMetaDataBO metaDataBO = new MetaDataBO(daoFacade);
        IPatternBO patternBO = new PatternBO(daoFacade);
        IInputLemmatization textLemmatizationBO = new InputLemmatization(wordBO, lemmatizationService, nlpRootService);
        WordGenerator wordGenerator = new WordGenerator();


        GlossaryGenerator  generator = new GlossaryGenerator (wordBO, lemmatizationService, nlpRootService);

        // Path to your poem text file
        String filePath = "C:\\Users\\Hp\\Desktop\\Arabic Dictionary Project\\Arabic-Dictionary-Project-\\src\\BusinessLogicLayer\\Utils\\poem.txt";


        Map<String, Set<String>> glossary = generator.generateGlossaryFromFile(filePath);

        // Print glossary
        for (Map.Entry<String, Set<String>> entry : glossary.entrySet()) {
            System.out.println("Root: " + entry.getKey() + " -> Words: " + entry.getValue());
        }

        // Export to CSV
        generator.exportToCSV(glossary, "C:\\Users\\Hp\\Desktop\\Arabic Dictionary Project\\Arabic-Dictionary-Project-\\src\\BusinessLogicLayer\\Utils\\glossary.csv");
        System.out.println("Glossary exported to glossary.csv");
    }

}
