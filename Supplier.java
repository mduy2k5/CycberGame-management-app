import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import tempclass.SupplierClass;

public class Supplier {
    public static void AddSupplier(String tenNCC, String sdt, String email, String website, String diaChi) {
        String url = "jdbc:oracle:thin:@//localhost:1521/orcl";
        String username = "QUANLYTIEMCYCBERGAME";
        String password = "Admin123";
        String sql = "INSERT INTO NHA_CUNG_CAP (TENNCC, SDT, EMAIL, WEBSITE, DIACHI) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenNCC);
            stmt.setString(2, sdt);
            stmt.setString(3, email);
            stmt.setString(4, website);
            stmt.setString(5, diaChi);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Record added successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error adding record: " + e.getMessage());
        }
    }

    // Function to update a record in NHA_CUNG_CAP
    public static void UpdateSupplier(String maNCC, String tenNCC, String sdt, String email, String website, String diaChi) {
        String url = "jdbc:oracle:thin:@//localhost:1521/orcl";
        String username = "QUANLYTIEMCYCBERGAME";
        String password = "Admin123";
        String sql = "UPDATE NHA_CUNG_CAP SET TENNCC = ?, SDT = ?, EMAIL = ?, WEBSITE = ?, DIACHI = ? WHERE MANCC = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenNCC);
            stmt.setString(2, sdt);
            stmt.setString(3, email);
            stmt.setString(4, website);
            stmt.setString(5, diaChi);
            stmt.setString(6, maNCC);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Record updated successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error updating record: " + e.getMessage());
        }
    }

    // Function to delete a record from NHA_CUNG_CAP by ID
    public static void DeleteSupplier(String maNCC) {
        String url = "jdbc:oracle:thin:@//localhost:1521/orcl";
        String username = "QUANLYTIEMCYCBERGAME";
        String password = "Admin123";
        String sql = "DELETE FROM NHA_CUNG_CAP WHERE MANCC = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maNCC);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Record deleted successfully!");
            }
        } catch (SQLException e) {
            System.err.println("Error deleting record: " + e.getMessage());
        }
    }
    public static ArrayList<SupplierClass> getAllSuppliers() {
        String url = "jdbc:oracle:thin:@//localhost:1521/orcl";
        String username = "QUANLYTIEMCYCBERGAME";
        String password = "Admin123";
        String sql = "SELECT MANCC, TENNCC, SDT, EMAIL, WEBSITE, DIACHI FROM NHA_CUNG_CAP";
        ArrayList<SupplierClass> suppliers = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                SupplierClass supplier = new SupplierClass();
                supplier.setMaNCC(rs.getString("MANCC"));
                supplier.setTenNCC(rs.getString("TENNCC"));
                supplier.setSdt(rs.getString("SDT"));
                supplier.setEmail(rs.getString("EMAIL"));
                supplier.setWebsite(rs.getString("WEBSITE"));
                supplier.setDiaChi(rs.getString("DIACHI"));

                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error fetching suppliers: " + e.getMessage());
        }

        return suppliers;
    }
}
