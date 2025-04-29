package DatabaseFunction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import TempClass.PromotionClass;

public class Promotion {
    // Global variables for database connection
    static String url = "jdbc:oracle:thin:@//localhost:1521/orcl";
    static String username = "QUANLYTIEMCYCBERGAME";
    static String password = "Admin123";

    // Function to add a new promotion
    public static void AddPromotion(String tenCTR, String loaiCTR, java.util.Date ngayBD, java.util.Date ngayKT, double chietKhau) {
        String sql = "INSERT INTO CHUONG_TRINH_KHUYEN_MAI (TENCTR, LOAICTR, NGBD, NGKT, CHIETKHAU) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenCTR);
            stmt.setString(2, loaiCTR);
            stmt.setDate(3, new java.sql.Date(ngayBD.getTime()));
            stmt.setDate(4, new java.sql.Date(ngayKT.getTime()));
            stmt.setDouble(5, chietKhau);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ Promotion added successfully!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error adding promotion: " + e.getMessage());
        }
    }

    // Function to update promotion information
    public static void UpdatePromotion(String maCTR, String tenCTR, String loaiCTR, java.util.Date ngayBD, java.util.Date ngayKT, double chietKhau) {
        String sql = "UPDATE CHUONG_TRINH_KHUYEN_MAI SET TENCTR = ?, LOAICTR = ?, NGBD = ?, NGKT = ?, CHIETKHAU = ? WHERE MACTR = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tenCTR);
            stmt.setString(2, loaiCTR);
            stmt.setDate(3, new java.sql.Date(ngayBD.getTime()));
            stmt.setDate(4, new java.sql.Date(ngayKT.getTime()));
            stmt.setDouble(5, chietKhau);
            stmt.setString(6, maCTR);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("✅ Promotion updated successfully!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error updating promotion: " + e.getMessage());
        }
    }

    // Function to delete a promotion by setting IS_DELETE to 1
    public static void DeletePromotion(String maCTR) {
        String sql = "UPDATE CHUONG_TRINH_KHUYEN_MAI SET IS_DELETE = 1 WHERE MACTR = ?";

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maCTR);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("✅ Promotion deleted successfully!");
            }
        } catch (SQLException e) {
            System.err.println("❌ Error deleting promotion: " + e.getMessage());
        }
    }

    // Function to select all promotions and return an ArrayList of PromotionClass
    public static ArrayList<PromotionClass> SelectPromotion() {
        String sql = "SELECT MACTR, TENCTR, LOAICTR, NGBD, NGKT, CHIETKHAU FROM CHUONG_TRINH_KHUYEN_MAI WHERE IS_DELETE = 0";
        ArrayList<PromotionClass> promotions = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PromotionClass promotion = new PromotionClass();
                promotion.setMaCTR(rs.getString("MACTR"));
                promotion.setTenCTR(rs.getString("TENCTR"));
                promotion.setLoaiCTR(rs.getString("LOAICTR"));
                promotion.setNgayBD(rs.getDate("NGBD"));
                promotion.setNgayKT(rs.getDate("NGKT"));
                promotion.setChietKhau(rs.getDouble("CHIETKHAU"));

                promotions.add(promotion);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error fetching promotions: " + e.getMessage());
        }

        return promotions;
    }
    public static ArrayList<PromotionClass> SelectPromotionsByArea(String maKV) {
        String sql = "SELECT ctr.MACTR, ctr.TENCTR, ctr.LOAICTR, ctr.NGBD, ctr.NGKT, ctr.CHIETKHAU " +
                     "FROM KHUYEN_MAI_KHU_VUC kv " +
                     "JOIN CHUONG_TRINH_KHUYEN_MAI ctr ON kv.MACTR = ctr.MACTR " +
                     "WHERE kv.MAKV = ? AND kv.IS_DELETE = 0 AND ctr.IS_DELETE = 0 " +
                     "AND SYSDATE BETWEEN ctr.NGBD AND ctr.NGKT";
    
        ArrayList<PromotionClass> promotions = new ArrayList<>();
    
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setString(1, maKV);
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                PromotionClass promotion = new PromotionClass();
                promotion.setMaCTR(rs.getString("MACTR"));
                promotion.setTenCTR(rs.getString("TENCTR"));
                promotion.setLoaiCTR(rs.getString("LOAICTR"));
                promotion.setNgayBD(rs.getDate("NGBD"));
                promotion.setNgayKT(rs.getDate("NGKT"));
                promotion.setChietKhau(rs.getDouble("CHIETKHAU"));
    
                promotions.add(promotion);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error fetching promotions by area: " + e.getMessage());
        }
    
        return promotions;
    }
}