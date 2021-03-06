package Model;

import java.util.List;

import Model.mysql.ClienteMysqlDAO;

public class Cliente {

	private Integer _id;

	private String nome;
	private String endereco;
	private String telefone;

	public Cliente(String nome, String endereco, String telefone) {
		super();
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
	}

	public Cliente(Integer _id, String nome, String endereco, String telefone) {
		this._id = _id;
		this.nome = nome;
		this.endereco = endereco;
		this.telefone = telefone;
	}

	public Integer get_id() {
		return _id;
	}

	public void set_id(Integer _id) {
		this._id = _id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Override
	public String toString() {
		return nome;
	}

	// ----------- DAO

	private static ClienteMysqlDAO dao = new ClienteMysqlDAO();

	public void save() {
		if (_id != null) {
			dao.find(_id);
			dao.update(this);
		} else {
			dao.create(this);
		}
	}

	public void delete() {
		if (dao.find(_id) != null)
			dao.delete(this);
	}

	public static List<Cliente> all() {
		return ClienteMysqlDAO.all();
	}

	public static Cliente find(int pk) {
		return dao.find(pk);
	}

}
