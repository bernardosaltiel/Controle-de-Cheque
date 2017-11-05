package Model.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.Cliente;

public class ClienteMysqlDAO extends MysqlBase {

    public ClienteMysqlDAO(){

        open();
        try {
            PreparedStatement smt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Cliente("+
                    "id INTEGER PRIMARY KEY AUTO_INCREMENT,"+
                    "nome TEXT,"+
                    "endereco TEXT,"+
                    "telefone TEXT);");
            int i = smt.executeUpdate();
            System.out.println(i);
        } catch (SQLException e) {
            e.printStackTrace();
        }   finally {
            close();
        }

    }

    public void create(Cliente c){

        open();
        try {
            PreparedStatement smt = conn.prepareStatement("INSERT INTO Cliente (nome,endereco,telefone) VALUES(?,?,?)");
            smt.setString(1,c.getNome());
            smt.setString(2,c.getEndereco());
            smt.setString(3,c.getTelefone());
            smt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }   finally {
            close();
        }

    }

    public static List<Cliente> all(){
        ArrayList<Cliente> result = new ArrayList<>();

        open();
        try {
            PreparedStatement smt = conn.prepareStatement("select * from Cliente ORDER BY nome ASC;");

            ResultSet rs = smt.executeQuery();
            while (rs.next()) {

                Cliente c = new Cliente(
                        rs.getInt(1),//id
                        rs.getString(2),//nome
                        rs.getString(3),//endereco
                        rs.getString(4)); //telefone
                result.add(c);
            }
        } catch(SQLException e){
            e.printStackTrace();
        }   finally{
            close();
        }
        return result;
    }

    public void update(Cliente c){

        open();
        try {
            PreparedStatement smt = conn.prepareStatement("UPDATE Cliente SET "+
                    "nome = ?, "+
                    "endereco = ?, "+
                    "telefone = ? "+
                    "WHERE ID = ?;");
            smt.setString(1,c.getNome());
            smt.setString(2,c.getEndereco());
            smt.setString(3,c.getTelefone());
            smt.setInt(4,c.get_id());
            smt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }   finally {
            close();
        }

    }

    public void delete(Cliente c){

        open();
        try {
            PreparedStatement smt = conn.prepareStatement("DELETE FROM Cliente WHERE id = ?;");
            smt.setInt(1,c.get_id());
            smt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }   finally {
            close();
        }

    }

    public Cliente find(int pk) {
        Cliente result = null;
        conn = open();

        try {
            PreparedStatement smt = conn.prepareStatement("SELECT * FROM Cliente WHERE id = ?;");
            smt.setInt(1, pk);
            ResultSet rs = smt.executeQuery();
            if(rs.next()){
                Cliente c = new Cliente(
                        rs.getInt(1),//id
                        rs.getString(2),//nome
                        rs.getString(3),//endereco
                        rs.getString(4)); //telefone
                result = c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return result;
    }
}
