package Model.mysql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Model.Cheque;
import Model.Cliente;
import Model.Status;
import javafx.scene.control.ListView;

public class ChequeMysqlDAO extends MysqlBase {
    public ChequeMysqlDAO() {
        open();
        try {
            PreparedStatement smt = conn.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS cheque(" + "id INTEGER PRIMARY KEY AUTO_INCREMENT," + "numero INTEGER,"
                            + "valor DOUBLE," + "data DATE," + "bomPara DATE," + "banco TEXT," + "agencia TEXT,"
                            + "conta TEXT," + "cdTitular INTEGER," + "cdRecebidoDe INTEGER," + "descricao TEXT,"
                            + "recebidoEm DATE," + "repassadoEm DATE," + "cdRepassadoPara INTEGER," + "cdstatus INTEGER," +
                            "FOREIGN KEY(cdRecebidoDe)"+
                            "REFERENCES Cliente(cdRecebidoDe),"+
                            "FOREIGN KEY(cdTitular)"+
                            "REFERENCES Cliente(cdTitular),"+
                            "FOREIGN KEY(cdRepassadoPara)"+
                            "REFERENCES Cliente(cdRepassadoPara),"+
                            "FOREIGN KEY(cdstatus)"+
                            "REFERENCES Status(cdstatus));");
            int i = smt.executeUpdate();
            System.out.println(i);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

    }
    public void create(Cheque c) {
        open();
        try {
            PreparedStatement smt = conn.prepareStatement(
                    "INSERT INTO cheque (numero,valor,data,bomPara,banco,agencia,conta,cdTitular,cdRecebidoDe,descricao,recebidoEm,repassadoEm,cdRepassadoPara,cdstatus) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            smt.setInt(1,c.getNumero());
            smt.setDouble(2,c.getValor());
            smt.setString(3,c.getData());
            smt.setString(4,c.getBomPara());
            smt.setString(5,c.getBanco());
            smt.setString(6,c.getAgencia());
            smt.setString(7,c.getConta());
            smt.setInt(8,c.getTitular().get_id());
            smt.setInt(9,c.getRecebidode().get_id());
            smt.setString(10,c.getDescricao());
            smt.setString(11,c.getRecebidoEm());
            smt.setString(12,c.getRepassadoEm());
            smt.setInt(13,c.getRepassadoPara().get_id());
            smt.setInt(14,c.getStatus().get_id());
            smt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

    }

    public List<Cheque> all() {
        ArrayList<Cheque> ChequeList = new ArrayList<>();
        open();
        try {
            ResultSet rs;
            PreparedStatement stm = conn.prepareStatement("SELECT * FROM Cheque");
            rs = stm.executeQuery();
            while (rs.next()) {
                Cliente cliente = Category.findById(rs.getInt("category_id"));
                Product product = new Product(rs.getString("name"), rs.getDouble("price"), category);
                product.setDescription(rs.getString("description"));
                product.setId(rs.getInt("id"));
                ChequeList.add(CHEQUE);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar os Cheque:" + e.getMessage());
        } finally {
            close();
        }
        return productList;
    }
    /**
     * public void update(Cheque cheque){
     *
     * open(); try { PreparedStatement smt = conn.prepareStatement("UPDATE
     * Cheque SET "+ "nome = ?, "+ "endereco = ?, "+ "telefone = ? "+ "WHERE ID
     * = ?;"); smt.setString(1,cheque.get());
     * smt.setString(2,cheque.getEndereco());
     * smt.setString(3,cheque.getTelefone()); smt.setInt(4,cheque.get_id());
     * smt.executeUpdate(); } catch (SQLException e) { e.printStackTrace(); }
     * finally { close(); }
     *
     * }
**/
    public void delete(Cheque c) {

        open();
        try {
            PreparedStatement smt = conn.prepareStatement("DELETE FROM Cheque WHERE id = ?;");
            smt.setInt(1, c.get_id());
            smt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }

    }

    public Cheque find(int pk) {
        Cheque result = null;
        conn = open();

        try {
            PreparedStatement smt = conn.prepareStatement("SELECT * FROM Cheque WHERE id = ?;");
            smt.setInt(1, pk);
            ResultSet rs = smt.executeQuery();
            if (rs.next()) {
                Cheque c = new Cheque(
                		rs.getInt(1), // id
                        rs.getInt(2), // numero
                        rs.getDouble(3), // valor
                        rs.getDate(4), // DATA
                        rs.getDate(5), // bomPARA
                        rs.getString(6), // Banco
                        rs.getString(7), // Agencia
                        rs.getString(8), // Conta
                        rs.getInt(9), // titular
                        rs.getObject(10), // RecebidoDe
                        rs.getString(11), // descricacao
                        rs.getDate(12), // RecebidoEm
                        rs.getDate(13), // RepassadoEm
                        rs.getInt(14), // Repassadopara
                        rs.getInt(15));// STATUS
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