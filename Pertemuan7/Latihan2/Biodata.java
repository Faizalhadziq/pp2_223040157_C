import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Biodata extends JFrame {
    public Biodata() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Form Biodata");
        this.setSize(400, 400);
        this.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel formTitle = new JLabel("Form Biodata", SwingConstants.CENTER);
        formTitle.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(formTitle, gbc);

        JLabel labelNama = new JLabel("Nama:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(labelNama, gbc);

        JTextField textNama = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(textNama, gbc);

        JLabel labelNomorHP = new JLabel("Nomor HP:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(labelNomorHP, gbc);

        JTextField textNomorHP = new JTextField(15);
        gbc.gridx = 1;
        mainPanel.add(textNomorHP, gbc);

        JLabel labelJenisKelamin = new JLabel("Jenis Kelamin:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        mainPanel.add(labelJenisKelamin, gbc);

        JRadioButton radioLaki = new JRadioButton("Laki-Laki");
        JRadioButton radioPerempuan = new JRadioButton("Perempuan");
        ButtonGroup bg = new ButtonGroup();
        bg.add(radioLaki);
        bg.add(radioPerempuan);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(radioLaki);
        genderPanel.add(radioPerempuan);
        gbc.gridx = 1;
        mainPanel.add(genderPanel, gbc);

        JCheckBox checkWNA = new JCheckBox("Warga Negara Asing");
        gbc.gridx = 1;
        gbc.gridy = 4;
        mainPanel.add(checkWNA, gbc);

        JButton buttonSimpan = new JButton("Simpan");
        gbc.gridx = 0;
        gbc.gridy = 5;
        mainPanel.add(buttonSimpan, gbc);

        JButton buttonUpdate = new JButton("Update");
        gbc.gridx = 1;
        mainPanel.add(buttonUpdate, gbc);

        JButton buttonDelete = new JButton("Delete");
        gbc.gridy = 6;
        gbc.gridx = 1;
        mainPanel.add(buttonDelete, gbc);

        JTextArea txtOutput = new JTextArea(5, 20);
        txtOutput.setEditable(false);
        JScrollPane scrollOutput = new JScrollPane(txtOutput);
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.add(scrollOutput, BorderLayout.CENTER);

        this.add(mainPanel, BorderLayout.CENTER);
        this.add(outputPanel, BorderLayout.SOUTH);

        BiodataDao dao = new BiodataDao();

        buttonSimpan.addActionListener(e -> {
            try {
                String nama = textNama.getText();
                String nomorHP = textNomorHP.getText();
                String jenisKelamin = radioLaki.isSelected() ? "Laki-Laki" : "Perempuan";
                String wnaStatus = checkWNA.isSelected() ? "Ya" : "Tidak";

                dao.insertBiodata(nama, nomorHP, jenisKelamin, wnaStatus);
                txtOutput.setText("Data berhasil disimpan.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        buttonUpdate.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Masukkan ID untuk update:"));
                String nama = textNama.getText();
                String nomorHP = textNomorHP.getText();
                String jenisKelamin = radioLaki.isSelected() ? "Laki-Laki" : "Perempuan";
                String wnaStatus = checkWNA.isSelected() ? "Ya" : "Tidak";

                dao.updateBiodata(id, nama, nomorHP, jenisKelamin, wnaStatus);
                txtOutput.setText("Data berhasil diperbarui.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        buttonDelete.addActionListener(e -> {
            try {
                int id = Integer.parseInt(JOptionPane.showInputDialog("Masukkan ID untuk delete:"));
                dao.deleteBiodata(id);
                txtOutput.setText("Data berhasil dihapus.");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
            }
        });

        this.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Biodata::new);
    }
}
