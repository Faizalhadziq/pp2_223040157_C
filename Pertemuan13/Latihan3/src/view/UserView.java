package Pertemuan13.Latihan3.src.view;
import Pertemuan13.Latihan3.src.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UserView extends JFrame {
    private final JTable userTable;
    private final JButton loadButton;
    private final JButton exportButton;
    private final JProgressBar progressBar;

    public UserView() {
        setTitle("User Management");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Komponen GUI
        userTable = new JTable();
        loadButton = new JButton("Load Users");
        exportButton = new JButton("Export to PDF");
        progressBar = new JProgressBar();
        progressBar.setVisible(false);

        // Panel untuk tombol
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loadButton);
        buttonPanel.add(exportButton);

        // Tambahkan ke frame
        add(new JScrollPane(userTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        add(progressBar, BorderLayout.NORTH);
    }

    public void setLoadButtonListener(ActionListener listener) {
        loadButton.addActionListener(listener);
    }

    public void setExportButtonListener(ActionListener listener) {
        exportButton.addActionListener(listener);
    }

    public void updateTable(List<User> users) {
        String[] columnNames = {"Name", "Email"};
        String[][] data = new String[users.size()][2];
        for (int i = 0; i < users.size(); i++) {
            data[i][0] = users.get(i).getName();
            data[i][1] = users.get(i).getEmail();
        }
        userTable.setModel(new DefaultTableModel(data, columnNames));
    }

    public List<User> getDisplayedUsers() {
        // Simulasi mendapatkan data dari tabel
        return userTable.getRowCount() > 0 ?
                IntStream.range(0, userTable.getRowCount())
                        .mapToObj(i -> new User((String) userTable.getValueAt(i, 0), (String) userTable.getValueAt(i, 1)))
                        .collect(Collectors.toList())
                : List.of();
    }

    public void showProgressBar(boolean visible) {
        progressBar.setVisible(visible);
    }
}
