package controller;

import Model.Cliente;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;

import java.util.Optional;


public class ClienteController {

    @FXML
    protected ListView<Cliente> lvClientes;

    @FXML
    protected void initialize(){
        Main.addOnChangeScreenListener(new Main.OnChangeScreen() {
            @Override
            public void onScreenChanged(String newScreen, Object userData) {

            }
        });
    }

    @FXML
    protected void btDeleteAction(ActionEvent e){
        ObservableList<Cliente> ol = lvClientes.getSelectionModel().getSelectedItems();

        if(!ol.isEmpty()) {
            Cliente c = ol.get(0);

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Deseja realmente excluir o Cliente?");
            alert.setContentText(c.toString());

            Optional<ButtonType> result = alert.showAndWait();

            if(result.get() == ButtonType.OK ) {
                c.delete();
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

        ObservableList<Cliente> ol = lvClientes.getSelectionModel().getSelectedItems();

        if(!ol.isEmpty()) {
            Cliente c = ol.get(0);

            Main.changeScreen("dadosClientes",c);
        }

    }

    @FXML
    protected void btNovoAction(ActionEvent e) {
        Main.changeScreen("dadosClientes");
    }

}
