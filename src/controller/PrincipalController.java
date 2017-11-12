package controller;

import java.net.URL;
import java.util.Optional;

import Model.Status;
import Model.mysql.ChequeMysqlDAO;
import Model.mysql.ClienteMysqlDAO;
import Model.mysql.MysqlBase;
import Model.mysql.StatusMysqlDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class PrincipalController {

	@FXML
	protected void initialize() {
		ClienteMysqlDAO clientedao = new ClienteMysqlDAO();
		StatusMysqlDAO statusdao = new StatusMysqlDAO();
		ChequeMysqlDAO dao = new ChequeMysqlDAO();
	}

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
    @FXML
    protected void relTotalClientes(ActionEvent e) throws JRException{
            URL url = getClass().getResource("/relatorios/relTotalClientes.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, MysqlBase.open());//null: caso não existam filtros
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);//false: não deixa fechar a aplicação principal
            jasperViewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
            jasperViewer.setVisible(true);

    }
    @FXML
    protected void relStatus(ActionEvent e ){
        Dialog<Status> dialog = new Dialog<>();
        dialog.setTitle("Relatorio por Status");
        dialog.setHeaderText("Escolha o Status para ter o relatorio");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setPrefHeight(250);
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        ObservableList<Status> options = FXCollections.observableArrayList(Status.all());
        ComboBox<Status> comboBox = new ComboBox<>(options);
        comboBox.setPrefHeight(40.0);
        comboBox.getSelectionModel().selectFirst();
        dialogPane.setContent(comboBox);
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new Status(comboBox.getValue().get_id());
            }
            return null;
        });
        Optional<Status> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((Status results) -> {
            System.out.println(
                results.get_id());
        });
    }
}
