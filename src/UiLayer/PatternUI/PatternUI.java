package UiLayer.PatternUI;

import BusinessLogicLayer.BOFacade;
import DTOLayer.PatternDTO;
import UiLayer.Support.RoundedButton;
import UiLayer.Support.RoundedPanel;
import UiLayer.UI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PatternUI implements UI {

    private final BOFacade facade;
    private PatternDTO selectedPattern;

    public PatternUI(BOFacade facade) {
        this.facade = facade;
    }

    @Override
    public void display() {
        JFrame frame = new JFrame("Pattern Management");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(18, 18, 20));

        RoundedPanel panel = new RoundedPanel(25);
        panel.setLayout(null);
        panel.setBackground(new Color(28, 29, 33));
        panel.setBounds(50, 30, 800, 500);
        frame.add(panel);

        JLabel title = new JLabel("Pattern Management");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBounds(250, 10, 300, 30);
        panel.add(title);

        JLabel lblWordId = new JLabel("Word ID:");
        lblWordId.setForeground(Color.WHITE);
        lblWordId.setBounds(50, 60, 120, 25);
        panel.add(lblWordId);

        JTextField txtWordId = new JTextField();
        txtWordId.setBounds(180, 60, 100, 25);
        panel.add(txtWordId);

        JLabel lblPatternName = new JLabel("Pattern Name:");
        lblPatternName.setForeground(Color.WHITE);
        lblPatternName.setBounds(300, 60, 120, 25);
        panel.add(lblPatternName);

        JTextField txtPatternName = new JTextField();
        txtPatternName.setBounds(430, 60, 150, 25);
        panel.add(txtPatternName);

        JLabel lblSearch = new JLabel("Search Pattern:");
        lblSearch.setForeground(Color.WHITE);
        lblSearch.setBounds(50, 100, 120, 25);
        panel.add(lblSearch);

        JTextField txtSearch = new JTextField();
        txtSearch.setBounds(180, 100, 150, 25);
        panel.add(txtSearch);

        RoundedButton btnSearch = new RoundedButton("Search", new Color(0, 200, 0));
        btnSearch.setBounds(350, 100, 100, 25);
        panel.add(btnSearch);

        RoundedButton btnAdd = new RoundedButton("Add Pattern", new Color(0, 122, 255));
        btnAdd.setBounds(50, 140, 150, 35);
        panel.add(btnAdd);

        RoundedButton btnUpdate = new RoundedButton("Update Pattern", new Color(255, 165, 0));
        btnUpdate.setBounds(220, 140, 150, 35);
        panel.add(btnUpdate);

        RoundedButton btnDelete = new RoundedButton("Delete Pattern", new Color(255, 0, 80));
        btnDelete.setBounds(390, 140, 150, 35);
        panel.add(btnDelete);

        JLabel lblResults = new JLabel("Existing Patterns:");
        lblResults.setForeground(Color.WHITE);
        lblResults.setBounds(50, 190, 200, 25);
        panel.add(lblResults);

        DefaultListModel<PatternDTO> listModel = new DefaultListModel<>();
        JList<PatternDTO> listPatterns = new JList<>(listModel);
        listPatterns.setBackground(new Color(40, 41, 46));
        listPatterns.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(listPatterns);
        scrollPane.setBounds(50, 220, 700, 250);
        panel.add(scrollPane);

        // Custom display in JList without showing IDs
        listPatterns.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof PatternDTO) {
                    PatternDTO p = (PatternDTO) value;
                    setText("Word ID: " + p.getWordId() + " | Pattern: " + p.getPatternName());
                }
                return c;
            }
        });

        // Select pattern on click
        listPatterns.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedPattern = listPatterns.getSelectedValue();
                if (selectedPattern != null) {
                    txtWordId.setText(String.valueOf(selectedPattern.getWordId()));
                    txtPatternName.setText(selectedPattern.getPatternName());
                }
            }
        });

        loadAllPatterns(listModel);

        btnAdd.addActionListener(e -> {
            try {
                int wordId = Integer.parseInt(txtWordId.getText().trim());
                String patternName = txtPatternName.getText().trim();

                if (patternName.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Pattern name cannot be empty!");
                    return;
                }

                int id = facade.addPattern(new PatternDTO(0, wordId, patternName));
                if (id > 0) {
                    JOptionPane.showMessageDialog(frame, "✅ Pattern added successfully!");
                    txtWordId.setText("");
                    txtPatternName.setText("");
                    loadAllPatterns(listModel);
                } else JOptionPane.showMessageDialog(frame, "Failed to add pattern.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid Word ID!");
            }
        });

        btnUpdate.addActionListener(e -> {
            if (selectedPattern == null) {
                JOptionPane.showMessageDialog(frame, "Select a pattern from the list first!");
                return;
            }
            try {
                int wordId = Integer.parseInt(txtWordId.getText().trim());
                String patternName = txtPatternName.getText().trim();

                if (patternName.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Pattern name cannot be empty!");
                    return;
                }

                boolean ok = facade.updatePattern(new PatternDTO(selectedPattern.getId(), wordId, patternName));
                JOptionPane.showMessageDialog(frame, ok ? "Pattern updated!" : "Pattern not found.");
                loadAllPatterns(listModel);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid Word ID!");
            }
        });

        btnDelete.addActionListener(e -> {
            if (selectedPattern == null) {
                JOptionPane.showMessageDialog(frame, "Select a pattern from the list first!");
                return;
            }

            boolean ok = facade.deletePattern(selectedPattern.getId());
            if (ok) selectedPattern = null; // clear selection
            JOptionPane.showMessageDialog(frame, ok ? "Pattern deleted!" : "Pattern not found.");
            loadAllPatterns(listModel);
        });

        btnSearch.addActionListener(e -> {
            String searchText = txtSearch.getText().trim();
            selectedPattern = null; // clear previous selection
            if (searchText.isEmpty()) {
                loadAllPatterns(listModel);
                return;
            }

            List<PatternDTO> patterns = facade.getPatternsByText(searchText);
            listModel.clear();
            for (PatternDTO p : patterns) listModel.addElement(p);
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void loadAllPatterns(DefaultListModel<PatternDTO> listModel) {
        List<PatternDTO> patterns = facade.getAllPatterns();
        listModel.clear();
        if (patterns != null) {
            for (PatternDTO p : patterns) listModel.addElement(p);
        }
    }
}
