package Model.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;

import javafx.scene.control.Alert;

public class MysqlBase {

    protected static Connection conn;

    public static Connection open(){

        try {
            conn = DriverManager.getConnection("jdbc:mysql://mysql.mpiinformatica.com/mpiinformatica21?useSSL=false", "mpiinformatica21","mpi0055");
                return conn;
        } catch (MySQLSyntaxErrorException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Vôce Não esta conectado no banco.");
            alert.setContentText("Feche o sistema e reinicie a maquina e verifique se esta com internet");
            alert.showAndWait();
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
