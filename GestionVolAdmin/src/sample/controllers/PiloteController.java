package sample.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sample.models.Client;
import sample.models.Pilote;

public class PiloteController {
        @FXML
        private JFXTextField matriculeTextField;
        @FXML
        private JFXTextField nomTextField;
        @FXML
        private JFXTextField prenomTextField;
        @FXML
        private JFXTextField adresseTextField;
        @FXML
        private TableView tableView;

        private boolean isClear=true;



        public void initialize(){

            tableView.getItems().setAll(Pilote.getPilotes());

            tableView.getSelectionModel().selectedItemProperty().addListener((obs,oldS,newS)->{

                        Pilote pilote = (Pilote) tableView.getSelectionModel().getSelectedItem();
                        if(pilote!=null) {
                            matriculeTextField.setText(pilote.getMatricule().toString());
                            nomTextField.setText(pilote.getNomPilote());
                            prenomTextField.setText(pilote.getPrenomPilote());
                            adresseTextField.setText(pilote.getAdressePilote());
                            isClear = false;
                        }
                    }
            );

        }



        @FXML
        private void initialiserHandler(){
            matriculeTextField.clear();
            nomTextField.clear();
            prenomTextField.clear();
            adresseTextField.clear();
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

                Pilote pilote = new Pilote();

                if(checkFields()){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("veuillez saisir un pilote pour l'ajouter");
                    alert.showAndWait();
                }

                else{
                    pilote.setNomPilote(nomTextField.getText());
                    pilote.setPrenomPilote(prenomTextField.getText());
                    pilote.setAdressePilote(adresseTextField.getText());
                    Pilote.insererPilote(pilote);
                    tableView.getItems().setAll(Pilote.getPilotes());

                }
            }
        }

        @FXML
        private void modifierHandler(){
            if(isClear) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("veuillez sélectionner un pilote sur la table");
                alert.showAndWait();
            }
            else{

                Pilote pilote = new Pilote();
                if(checkFields()){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("veuillez saisir un pilote pour l'ajouter");
                    alert.showAndWait();
                }
                else{
                    pilote.setMatricule(Integer.parseInt(matriculeTextField.getText()));
                    pilote.setNomPilote(nomTextField.getText());
                    pilote.setPrenomPilote(prenomTextField.getText());
                    pilote.setAdressePilote(adresseTextField.getText());

                    Pilote.updatePilote(pilote);
                    tableView.getItems().setAll(Pilote.getPilotes());

                }
            }
        }

        @FXML
        private void supprimerHandler(){

            if(isClear) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("veuillez sélectionner un pilote sur la table");
                alert.showAndWait();
            }
            else{
                Pilote pilote = new Pilote();
                pilote.setMatricule(Integer.parseInt(matriculeTextField.getText()));
                Pilote.deletePilote(pilote);
                tableView.getItems().setAll(Pilote.getPilotes());
                initialiserHandler();
            }

        }



    }
