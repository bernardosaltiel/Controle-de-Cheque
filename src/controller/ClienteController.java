package controller;

import java.util.Optional;

import Model.Cliente;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class ClienteController {


    @FXML
    private TableColumn<Cliente, String> colNome;

    @FXML
    private TableColumn<Cliente, String> colTelefone;

    @FXML
    private TableView<Cliente> tvClientes;

    @FXML
    private TableColumn<Cliente, String> colEnde;

    @FXML
    protected void initialize(){
        Main.addOnChangeScreenListener(new Main.OnChangeScreen() {
            @Override
            public void onScreenChanged(String newScreen, Object userData) {
            	updateList();
            }
        });
        colNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
        colEnde.setCellValueFactory(new PropertyValueFactory<>("Endereco"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("Telefone"));
        updateList();
    }
    @FXML
    protected void btBackAction(ActionEvent e){
    	Main.changeScreen("principal");
    }
    @FXML
    protected void btDeleteAction(ActionEvent e){
        ObservableList<Cliente> ol = tvClientes.getSelectionModel().getSelectedItems();

        if(!ol.isEmpty()) {
            Cliente c = ol.get(0);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Deseja realmente excluir o Cliente?");
            alert.setContentText(c.toString());

            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK ) {
                c.delete();
                updateList();
            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Informação");
            alert.setHeaderText("Nenhum Cliente selecionado");
            alert.setContentText("selecione algum elemento da lista");
            alert.showAndWait();
        }

    }
    @FXML
    protected void btEditarAction(ActionEvent e){

        ObservableList<Cliente> ol = tvClientes.getSelectionModel().getSelectedItems();

        if(!ol.isEmpty()) {
            Cliente c = ol.get(0);

            Main.changeScreen("dadosClientes",c);
        }

    }

    @FXML
    protected void btNovoAction(ActionEvent e) {
        Main.changeScreen("dadosClientes");
    }

	private void updateList() {
		tvClientes.getItems().clear();
		for (Cliente c : Cliente.all()) {
			tvClientes.getItems().add(c);
		}
	}

}
