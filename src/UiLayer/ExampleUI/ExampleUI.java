package UiLayer.ExampleUI;

import BusinessLogicLayer.BOFacade;
import DTOLayer.ExampleDTO;
import DTOLayer.WordDTO;
import UiLayer.Support.RoundedButton;
import UiLayer.Support.RoundedPanel;
import UiLayer.UI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ExampleUI implements UI {
    private final BOFacade facade;
    private ExampleDTO selectedExample;

    public ExampleUI(BOFacade facade) {
        this.facade = facade;
    }

    @Override
    public void display() {
        JFrame frame = new JFrame("Example Management");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(18, 18, 20));

        RoundedPanel panel = new RoundedPanel(25);
        panel.setLayout(null);
        panel.setBackground(new Color(28, 29, 33));
        panel.setBounds(50, 30, 800, 500);
        frame.add(panel);

        JLabel title = new JLabel("Example Management");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBounds(250, 10, 300, 30);
        panel.add(title);

        JLabel lblWord = new JLabel("Word:");
        lblWord.setForeground(Color.WHITE);
        lblWord.setBounds(50, 60, 100, 25);
        panel.add(lblWord);

        JComboBox<WordDTO> cmbWord = new JComboBox<>();
        cmbWord.setBounds(150, 60, 200, 25);
        panel.add(cmbWord);

        JLabel lblSentence = new JLabel("Sentence:");
        lblSentence.setForeground(Color.WHITE);
        lblSentence.setBounds(50, 100, 100, 25);
        panel.add(lblSentence);

        JTextField txtSentence = new JTextField();
        txtSentence.setBounds(150, 100, 500, 25);
        panel.add(txtSentence);

        JLabel lblSource = new JLabel("Source:");
        lblSource.setForeground(Color.WHITE);
        lblSource.setBounds(50, 140, 100, 25);
        panel.add(lblSource);

        JTextField txtSource = new JTextField();
        txtSource.setBounds(150, 140, 500, 25);
        panel.add(txtSource);

        JLabel lblRef = new JLabel("Reference:");
        lblRef.setForeground(Color.WHITE);
        lblRef.setBounds(50, 180, 100, 25);
        panel.add(lblRef);

        JTextField txtRef = new JTextField();
        txtRef.setBounds(150, 180, 500, 25);
        panel.add(txtRef);

        RoundedButton btnAdd = new RoundedButton("Add Example", new Color(0, 122, 255));
        btnAdd.setBounds(50, 220, 150, 35);
        panel.add(btnAdd);

        RoundedButton btnUpdate = new RoundedButton("Update Example", new Color(255, 165, 0));
        btnUpdate.setBounds(220, 220, 150, 35);
        panel.add(btnUpdate);

        RoundedButton btnDelete = new RoundedButton("Delete Example", new Color(255, 0, 80));
        btnDelete.setBounds(390, 220, 150, 35);
        panel.add(btnDelete);

        JLabel lblResults = new JLabel("Examples:");
        lblResults.setForeground(Color.WHITE);
        lblResults.setBounds(50, 270, 200, 25);
        panel.add(lblResults);

        DefaultListModel<ExampleDTO> listModel = new DefaultListModel<>();
        JList<ExampleDTO> listExamples = new JList<>(listModel);
        listExamples.setBackground(new Color(40, 41, 46));
        listExamples.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(listExamples);
        scrollPane.setBounds(50, 300, 700, 180);
        panel.add(scrollPane);

        // Custom display in list
        listExamples.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof ExampleDTO ex) {
                    WordDTO word = facade.getWordById(ex.getWordId());
                    String wordText = word != null ? word.getArabicForm() : "Unknown";
                    setText("Word: " + wordText + " | " + ex.getSentence()
                            + " | Source: " + ex.getSource()
                            + " | Reference: " + ex.getReference());
                }
                return c;
            }
        });

        // Select example from list
        listExamples.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedExample = listExamples.getSelectedValue();
                if (selectedExample != null) {
                    txtSentence.setText(selectedExample.getSentence());
                    txtSource.setText(selectedExample.getSource());
                    txtRef.setText(selectedExample.getReference());
                    // Select the word in combo
                    for (int i = 0; i < cmbWord.getItemCount(); i++) {
                        if (cmbWord.getItemAt(i).getId() == selectedExample.getWordId()) {
                            cmbWord.setSelectedIndex(i);
                            break;
                        }
                    }
                }
            }
        });

        loadWords(cmbWord);
        loadAllExamples(listModel);

        btnAdd.addActionListener(e -> {
            WordDTO word = (WordDTO) cmbWord.getSelectedItem();
            if (word == null) return;

            int wordId = facade.getWordIdByArabicForm(word.getArabicForm());
            if (wordId <= 0) {
                JOptionPane.showMessageDialog(frame, "❌ Selected word does not exist in database!");
                return;
            }

            ExampleDTO ex = new ExampleDTO(0, wordId, txtSentence.getText(), txtSource.getText(), txtRef.getText());
            int id = facade.addExample(ex);
            if (id > 0) {
                JOptionPane.showMessageDialog(frame, "✅ Example added!");
                clearFields(txtSentence, txtSource, txtRef);
                loadAllExamples(listModel);
            } else {
                JOptionPane.showMessageDialog(frame, "❌ Failed to add example.");
            }
        });

        btnUpdate.addActionListener(e -> {
            if (selectedExample == null) return;
            WordDTO word = (WordDTO) cmbWord.getSelectedItem();
            if (word == null) return;

            int wordId = facade.getWordIdByArabicForm(word.getArabicForm());
            if (wordId <= 0) {
                JOptionPane.showMessageDialog(frame, "❌ Selected word does not exist in database!");
                return;
            }

            ExampleDTO ex = new ExampleDTO(selectedExample.getId(), wordId, txtSentence.getText(),
                    txtSource.getText(), txtRef.getText());
            boolean ok = facade.updateExample(ex);
            JOptionPane.showMessageDialog(frame, ok ? "✅ Example updated!" : "❌ Not found.");
            loadAllExamples(listModel);
        });

        btnDelete.addActionListener(e -> {
            if (selectedExample == null) return;
            boolean ok = facade.deleteExample(selectedExample.getId());
            JOptionPane.showMessageDialog(frame, ok ? "✅ Example deleted!" : "❌ Not found.");
            clearFields(txtSentence, txtSource, txtRef);
            loadAllExamples(listModel);
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void loadWords(JComboBox<WordDTO> cmbWord) {
        List<WordDTO> words = facade.getAllWords();
        cmbWord.removeAllItems();
        if (words != null) words.forEach(cmbWord::addItem);
    }

    private void loadAllExamples(DefaultListModel<ExampleDTO> listModel) {
        List<ExampleDTO> examples = facade.getAllExamples();
        listModel.clear();
        if (examples != null) examples.forEach(listModel::addElement);
    }

    private void clearFields(JTextField... fields) {
        for (JTextField f : fields) f.setText("");
    }
}
