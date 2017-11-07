package controller;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import Model.Cheque;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;

public class ChequeController {

	    @FXML
	    private TableView<Cheque> tvCheque;

	    @FXML
	    private TableColumn<Cheque, LocalDate> colData;
	    @FXML
	    private TableColumn<Cheque, Integer> colTitular;
	    @FXML
	    private TableColumn<Cheque, Integer> colnumero;
	    @FXML
	    private TableColumn<Cheque, Double> colValor;
	    @FXML
	    private TableColumn<Cheque, Integer> colRecebidoDe;
	    @FXML
	    private TableColumn<Cheque, String> ColDescricao;




	    @FXML
	    protected void personalizaColuna(TableColumn t){
			DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			// Custom rendering of the table cell.
			t.setCellFactory(column -> {
				return new TableCell<Cheque, LocalDate>() {
					@Override
					protected void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (item == null || empty) {
							setText(null);
							setStyle("");
						} else {
							// Format date.
							setText(myDateFormatter.format(item));

							// Style all dates in March with a different color.
							if (item.getMonth() == Month.MARCH) {
								setTextFill(Color.CHOCOLATE);
								setStyle("-fx-background-color: yellow");
							} else {
								setTextFill(Color.BLACK);
								setStyle("");
							}
						}
					}
				};
			});
	    }
	@FXML
	protected void initialize() {
		personalizaColuna(colData);
		Main.addOnChangeScreenListener(new Main.OnChangeScreen() {
			@Override
			public void onScreenChanged(String newScreen, Object userData) {
					updateList();
			}
		});

	    colnumero.setCellValueFactory(new PropertyValueFactory<>("Numero"));
	    colRecebidoDe.setCellValueFactory(new PropertyValueFactory<>("Recebidode"));
	    colValor.setCellValueFactory(new PropertyValueFactory<>("Valor"));
	    ColDescricao.setCellValueFactory(new PropertyValueFactory<>("Descricao"));
	    colTitular.setCellValueFactory(new PropertyValueFactory<>("Titular"));
		colData.setCellValueFactory(new PropertyValueFactory<>("data"));

		updateList();
	}

    @FXML
    protected void btBackAction(ActionEvent e){
    	Main.changeScreen("principal");
    }

	@FXML
	protected void btDeletarCheque(ActionEvent e) {
		ObservableList<Cheque> ol = tvCheque.getSelectionModel().getSelectedItems();

		if (!ol.isEmpty()) {
			Cheque h = ol.get(0);

			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirmação");
			alert.setHeaderText("Deseja realmente excluir o Cheque?");
			alert.setContentText(h.toString());

			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.OK) {
				h.delete();
				updateList();
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
	protected void btEditarCheque(ActionEvent e) {

		ObservableList<Cheque> ol = tvCheque.getSelectionModel().getSelectedItems();

		if (!ol.isEmpty()) {
			Cheque h = ol.get(0);

			Main.changeScreen("chequecriar",h);
		}

	}

	@FXML
	protected void btNovoCheque(ActionEvent e) {
		Main.changeScreen("chequecriar");
	}

	private void updateList() {
		tvCheque.getItems().clear();
		for (Cheque h : Cheque.all()) {
			tvCheque.getItems().add(h);
		}
	}
}
