package workshop;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class koneksi {
    public static Connection sambungkeDB() {
        try {
            MysqlDataSource mds = new MysqlDataSource();
            mds.setUser("root");
            mds.setPassword(""); 
            mds.setDatabaseName("workshop");
            mds.setPort(3306); 
            mds.setServerName("localhost");
            mds.setServerTimezone("Asia/Jakarta");
            Connection con = mds.getConnection();
            return con;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    public static void main(String[] args) {
        Connection c = sambungkeDB();
        if (c != null) {
            System.out.println("Terhubung");
        } else {
            System.out.println("Gagal terhubung");
        }
    }
}
