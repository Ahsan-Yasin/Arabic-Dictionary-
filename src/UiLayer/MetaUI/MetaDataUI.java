package UiLayer.MetaUI;

import BusinessLogicLayer.BOFacade;
import DTOLayer.NounMetaDataDTO;
import DTOLayer.VerbMetaDataDTO;
import DTOLayer.WordDTO;
import UiLayer.Support.RoundedButton;
import UiLayer.Support.RoundedPanel;
import UiLayer.UI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MetaDataUI implements UI {
    private final BOFacade facade;

    public MetaDataUI(BOFacade facade) {
        this.facade = facade;
    }

    public void display() {
        JFrame frame = new JFrame("Metadata Management");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(18, 18, 20));

        RoundedPanel panel = new RoundedPanel(25);
        panel.setLayout(null);
        panel.setBackground(new Color(28, 29, 33));
        panel.setBounds(20, 20, 850, 540);
        frame.add(panel);

        JLabel title = new JLabel("Metadata Management");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setBounds(250, 10, 400, 35);
        panel.add(title);

        // ---------------- TABLES ----------------
        String[] nounColumns = {"Word", "Gender", "Number", "Case"};
        DefaultTableModel nounModel = new DefaultTableModel(nounColumns, 0);
        JTable nounTable = new JTable(nounModel);
        JScrollPane nounScroll = new JScrollPane(nounTable);
        nounScroll.setBounds(20, 50, 400, 150);
        panel.add(nounScroll);

        String[] verbColumns = {"Word", "Verb Form", "Tense", "Transitivity"};
        DefaultTableModel verbModel = new DefaultTableModel(verbColumns, 0);
        JTable verbTable = new JTable(verbModel);
        JScrollPane verbScroll = new JScrollPane(verbTable);
        verbScroll.setBounds(420, 50, 400, 150);
        panel.add(verbScroll);

        // ---------------- FIELDS ----------------
        JLabel lblWord = new JLabel("Select Word:");
        lblWord.setForeground(Color.WHITE);
        lblWord.setBounds(20, 220, 100, 25);
        panel.add(lblWord);
        JComboBox<String> cmbWords = new JComboBox<>();
        cmbWords.setBounds(130, 220, 150, 25);
        panel.add(cmbWords);

        JLabel lblGender = new JLabel("Gender:");
        lblGender.setForeground(Color.WHITE);
        lblGender.setBounds(20, 260, 100, 25);
        panel.add(lblGender);
        JTextField txtGender = new JTextField();
        txtGender.setBounds(130, 260, 150, 25);
        panel.add(txtGender);

        JLabel lblNumber = new JLabel("Number:");
        lblNumber.setForeground(Color.WHITE);
        lblNumber.setBounds(300, 260, 100, 25);
        panel.add(lblNumber);
        JTextField txtNumber = new JTextField();
        txtNumber.setBounds(400, 260, 150, 25);
        panel.add(txtNumber);

        JLabel lblCase = new JLabel("Case:");
        lblCase.setForeground(Color.WHITE);
        lblCase.setBounds(570, 260, 100, 25);
        panel.add(lblCase);
        JTextField txtCase = new JTextField();
        txtCase.setBounds(650, 260, 150, 25);
        panel.add(txtCase);

        JLabel lblVerbForm = new JLabel("Verb Form:");
        lblVerbForm.setForeground(Color.WHITE);
        lblVerbForm.setBounds(20, 300, 100, 25);
        panel.add(lblVerbForm);
        JTextField txtVerbForm = new JTextField();
        txtVerbForm.setBounds(130, 300, 150, 25);
        panel.add(txtVerbForm);

        JLabel lblTense = new JLabel("Tense:");
        lblTense.setForeground(Color.WHITE);
        lblTense.setBounds(300, 300, 100, 25);
        panel.add(lblTense);
        JTextField txtTense = new JTextField();
        txtTense.setBounds(400, 300, 150, 25);
        panel.add(txtTense);

        JLabel lblTrans = new JLabel("Transitivity:");
        lblTrans.setForeground(Color.WHITE);
        lblTrans.setBounds(570, 300, 100, 25);
        panel.add(lblTrans);
        JTextField txtTrans = new JTextField();
        txtTrans.setBounds(650, 300, 150, 25);
        panel.add(txtTrans);

        // ---------------- BUTTONS ----------------
        RoundedButton btnAddNoun = new RoundedButton("Add Noun", new Color(0, 122, 255));
        btnAddNoun.setBounds(20, 350, 150, 35);
        panel.add(btnAddNoun);

        RoundedButton btnUpdateNoun = new RoundedButton("Update Noun", new Color(255, 165, 0));
        btnUpdateNoun.setBounds(180, 350, 150, 35);
        panel.add(btnUpdateNoun);

        RoundedButton btnDeleteNoun = new RoundedButton("Delete Noun", new Color(255, 0, 80));
        btnDeleteNoun.setBounds(340, 350, 150, 35);
        panel.add(btnDeleteNoun);

        RoundedButton btnAddVerb = new RoundedButton("Add Verb", new Color(0, 200, 0));
        btnAddVerb.setBounds(20, 400, 150, 35);
        panel.add(btnAddVerb);

        RoundedButton btnUpdateVerb = new RoundedButton("Update Verb", new Color(255, 165, 0));
        btnUpdateVerb.setBounds(180, 400, 150, 35);
        panel.add(btnUpdateVerb);

        RoundedButton btnDeleteVerb = new RoundedButton("Delete Verb", new Color(255, 0, 80));
        btnDeleteVerb.setBounds(340, 400, 150, 35);
        panel.add(btnDeleteVerb);

        RoundedButton btnClear = new RoundedButton("Clear Fields", new Color(200, 200, 0));
        btnClear.setBounds(500, 350, 150, 35);
        panel.add(btnClear);

        // ---------------- FILL TABLES ----------------
        Runnable refreshTables = () -> {
            nounModel.setRowCount(0);
            List<NounMetaDataDTO> nouns = facade.getAllNounMetaData();
            for (NounMetaDataDTO n : nouns) {
                WordDTO word = facade.getWordById(n.getWordId());
                nounModel.addRow(new Object[]{word.getArabicForm(), n.getGender(), n.getNumber(), n.getGrammaticalCase()});
            }

            verbModel.setRowCount(0);
            List<VerbMetaDataDTO> verbs = facade.getAllVerbMetaData();
            for (VerbMetaDataDTO v : verbs) {
                WordDTO word = facade.getWordById(v.getWordId());
                verbModel.addRow(new Object[]{word.getArabicForm(), v.getVerbForm(), v.getTense(), v.getTransitivity()});
            }

            // Update dropdown with only unused words
            cmbWords.removeAllItems();
            List<WordDTO> allWords = facade.getAllWords();
            List<Integer> usedIds = new ArrayList<>();
            for (NounMetaDataDTO n : nouns) usedIds.add(n.getWordId());
            for (VerbMetaDataDTO v : verbs) usedIds.add(v.getWordId());
            for (WordDTO w : allWords) {
                if (!usedIds.contains(w.getId())) cmbWords.addItem(w.getArabicForm());
            }
        };
        refreshTables.run();

        // ---------------- TABLE CLICK ----------------
        nounTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && nounTable.getSelectedRow() != -1) {
                int row = nounTable.getSelectedRow();
                txtGender.setText((String) nounModel.getValueAt(row, 1));
                txtNumber.setText((String) nounModel.getValueAt(row, 2));
                txtCase.setText((String) nounModel.getValueAt(row, 3));
            }
        });

        verbTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && verbTable.getSelectedRow() != -1) {
                int row = verbTable.getSelectedRow();
                txtVerbForm.setText((String) verbModel.getValueAt(row, 1));
                txtTense.setText((String) verbModel.getValueAt(row, 2));
                txtTrans.setText((String) verbModel.getValueAt(row, 3));
            }
        });

        // ---------------- BUTTON LOGIC ----------------
        btnAddNoun.addActionListener(e -> {
            String selectedWord = (String) cmbWords.getSelectedItem();
            if (selectedWord != null) {
                int wordId = facade.getWordIdByArabicForm(selectedWord);
                facade.addMetaData(new NounMetaDataDTO(wordId,
                        txtGender.getText().trim(),
                        txtNumber.getText().trim(),
                        txtCase.getText().trim()));
                refreshTables.run();
            }
        });

        btnUpdateNoun.addActionListener(e -> {
            int row = nounTable.getSelectedRow();
            if (row != -1) {
                String wordText = (String) nounModel.getValueAt(row, 0);
                int wordId = facade.getWordIdByArabicForm(wordText);
                NounMetaDataDTO n = new NounMetaDataDTO(wordId,
                        txtGender.getText().trim(),
                        txtNumber.getText().trim(),
                        txtCase.getText().trim());
                facade.updateNounMetaData(n);
                refreshTables.run();
            }
        });

        btnDeleteNoun.addActionListener(e -> {
            int row = nounTable.getSelectedRow();
            if (row != -1) {
                String wordText = (String) nounModel.getValueAt(row, 0);
                int wordId = facade.getWordIdByArabicForm(wordText);
                facade.deleteNounMetaData(wordId);
                refreshTables.run();
            }
        });

        btnAddVerb.addActionListener(e -> {
            String selectedWord = (String) cmbWords.getSelectedItem();
            if (selectedWord != null) {
                int wordId = facade.getWordIdByArabicForm(selectedWord);
                facade.addMetaData(new VerbMetaDataDTO(wordId,
                        txtVerbForm.getText().trim(),
                        txtTense.getText().trim(),
                        txtTrans.getText().trim()));
                refreshTables.run();
            }
        });

        btnUpdateVerb.addActionListener(e -> {
            int row = verbTable.getSelectedRow();
            if (row != -1) {
                String wordText = (String) verbModel.getValueAt(row, 0);
                int wordId = facade.getWordIdByArabicForm(wordText);
                VerbMetaDataDTO v = new VerbMetaDataDTO(wordId,
                        txtVerbForm.getText().trim(),
                        txtTense.getText().trim(),
                        txtTrans.getText().trim());
                facade.updateVerbMetaData(v);
                refreshTables.run();
            }
        });

        btnDeleteVerb.addActionListener(e -> {
            int row = verbTable.getSelectedRow();
            if (row != -1) {
                String wordText = (String) verbModel.getValueAt(row, 0);
                int wordId = facade.getWordIdByArabicForm(wordText);
                facade.deleteVerbMetaData(wordId);
                refreshTables.run();
            }
        });

        btnClear.addActionListener(e -> {
            txtGender.setText("");
            txtNumber.setText("");
            txtCase.setText("");
            txtVerbForm.setText("");
            txtTense.setText("");
            txtTrans.setText("");
            nounTable.clearSelection();
            verbTable.clearSelection();
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
