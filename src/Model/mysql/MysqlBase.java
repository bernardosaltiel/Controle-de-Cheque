package Model.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlBase {

    protected static Connection conn;

    public static Connection open(){

        try {
            conn = DriverManager.getConnection("jdbc:mysql://mysql.mpiinformatica.com/mpiinformatica21?useSSL=false", "mpiinformatica21","mpi0055");
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
