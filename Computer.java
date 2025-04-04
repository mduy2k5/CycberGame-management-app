import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import tempclass.customerclass;

public class Computer {
    public static boolean registerComputer(String MAPC, String RAM, String ROM, String CPU, 
    String VGA, int SOMAY, String TRANGTHAI, String MAKV) {
        String url = "jdbc:oracle:thin:@//localhost:1521/orcl"; // Thay bằng URL CSDL
        String username = "QUANLYTIEMCYCBERGAME";  
        String password = "Admin123";  

        String sql = "INSERT INTO PC (MAPC, RAM, ROM, CPU, VGA, SOMAY, TRANGTHAI, MAKV) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, username, password);
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            Class.forName("oracle.jdbc.OracleDriver");

            stmt.setString(1, MAPC);
            stmt.setString(2, RAM);
            stmt.setString(3, ROM);
            stmt.setString(4, CPU);
            stmt.setString(5, VGA);
            stmt.setInt(6, SOMAY);
            stmt.setString(7, TRANGTHAI);
            stmt.setString(8, MAKV);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Đăng ký máy tính thành công!");
                return true;
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Not found");
        } catch (SQLException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return false;
    }
}
