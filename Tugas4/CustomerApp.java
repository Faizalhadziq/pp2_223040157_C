package Tugas4;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;


public class CustomerApp {
    private JFrame frame;
    private JTable table;
    private JTextField txtName, txtEmail, txtPhone, txtAddress;
    private JButton btnAdd, btnUpdate, btnDelete, btnRefresh;
    private DefaultTableModel model;

    private Connection connection;

    public CustomerApp() {
        // Initialize GUI
        frame = new JFrame("Customer Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Table setup
        model = new DefaultTableModel(new String[]{"ID", "Name", "Email", "Phone", "Address"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Form panel setup
        JPanel formPanel = new JPanel(new GridLayout(5, 2));

        formPanel.add(new JLabel("Name:"));
        txtName = new JTextField();
        formPanel.add(txtName);

        formPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);

        formPanel.add(new JLabel("Phone:"));
        txtPhone = new JTextField();
        formPanel.add(txtPhone);

        formPanel.add(new JLabel("Address:"));
        txtAddress = new JTextField();
        formPanel.add(txtAddress);

        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnRefresh = new JButton("Refresh");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnRefresh);

        frame.add(formPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Initialize database connection
        connectToDatabase();
        loadTableData();

        // Button actions
        btnAdd.addActionListener(e -> insertData());
        btnUpdate.addActionListener(e -> updateData());
        btnDelete.addActionListener(e -> deleteData());
        btnRefresh.addActionListener(e -> loadTableData());

        frame.setSize(600, 400);
        frame.setVisible(true);
    }

    private void connectToDatabase() {
        try {
            connection = DatabaseConnection.getConnection();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Failed to connect to database: " + e.getMessage());
        }
    }

    private void loadTableData() {
        model.setRowCount(0);
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM customers")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error loading data: " + e.getMessage());
        }
    }

    private void insertData() {
        try (PreparedStatement ps = connection.prepareStatement("INSERT INTO customers (name, email, phone, address) VALUES (?, ?, ?, ?)")) {
            ps.setString(1, txtName.getText());
            ps.setString(2, txtEmail.getText());
            ps.setString(3, txtPhone.getText());
            ps.setString(4, txtAddress.getText());
            ps.executeUpdate();
            loadTableData();
            JOptionPane.showMessageDialog(frame, "Data added successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error adding data: " + e.getMessage());
        }
    }

    private void updateData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a row to update.");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        try (PreparedStatement ps = connection.prepareStatement("UPDATE customers SET name=?, email=?, phone=?, address=? WHERE id=?")) {
            ps.setString(1, txtName.getText());
            ps.setString(2, txtEmail.getText());
            ps.setString(3, txtPhone.getText());
            ps.setString(4, txtAddress.getText());
            ps.setInt(5, id);
            ps.executeUpdate();
            loadTableData();
            JOptionPane.showMessageDialog(frame, "Data updated successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error updating data: " + e.getMessage());
        }
    }

    private void deleteData() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Please select a row to delete.");
            return;
        }

        int id = (int) table.getValueAt(selectedRow, 0);
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM customers WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
            loadTableData();
            JOptionPane.showMessageDialog(frame, "Data deleted successfully!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error deleting data: " + e.getMessage());
        }
    }
}
