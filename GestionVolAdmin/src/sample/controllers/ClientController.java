package sample.controllers;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.w3c.dom.Text;
import sample.models.Client;
import sample.models.Ville;

public class ClientController {
    @FXML
    private JFXTextField cinTextField;
    @FXML
    private JFXTextField nomTextField;
    @FXML
    private JFXTextField prenomTextField;
    @FXML
    private JFXTextArea adresseTextField;
    @FXML
    private JFXTextField usernameTextField;
    @FXML
    private JFXTextField passwordTextField;
    @FXML
    private TableView tableView;

    private boolean isClear=true;



    public void initialize(){

        tableView.getItems().setAll(Client.getClients());

        tableView.getSelectionModel().selectedItemProperty().addListener((obs,oldS,newS)->{

                    Client client = (Client)tableView.getSelectionModel().getSelectedItem();
                    if(client!=null) {
                        cinTextField.setText(client.getCinClient().toString());
                        nomTextField.setText(client.getNomClient());
                        prenomTextField.setText(client.getPrenomClient());
                        adresseTextField.setText(client.getAdresseClient());
                        usernameTextField.setText(client.getUsername());
                        passwordTextField.setText(client.getPassword());
                        isClear = false;
                    }
                }
        );

    }



    @FXML
    private void initialiserHandler(){
        cinTextField.clear();
        nomTextField.clear();
        prenomTextField.clear();
        adresseTextField.clear();
        usernameTextField.clear();
        passwordTextField.clear();
        isClear=true;

    }

    public boolean checkFields(){

        if(nomTextField.getText().equals("")){
            return true;
        }
        else if(prenomTextField.getText().equals("")){
            return true;
        }
        else if(adresseTextField.getText().equals("")){
            return true;
        }
        else if(usernameTextField.getText().equals("")){
            return true;
        }
        else if (passwordTextField.getText().equals("")){
            return true;
        }
        else
            return false;
    }


    @FXML
    private void ajouterHandler(){
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
                client.setPassword(passwordTextField.getText());
                Client.insererClient(client);
                tableView.getItems().setAll(Client.getClients());

            }
        }
    }

    @FXML
    private void modifierHandler(){
        if(isClear) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("veuillez sélectionner une ville sur la table");
            alert.showAndWait();
        }
        else{

            Client client=new Client();
            if(checkFields()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("veuillez saisir un client pour l'ajouter");
                alert.showAndWait();
            }
            else{
                client.setCinClient(Integer.parseInt(cinTextField.getText()));
                client.setNomClient(nomTextField.getText());
                client.setPrenomClient(prenomTextField.getText());
                client.setAdresseClient(adresseTextField.getText());
                client.setUsername(usernameTextField.getText());
                client.setPassword(passwordTextField.getText());
                Client.updateClient(client);
                tableView.getItems().setAll(Client.getClients());

            }
        }


    }

    @FXML
    private void supprimerHandler(){

        if(isClear) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("veuillez sélectionner un client sur la table");
            alert.showAndWait();
        }
        else{
            Client client=new Client();
            client.setCinClient(Integer.parseInt(cinTextField.getText()));
            Client.deleteClient(client);
            tableView.getItems().setAll(Client.getClients());
            initialiserHandler();
        }

    }








}
