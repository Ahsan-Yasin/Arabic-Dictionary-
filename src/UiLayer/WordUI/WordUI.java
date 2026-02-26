package UiLayer.WordUI;

import BusinessLogicLayer.BOFacade;
import BusinessLogicLayer.Utils.RootSuggestionService;
import DTOLayer.*;
import UiLayer.Support.RoundedButton;
import UiLayer.Support.RoundedPanel;
import UiLayer.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class WordUI implements UI {
    private final BOFacade facade;
    private final RootSuggestionService service  ;
    private JTextField arabicField; // store reference to Arabic field
    JTextField patternIdField ;
    JTextField rootIdField;
    JComboBox<String> patternDropdown;
    JComboBox<String> rootDropdown;
    public WordUI(BOFacade facade , RootSuggestionService r ) {
        this.facade = facade;
        service = r ;
    }

    @Override
    public void display() {
        JFrame frame = new JFrame("Manage Words");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(18, 18, 20));

        RoundedPanel mainPanel = new RoundedPanel(30);
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBounds(25, 25, 830, 620);
        mainPanel.setBackground(new Color(28, 29, 33));
        frame.add(mainPanel);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(new Color(28, 29, 33));
        tabs.setForeground(Color.WHITE);
        mainPanel.add(tabs, BorderLayout.CENTER);

        tabs.addTab("Add Word", createAddPanel());
        tabs.addTab("Update/Delete", createUpdateDeletePanel());
        tabs.addTab("Search Words", createBrowsePanel());
        tabs.addTab("Substring/Regex Search", createSubstringSearchPanel());
        tabs.addTab("Browse by Lemmas/Segments", createLemmaSegmentTab());



        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }
    private JPanel createLemmaSegmentTab() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(28, 29, 33));

        // ------- LEMMA -------
        JLabel lemmaLabel = new JLabel("Lemma:");
        lemmaLabel.setForeground(Color.WHITE);
        lemmaLabel.setBounds(50, 20, 100, 25);
        panel.add(lemmaLabel);

        JComboBox<String> lemmaDropdown = new JComboBox<>();
        lemmaDropdown.setBounds(150, 20, 150, 25);
        panel.add(lemmaDropdown);

        // ------- SEGMENT -------
        JLabel segmentLabel = new JLabel("Segment:");
        segmentLabel.setForeground(Color.WHITE);
        segmentLabel.setBounds(350, 20, 100, 25);
        panel.add(segmentLabel);

        JComboBox<String> segmentDropdown = new JComboBox<>();
        segmentDropdown.setBounds(450, 20, 150, 25);
        panel.add(segmentDropdown);

        // ------- ROOT -------
        JLabel rootLabel = new JLabel("Root:");
        rootLabel.setForeground(Color.WHITE);
        rootLabel.setBounds(50, 55, 100, 25);
        panel.add(rootLabel);

        JComboBox<String> rootDropdown = new JComboBox<>();
        rootDropdown.setBounds(150, 55, 150, 25);
        panel.add(rootDropdown);
        // ------- PATTERN -------
        JLabel patternLabel = new JLabel("Pattern:");
        patternLabel.setForeground(Color.WHITE);
        patternLabel.setBounds(350, 55, 100, 25);
        panel.add(patternLabel);

        JComboBox<String> patternDropdown = new JComboBox<>();
        patternDropdown.setBounds(450, 55, 150, 25);
        panel.add(patternDropdown);

        // ------- BUTTON -------
        RoundedButton searchBtn = new RoundedButton("Browse", new Color(0, 128, 255));
        searchBtn.setBounds(620, 20, 100, 25);
        panel.add(searchBtn);

        // ------- TABLE -------
        String[] columns = {"ID", "Arabic Form", "Base Form", "Urdu Meaning", "Part of Speech", "Root ID"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(50, 100, 700, 440);
        panel.add(scroll);
        String pre ,post ,stem ;

        // ------- LOAD DROPDOWNS -------
        try {
            for (LemmaDTO l : facade.getAllLemmas()) {
                lemmaDropdown.addItem(l.getLemma());
            }
            for (SegmentationDTO s : facade.getAllSegments()) {
                post = s.getSegment();
                pre=s.getPrefix();
                stem=s.getStem();
                if (post != null && !post.isEmpty())
                    segmentDropdown.addItem(post);

                if (pre != null && !pre.isEmpty())
                    segmentDropdown.addItem(pre);

                if (stem != null && !stem.isEmpty())
                    segmentDropdown.addItem(stem);
            }
            for (RootDTO r : facade.getAllRoots()) {
                rootDropdown.addItem(r.getRootLetters());
            }
            for (PatternDTO p : facade.getAllPatterns()) {
                patternDropdown.addItem(p.getPattern());
            }

        } catch (Exception ignored) {}

        // ------- ACTION -------
        searchBtn.addActionListener(e -> {
            tableModel.setRowCount(0);

            String lemmaText = (String) lemmaDropdown.getSelectedItem();
            String segmentText = (String) segmentDropdown.getSelectedItem();
            String rootText = (String) rootDropdown.getSelectedItem();
            String patternText = (String) patternDropdown.getSelectedItem();


            try {
                ArrayList<WordDTO> words = new ArrayList<>();

                if (lemmaText != null && !lemmaText.isEmpty()) {
                    words.addAll(facade.getWordsByLemma(lemmaText));
                }

                if (segmentText != null && !segmentText.isEmpty()) {
                    words.addAll(facade.getWordsBySegment(segmentText));
                }

                if (rootText != null && !rootText.isEmpty()) {
                    for (RootDTO r : facade.getAllRoots()) {
                        if (r.getRootLetters().equals(rootText)) {
                            words.addAll(facade.getWordsByRoot(r.getId()));
                            break;
                        }
                    }
                }
                if (patternText != null && !patternText.isEmpty()) {
                    words.addAll(facade.getWordsByPatternName(patternText));
                }


                if (words.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "No words found for given inputs.");
                } else {
                    for (WordDTO w : words) {
                        tableModel.addRow(new Object[]{
                                w.getId(),
                                w.getArabicForm(),
                                w.getBaseForm(),
                                w.getUrduMeaning(),
                                w.getPartOfSpeech(),
                                w.getRootId()
                        });
                    }
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage());
            }

            lemmaDropdown.setSelectedIndex(-1);
            segmentDropdown.setSelectedIndex(-1);
            rootDropdown.setSelectedIndex(-1);
            patternDropdown.setSelectedIndex(-1);

        });

        return panel;
    }

    private JPanel createSubstringSearchPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(28, 29, 33));

        JLabel searchLabel = new JLabel("Enter Substring:");
        searchLabel.setForeground(Color.WHITE);
        searchLabel.setBounds(50, 20, 150, 25);
        panel.add(searchLabel);

        JTextField substringField = new JTextField();
        substringField.setBounds(200, 20, 200, 25);
        panel.add(substringField);

        RoundedButton searchBtn = new RoundedButton("Search", new Color(0, 128, 255));
        searchBtn.setBounds(420, 20, 100, 25);
        panel.add(searchBtn);

        // Table to show results
        String[] columns = {"ID", "Arabic Form", "Base Form", "Urdu Meaning", "Part of Speech", "Root ID"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(50, 60, 700, 500);
        panel.add(scroll);
        // Regex Search button
        RoundedButton regexBtn = new RoundedButton("Regex Search", new Color(255, 128, 0));
        regexBtn.setBounds(530, 20, 120, 25);
        panel.add(regexBtn);

        regexBtn.addActionListener(e -> {
            tableModel.setRowCount(0); // clear previous results
            String pattern = substringField.getText().trim();
            if (pattern.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Enter a regex pattern to search.");
                return;
            }

            try {
                ArrayList<WordDTO> words = (ArrayList<WordDTO>) facade.getWordsByRegexSearch(pattern);
                if (words.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "No words found for pattern: " + pattern);
                } else {
                    for (WordDTO w : words) {
                        tableModel.addRow(new Object[]{
                                w.getId(),
                                w.getArabicForm(),
                                w.getBaseForm(),
                                w.getUrduMeaning(),
                                w.getPartOfSpeech(),
                                w.getRootId()
                        });
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage());
            }
        });

        // Action listener
        searchBtn.addActionListener(e -> {
            tableModel.setRowCount(0); // clear previous results
            String substring = substringField.getText().trim();
            if (substring.isEmpty()) {
                JOptionPane.showMessageDialog(panel, "Enter a substring to search.");
                return;
            }

            try {
                ArrayList<WordDTO> words = (ArrayList<WordDTO>) facade.getSubStringsWords(substring);
                if (words.isEmpty()) {
                    JOptionPane.showMessageDialog(panel, "No words found for: " + substring);
                } else {
                    for (WordDTO w : words) {
                        tableModel.addRow(new Object[]{
                                w.getId(),
                                w.getArabicForm(),
                                w.getBaseForm(),
                                w.getUrduMeaning(),
                                w.getPartOfSpeech(),
                                w.getRootId()
                        });
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(panel, "Error: " + ex.getMessage());
            }
        });

        return panel;
    }

    private JPanel createBrowsePanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(28, 29, 33));
//
//        JLabel rootLabel = new JLabel("Root ID:");
//        rootLabel.setForeground(Color.WHITE);
//        rootLabel.setBounds(50, 20, 100, 25);
//        panel.add(rootLabel);

//        JTextField rootField = new JTextField();
//        rootField.setBounds(150, 20, 100, 25);
//        panel.add(rootField);

        JLabel baseLabel = new JLabel("Base/Arabic Form:");
        baseLabel.setForeground(Color.WHITE);
        baseLabel.setBounds(300, 20, 150, 25);
        panel.add(baseLabel);

        JTextField baseField = new JTextField();
        baseField.setBounds(450, 20, 150, 25);
        panel.add(baseField);

        JLabel urduLabel = new JLabel("Urdu Meaning:");
        urduLabel.setForeground(Color.WHITE);
        urduLabel.setBounds(50, 60, 120, 25);
        panel.add(urduLabel);

        JTextField urduField = new JTextField();
        urduField.setBounds(150, 60, 200, 25);
        panel.add(urduField);

        RoundedButton searchBtn = new RoundedButton("Search", new Color(0, 128, 255));
        searchBtn.setBounds(370, 60, 100, 25);
        panel.add(searchBtn);

        // Table
        String[] columns = {"Root ID", "Arabic", "Base Form"};
        DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(50, 100, 700, 460);
        panel.add(scroll);

        // Action
        searchBtn.addActionListener(e -> {
            tableModel.setRowCount(0); // clear previous results

            // Browse by Root ID
//            String rootText = rootField.getText().trim();
//            if (!rootText.isEmpty()) {
//                try {
//                    int rootId = Integer.parseInt(rootText);
//                    var words = facade.getWordsByRoot(rootId);
//                    for (WordDTO w : words) {
//                        tableModel.addRow(new Object[]{w.getRootId(), w.getArabicForm(), w.getBaseForm()});
//                    }
//                } catch (NumberFormatException ex) {
//                    JOptionPane.showMessageDialog(panel, "Root ID must be a number.");
//                }
//            }

            // Browse by Base/Arabic Form
            String baseText = baseField.getText().trim();
            if (!baseText.isEmpty()) {
                WordDTO word = facade.getWordByBaseForm(baseText);
                if (word != null) {
                    tableModel.addRow(new Object[]{word.getRootId(), word.getArabicForm(), word.getBaseForm()});
                }
            }

            // Browse by Urdu Meaning
            String urduText = urduField.getText().trim();
            if (!urduText.isEmpty()) {
                WordDTO word = facade.getWordByUrduMeaning(urduText);
                if (word != null) {
                    tableModel.addRow(new Object[]{word.getRootId(), word.getArabicForm(), word.getBaseForm()});
                }
            }

            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(panel, "No words found for given inputs.");
            }
            //  rootField.setText("");
            baseField.setText("");
            urduField.setText("");
        });

        return panel;
    }

    private JPanel createAddPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(28, 29, 33));

        JLabel addTitle = new JLabel("Add Word");
        addTitle.setForeground(Color.WHITE);
        addTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        addTitle.setBounds(50, 10, 150, 25);
        panel.add(addTitle);

        arabicField = createField(panel, "Arabic Form:", 50);
        JTextField baseField = createField(panel, "Base Form:", 90);
        JTextField urduField = createField(panel, "Urdu Meaning:", 130);
        JTextField posField = createField(panel, "Part of Speech:", 170);

        // --- ROOT DROPDOWN ---
        JLabel rootDropdownLabel = new JLabel("Select Root:");
        rootDropdownLabel.setForeground(Color.WHITE);
        rootDropdownLabel.setBounds(50, 210, 180, 25);
        panel.add(rootDropdownLabel);
         rootDropdown = new JComboBox<>();
        rootDropdown.setBounds(240, 210, 200, 25);
        panel.add(rootDropdown);

        // --- PATTERN DROPDOWN ---
        JLabel patternDropdownLabel = new JLabel("Select Pattern:");
        patternDropdownLabel.setForeground(Color.WHITE);
        patternDropdownLabel.setBounds(50, 250, 180, 25);
        panel.add(patternDropdownLabel);
        patternDropdown = new JComboBox<>();
        patternDropdown.setBounds(240, 250, 200, 25);
        panel.add(patternDropdown);

        JTextField exField = createField(panel, "Example (sent, source, ref):", 290);
        JTextField metaField = createField(panel, "Metadata (comma-separated):", 330);

        // --- NLP Root Suggestions ---
        JTextArea suggestionArea = new JTextArea();
        suggestionArea.setEditable(false);
        suggestionArea.setBackground(new Color(40, 41, 46));
        suggestionArea.setForeground(Color.WHITE);
        suggestionArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane suggestionScroll = new JScrollPane(suggestionArea);
        suggestionScroll.setBounds(460, 50, 300, 120);
        panel.add(suggestionScroll);

        DefaultListModel<String> nlpRootModel = new DefaultListModel<>();
        JList<String> nlpRootList = new JList<>(nlpRootModel);
        nlpRootList.setBackground(new Color(40, 41, 46));
        nlpRootList.setForeground(Color.WHITE);
        nlpRootList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane nlpRootScroll = new JScrollPane(nlpRootList);
        nlpRootScroll.setBounds(460, 210, 300, 120);
        panel.add(nlpRootScroll);

        RoundedButton suggestBtn = new RoundedButton("Suggest Roots", new Color(0, 200, 128));
        suggestBtn.setBounds(460, 10, 150, 30);
        panel.add(suggestBtn);

        suggestBtn.addActionListener(e -> {
            String word = arabicField.getText().trim();
            if (!word.isEmpty()) {
                var roots = service.suggestRoots(word, 3);
                StringBuilder sb = new StringBuilder();
                for (var r : roots) {
                    int maxLen = Math.max(word.length(), r.getRootLetters().length());
                    double percent = ((double) service.similarity(word, r.getRootLetters()) / maxLen) * 100;
                    sb.append(String.format("%s (%.0f%%) (ID=%d)\n", r.getRootLetters(), percent, r.getId()));
                }
                suggestionArea.setText(sb.toString());

                // --- Populate NLP Roots List ---
                nlpRootModel.clear();
                java.util.List<String> nlpRoots = facade.getRootsFromNLP(word);
                for (String r : nlpRoots) {
                    nlpRootModel.addElement(r);
                }
            } else {
                suggestionArea.setText("Enter Arabic word first");
                nlpRootModel.clear();
            }
        });

        RoundedButton addNlpRootBtn = new RoundedButton("Add Selected Root", new Color(0, 200, 128));
        addNlpRootBtn.setBounds(460, 340, 200, 30);
        panel.add(addNlpRootBtn);

        addNlpRootBtn.addActionListener(e -> {
            int selectedIdx = nlpRootList.getSelectedIndex();

            if (selectedIdx == -1) {
                JOptionPane.showMessageDialog(panel, "Select a root first.");
                return;
            }

            String selectedRoot = nlpRootList.getSelectedValue();
            int newRootId = facade.addRoot(selectedRoot);
            rootDropdown.addItem(selectedRoot);
            rootDropdown.setSelectedItem(selectedRoot);


            rootIdField = new JTextField();
            rootIdField.setText(String.valueOf(newRootId));

            JOptionPane.showMessageDialog(panel,
                    "Root added: " + selectedRoot + " (ID=" + newRootId + ")");
        });


        // --- Populate Root & Pattern Dropdowns ---
        try {
            for (RootDTO r : facade.getAllRoots()) rootDropdown.addItem(r.getRootLetters());
            for (PatternDTO p : facade.getAllPatterns()) patternDropdown.addItem(p.getPattern());
        } catch (Exception ignored) {}

        // --- Action: Dropdown selection sets IDs ---
        rootDropdown.addActionListener(e -> {
            String selectedRoot = (String) rootDropdown.getSelectedItem();
            if (selectedRoot != null) {
                rootIdField = new JTextField();
                rootIdField.setText(String.valueOf(facade.getRootId(selectedRoot)));
            }
        });

        patternDropdown.addActionListener(e -> {
            String selectedPattern = (String) patternDropdown.getSelectedItem();
            if (selectedPattern != null) {
                patternIdField = new JTextField();
                patternIdField.setText(String.valueOf(facade.getPatternId(selectedPattern)));
            }
        });

        RoundedButton addBtn = new RoundedButton("Add Word", new Color(0, 122, 255));
        addBtn.setBounds(60, 360, 150, 35);
        panel.add(addBtn);
        addBtn.addActionListener(e -> addWord(
                arabicField, baseField, urduField, posField,
                rootIdField, exField, metaField, patternIdField
        ));

        return panel;
    }


    private JPanel createUpdateDeletePanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(new Color(28, 29, 33));

        JLabel manageTitle = new JLabel("Update / Delete Word");
        manageTitle.setForeground(Color.WHITE);
        manageTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        manageTitle.setBounds(50, 10, 300, 25);
        panel.add(manageTitle);

        JLabel lblWord = new JLabel("Word:");
        lblWord.setForeground(Color.WHITE);
        lblWord.setBounds(50, 50, 100, 25);
        panel.add(lblWord);

        JComboBox<WordDTO> cmbWords = new JComboBox<>();
        cmbWords.setBounds(150, 50, 300, 25);
        panel.add(cmbWords);

        JTextField baseFormField = createField(panel, "Base Form:", 90);
        JTextField urduFieldU = createField(panel, "Urdu Meaning:", 130);
        JTextField posFieldU = createField(panel, "Part of Speech:", 170);
        JTextField exFieldU = createField(panel, "Examples (comma-separated):", 210);

        JLabel resultLabel = new JLabel("");
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setBounds(50, 310, 500, 25);
        panel.add(resultLabel);

        RoundedButton fetchBtn = new RoundedButton("Fetch Word", new Color(0, 128, 255));
        fetchBtn.setBounds(50, 250, 150, 35);
        panel.add(fetchBtn);

        RoundedButton updateBtn = new RoundedButton("Update Word", new Color(0, 128, 255));
        updateBtn.setBounds(220, 250, 150, 35);
        panel.add(updateBtn);

        RoundedButton deleteBtn = new RoundedButton("Delete Word", new Color(0, 128, 255));
        deleteBtn.setBounds(390, 250, 150, 35);
        panel.add(deleteBtn);

        // Load words into combo
        List<WordDTO> words = facade.getAllWords();
        cmbWords.removeAllItems();
        if (words != null) words.forEach(cmbWords::addItem);

        // Fetch selected word
        fetchBtn.addActionListener(e -> {
            WordDTO selected = (WordDTO) cmbWords.getSelectedItem();
            if (selected == null) {
                resultLabel.setText("No word selected.");
                return;
            }
            baseFormField.setText(selected.getBaseForm());
            urduFieldU.setText(selected.getUrduMeaning());
            posFieldU.setText(selected.getPartOfSpeech());
            exFieldU.setText(facade.getExamplesByWordId(selected.getId()).stream()
                    .map(ExampleDTO::getSentence)
                    .reduce((a, b) -> a + ", " + b)
                    .orElse(""));
            resultLabel.setText("✅ Word loaded.");
        });

        // Update word
        updateBtn.addActionListener(e -> {
            WordDTO selected = (WordDTO) cmbWords.getSelectedItem();
            if (selected == null) {
                resultLabel.setText(" No word selected.");
                return;
            }
            selected.setBaseForm(baseFormField.getText());
            selected.setUrduMeaning(urduFieldU.getText());
            selected.setPartOfSpeech(posFieldU.getText());
            boolean ok = facade.updateWord(selected);
            resultLabel.setText(ok ? "✅ Word updated!" : " Not Updated ! ");
        });

        // Delete word
        deleteBtn.addActionListener(e -> {
            WordDTO selected = (WordDTO) cmbWords.getSelectedItem();
            if (selected == null) {
                resultLabel.setText(" No word selected.");
                return;
            }
            boolean ok = facade.deleteWord(selected.getId());
            if (ok) {
                resultLabel.setText("✅ Word deleted!");
                cmbWords.removeItem(selected);
                baseFormField.setText("");
                urduFieldU.setText("");
                posFieldU.setText("");
                exFieldU.setText("");
            } else {
                resultLabel.setText("Failed to delete.");
            }
        });

        return panel;
    }

    private JTextField createField(JPanel panel, String label, int y) {
        JLabel l = new JLabel(label);
        l.setForeground(Color.WHITE);
        l.setBounds(50, y, 180, 25);
        panel.add(l);
        JTextField f = new JTextField();
        f.setBounds(240, y, 200, 25);
        panel.add(f);
        return f;
    }

    private void addWord(JTextField arabicField, JTextField baseField, JTextField urduField, JTextField posField,
                         JTextField rootIdField, JTextField exField, JTextField metaField ,  JTextField pattern) {
        try {
            String arabic = arabicField.getText().trim();
            String base = baseField.getText().trim();
            String urdu = urduField.getText().trim();
            String pos = posField.getText().trim().toLowerCase();
            int  patternId= Integer.parseInt(pattern.getText().trim());
            int rootId = Integer.parseInt(rootIdField.getText().trim());

            WordDTO word = new WordDTO(patternId, 0,arabic, base, urdu, pos, rootId);
            int wordId = facade.addWord(word);

            if (wordId > 0) {
                String[] exParts = exField.getText().split(",");
                if (exParts.length >= 3) {
                    ExampleDTO ex = new ExampleDTO(0, wordId, exParts[0].trim(), exParts[1].trim(), exParts[2].trim());
                    facade.addExample(ex);
                }

                String[] metaParts = metaField.getText().split(",");
                if (pos.equals("noun") && metaParts.length >= 3)
                    facade.addMetaData(new NounMetaDataDTO(wordId, metaParts[0], metaParts[1], metaParts[2]));
                else if (pos.equals("verb") && metaParts.length >= 3)
                    facade.addMetaData(new VerbMetaDataDTO(wordId, metaParts[0], metaParts[1], metaParts[2]));

                JOptionPane.showMessageDialog(null, "Word added successfully!");
            } else {
                JOptionPane.showMessageDialog(null, "Failed to add word.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Invalid input: " + ex.getMessage());
        }
    }

    private void fetchWord(JTextField idField, JTextField baseField, JTextField urduField, JTextField posField,
                           JTextField exField, JLabel resultLabel) {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            WordDTO word = facade.getWordById(id);
            if (word != null) {
                baseField.setText(word.getBaseForm());
                urduField.setText(word.getUrduMeaning());
                posField.setText(word.getPartOfSpeech());

                if (word.getExamples() != null && !word.getExamples().isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    for (ExampleDTO ex : word.getExamples()) {
                        sb.append(ex.getSentence()).append(",").append(ex.getSource()).append(",").append(ex.getReference()).append(";");
                    }
                    exField.setText(sb.toString());
                }

                resultLabel.setText("Word loaded.");
            } else {
                resultLabel.setText("Word not found.");
            }
        } catch (Exception e) {
            resultLabel.setText("Invalid ID.");
        }
    }

    private void updateWord(JTextField idField, JTextField baseField, JTextField urduField, JTextField posField,
                            JTextField exField, JLabel resultLabel   ) {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            WordDTO existing = facade.getWordById(id);
            if (existing == null) {
                resultLabel.setText("Word not found.");
                return;
            }
            WordDTO updated = new WordDTO(existing.getPatternId(),id, existing.getArabicForm(), baseField.getText().trim(),
                    urduField.getText().trim(), posField.getText().trim(), existing.getRootId());

            boolean ok = facade.updateWord(updated);

            String[] examples = exField.getText().split(";");
            for (String exStr : examples) {
                String[] parts = exStr.split(",");
                if (parts.length >= 3) {
                    ExampleDTO ex = new ExampleDTO(0, id, parts[0].trim(), parts[1].trim(), parts[2].trim());
                    facade.addExample(ex);
                }
            }

            resultLabel.setText(ok ? "Updated successfully!" : "Update failed.");
        } catch (Exception e) {
            resultLabel.setText("Invalid input.");
        }
    }

    private void deleteWord(JTextField idField, JLabel resultLabel) {
        try {
            int id = Integer.parseInt(idField.getText().trim());
            boolean ok = facade.deleteWord(id);
            resultLabel.setText(ok ? "Word deleted!" : "Word not found.");
        } catch (Exception e) {
            resultLabel.setText("Invalid ID.");
        }
    }

    private void searchWord(JTextField searchField, JTextArea resultArea) {
        String base = searchField.getText().trim();
        if (base.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Enter a word to search.");
            return;
        }
        WordDTO word = facade.getWordByBaseForm(base);
        if (word != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("ID: ").append(word.getId())
                    .append("\nArabic: ").append(word.getArabicForm())
                    .append("\nBase: ").append(word.getBaseForm())
                    .append("\nUrdu: ").append(word.getUrduMeaning())
                    .append("\nPOS: ").append(word.getPartOfSpeech())
                    .append("\nRoot ID: ").append(word.getRootId());

            if (word.getExamples() != null && !word.getExamples().isEmpty()) {
                sb.append("\nExamples:\n");
                for (ExampleDTO ex : word.getExamples()) {
                    sb.append(" - ").append(ex.getSentence())
                            .append(" (").append(ex.getSource()).append(")\n");
                }
            }

            resultArea.setText(sb.toString());
        } else {
            resultArea.setText("No word found for: " + base);
        }
    }
    public void setArabicForm(String word) {
        if (arabicField != null) {
            arabicField.setText(word);
        }
    }


    public void setRoot(String root) {
        // Select root in dropdown if exists
        if (rootDropdown != null && root != null) {
            rootDropdown.setSelectedItem(root);
        }
    }


    public void setPattern(String pattern) {
        // Select pattern in dropdown if exists
        if (patternDropdown != null && pattern != null) {
            patternDropdown.setSelectedItem(pattern);
        }
    }
}
