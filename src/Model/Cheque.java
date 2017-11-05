package Model;

import java.time.LocalDate;
import java.util.List;

import Model.mysql.ChequeMysqlDAO;

public class Cheque {
    private Integer _id;
    private Integer Numero;
    private Double valor;
    private LocalDate data;
    private LocalDate bomPara;
    private String banco;
    private String agencia;
    private String conta;
    private Cliente titular;
    private Cliente recebidode;
    private String descricao;
    private LocalDate recebidoEm;
    private LocalDate repassadoEm;
    private Cliente repassadoPara;
    private Status status;

    public Cheque(){
    }


	public Cheque(Integer numero, Double valor, LocalDate data, LocalDate bomPara, String banco, String agencia, String conta,
			Cliente titular, Cliente recebidode, String descricao, LocalDate recebidoEm, LocalDate repassadoEm,
			Cliente repassadoPara, Status status) {
		Numero = numero;
		this.valor = valor;
		this.data = data;
		this.bomPara = bomPara;
		this.banco = banco;
		this.agencia = agencia;
		this.conta = conta;
		this.titular = titular;
		this.recebidode = recebidode;
		this.descricao = descricao;
		this.recebidoEm = recebidoEm;
		this.repassadoEm = repassadoEm;
		this.repassadoPara = repassadoPara;
		this.status = status;
	}




	public Cheque(Integer _id, Integer numero, Double valor, LocalDate data, LocalDate bomPara, String banco, String agencia,
			String conta, Cliente titular, Cliente recebidode, String descricao, LocalDate recebidoEm, LocalDate repassadoEm,
			Cliente repassadoPara, Status status) {
		this._id = _id;
		Numero = numero;
		this.valor = valor;
		this.data = data;
		this.bomPara = bomPara;
		this.banco = banco;
		this.agencia = agencia;
		this.conta = conta;
		this.titular = titular;
		this.recebidode = recebidode;
		this.descricao = descricao;
		this.recebidoEm = recebidoEm;
		this.repassadoEm = repassadoEm;
		this.repassadoPara = repassadoPara;
		this.status = status;
	}



    public Cheque(Integer numero,Double valor,String agencia,String conta, LocalDate data, String banco, Cliente titular, Cliente recebidode,Cliente repassadoPara,Status status) {
        this.Numero = numero;
        this.valor = valor;
        this.agencia = agencia;
        this.conta = conta;
        this.data = data;
        this.banco = banco;
        this.titular = titular;
        this.recebidode = recebidode;
        this.repassadoPara = repassadoPara;
        this.status = status;
    }


	public Cheque(int int1, int int2, double double1, LocalDate localDate, LocalDate localDate2, String string,
			String string2, String string3, int int3, int int4, String string4, LocalDate localDate3,
			LocalDate localDate4, int int5, int int6) {
		// TODO Auto-generated constructor stub
	}


	public Integer get_id() {
		return _id;
	}


	public void set_id(Integer _id) {
		this._id = _id;
	}


	public Integer getNumero() {
		return Numero;
	}


	public void setNumero(Integer numero) {
		Numero = numero;
	}


	public Double getValor() {
		return valor;
	}


	public void setValor(Double valor) {
		this.valor = valor;
	}


	public LocalDate getData() {
		return data;
	}


	public void setData(LocalDate data) {
		this.data = data;
	}


	public LocalDate getBomPara() {
		return bomPara;
	}


	public void setBomPara(LocalDate bomPara) {
		this.bomPara = bomPara;
	}


	public String getBanco() {
		return banco;
	}


	public void setBanco(String banco) {
		this.banco = banco;
	}


	public String getAgencia() {
		return agencia;
	}


	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}


	public String getConta() {
		return conta;
	}


	public void setConta(String conta) {
		this.conta = conta;
	}


	public Cliente getTitular() {
		return titular;
	}


	public void setTitular(Cliente titular) {
		this.titular = titular;
	}


	public Cliente getRecebidode() {
		return recebidode;
	}


	public void setRecebidode(Cliente recebidode) {
		this.recebidode = recebidode;
	}


	public String getDescricao() {
		return descricao;
	}


	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}


	public LocalDate getRecebidoEm() {
		return recebidoEm;
	}


	public void setRecebidoEm(LocalDate recebidoEm) {
		this.recebidoEm = recebidoEm;
	}


	public LocalDate getRepassadoEm() {
		return repassadoEm;
	}


	public void setRepassadoEm(LocalDate repassadoEm) {
		this.repassadoEm = repassadoEm;
	}


	public Cliente getRepassadoPara() {
		return repassadoPara;
	}


	public void setRepassadoPara(Cliente repassadoPara) {
		this.repassadoPara = repassadoPara;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	@Override
    public String toString() {
        return "Cheque Numero:"+Numero+" ("+banco+"\t"+titular+"\t"+recebidode+") ["+status+"]";
    }

    //----------- DAO
    private static ChequeMysqlDAO dao = new ChequeMysqlDAO();

    public void save(){
        if( _id!=null && dao.find(_id) != null )
            dao.update(this);
        else
            dao.create(this);
    }


    public void delete(){
        if( dao.find(_id)!= null)
            dao.delete(this);
    }

    public static List<Cheque> all(){
        return dao.all();
    }

    public static Cheque find(int pk){
        return dao.find(pk);
    }
}
