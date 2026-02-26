package UiLayer;

import BusinessLogicLayer.BOFacade;
import BusinessLogicLayer.RootBO.IRootBO;
import BusinessLogicLayer.RootBO.RootBO;
import BusinessLogicLayer.WordBO.IWordBO;
import BusinessLogicLayer.WordBO.WordBO;
import BusinessLogicLayer.ExampleBO.IExampleBO;
import BusinessLogicLayer.ExampleBO.ExampleBO;
import BusinessLogicLayer.MetaDataBO.IMetaDataBO;
import BusinessLogicLayer.MetaDataBO.MetaDataBO;
import BusinessLogicLayer.PatternBO.IPatternBO;
import BusinessLogicLayer.PatternBO.PatternBO;
import BusinessLogicLayer.InputLemmatization.IInputLemmatization;
import BusinessLogicLayer.InputLemmatization.InputLemmatization;
import BusinessLogicLayer.Utils.*;
import DAOLayer.*;
import UiLayer.ExampleUI.ExampleUI;
import UiLayer.MetaUI.MetaDataUI;
import UiLayer.PatternUI.PatternUI;
import UiLayer.RootUI.RootUI;
import UiLayer.WordUI.WordUI;
import UiLayer.WordSuggestionUI.*;
import UiLayer.Support.RoundedButton;
import UiLayer.Support.RoundedPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class MainUI {

    private final BOFacade facade;
    private final RootSuggestionService rootService;
    private JFrame mainFrame;
     private  final  DAOFacade daoFacade;

    public MainUI(BOFacade facade, RootSuggestionService rootService, DAOFacade dao) {
        this.facade = facade;
        this.daoFacade=dao  ;
        this.rootService = rootService;
    }

    public void display() {
        mainFrame = new JFrame("Arabic → Urdu Dictionary System");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1100, 650);
        mainFrame.setLayout(null);
        mainFrame.getContentPane().setBackground(new Color(18, 18, 20));

        JPanel sidebar = new JPanel();
        sidebar.setLayout(null);
        sidebar.setBackground(new Color(25, 25, 28));
        sidebar.setBounds(0, 0, 220, 650);

        JLabel appTitle = new JLabel("📘 Dictionary", SwingConstants.CENTER);
        appTitle.setForeground(new Color(200, 200, 200));
        appTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        appTitle.setBounds(0, 30, 220, 30);
        sidebar.add(appTitle);

        RoundedButton btnRoot = new RoundedButton("Manage Roots", new Color(40, 40, 45));
        RoundedButton btnWord = new RoundedButton("Manage Words", new Color(40, 40, 45));
        RoundedButton btnExample = new RoundedButton("Manage Examples", new Color(40, 40, 45));
        RoundedButton btnMeta = new RoundedButton("Manage Metadata", new Color(40, 40, 45));
        RoundedButton btnPattern = new RoundedButton("Manage Patterns", new Color(40, 40, 45));
        RoundedButton btnLemmatization = new RoundedButton("Text Lemmatization", new Color(40, 40, 45));

        btnLemmatization.setBounds(25, 420, 170, 40);
        btnPattern.setBounds(25, 360, 170, 40);
        btnRoot.setBounds(25, 120, 170, 40);
        btnWord.setBounds(25, 180, 170, 40);
        btnExample.setBounds(25, 240, 170, 40);
        btnMeta.setBounds(25, 300, 170, 40);

        sidebar.add(btnRoot);
        sidebar.add(btnWord);
        sidebar.add(btnExample);
        sidebar.add(btnMeta);
        sidebar.add(btnPattern);
        sidebar.add(btnLemmatization);
        mainFrame.add(sidebar);

        JPanel placeholder = new RoundedPanel(30);
        placeholder.setLayout(null);
        placeholder.setBackground(new Color(28, 29, 33));
        placeholder.setBounds(250, 40, 800, 550);

        JLabel label = new JLabel("Welcome to Arabic → Urdu Dictionary System", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 24));
        label.setForeground(Color.WHITE);
        label.setBounds(50, 250, 700, 40);
        placeholder.add(label);

        mainFrame.add(placeholder);

        btnRoot.addActionListener(e -> openSubWindow(new RootUI(facade)));
        btnWord.addActionListener(e -> openSubWindow(new WordUI(facade, rootService)));
        btnExample.addActionListener(e -> openSubWindow(new ExampleUI(facade)));
        btnMeta.addActionListener(e -> openSubWindow(new MetaDataUI(facade)));
        btnPattern.addActionListener(e -> openSubWindow(new PatternUI(facade)));
        btnLemmatization.addActionListener(e -> openSubWindow(new TextLemmatizationUI(facade,daoFacade)));

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void openSubWindow(UI ui) {
        SwingUtilities.invokeLater(ui::display);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // DAOs
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
            GlossaryGenerator glossaryGenerator = new GlossaryGenerator(wordBO,lemmatizationService,nlpRootService);
            WordGenerator wordGenerator = new WordGenerator();

            // Facade
            BOFacade facade = new BOFacade(rootBO, wordBO, exampleBO, metaDataBO, patternBO, textLemmatizationBO, wordGenerator,glossaryGenerator);

            // Inject only facade and rootService
            new MainUI(facade, rootService,daoFacade).display();

        });
    }
}
