import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import tempclass.WareHouseClass;

public class WareHouse {

    // Function to add a new record to the KHO table
    public static void AddWHouse(String tenKho, String diaChi) {
        String url = "jdbc:oracle:thin:@//localhost:1521/orcl";
        String username = "QUANLYTIEMCYCBERGAME";
        String password = "Admin123";
        String sql = "INSERT INTO KHO (TENKHO, DIACHI) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenKho);
            stmt.setString(2, diaChi);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Warehouse added successfully!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error adding warehouse: " + e.getMessage());
        }
    }

    // Function to update a record in the KHO table
    public static void UpdateWHouse(String maKho, String tenKho, String diaChi) {
        String url = "jdbc:oracle:thin:@//localhost:1521/orcl";
        String username = "QUANLYTIEMCYCBERGAME";
        String password = "Admin123";
        String sql = "UPDATE KHO SET TENKHO = ?, DIACHI = ? WHERE MAKHO = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenKho);
            stmt.setString(2, diaChi);
            stmt.setString(3, maKho);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Warehouse updated successfully!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error updating warehouse: " + e.getMessage());
        }
    }

    // Function to delete a record from the KHO table by ID
    public static void DeleteWHouse(String maKho) {
        String url = "jdbc:oracle:thin:@//localhost:1521/orcl";
        String username = "QUANLYTIEMCYCBERGAME";
        String password = "Admin123";
        String sql = "DELETE FROM KHO WHERE MAKHO = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maKho);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Warehouse deleted successfully!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error deleting warehouse: " + e.getMessage());
        }
    }
    public static ArrayList<WareHouseClass> getAllWarehouses() {
        String url = "jdbc:oracle:thin:@//localhost:1521/orcl";
        String username = "QUANLYTIEMCYCBERGAME";
        String password = "Admin123";
        String sql = "SELECT MAKHO, TENKHO, DIACHI FROM KHO";
        ArrayList<WareHouseClass> warehouses = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                WareHouseClass warehouse = new WareHouseClass();
                warehouse.setMaKho(rs.getString("MAKHO"));
                warehouse.setTenKho(rs.getString("TENKHO"));
                warehouse.setDiaChi(rs.getString("DIACHI"));

                warehouses.add(warehouse);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error fetching warehouses: " + e.getMessage());
        }

        return warehouses;
    }
}