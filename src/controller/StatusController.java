package controller;

import java.util.Optional;

import Model.Status;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class StatusController {


    @FXML
    private TableColumn<Status, String> colNome;

    @FXML
    private TableView<Status> tvStatus;

    @FXML
    protected void initialize(){
        Main.addOnChangeScreenListener(new Main.OnChangeScreen() {
            @Override
            public void onScreenChanged(String newScreen, Object userData) {
            	updateList();
            }
        });
        colNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        updateList();
    }
    @FXML
    protected void btBackAction(ActionEvent e){
    	Main.changeScreen("principal");
    }

    @FXML
    protected void btEditarAction(ActionEvent e){

        ObservableList<Status> ol = tvStatus.getSelectionModel().getSelectedItems();

        if(!ol.isEmpty()) {
            Status s = ol.get(0);

            Main.changeScreen("dadosStatus",s);
        }

    }

    @FXML
    protected void btNovoAction(ActionEvent e) {
        Main.changeScreen("dadosStatus");
    }

    @FXML
    protected void btDeleteAction(ActionEvent e){
        ObservableList<Status> ol = tvStatus.getSelectionModel().getSelectedItems();

        if(!ol.isEmpty()) {
            Status s = ol.get(0);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Deseja realmente excluir o Status?");
            alert.setContentText(s.toString());

            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK ) {
                s.delete();
                updateList();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informação");
            alert.setHeaderText("Nenhum Status selecionado");
            alert.setContentText("selecione algum elemento da lista");
            alert.showAndWait();
        }

    }

	private void updateList() {
		tvStatus.getItems().clear();
		for (Status s : Status.all()) {
			tvStatus.getItems().add(s);
		}
	}

}
