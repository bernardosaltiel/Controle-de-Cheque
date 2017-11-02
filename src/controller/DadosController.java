package controller;

import Model.Cliente;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


public class DadosController {
    private Cliente clienteAtual;

    @FXML
    private TextField tfNome;
    @FXML

    private TextField tfEndereco;
    @FXML

    private TextField tfTelefone;
    @FXML

    protected void initialize(){
        Main.addOnChangeScreenListener(new Main.OnChangeScreen() {
            @Override
            public void onScreenChanged(String newScreen, Object userData) {

                if(newScreen.equals("dadosClientes")) {

                    if(userData != null){
                        clienteAtual = (Cliente) userData;
                        tfNome.setText(clienteAtual.getNome());
                        tfEndereco.setText(clienteAtual.getEndereco());
                        tfTelefone.setText(clienteAtual.getTelefone());
                    }else{
                        tfNome.setText("");
                        tfEndereco.setText("");
                        tfTelefone.setText("");
                    }
                }

            }
        });
    }
    @FXML
    protected void btCancelarAction(ActionEvent e) {
        Main.changeScreen("listClientes");
    }

    @FXML
    protected void btOkAction(ActionEvent e) {

        try {

            if(tfNome.getText().isEmpty())
                throw new RuntimeException("O atributo Nome não pode ser vazio");
            if(tfEndereco.getText().isEmpty())
                throw new RuntimeException("O atributo Endereco não pode ser vazio");
            if(clienteAtual != null){
                clienteAtual.setNome(tfNome.getText());
                clienteAtual.setEndereco(tfEndereco.getText());
                clienteAtual.setTelefone(tfTelefone.getText());

                clienteAtual.save();
            }
            else {
                Cliente c = new Cliente(
                        tfNome.getText(),
                        tfEndereco.getText(),
                        tfTelefone.getText());

                c.save();
            }
            Main.changeScreen("listClientes", "dados tela");
        }catch (RuntimeException ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("erro ao criar Cliente");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }
}
