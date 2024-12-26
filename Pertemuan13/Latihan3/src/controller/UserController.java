package Pertemuan13.Latihan3.src.controller;

import Pertemuan13.Latihan3.src.model.User;
import Pertemuan13.Latihan3.src.model.UserMapper;
import Pertemuan13.Latihan3.src.view.UserView;
import Pertemuan13.Latihan3.src.view.UserPdf;

import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.List;

public class UserController {
    private final UserView view;
    private final UserMapper userMapper;

    public UserController(UserView view) {
        this.view = view;
        this.userMapper = new UserMapper();

        // Event listener untuk tombol Load
        view.setLoadButtonListener(e -> loadUsers());

        // Event listener untuk tombol Export
        view.setExportButtonListener(e -> exportUsers());
    }

    private void loadUsers() {
        SwingWorker<List<User>, Integer> worker = new SwingWorker<>() {
            @Override
            protected List<User> doInBackground() throws Exception {
                view.showProgressBar(true);
                return userMapper.getUsers(100); // Simulasi memuat 100 data
            }

            @Override
            protected void done() {
                try {
                    List<User> users = get();
                    view.updateTable(users);
                    view.showProgressBar(false);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Gagal memuat data: " + ex.getMessage());
                }
            }
        };
        worker.execute();
    }

    private void exportUsers() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                view.showProgressBar(true);
                List<User> users = view.getDisplayedUsers();
                UserPdf.exportPdf(users);
                return null;
            }

            @Override
            protected void done() {
                view.showProgressBar(false);
                JOptionPane.showMessageDialog(view, "Data berhasil diekspor ke PDF.");
            }
        };
        worker.execute();
    }
}