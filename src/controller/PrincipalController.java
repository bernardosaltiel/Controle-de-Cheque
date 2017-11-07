package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class PrincipalController {

    @FXML
    protected void CadClientes(ActionEvent e){
            Main.changeScreen("listClientes");
    }
    @FXML
    protected void CadStatus(ActionEvent e ){
    	Main.changeScreen("listStatus");
    }
    @FXML
    protected void cadCheque(ActionEvent e ){
    	Main.changeScreen("apresentarcheque");
    }
}
