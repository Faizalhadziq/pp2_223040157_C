package tugas03;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

public class AplikasiInventaris extends JFrame {
    private JTextField namaProdukField;
    private JComboBox<String> kategoriComboBox;
    private JSpinner hargaSpinner;
    private JSpinner stokSpinner;
    private JTextArea deskripsiArea;
    private DefaultTableModel tableModel;
    private JTable productTable;
    private JList<String> produkList; // Deklarasi JList untuk produk
    private DefaultListModel<String> produkListModel; // Model untuk JList produk
    private JRadioButton tersediaRadioButton;
    private JRadioButton tidakTersediaRadioButton;
    private JCheckBox diskonCheckBox;
    private JSlider ratingSlider;

    public AplikasiInventaris() {
        // Set up main frame
        setTitle("Aplikasi Manajemen Inventaris");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set up menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Pilihan");
        JMenuItem tambahItem = new JMenuItem("Tambah Produk");
        JMenuItem lihatItem = new JMenuItem("Lihat Produk");

        menu.add(tambahItem);
        menu.add(lihatItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        // Panel untuk form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        // Input fields
        formPanel.add(createInputField("Nama Produk:", namaProdukField = new JTextField(20)));
        formPanel.add(createInputField("Kategori:", kategoriComboBox = new JComboBox<>(new String[]{"Elektronik", "Pakaian", "Makanan", "Minuman", "Buku"})));
        formPanel.add(createInputField("Harga (Rp):", hargaSpinner = createHargaSpinner()));
        formPanel.add(createInputField("Stok:", stokSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1))));
        formPanel.add(createInputField("Deskripsi:", new JScrollPane(deskripsiArea = new JTextArea(3, 20))));

        // Availability Radio Buttons
        formPanel.add(createAvailabilityPanel());

        // Discount Checkbox
        diskonCheckBox = new JCheckBox("Diskon");
        diskonCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(diskonCheckBox);

        // Rating Slider
        formPanel.add(createRatingSlider());

        // Set up JTable
        tableModel = new DefaultTableModel(new String[]{"Nama", "Kategori", "Harga (Rp)", "Stok", "Deskripsi", "Ketersediaan", "Diskon", "Rating"}, 0);
        productTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(productTable);

        // Inisialisasi JList untuk produk
        produkListModel = new DefaultListModel<>();
        produkList = new JList<>(produkListModel);
        JScrollPane produkScrollPane = new JScrollPane(produkList); // Tambahkan JList ke JScrollPane

        // Tambahkan panel form, tabel, dan JList ke frame utama
        add(formPanel, BorderLayout.NORTH);
        add(tableScrollPane, BorderLayout.CENTER);

        // Panel untuk menampilkan daftar produk
        JPanel produkPanel = new JPanel();
        produkPanel.setLayout(new BorderLayout());
        produkPanel.add(new JLabel("Daftar Produk:"), BorderLayout.NORTH);
        produkPanel.add(produkScrollPane, BorderLayout.CENTER);

        // Tambahkan panel produk ke frame
        add(produkPanel, BorderLayout.EAST); // Tambahkan panel produk ke sisi kanan

        // Panel untuk tombol
        JPanel buttonPanel = new JPanel();
        
        // Tombol untuk menambahkan data
        JButton tambahButton = new JButton("Tambah Produk");
        tambahButton.addActionListener(new TambahProdukAction());
        buttonPanel.add(tambahButton);

        // Tombol untuk menghapus data
        JButton hapusButton = new JButton("Hapus Produk");
        hapusButton.addActionListener(new HapusProdukAction());
        buttonPanel.add(hapusButton);

        // Tambahkan panel tombol ke frame utama
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);

        // Action listeners untuk item menu
        tambahItem.addActionListener(e -> formPanel.setVisible(true));
        lihatItem.addActionListener(e -> formPanel.setVisible(false));
    }

    private JPanel createInputField(String label, JComponent input) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel(label));
        panel.add(input);
        return panel;
    }

    private JSpinner createHargaSpinner() {
        SpinnerNumberModel hargaModel = new SpinnerNumberModel(0, 0, 10000000, 1000);
        JSpinner spinner = new JSpinner(hargaModel);
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "Rp #,##0");
        spinner.setEditor(editor);
        return spinner;
    }

    private JPanel createAvailabilityPanel() {
        JPanel availabilityPanel = new JPanel();
        availabilityPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        availabilityPanel.add(new JLabel("Ketersediaan:"));
        tersediaRadioButton = new JRadioButton("Tersedia");
        tidakTersediaRadioButton = new JRadioButton("Tidak Tersedia");
        ButtonGroup availabilityGroup = new ButtonGroup();
        availabilityGroup.add(tersediaRadioButton);
        availabilityGroup.add(tidakTersediaRadioButton);
        availabilityPanel.add(tersediaRadioButton);
        availabilityPanel.add(tidakTersediaRadioButton);
        return availabilityPanel;
    }

    private JPanel createRatingSlider() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("Rating (1-10):"));
        ratingSlider = new JSlider(1, 10, 5);
        ratingSlider.setMajorTickSpacing(1);
        ratingSlider.setPaintTicks(true);
        ratingSlider.setPaintLabels(true);
        panel.add(ratingSlider);
        return panel;
    }

    private class TambahProdukAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nama = namaProdukField.getText();
            String kategori = (String) kategoriComboBox.getSelectedItem();
            int harga = (Integer) hargaSpinner.getValue();
            int stok = (Integer) stokSpinner.getValue();
            String deskripsi = deskripsiArea.getText();
            String ketersediaan = tersediaRadioButton.isSelected() ? "Tersedia" : "Tidak Tersedia";
            boolean diskon = diskonCheckBox.isSelected();
            int rating = ratingSlider.getValue();

            // Format harga sebagai Rupiah
            NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(new Locale("in", "ID"));
            String hargaFormatted = rupiahFormat.format(harga);

            // Tambahkan data ke JTable
            tableModel.addRow(new Object[]{nama, kategori, hargaFormatted, stok, deskripsi, ketersediaan, diskon ? "Ya" : "Tidak", rating});

            // Tambahkan produk ke JList
            produkListModel.addElement(nama + " - " + kategori); // Menampilkan nama dan kategori

            // Reset form
            resetForm();
        }
    }

    private void resetForm() {
        namaProdukField.setText("");
        kategoriComboBox.setSelectedIndex(0);
        hargaSpinner.setValue(0);
        stokSpinner.setValue(1);
        deskripsiArea.setText("");
        tersediaRadioButton.setSelected(true); // Default to "Tersedia"
        diskonCheckBox.setSelected(false); // Default to unchecked
        ratingSlider.setValue(5); // Reset to default rating
    }

    private class HapusProdukAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Hapus produk dari JList
            int selectedIndex = produkList.getSelectedIndex();
            if (selectedIndex != -1) { // Jika ada produk yang dipilih di JList
                String selectedProduct = produkListModel.getElementAt(selectedIndex);
                
                // Cari dan hapus dari JTable
                for (int row = 0; row < tableModel.getRowCount(); row++) {
                    if (tableModel.getValueAt(row, 0).equals(selectedProduct.split(" - ")[0])) {
                        tableModel.removeRow(row);
                        break; // Hentikan loop setelah menghapus produk
                    }
                }

                // Hapus dari JList
                produkListModel.remove(selectedIndex);
            } else {
                // Jika tidak ada produk yang dipilih
                JOptionPane.showMessageDialog(null, "Pilih produk yang ingin dihapus dari daftar.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AplikasiInventaris());
    }
}
