import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OracleJDBC {
    public static void tab(){
        String url = "jdbc:oracle:thin:@//localhost:1521/orcl"; // Hoặc @IP_SERVER:PORT/SERVICE_NAME
        String user = "ADMIN";  // Thay bằng username thật
        String password = "Admin123";  // Thay bằng password thật

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ Kết nối thành công!");

            // Lấy metadata của database
            DatabaseMetaData metaData = conn.getMetaData();

            // Lấy danh sách bảng trong schema hiện tại
            try (ResultSet rs = metaData.getTables(null, user.toUpperCase(), "%", new String[]{"TABLE"})) {
                System.out.println("📌 Danh sách bảng trong schema " + user.toUpperCase() + ":");
                while (rs.next()) {
                    System.out.println("- " + rs.getString("TABLE_NAME"));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi: " + e.getMessage());
        }
    }
    public static void check(){
        String url = "jdbc:oracle:thin:@//localhost:1521/orcl"; // Hoặc @IP_SERVER:PORT/SERVICE_NAME
        String username = "QUANLYTIEMCYCBERGAME";  // Thay bằng username thật
        String password = "Admin123";  // Thay bằng password thật

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            System.out.println("✅ Kết nối thành công!");
            
            // Lấy schema hiện tại
            String schema = conn.getSchema();
            System.out.println("🔍 Schema hiện tại: " + schema);
            
            // Hoặc dùng truy vấn SQL
            try (Statement stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery("SELECT SYS_CONTEXT('USERENV', 'CURRENT_SCHEMA') FROM DUAL")) {
                if (rs.next()) {
                    System.out.println("🔍 Schema từ SQL: " + rs.getString(1));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Lỗi kết nối: " + e.getMessage());
        }
    }
    public static void connect() {
        String url = "jdbc:oracle:thin:@//localhost:1521/orcl"; // Hoặc @IP_SERVER:PORT/SERVICE_NAME
        String username = "QUANLYTIEMCYCBERGAME";  // Thay bằng username thật
        String password = "Admin123";  // Thay bằng password thật
        Statement stmt = null;
        ResultSet rs = null;
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection conn = DriverManager.getConnection(url, username, password);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT ACCOUNT_ID FROM QUANLYTIEMCYCBERGAME.ACCOUNT");
            //rs = stmt.executeQuery("SELECT USER_ID FROM USERS");
            //rs = stmt.executeQuery("SELECT TABLE_NAME FROM USER_TABLES");
            //rs = stmt.executeQuery("SELECT * FROM USERS");
            // try (Statement stmts = conn.createStatement();
            //         ResultSet rss = stmts.executeQuery("SELECT SYS_CONTEXT('USERENV', 'CURRENT_SCHEMA') FROM DUAL")) {
            //     if (rs.next()) {
            //         System.out.println("🔍 Schema từ SQL: " + rs.getString(1));
            //     }
            // }
            //rs = stmt.executeQuery("SELECT SYS_CONTEXT('USERENV', 'CURRENT_USER') FROM DUAL");
            while (rs.next()){
                // String name = rs.getString(1);
                // int salary = rs.getInt(2);
                // System.out.println(name + " : " + salary);
                // System.out.println(rs.getString("TABLE_NAME"));
                // ResultSetMetaData metaData = rs.getMetaData();
                // int columnCount = metaData.getColumnCount();

                // // In tiêu đề cột
                // for (int i = 1; i <= columnCount; i++) {
                //     System.out.print(metaData.getColumnName(i) + "\t");
                // }
                // System.out.println("\n-----------------------------------------");

                // In từng dòng dữ liệu
                System.out.println(rs.getString("ACCOUNT_ID"));
                
                // while (rs.next()) {
                //     System.out.println("User hiện tại: " + rs.getString(1));
                // }
            }

            
            
            System.out.println("Success");
            // Đóng kết nối
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
    }
    public static void Create_ACCOUNT(String UserID, String UserName, String Pass){
        String passHas = Hash.change(Pass);
        String url = "jdbc:oracle:thin:@//localhost:1521/orcl"; // Hoặc @IP_SERVER:PORT/SERVICE_NAME
        String username = "QUANLYTIEMCYCBERGAME";  // Thay bằng username thật
        String password = "Admin123";  // Thay bằng password thật
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "{ call Create_ACCOUNT(?, ?, ?) }";
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection conn = DriverManager.getConnection(url, username, password);
            stmt = conn.prepareCall(sql);
            stmt.setString(1,UserID);
            stmt.setString(2,UserName);
            stmt.setString(3,passHas);
            
            stmt.execute();
            
            System.out.println("Success login");
            // Đóng kết nối
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
    }
    public static String login(String UserName, String pass){
        String hasPass = Hash.change(pass);
        System.out.println(UserName+ ":" +  hasPass);
        String url = "jdbc:oracle:thin:@//localhost:1521/orcl"; // Hoặc @IP_SERVER:PORT/SERVICE_NAME
        String username = "QUANLYTIEMCYCBERGAME";  // Thay bằng username thật
        String password = "Admin123";  // Thay bằng password thật
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = "SELECT USER_ID FROM QUANLYTIEMCYCBERGAME.ACCOUNT WHERE USERNAME = ? AND PASSWORD_HASH = ? ";
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection conn = DriverManager.getConnection(url, username, password);
            stmt= conn.prepareStatement(sql);
            stmt.setString(1, UserName);
            stmt.setString(2, hasPass);

            rs = stmt.executeQuery();
            int count = 0;
            while (rs.next()){
                System.out.println(rs.getString("USER_ID"));
                count += 1;
            }
            if (count >0) return "Success";
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
        return "Fail";
    }
}