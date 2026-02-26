package UiLayer.RootUI;

import BusinessLogicLayer.BOFacade;
import DTOLayer.RootDTO;
import UiLayer.Support.RoundedButton;
import UiLayer.Support.RoundedPanel;
import UiLayer.UI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RootUI implements UI {

    private final BOFacade facade;
    private RootDTO selectedRoot;

    public RootUI(BOFacade facade) {
        this.facade = facade;
    }

    @Override
    public void display() {
        JFrame frame = new JFrame("Root Management");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(18, 18, 20));

        RoundedPanel panel = new RoundedPanel(25);
        panel.setLayout(null);
        panel.setBackground(new Color(28, 29, 33));
        panel.setBounds(50, 30, 700, 500);
        frame.add(panel);

        JLabel title = new JLabel("Root Management");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBounds(230, 10, 300, 30);
        panel.add(title);

        JLabel lblRoot = new JLabel("Root Letters:");
        lblRoot.setForeground(Color.WHITE);
        lblRoot.setBounds(50, 60, 120, 25);
        panel.add(lblRoot);

        JTextField txtRoot = new JTextField();
        txtRoot.setBounds(180, 60, 200, 25);
        panel.add(txtRoot);

        RoundedButton btnAdd = new RoundedButton("Add Root", new Color(0, 122, 255));
        btnAdd.setBounds(50, 100, 150, 35);
        panel.add(btnAdd);

        RoundedButton btnUpdate = new RoundedButton("Update Root", new Color(255, 165, 0));
        btnUpdate.setBounds(220, 100, 150, 35);
        panel.add(btnUpdate);

        RoundedButton btnDelete = new RoundedButton("Delete Root", new Color(255, 0, 80));
        btnDelete.setBounds(390, 100, 150, 35);
        panel.add(btnDelete);

        RoundedButton btnSearch = new RoundedButton("Search Root", new Color(0, 200, 0));
        btnSearch.setBounds(560, 100, 150, 35);
        panel.add(btnSearch);

        JLabel lblResults = new JLabel("Existing Roots:");
        lblResults.setForeground(Color.WHITE);
        lblResults.setBounds(50, 150, 200, 25);
        panel.add(lblResults);

        DefaultListModel<RootDTO> listModel = new DefaultListModel<>();
        JList<RootDTO> listRoots = new JList<>(listModel);
        listRoots.setBackground(new Color(40, 41, 46));
        listRoots.setForeground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(listRoots);
        scrollPane.setBounds(50, 180, 600, 280);
        panel.add(scrollPane);

        // Custom renderer to hide IDs
        listRoots.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof RootDTO) {
                    RootDTO r = (RootDTO) value;
                    setText(r.getRootLetters());
                }
                return c;
            }
        });

        // Select root on click
        listRoots.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedRoot = listRoots.getSelectedValue();
                if (selectedRoot != null) {
                    txtRoot.setText(selectedRoot.getRootLetters());
                }
            }
        });

        loadAllRoots(listModel);

        btnAdd.addActionListener(e -> {
            String letters = txtRoot.getText().trim();
            if (letters.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Root letters cannot be empty!");
                return;
            }
            int id = facade.addRoot(letters);
            if (id > 0) {
                JOptionPane.showMessageDialog(frame, "✅ Root added successfully!");
                txtRoot.setText("");
                loadAllRoots(listModel);
            } else JOptionPane.showMessageDialog(frame, "❌ Failed to add root.");
        });

        btnUpdate.addActionListener(e -> {
            if (selectedRoot == null) {
                JOptionPane.showMessageDialog(frame, "Select a root from the list first!");
                return;
            }
            String letters = txtRoot.getText().trim();
            if (letters.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Root letters cannot be empty!");
                return;
            }
            boolean ok = facade.updateRoot(selectedRoot.getId(), letters);
            JOptionPane.showMessageDialog(frame, ok ? "Root updated!" : "Root not found.");
            loadAllRoots(listModel);
        });

        btnDelete.addActionListener(e -> {
            if (selectedRoot == null) {
                JOptionPane.showMessageDialog(frame, "Select a root from the list first!");
                return;
            }
            boolean ok = facade.deleteRoot(selectedRoot.getId());
            if (ok) selectedRoot = null;
            JOptionPane.showMessageDialog(frame, ok ? "Root deleted!" : "Root not found.");
            loadAllRoots(listModel);
        });

        btnSearch.addActionListener(e -> {
            String searchText = txtRoot.getText().trim();
            selectedRoot = null;
            if (searchText.isEmpty()) {
                loadAllRoots(listModel);
                return;
            }
            List<RootDTO> roots = facade.getRootsByText(searchText);
            listModel.clear();
            for (RootDTO r : roots) listModel.addElement(r);
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void loadAllRoots(DefaultListModel<RootDTO> listModel) {
        List<RootDTO> roots = facade.getAllRoots();
        listModel.clear();
        if (roots != null) {
            for (RootDTO r : roots) listModel.addElement(r);
        }
    }
}
