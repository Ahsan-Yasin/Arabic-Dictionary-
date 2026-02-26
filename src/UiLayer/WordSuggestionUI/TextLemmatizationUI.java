package UiLayer.WordSuggestionUI;

import BusinessLogicLayer.BOFacade;
import BusinessLogicLayer.Utils.RootSuggestionService;
import DAOLayer.DAOFacade;
import DAOLayer.RootDAO;
import DTOLayer.PatternDTO;
import DTOLayer.RootDTO;
import DTOLayer.WordDTO;
import UiLayer.Support.RoundedButton;
import UiLayer.UI;
import UiLayer.WordUI.WordUI;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.Map;
import java.util.Set;

public class TextLemmatizationUI implements UI {

    private final BOFacade facade;
    private final DAOFacade daoFacade;

    public TextLemmatizationUI(BOFacade facade , DAOFacade dao) {
        this.facade = facade;
        this.daoFacade = dao;
    }

    @Override
    public void display() {
        JFrame frame = new JFrame("Text Lemmatization");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(820, 600);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(28, 29, 33));

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBounds(0, 0, 820, 600);
        frame.add(tabbedPane);

        // ----------------- Text Lemmatization Panel -----------------
        JPanel lemmatizationPanel = new JPanel(null);
        lemmatizationPanel.setBackground(new Color(28, 29, 33));
        tabbedPane.addTab("Text Lemmatization", lemmatizationPanel);

        JLabel lblText = new JLabel("Enter Arabic Text:");
        lblText.setForeground(Color.WHITE);
        lblText.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        lblText.setBounds(20, 20, 300, 35);
        lemmatizationPanel.add(lblText);

        JTextArea txtInput = new JTextArea();
        txtInput.setLineWrap(true);
        txtInput.setWrapStyleWord(true);
        txtInput.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scrollInput = new JScrollPane(txtInput);
        scrollInput.setBounds(20, 60, 600, 100);
        lemmatizationPanel.add(scrollInput);

        RoundedButton btnProcess = new RoundedButton("Process Text", new Color(0, 128, 255));
        btnProcess.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btnProcess.setBounds(640, 60, 150, 35);
        lemmatizationPanel.add(btnProcess);

        JLabel lblResults = new JLabel("Words Status (Red = Not in DB):");
        lblResults.setForeground(Color.WHITE);
        lblResults.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblResults.setBounds(20, 180, 500, 35);
        lemmatizationPanel.add(lblResults);

