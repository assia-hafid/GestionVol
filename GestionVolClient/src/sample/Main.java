package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.models.Client;


public class Main extends Application {
    private static Stage stage;

    private static Client loggedClient;

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        Main.stage = stage;
    }

    public static Client getLoggedClient() {
        return loggedClient;
    }

    public static void setLoggedClient(Client loggedClient) {
        Main.loggedClient = loggedClient;
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("views/login.fxml"));
        stage = primaryStage;
        primaryStage.setTitle("Gestion Interface Utilisteur");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 680, 650));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
