package controller;

import java.util.Optional;

import Model.Cheque;
import Model.mysql.ChequeMysqlDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

public class ChequeController {

	@FXML
	protected ListView<Cheque> lvCheque;

	@FXML
	protected void initialize() {
		Main.addOnChangeScreenListener(new Main.OnChangeScreen() {
			@Override
			public void onScreenChanged(String newScreen, Object userData) {
				if (newScreen.equals("listCheque"))
					updateList();
			}
		});

		updateList();
	}

	@FXML
	protected void btDeleteAction(ActionEvent e) {
		ObservableList<Cheque> ol = lvCheque.getSelectionModel().getSelectedItems();

		if (!ol.isEmpty()) {
			Cheque c = ol.get(0);

			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Deseja realmente excluir o Cheque?");
			alert.setContentText(c.toString());

			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.OK) {
				c.delete();
			}
		} else {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Informação");
			alert.setHeaderText("Nenhum Cheque selecionado");
			alert.setContentText("selecione algum elemento da lista");
			alert.showAndWait();
		}

	}

	@FXML
	protected void btEditarAction(ActionEvent e) {

		ObservableList<Cheque> ol = lvCheque.getSelectionModel().getSelectedItems();

		if (!ol.isEmpty()) {
			Cheque c = ol.get(0);

			Main.changeScreen("dadosCheque", c);
		}

	}

	@FXML
	protected void btNovoAction(ActionEvent e) {
		Main.changeScreen("dadosCheque");
	}

	private void updateList() {
		lvCheque.getItems().clear();
		for (Cheque c : Cheque.all()) {
			lvCheque.getItems().add(c);
		}
	}
}
