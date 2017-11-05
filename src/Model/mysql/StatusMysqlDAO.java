package Model.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Model.Status;

public class StatusMysqlDAO extends MysqlBase {

    public StatusMysqlDAO(){

        open();
        try {
            PreparedStatement smt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Status("+
                    "id INTEGER PRIMARY KEY AUTO_INCREMENT,"+
                    "nome TEXT);");
            int i = smt.executeUpdate();
            System.out.println(i);
        } catch (SQLException e) {
            e.printStackTrace();
        }   finally {
            close();
        }

    }

    public void create(Status s){

        open();
        try {
            PreparedStatement smt = conn.prepareStatement("INSERT INTO Status (nome) VALUES(?)");
            smt.setString(1,s.getNome());
            smt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }   finally {
            close();
        }

    }

    public static Status buscar(Status Status) {
    	open();
        String sql = "SELECT * FROM Status WHERE id=?";
        Status retorno = new Status();
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, retorno.get_id());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                retorno.set_id(resultado.getInt("id"));
                retorno.setNome(resultado.getString("nome"));

                retorno = Status;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ClienteMysqlDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public static List<Status> all(){
        ArrayList<Status> result = new ArrayList<>();

        open();
        try {
            PreparedStatement smt = conn.prepareStatement("select * from Status ORDER BY nome ASC;");

            ResultSet rs = smt.executeQuery();
            while (rs.next()) {

                Status s = new Status(
                        rs.getInt(1),//id
                        rs.getString(2));//nome
                result.add(s);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }   finally{
            close();
        }
        return result;
    }

    public void update(Status s){

        open();
        try {
            PreparedStatement smt = conn.prepareStatement("UPDATE Status SET "+
                    "nome = ? "+
                    "WHERE ID = ?;");
            smt.setString(1,s.getNome());
            smt.setInt(2,s.get_id());
            smt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }   finally {
            close();
        }

    }

    public void delete(Status s){

        open();
        try {
            PreparedStatement smt = conn.prepareStatement("DELETE FROM Status WHERE id = ?;");
            smt.setInt(1,s.get_id());
            smt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }   finally {
            close();
        }

    }

    public Status find(int pk) {
    	Status result = null;
        conn = open();

        try {
            PreparedStatement smt = conn.prepareStatement("SELECT * FROM Status WHERE id = ?;");
            smt.setInt(1, pk);
            ResultSet rs = smt.executeQuery();
            if(rs.next()){
                Status s = new Status(
                        rs.getInt(1),//id
                        rs.getString(2));//nome
                result = s;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }
}
