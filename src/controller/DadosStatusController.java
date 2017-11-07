package controller;

import Model.Status;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


public class DadosStatusController {
    private Status StatusAtual;

    @FXML
    private TextField tfNome;
    @FXML

    protected void initialize(){
        Main.addOnChangeScreenListener(new Main.OnChangeScreen() {
            @Override
            public void onScreenChanged(String newScreen, Object userData) {

                if(newScreen.equals("dadosStatus")) {

                    if(userData != null){
                    	StatusAtual = (Status) userData;
                        tfNome.setText(StatusAtual.getNome());
                    }else{
                        tfNome.setText("");
                    }
                }

            }
        });
    }
    @FXML
    protected void btCancelarAction(ActionEvent e) {
        Main.changeScreen("listStatus");
    }

    @FXML
    protected void btOkAction(ActionEvent e) {

        try {

            if(tfNome.getText().isEmpty())
                throw new RuntimeException("O atributo Nome não pode ser vazio");
            if(StatusAtual != null){
            	StatusAtual.setNome(tfNome.getText());

            	StatusAtual.save();
            }
            else {
                Status s = new Status(
                        tfNome.getText());

                s.save();
            }
            Main.changeScreen("listStatus", "dados tela");
        }catch (RuntimeException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("erro ao criar Status");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }
}