        String[] columns = {"Word", "Exists in Dictionary"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        JScrollPane scrollTable = new JScrollPane(table);
        scrollTable.setBounds(20, 220, 770, 300);
        lemmatizationPanel.add(scrollTable);

        btnProcess.addActionListener(e -> {
            tableModel.setRowCount(0);
            String text = txtInput.getText().trim();
            if (text.isEmpty()) return;
            Map<String, Boolean> result = facade.processText(text);
            for (Map.Entry<String, Boolean> entry : result.entrySet()) {
                tableModel.addRow(new Object[]{entry.getKey(), entry.getValue()});
            }
        });

        // ========================================================================
        // ADDED CODE 1 — Coloring Words (Green if exists, Red if missing)
        // ========================================================================
        table.getColumnModel().getColumn(1).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v, boolean isSel, boolean hasF, int r, int c) {
                Component comp = super.getTableCellRendererComponent(t, v, isSel, hasF, r, c);
                if (v instanceof Boolean) {
                    boolean exists = (Boolean) v;
                    comp.setForeground(exists ? Color.GREEN : Color.RED);
                }
                return comp;
            }
        });

        // ========================================================================
        // ADDED CODE 2 — Clicking RED word opens WordUI with Arabic + Root + Pattern
        // ========================================================================
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
                if (row >= 0 && col == 1) {
                    Boolean exists = (Boolean) table.getValueAt(row, 1);
                    if (!exists) {
                        String word = (String) table.getValueAt(row, 0);

                        SwingUtilities.invokeLater(() -> {
                            WordUI wordUI = new WordUI(facade, new RootSuggestionService(daoFacade));
                            wordUI.display();
                            wordUI.setArabicForm(word);

                            // Auto-suggest root + pattern
                            try {
                                String root = String.valueOf(facade.getRootsFromNLP(word));

                                if (root != null) wordUI.setRoot(root);


                            } catch (Exception ignored) {}
                        });
                    }
                }
            }
        });

        // ----------------- Word Generator Panel -----------------
        JPanel generatorPanel = new JPanel(null);
        generatorPanel.setBackground(new Color(28, 29, 33));
        tabbedPane.addTab("Word Generator", generatorPanel);

        JLabel lblRoot = new JLabel("Root:");
        lblRoot.setForeground(Color.WHITE);
        lblRoot.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblRoot.setBounds(50, 20, 100, 25);
        generatorPanel.add(lblRoot);

        JComboBox<String> rootDropdown = new JComboBox<>();
        rootDropdown.setBounds(150, 20, 200, 25);
        generatorPanel.add(rootDropdown);

        JLabel lblPattern = new JLabel("Pattern:");
        lblPattern.setForeground(Color.WHITE);
        lblPattern.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblPattern.setBounds(50, 60, 100, 25);
        generatorPanel.add(lblPattern);

        JComboBox<String> patternDropdown = new JComboBox<>();
        patternDropdown.setBounds(150, 60, 200, 25);
        generatorPanel.add(patternDropdown);

        JLabel lblWord = new JLabel("Generated Word:");
        lblWord.setForeground(Color.WHITE);
        lblWord.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblWord.setBounds(50, 100, 150, 25);
        generatorPanel.add(lblWord);

        JLabel lblGeneratedWord = new JLabel("");
        lblGeneratedWord.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblGeneratedWord.setBounds(200, 100, 200, 25);
        generatorPanel.add(lblGeneratedWord);

        RoundedButton btnAddWord = new RoundedButton("Add Word", new Color(255, 128, 0));
        btnAddWord.setBounds(150, 140, 200, 35);
        btnAddWord.setVisible(false);
        generatorPanel.add(btnAddWord);

        RoundedButton btnGenerate = new RoundedButton("Generate Word", new Color(0, 200, 100));
        btnGenerate.setBounds(150, 180, 200, 35);
        generatorPanel.add(btnGenerate);

        try {
            for (RootDTO r : facade.getAllRoots()) {
                rootDropdown.addItem(r.getRootLetters());
            }
            for (PatternDTO p : facade.getAllPatterns()) {
                patternDropdown.addItem(p.getPattern());
            }
        } catch (Exception ignored) {}

        btnGenerate.addActionListener(e -> {
            String root = (String) rootDropdown.getSelectedItem();
            String pattern = (String) patternDropdown.getSelectedItem();
            if (root != null && pattern != null) {
                String word = facade.getWord(root, pattern);
                lblGeneratedWord.setText(word);

                WordDTO existing = facade.getWordByBaseForm(word);
                if (existing != null) {
                    lblGeneratedWord.setForeground(Color.GREEN);
                    btnAddWord.setVisible(false);
                } else {
                    lblGeneratedWord.setForeground(Color.RED);
                    btnAddWord.setVisible(true);
                }
            }
        });

        btnAddWord.addActionListener(e -> {
            String root = (String) rootDropdown.getSelectedItem();
            String pattern = (String) patternDropdown.getSelectedItem();
            String word = lblGeneratedWord.getText();

            if (word != null && !word.isEmpty()) {
                SwingUtilities.invokeLater(() -> {
                    WordUI wordUI = new WordUI(facade, new RootSuggestionService(daoFacade));
                    wordUI.display();
                    wordUI.setArabicForm(word);
                    wordUI.setRoot(root);
                    wordUI.setPattern(pattern);
                });
            }
        });

        // ----------------- Glossary Generator Panel -----------------
        JPanel glossaryPanel = new JPanel(null);
        glossaryPanel.setBackground(new Color(28, 29, 33));
        tabbedPane.addTab("Glossary Generator", glossaryPanel);

        JLabel lblFile = new JLabel("Select Text File:");
        lblFile.setForeground(Color.WHITE);
        lblFile.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblFile.setBounds(20, 20, 150, 30);
        glossaryPanel.add(lblFile);

        JTextField txtFilePath = new JTextField();
        txtFilePath.setBounds(170, 20, 400, 30);
        glossaryPanel.add(txtFilePath);

        RoundedButton btnBrowse = new RoundedButton("Browse", new Color(0, 128, 255));
        btnBrowse.setBounds(580, 20, 100, 30);
        glossaryPanel.add(btnBrowse);

        JTextArea txtWords = new JTextArea();
        txtWords.setLineWrap(true);
        txtWords.setWrapStyleWord(true);
        txtWords.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scrollWords = new JScrollPane(txtWords);
        scrollWords.setBounds(20, 70, 760, 400);
        glossaryPanel.add(scrollWords);

        RoundedButton btnGenerateGlossary = new RoundedButton("Generate Glossary", new Color(0, 200, 100));
        btnGenerateGlossary.setBounds(20, 480, 200, 35);
        glossaryPanel.add(btnGenerateGlossary);

        RoundedButton btnSaveCSV = new RoundedButton("Save CSV", new Color(255, 128, 0));
        btnSaveCSV.setBounds(240, 480, 200, 35);
        glossaryPanel.add(btnSaveCSV);

        btnBrowse.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int option = chooser.showOpenDialog(frame);
            if(option == JFileChooser.APPROVE_OPTION){
                File file = chooser.getSelectedFile();
                txtFilePath.setText(file.getAbsolutePath());
            }
        });

        btnGenerateGlossary.addActionListener(e -> {
            String path = txtFilePath.getText().trim();
            if(path.isEmpty()) return;
            Map<String, Set<String>> glossary = facade.generateGlossaryFromFile(path);
            txtWords.setText("");
            for(Map.Entry<String, Set<String>> entry : glossary.entrySet()){
                txtWords.append("Root: " + entry.getKey() + " -> Words: " + entry.getValue() + "\n");
            }
        });

        btnSaveCSV.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int option = chooser.showSaveDialog(frame);
            if(option == JFileChooser.APPROVE_OPTION){
                File file = chooser.getSelectedFile();
                String path = file.getAbsolutePath();
                Map<String, Set<String>> glossary = facade.generateGlossaryFromFile(txtFilePath.getText().trim());
                facade.exportToCSV(glossary, path);
                JOptionPane.showMessageDialog(frame, "CSV saved to: " + path);
            }
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
