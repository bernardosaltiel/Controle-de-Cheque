package Model.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlBase {

    protected static Connection conn;

    public static Connection open(){

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/controlecheque?useSSL=false", "root","");
                return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void close(){
        try {
            if(conn !=null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
