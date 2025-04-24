import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import tempclass.ProductClass;

public class Product {
    // Global variables for database connection
    static String url = "jdbc:oracle:thin:@//localhost:1521/orcl";
    static String username = "QUANLYTIEMCYCBERGAME";
    static String password = "Admin123";

    // Function to add a new product
    public static void AddProduct(String tenSP, String dvt, String loaiSP, int soLuongTK, int soDiemTichLuy, double donGiaBQ) {
        String sql = "INSERT INTO SAN_PHAM (TENSP, DVT, LOAISP, SOLUONGTK, SODIEMTICHLUY, DONGIABQ) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenSP);
            stmt.setString(2, dvt);
            stmt.setString(3, loaiSP);
            stmt.setInt(4, soLuongTK);
            stmt.setInt(5, soDiemTichLuy);
            stmt.setDouble(6, donGiaBQ);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Product added successfully!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error adding product: " + e.getMessage());
        }
    }

    // Function to update product information
    public static void UpdateProduct(String maSP, String tenSP, String dvt, String loaiSP, int soLuongTK, int soDiemTichLuy, double donGiaBQ) {
        String sql = "UPDATE SAN_PHAM SET TENSP = ?, DVT = ?, LOAISP = ?, SOLUONGTK = ?, SODIEMTICHLUY = ?, DONGIABQ = ? WHERE MASP = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenSP);
            stmt.setString(2, dvt);
            stmt.setString(3, loaiSP);
            stmt.setInt(4, soLuongTK);
            stmt.setInt(5, soDiemTichLuy);
            stmt.setDouble(6, donGiaBQ);
            stmt.setString(7, maSP);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Product updated successfully!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error updating product: " + e.getMessage());
        }
    }

    // Function to delete a product by setting IS_DELETE to 1
    public static void DeleteProduct(String maSP) {
        String sql = "UPDATE SAN_PHAM SET IS_DELETE = 1 WHERE MASP = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maSP);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Product deleted successfully!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error deleting product: " + e.getMessage());
        }
    }

    // Function to select all products and return an ArrayList of ProductClass
    public static ArrayList<ProductClass> SelectProduct() {
        String sql = "SELECT MASP, TENSP, DVT, LOAISP, SOLUONGTK, SODIEMTICHLUY, DONGIABQ FROM SAN_PHAM WHERE IS_DELETE = 0";
        ArrayList<ProductClass> products = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ProductClass product = new ProductClass();
                product.setMaSP(rs.getString("MASP"));
                product.setTenSP(rs.getString("TENSP"));
                product.setDvt(rs.getString("DVT"));
                product.setLoaiSP(rs.getString("LOAISP"));
                product.setSoLuongTK(rs.getInt("SOLUONGTK"));
                product.setSoDiemTichLuy(rs.getInt("SODIEMTICHLUY"));
                product.setDonGiaBQ(rs.getDouble("DONGIABQ"));

                products.add(product);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error fetching products: " + e.getMessage());
        }

        return products;
    }
}