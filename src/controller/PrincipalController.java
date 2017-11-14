package controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
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
import javafx.scene.control.ChoiceDialog;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
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
            	PreparedStatement smt = conn.prepareStatement("SELECT * FROM Cheque t, Cliente rec, Cliente pas, Cliente tit, Status s WHERE t.cdTitular = rec.id AND t.cdRecebidoDe = pas.id AND t.cdRepassadoPara = tit.id AND t.cdstatus = s.id AND t.cdstatus = ?");
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
    protected void relData(ActionEvent e){

    }
}
