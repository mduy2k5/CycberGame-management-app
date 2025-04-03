import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import tempclass.customerclass;

public class Checker {
    public static customerclass check_account_existence_byphone(String SDT){
        customerclass customer_result = new customerclass();      
        String url = "jdbc:oracle:thin:@//localhost:1521/orcl"; // Hoặc @IP_SERVER:PORT/SERVICE_NAME
        String username = "CHECKER";  // Thay bằng username thật
        String password = "Checker123";  // Thay bằng password thật
        String user_id = null;
        PreparedStatement stmt = null;
        PreparedStatement stmt_1 = null;
        ResultSet rs = null;
        String sql = "SELECT USER_ID,MAHKH, SODIEMTICHLUY,SODUTK,TRANGTHAI FROM QUANLYTIEMCYCBERGAME.KHACH_HANG";
        String sql_user = "SELECT USER_ID FROM QUANLYTIEMCYCBERGAME.USERS WHERE SDT = ?";
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection conn = DriverManager.getConnection(url, username, password);
            stmt= conn.prepareStatement(sql);
            stmt_1 = conn.prepareStatement(sql_user);
            stmt_1.setString(1, SDT);
            rs = stmt_1.executeQuery();
            while (rs.next()) { 
                user_id = rs.getString("USER_ID");
            }
            rs = stmt.executeQuery();
            while (rs.next()){
                if (user_id != null){
                    customer_result.setMahkh(rs.getString("MAHKH"));
                    customer_result.setUserid(rs.getString("USER_ID"));
                    customer_result.setSdutk(rs.getInt("SODUTK"));
                    customer_result.setSodiemtichluy(rs.getInt("SODIEMTICHLUY"));
                    customer_result.setTrangthai(rs.getString("TRANGTHAI"));
                }
                else return customer_result;
            }
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
        return customer_result;
    }
}
