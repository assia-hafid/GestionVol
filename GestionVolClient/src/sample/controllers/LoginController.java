package sample.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Client;

import java.io.File;

public class LoginController {
    @FXML
    private JFXTextField userTextField;
    @FXML
    private JFXPasswordField passTextField;
    @FXML
    private JFXTextField nomTextField;
    @FXML
    private JFXTextField prenomTextField;
    @FXML
    private JFXTextArea adresseTextField;
    @FXML
    private JFXTextField usernameTextField;
    @FXML
    private JFXPasswordField passwordField;

    @FXML
    private ImageView logo;

    private boolean isClear=true;

    public void initialize(){
        File file = new File("src/logo.png");
        Image image = new Image(file.toURI().toString());
        logo.setImage(image);

    }

    @FXML
    private void connexionHandler(){
        Stage stage = Main.getStage();
        String nomUtilisateur = userTextField.getText();
        String motDePasse = passTextField.getText();
        Client client = Client.login(nomUtilisateur,motDePasse);
        if(client == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Connexion echouée");
            alert.setContentText("nom d'utilisateur,mot de passe incorrectes");
            alert.showAndWait();
        }
        else{
            Main.setLoggedClient(client);
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/sample/views/userWindow.fxml"));
                Parent scene = fxmlLoader.load();
                stage.setScene(new Scene(scene,800,800));
            }catch (Exception e){
                System.out.println("Error in loading user window");
                e.printStackTrace();
            }
        }

    }
    public boolean checkFields(){
        if(nomTextField.getText().equals(""))
            return true;
        if(prenomTextField.getText().equals(""))
            return true;
        if(adresseTextField.getText().equals(""))
            return true;
        if(usernameTextField.getText().equals(""))
            return true;
        if(passwordField.getText().equals(""))
            return true;
        return false;
    }

    @FXML
    private void inscriptionHandler(){
        if(!isClear) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("cliquer sur 'initialiser' pour ajouter");
            alert.showAndWait();
        }
        else{

            Client client=new Client();

            if(checkFields()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("veuillez inserer un client pour l'ajouter");
                alert.showAndWait();
            }

            else{
                client.setNomClient(nomTextField.getText());
                client.setPrenomClient(prenomTextField.getText());
                client.setAdresseClient(adresseTextField.getText());
                client.setUsername(usernameTextField.getText());
                client.setPassword(passwordField.getText());
                Client.insererClient(client);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Votre inscription est réussite");
                alert.showAndWait();
            }
        }
    }






}
