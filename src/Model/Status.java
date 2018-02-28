package Model;

import java.util.List;

import Model.mysql.StatusMysqlDAO;

public class Status {

    private Integer _id;
    private String nome;

    public Status(){
    }

    public Status(Integer _id) {
		this._id = _id;
	}

	public Status(String nome) {
		super();
		this.nome = nome;
	}

	public Status(Integer _id, String nome) {
		this._id = _id;
		this.nome = nome;
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

	@Override
    public String toString() {
        return nome;
    }
    //----------- DAO

    private static StatusMysqlDAO dao = new StatusMysqlDAO();

	public void save() {
		if (_id != null) {
			dao.find(_id);
			dao.update(this);
		} else {
			dao.create(this);
		}
	}

    public void delete(){
        if( dao.find(_id)!= null)
            dao.delete(this);
    }

    public static List<Status> all(){
        return StatusMysqlDAO.all();
    }

    public static Status find(int pk){
        return dao.find(pk);
    }
}
