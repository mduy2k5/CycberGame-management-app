import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Depositer {
    public static boolean Deposit (String makh, int sotien, String hinhthuc, String trangthai){
        String url = "jdbc:oracle:thin:@//localhost:1521/orcl"; // Hoặc @IP_SERVER:PORT/SERVICE_NAME
        String username = "QUANLYTIEMCYCBERGAME";  // Thay bằng username thật
        String password = "Admin123";  // Thay bằng password thật
        CallableStatement stmt = null;
        ResultSet rs = null;
        String sql = "{ call DEPOSIT(?, ?, ?, ?) }";
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection conn = DriverManager.getConnection(url, username, password);
            stmt = conn.prepareCall(sql);
            stmt.setString(1,makh);
            stmt.setInt(2,sotien);
            stmt.setString(3,hinhthuc);
            stmt.setString(4,trangthai);

            
            stmt.execute();
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println("Not found");
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close(); 
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return true;
    }
}
