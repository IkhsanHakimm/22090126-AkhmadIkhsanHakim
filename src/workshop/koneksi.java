package workshop;
import java.sql.Connection;
import java.sql.SQLException;
/**
 *
 * @author LENOVO
 */
public class koneksi {
    public static Connection sambungkeDB(){
        try {
            MysqlDataSource mds = new MysqlDataSource();
            mds.setUser("root"); 
            mds.setPassword(""); 
            mds.setDatabaseName("workshop");
            mds.setPortNumber(3306); 
            mds.setServerName("localhost"); 
            mds.setServerTimezone("Asia/Jakarta"); 
            Connection c = mds.getConnection();
            return c;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    public static void main(String[] args) {
        Connection c = sambungkeDB();
        if(c != null){
            System.out.println("Terhubung");
        }else {
            System.out.println("Gagal terhubung");
        }
    }

    private static class MysqlDataSource {

        public MysqlDataSource() {
        }
    }
}
