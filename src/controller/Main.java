package controller;

import java.util.ArrayList;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	private static Stage stage;

	private static Scene clienteScene;
	private static Scene principal;
	private static Scene listScene;
	private static Scene ApresentarCheque;
	private static Scene ChequeCriar;
	private static Scene StatusCriar;
	private static Scene listStatus;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stage = primaryStage;
		stage.setResizable(false);
		primaryStage.setTitle("Controle de Cheque");
		Image applicationIcon = new Image(getClass().getResourceAsStream("/imagens/Icon.png"));
		primaryStage.getIcons().add(applicationIcon);


		Parent FxmlPrincipal = FXMLLoader.load(getClass().getResource("/view/main.fxml"));
		principal = new Scene(FxmlPrincipal, 640, 400);

		Parent FxmlStatusd = FXMLLoader.load(getClass().getResource("/view/dados_Status.fxml"));
		StatusCriar = new Scene(FxmlStatusd, 640, 400);

		Parent FxmlStatus = FXMLLoader.load(getClass().getResource("/view/list_Status.fxml"));
		listStatus = new Scene(FxmlStatus, 640, 400);

		Parent FxmlListCheque = FXMLLoader.load(getClass().getResource("/view/list_cheque.fxml"));
		ApresentarCheque = new Scene(FxmlListCheque, 640, 400);

		Parent FxmlCheque = FXMLLoader.load(getClass().getResource("/view/dados_cheque.fxml"));
		ChequeCriar = new Scene(FxmlCheque, 640, 400);

		Parent FxmlCliente = FXMLLoader.load(getClass().getResource("/view/list_cliente.fxml"));
		listScene = new Scene(FxmlCliente, 640, 400);

		Parent FxmlDetails = FXMLLoader.load(getClass().getResource("/view/dados_clientes.fxml"));
		clienteScene = new Scene(FxmlDetails, 640, 400);

		primaryStage.setScene(principal);
		primaryStage.show();

	}

	public static void changeScreen(String scr, Object userData) {
		switch (scr) {
		case "listStatus":
			stage.setScene(listStatus);
			notifyAllListeners("listStatus", userData);
			break;
		case "dadosStatus":
			stage.setScene(StatusCriar);
			notifyAllListeners("dadosStatus", userData);
			break;
		case "principal":
			stage.setScene(principal);
			notifyAllListeners("principal", userData);
			break;
		case "listClientes":
			stage.setScene(listScene);
			notifyAllListeners("listClientes", userData);
			break;
		case "dadosClientes":
			stage.setScene(clienteScene);
			notifyAllListeners("dadosClientes", userData);
			break;
		case "chequecriar":
			stage.setScene(ChequeCriar);
			notifyAllListeners("chequecriar", userData);
			break;
		case "apresentarcheque":
			stage.setScene(ApresentarCheque);
			notifyAllListeners("apresentarcheque", userData);
		}
	}

	public static void changeScreen(String scr) {
		changeScreen(scr, null);
	}

	// ----
	private static ArrayList<OnChangeScreen> listeners = new ArrayList<>();

	public static interface OnChangeScreen {
		void onScreenChanged(String newScreen, Object userData);
	}

	public static void addOnChangeScreenListener(OnChangeScreen newListener) {
		listeners.add(newListener);

	}

	private static void notifyAllListeners(String newScreen, Object userData) {

		for (OnChangeScreen l : listeners)
			l.onScreenChanged(newScreen, userData);

	}
}
