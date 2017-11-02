package controller;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private static Stage stage;

    private static Scene clienteScene;
    private static Scene listScene;
    private static Scene listChequeScene;
    private static Scene chequeScene;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        primaryStage.setTitle("Controle de Cheque BY:MPI INFORMATICA");

        Parent FxmlListCheque = FXMLLoader.load(getClass().getResource("../view/list_cheque.fxml"));
        listChequeScene = new Scene(FxmlListCheque, 640, 400);

        Parent FxmlCheque = FXMLLoader.load(getClass().getResource("../view/dados_cheque.fxml"));
        chequeScene = new Scene(FxmlCheque, 640, 400);

        Parent FxmlCliente = FXMLLoader.load(getClass().getResource("../view/list_cliente.fxml"));
        listScene = new Scene(FxmlCliente, 640, 400);

        Parent FxmlDetails = FXMLLoader.load(getClass().getResource("../view/dados_clientes.fxml"));
        clienteScene = new Scene(FxmlDetails, 640, 400);

        primaryStage.setScene(chequeScene);
        primaryStage.show();

    }

    public static void changeScreen(String scr, Object userData) {
        switch (scr) {
            case "listClientes":
                stage.setScene(listScene);
                notifyAllListeners("listClientes",userData);
                break;
            case "dadosClientes":
                stage.setScene(clienteScene);
                notifyAllListeners("dadosClientes",userData);
            case "dadosCheque":
                stage.setScene(chequeScene);
                notifyAllListeners("dadosCheque",userData);
            case "listCheque":
                stage.setScene(listChequeScene);
                notifyAllListeners("listCheque",userData);
        }
    }
    public static void changeScreen(String scr){
        changeScreen(scr,null);
    }


    //----
    private static ArrayList<OnChangeScreen> listeners = new ArrayList<>();

    public static interface OnChangeScreen{
        void onScreenChanged(String newScreen,Object userData);
    }
    public static void addOnChangeScreenListener(OnChangeScreen newListener){
        listeners.add(newListener);

    }
    private static void notifyAllListeners(String newScreen,Object userData){

        for (OnChangeScreen l : listeners)
        l.onScreenChanged(newScreen, userData);

    }
}

