package DatabaseFunction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import tempclass.GiftClass;

public class Gift {
    // Global variables for database connection
    static String url = "jdbc:oracle:thin:@//localhost:1521/orcl";
    static String username = "QUANLYTIEMCYCBERGAME";
    static String password = "Admin123";

    // Function to add a new gift
    public static void AddGift(String noiDung, int soDiemTieuHao) {
        String sql = "INSERT INTO QUA_TANG (NOIDUNG, SODIEMTIEUHAO) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, noiDung);
            stmt.setInt(2, soDiemTieuHao);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Gift added successfully!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error adding gift: " + e.getMessage());
        }
    }

    // Function to update gift information
    public static void UpdateGift(String maQT, String noiDung, int soDiemTieuHao) {
        String sql = "UPDATE QUA_TANG SET NOIDUNG = ?, SODIEMTIEUHAO = ? WHERE MAQT = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, noiDung);
            stmt.setInt(2, soDiemTieuHao);
            stmt.setString(3, maQT);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Gift updated successfully!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error updating gift: " + e.getMessage());
        }
    }

    // Function to delete a gift by setting IS_DELETE to 1
    public static void DeleteGift(String maQT) {
        String sql = "UPDATE QUA_TANG SET IS_DELETE = 1 WHERE MAQT = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maQT);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Gift deleted successfully!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error deleting gift: " + e.getMessage());
        }
    }

    // Function to select all gifts and return an ArrayList of GiftClass
    public static ArrayList<GiftClass> SelectGift() {
        String sql = "SELECT MAQT, NOIDUNG, SODIEMTIEUHAO FROM QUA_TANG WHERE IS_DELETE = 0";
        ArrayList<GiftClass> gifts = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                GiftClass gift = new GiftClass();
                gift.setMaQT(rs.getString("MAQT"));
                gift.setNoiDung(rs.getString("NOIDUNG"));
                gift.setSoDiemTieuHao(rs.getInt("SODIEMTIEUHAO"));

                gifts.add(gift);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error fetching gifts: " + e.getMessage());
        }

        return gifts;
    }
    // Function to select a gift by MAQT and return a GiftClass object
    public static GiftClass SelectGiftById(String maQT) {
        String sql = "SELECT MAQT, NOIDUNG, SODIEMTIEUHAO FROM QUA_TANG WHERE MAQT = ? AND IS_DELETE = 0";
        GiftClass gift = null;

        try (Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maQT); // Set the MAQT parameter
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                gift = new GiftClass();
                gift.setMaQT(rs.getString("MAQT"));
                gift.setNoiDung(rs.getString("NOIDUNG"));
                gift.setSoDiemTieuHao(rs.getInt("SODIEMTIEUHAO"));
            }
        } catch (SQLException e) {
            System.err.println("❌ Error fetching gift by ID: " + e.getMessage());
        }

        return gift;
    }
}