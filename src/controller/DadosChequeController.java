package controller;

import java.util.List;

import Model.Cheque;
import Model.Cliente;
import Model.MaskTextField;
import Model.Status;
import Model.mysql.ClienteMysqlDAO;
import Model.mysql.StatusMysqlDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class DadosChequeController {
	private Cheque ChequeAtual;
	@FXML
	private DatePicker dtrecebidoem;

	@FXML
	private ComboBox<Cliente> cbrepassadopara;

	@FXML
	private TextField tfconta;

	@FXML
	private TextField tfbanco;

	@FXML
	private ComboBox<Cliente> cbrecebidode;

	@FXML
	private DatePicker dtData;
	@FXML
	private ComboBox<Cliente> cbtitular;

	@FXML
	private TextField tfagencia;

	@FXML
	private MaskTextField tfnumero;

	@FXML
	private DatePicker dtbomPara;

	@FXML
	private DatePicker dtrepassadoem;

	@FXML
	private ComboBox<Status> cbstatus;

	@FXML
	private MaskTextField tfvalor;

	@FXML
	private TextField tfdescricao;

	@FXML
	public void carregarClientesBox(ComboBox<Cliente> combo) {
		List<Cliente> listClientes;
		ObservableList<Cliente> observableListClientes;
		listClientes = ClienteMysqlDAO.all();
		observableListClientes = FXCollections.observableArrayList(listClientes);
		combo.setItems(observableListClientes);
	}

	@FXML
	public void carregarStatusBox(ComboBox<Status> comb) {
		List<Status> listStatus;
		ObservableList<Status> observableListStatus;
		listStatus = StatusMysqlDAO.all();
		observableListStatus = FXCollections.observableArrayList(listStatus);
		comb.setItems(observableListStatus);
	}

	@FXML
	protected void initialize() {
		carregarStatusBox(cbstatus);
		carregarClientesBox(cbtitular);
		carregarClientesBox(cbrecebidode);
		carregarClientesBox(cbrepassadopara);
		tfvalor.setMask("N!.N!");
		tfnumero.setMask("NNNNNNNN");
		Main.addOnChangeScreenListener(new Main.OnChangeScreen() {
			@Override
			public void onScreenChanged(String newScreen, Object userData) {
				if (newScreen.equals("chequecriar")) {
					if (userData != null) {
						ChequeAtual = (Cheque) userData;
						tfnumero.setText(String.valueOf((ChequeAtual.getNumero())));
						tfvalor.setText(String.valueOf(ChequeAtual.getValor()));
						tfconta.setText(ChequeAtual.getConta());
						tfbanco.setText(ChequeAtual.getBanco());
						tfagencia.setText(ChequeAtual.getAgencia());
						tfdescricao.setText(ChequeAtual.getDescricao());
						cbrepassadopara.setValue(ChequeAtual.getRepassadoPara());
						cbrecebidode.setValue(ChequeAtual.getRecebidode());
						cbtitular.setValue(ChequeAtual.getTitular());
						cbstatus.setValue(ChequeAtual.getStatus());
						dtrecebidoem.setValue(ChequeAtual.getRecebidoEm());
						dtData.setValue(ChequeAtual.getData());
						dtbomPara.setValue(ChequeAtual.getBomPara());
						dtrepassadoem.setValue(ChequeAtual.getRepassadoEm());

					} else {
						tfnumero.setText("");
						tfvalor.setText("");
						tfconta.setText("");
						tfbanco.setText("");
						tfagencia.setText("");
						tfdescricao.setText("");
						cbrepassadopara.setValue(null);
						cbrecebidode.setValue(null);
						cbtitular.setValue(null);
						cbstatus.setValue(null);
						dtrecebidoem.setValue(null);
						dtData.setValue(null);
						dtbomPara.setValue(null);
						dtrepassadoem.setValue(null);
					}
				}

			}
		});
	}

	@FXML
	protected void btCancelarAction(ActionEvent e) {
		Main.changeScreen("apresentarcheque");
	}

	@FXML
	protected void btOkAction(ActionEvent e) {

		try {
			if (tfnumero.getText().isEmpty())
				throw new RuntimeException("O atributo Numero não pode ser vazio");
			if (tfvalor.getText().isEmpty())
				throw new RuntimeException("O atributo Valor não pode ser vazio");
			if (tfconta.getText().isEmpty())
				throw new RuntimeException("O atributo Conta não pode ser vazio");
			if (tfbanco.getText().isEmpty())
				throw new RuntimeException("O atributo Banco não pode ser vazio");
			if (tfagencia.getText().isEmpty())
				throw new RuntimeException("O atributo Agencia não pode ser vazio");
			if (tfdescricao.getText().isEmpty())
				throw new RuntimeException("O atributo Descrição não pode ser vazio");
			if (cbrepassadopara.getSelectionModel().getSelectedItem() == null) {
				throw new RuntimeException("O atributo RepassadoPara não pode ser vazio");
			}
			if (cbrecebidode.getSelectionModel().getSelectedItem() == null) {
				throw new RuntimeException("O atributo Recebido de  não pode ser vazio");
			}
			if (cbtitular.getSelectionModel().getSelectedItem() == null) {
				throw new RuntimeException("O atributo Titular não pode ser vazio");
			}
			if (cbstatus.getSelectionModel().getSelectedItem() == null) {
				throw new RuntimeException("O atributo Status não pode ser vazio");
			}
			if (dtData.getValue() == null) {
				throw new RuntimeException("O atributo Data não pode ser vazio");
			}
			if (dtrecebidoem.getValue() == null) {
				throw new RuntimeException("O atributo Recebido Em não pode ser vazio");
			}
			if (dtbomPara.getValue() == null) {
				throw new RuntimeException("O atributo Bom Para Em não pode ser vazio");
			}
			if (dtrepassadoem.getValue() == null) {
				throw new RuntimeException("O atributo Repassado Em Em não pode ser vazio");
			}
			if (ChequeAtual != null) {
				ChequeAtual.setNumero(Integer.parseInt(tfnumero.getText()));
				ChequeAtual.setValor(Double.parseDouble(tfvalor.getText()));
				ChequeAtual.setConta(tfconta.getText());
				ChequeAtual.setBanco(tfbanco.getText());
				ChequeAtual.setAgencia(tfagencia.getText());
				ChequeAtual.setDescricao(tfdescricao.getText());
				ChequeAtual.setRecebidode((Cliente) cbrecebidode.getSelectionModel().getSelectedItem());
				ChequeAtual.setRepassadoPara((Cliente) cbrepassadopara.getSelectionModel().getSelectedItem());
				ChequeAtual.setTitular((Cliente) cbtitular.getSelectionModel().getSelectedItem());
				ChequeAtual.setStatus((Status) cbstatus.getSelectionModel().getSelectedItem());
				ChequeAtual.setRecebidoEm(dtrecebidoem.getValue());
				ChequeAtual.setData(dtData.getValue());
				ChequeAtual.setBomPara(dtbomPara.getValue());
				ChequeAtual.setRepassadoEm(dtrepassadoem.getValue());

				ChequeAtual.save();
			} else {
				Cheque c = new Cheque(Integer.parseInt(tfnumero.getText()), Double.parseDouble(tfvalor.getText()),
						dtData.getValue(), dtbomPara.getValue(), tfbanco.getText(), tfagencia.getText(),
						tfconta.getText(), cbtitular.getValue(), cbrecebidode.getValue(), tfdescricao.getText(),
						dtrecebidoem.getValue(), dtrepassadoem.getValue(), cbrepassadopara.getValue(),
						cbstatus.getValue());
				c.save();
			}
			Main.changeScreen("apresentarcheque", "dados tela");
		} catch (RuntimeException ex) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Erro");
			alert.setHeaderText("erro ao criar Cheque");
			alert.setContentText(ex.getMessage());
			alert.showAndWait();
		}
	}
}
