import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BiodataDao {
    // Menambahkan data baru ke tabel biodata
    public void insertBiodata(String nama, String nomorHp, String jenisKelamin, String wna) throws SQLException {
        String query = "INSERT INTO biodata (nama, nomor_hp, jenis_kelamin, wna) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nama);
            stmt.setString(2, nomorHp);
            stmt.setString(3, jenisKelamin);
            stmt.setString(4, wna);
            stmt.executeUpdate();
        }
    }

    // Memperbarui data di tabel biodata
    public void updateBiodata(int id, String nama, String nomorHp, String jenisKelamin, String wna) throws SQLException {
        String query = "UPDATE biodata SET nama = ?, nomor_hp = ?, jenis_kelamin = ?, wna = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nama);
            stmt.setString(2, nomorHp);
            stmt.setString(3, jenisKelamin);
            stmt.setString(4, wna);
            stmt.setInt(5, id);
            stmt.executeUpdate();
        }
    }

    // Menghapus data dari tabel biodata berdasarkan ID
    public void deleteBiodata(int id) throws SQLException {
        String query = "DELETE FROM biodata WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Mendapatkan semua data dari tabel biodata
    public ResultSet getAllBiodata() throws SQLException {
        String query = "SELECT * FROM biodata";
        Connection conn = DatabaseConnection.getConnection();
        return conn.createStatement().executeQuery(query);
    }
}
