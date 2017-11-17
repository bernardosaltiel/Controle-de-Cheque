package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Optional;

import Model.Cheque;
import Model.Status;
import Model.mysql.ChequeMysqlDAO;
import Model.mysql.ClienteMysqlDAO;
import Model.mysql.MysqlBase;
import Model.mysql.StatusMysqlDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class PrincipalController {
	private Stage stage;
	private Parent root;
    @FXML
    private DatePicker dateFinal;

    @FXML
    private DatePicker dateInicio;
    @FXML
    private Button btn2;
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
    protected void relTotalCheque(ActionEvent e) throws JRException{
    		URL url = getClass().getResource("/Relatorios/relTotalCheque.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, MysqlBase.open());//null: caso não existam filtros
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);//false: não deixa fechar a aplicação principal
            jasperViewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
            jasperViewer.setVisible(true);
    }
    @FXML
    protected void relTotalClientes(ActionEvent e) throws JRException{
            URL url = getClass().getResource("/Relatorios/relTotalClientes.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, MysqlBase.open());//null: caso não existam filtros
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);//false: não deixa fechar a aplicação principal
            jasperViewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
            jasperViewer.setVisible(true);
    }
    @FXML
    protected void relStatus(ActionEvent e ) throws JRException{
    	ObservableList<Status> options = FXCollections.observableArrayList(Status.all());
    	ChoiceDialog<Status> dialog = new ChoiceDialog<Status>(null, options);
    	dialog.setTitle("Relatorio por Status");
        dialog.setHeaderText("Escolha o Status para ter o relatorio");
        dialog.setContentText("Status:");
        dialog.dialogPaneProperty();
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {

            	try{
            	Connection conn = MysqlBase.open();
            	PreparedStatement smt = conn.prepareStatement("SELECT * FROM Cheque t LEFT JOIN Cliente rec ON t.cdRecebidoDe = rec.id LEFT JOIN Cliente pas ON t.cdTitular = pas.id LEFT JOIN Cliente tit ON t.cdRepassadoPara = tit.id LEFT JOIN Status s ON t.cdstatus = s.id WHERE t.cdstatus = ?");
            	smt.setInt(1,dialog.getSelectedItem().get_id());
            	smt.executeQuery();
        		JRResultSetDataSource jrs= new JRResultSetDataSource(smt.getResultSet());
                URL url = getClass().getResource("/Relatorios/relTotalCheque.jasper");
                JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), jrs);//null: caso não existam filtros
                JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);//false: não deixa fechar a aplicação principal
                jasperViewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
                jasperViewer.setVisible(true);
            	}catch(SQLException e2){
            		e2.printStackTrace();
            	} catch (JRException e1) {
					e1.printStackTrace();
				}
            }
            return null;
        });
        Optional<Status> optionalResult = dialog.showAndWait();
    }
    @FXML
    protected void relData(ActionEvent e) throws IOException{
    	   stage = new Stage();
    	   stage.getIcons().add(new Image("file:C:/imagens/Icon.png"));
    	   root = FXMLLoader.load(getClass().getResource("/view/relData.fxml"));
    	   stage.setScene(new Scene(root));
    	   stage.setTitle("Relatorio por Data");
    	   stage.initModality(Modality.APPLICATION_MODAL);
    	   stage.showAndWait();
    }
    @FXML
    protected void buttonCancelAction(ActionEvent e){
        stage=(Stage)btn2.getScene().getWindow();
        stage.close();
    }
    @FXML
    protected void buttonOkAction(ActionEvent e){
    	try{
    		if (dateInicio.getValue() == null) {
				throw new RuntimeException("O atributo Data Inicio não pode ser vazio");
			}
    		if (dateFinal.getValue() == null) {
				throw new RuntimeException("O atributo Data Final não pode ser vazio");
			}
        	Connection conn = MysqlBase.open();
        	PreparedStatement smt = conn.prepareStatement("SELECT * FROM Cheque t LEFT JOIN Cliente rec ON t.cdRecebidoDe = rec.id LEFT JOIN Cliente pas ON t.cdTitular = pas.id LEFT JOIN Cliente tit ON t.cdRepassadoPara = tit.id LEFT JOIN Status s ON t.cdstatus = s.id WHERE t.data BETWEEN ? AND ?");
        	smt.setDate(1,Date.valueOf(dateInicio.getValue()));
        	smt.setDate(2,Date.valueOf(dateFinal.getValue()));
        	smt.executeQuery();
    		JRResultSetDataSource jrs= new JRResultSetDataSource(smt.getResultSet());
            URL url = getClass().getResource("/Relatorios/relTotalCheque.jasper");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), jrs);//null: caso não existam filtros
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);//false: não deixa fechar a aplicação principal
            jasperViewer.setExtendedState(JasperViewer.MAXIMIZED_BOTH);
            jasperViewer.setVisible(true);
            stage=(Stage)btn2.getScene().getWindow();
            stage.close();
        	}catch(RuntimeException e3){
    			Alert alert = new Alert(Alert.AlertType.ERROR);
    			alert.setTitle("Erro");
    			alert.setHeaderText("erro ao Gerar Relatorio");
    			alert.setContentText(e3.getMessage());
    			alert.showAndWait();
        	}catch(SQLException e2){
        		e2.printStackTrace();
        	} catch (JRException e1) {
				e1.printStackTrace();
			}
    }
}
