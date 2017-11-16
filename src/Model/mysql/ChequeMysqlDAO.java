package Model.mysql;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import Model.Cheque;
import Model.Cliente;
import Model.Status;

public class ChequeMysqlDAO extends MysqlBase {
	public ChequeMysqlDAO() {
		open();
		try {
			PreparedStatement smt = conn.prepareStatement("CREATE TABLE IF NOT EXISTS Cheque("
					+ "id INTEGER PRIMARY KEY AUTO_INCREMENT," + "numero INTEGER," + "valor DOUBLE," + "data DATE,"
					+ "bomPara DATE," + "banco TEXT," + "agencia TEXT," + "conta TEXT," + "cdTitular INTEGER,"
					+ "cdRecebidoDe INTEGER," + "descricao TEXT," + "recebidoEm DATE," + "repassadoEm DATE,"
					+ "cdRepassadoPara INTEGER," + "cdstatus INTEGER," + "constraint fk_Cheque_Cliente " + "FOREIGN KEY(cdRecebidoDe)"
					+ "REFERENCES Cliente(id)," + "FOREIGN KEY(cdTitular)" + "REFERENCES Cliente(id),"
					+ "FOREIGN KEY(cdRepassadoPara)" + "REFERENCES Cliente(id)," + "constraint fk_Cheque_Status " + "FOREIGN KEY(cdstatus)"
					+ "REFERENCES Status(id));");
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
					"INSERT INTO Cheque (numero,valor,data,bomPara,banco,agencia,conta,cdTitular,cdRecebidoDe,descricao,recebidoEm,repassadoEm,cdRepassadoPara,cdstatus) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			smt.setInt(1, c.getNumero());
			smt.setDouble(2, c.getValor());
			smt.setDate(3, Date.valueOf(c.getData()));
			smt.setDate(4, Date.valueOf(c.getBomPara()));
			smt.setString(5, c.getBanco());
			smt.setString(6, c.getAgencia());
			smt.setString(7, c.getConta());
			smt.setInt(8, c.getTitular().get_id());
			smt.setInt(9, c.getRecebidode().get_id());
			smt.setString(10, c.getDescricao());
			smt.setDate(11, Date.valueOf(c.getRecebidoEm()));
			if (c.getRepassadoEm() == null){
			smt.setNull(12, Types.DATE);
			}else{
			smt.setDate(12, Date.valueOf(c.getRepassadoEm()));
			}
			if (c.getRepassadoPara() == null){
			smt.setNull(13, Types.INTEGER);
			}else{
			smt.setInt(13, c.getRepassadoPara().get_id());
			}
			smt.setInt(14, c.getStatus().get_id());
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
				Cliente Titular = Cliente.find(rs.getInt("cdTitular"));
				Cliente Recebidode = Cliente.find(rs.getInt("cdRecebidoDe"));
				Cliente RepassadoPara = Cliente.find(rs.getInt("cdRepassadoPara"));
				Status status = Status.find(rs.getInt("cdstatus"));
				Cheque Cheque = new Cheque(rs.getInt("numero"), rs.getDouble("valor"), rs.getString("agencia"),
						rs.getString("conta"), rs.getDate("data").toLocalDate(), rs.getString("banco"), Titular,
						Recebidode, RepassadoPara, status);
				Cheque.setBomPara(rs.getDate("bomPara").toLocalDate());
				if(rs.getDate("repassadoEm")!= null){
				Cheque.setRepassadoEm(rs.getDate("repassadoEm").toLocalDate());
				}else{
					Cheque.setRepassadoEm(null);
				}
				Cheque.setRecebidoEm(rs.getDate("recebidoEm").toLocalDate());
				Cheque.setDescricao(rs.getString("descricao"));
				Cheque.set_id(rs.getInt("id"));
				ChequeList.add(Cheque);
			}
		} catch (SQLException e) {
			System.err.println("Erro ao listar os Cheque:" + e.getMessage());
		} finally {
			close();
		}
		return ChequeList;
	}

    public void update(Cheque c) {
    	open();
        String sql = "UPDATE Cheque SET numero=?, valor=?, data=?, bomPara=?, banco=?, agencia=?, conta=?, cdTitular=?, cdRecebidoDe=?, descricao=?, recebidoEm=?, repassadoEm=?, cdRepassadoPara=?, cdstatus=? WHERE id=?";
        try {
            PreparedStatement smt = conn.prepareStatement(sql);
            smt.setInt(1, c.getNumero());
			smt.setDouble(2, c.getValor());
			smt.setDate(3, Date.valueOf(c.getData()));
			smt.setDate(4, Date.valueOf(c.getBomPara()));
			smt.setString(5, c.getBanco());
			smt.setString(6, c.getAgencia());
			smt.setString(7, c.getConta());
			smt.setInt(8, c.getTitular().get_id());
			smt.setInt(9, c.getRecebidode().get_id());
			smt.setString(10, c.getDescricao());
			smt.setDate(11, Date.valueOf(c.getRecebidoEm()));
			smt.setDate(12, Date.valueOf(c.getRepassadoEm()));
			smt.setInt(13, c.getRepassadoPara().get_id());
			smt.setInt(14, c.getStatus().get_id());
			smt.setInt(15, c.get_id());
			smt.executeUpdate();
        }catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
    }

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
				Cheque c = new Cheque(rs.getInt(1), // id
						rs.getInt(2), // numero
						rs.getDouble(3), // valor
						rs.getDate(4).toLocalDate(), // DATA
						rs.getDate(5).toLocalDate(), // bomPARA
						rs.getString(6), // Banco
						rs.getString(7), // Agencia
						rs.getString(8), // Conta
						rs.getInt(9), // titular
						rs.getInt(10), // RecebidoDe
						rs.getString(11), // descricacao
						rs.getDate(12).toLocalDate(), // RecebidoEm
						rs.getDate(13).toLocalDate(), // RepassadoEm
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