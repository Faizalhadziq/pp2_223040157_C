package Pertemuan13.Latihan3.src;
import Pertemuan13.Latihan3.src.controller.UserController;
import Pertemuan13.Latihan3.src.view.UserView;

public class main {
    public static void main(String[] args) {
        // Inisialisasi View dan Controller
        UserView userView = new UserView();
        UserController userController = new UserController(userView);

        // Tampilkan antarmuka pengguna
        userView.setVisible(true);
    }
}
