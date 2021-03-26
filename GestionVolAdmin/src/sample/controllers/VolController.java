package sample.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.models.Client;
import sample.models.Pilote;
import sample.models.Ville;
import sample.models.Vol;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

public class VolController {

    @FXML
    private JFXTextField numTextField;
    @FXML
    private JFXDatePicker dateDTextField;
    @FXML
    private JFXDatePicker dateATextField;
    @FXML
    private JFXTextField heureDTextField;
    @FXML
    private JFXTextField heureATextField;
    @FXML
    private JFXComboBox<Pilote> piloteComboBox;
    @FXML
    private JFXComboBox<Ville> villeDComboBox;
    @FXML
    private JFXComboBox<Ville> villeAComboBox;
    @FXML
    private TableView tableView;

    private boolean isClear=true;


    public void initialize(){

        tableView.getItems().setAll(Vol.getVols());
        piloteComboBox.getItems().setAll(Pilote.getPilotes());
        villeDComboBox.getItems().setAll(Ville.getVilles());
        villeAComboBox.getItems().setAll(Ville.getVilles());

//        piloteComboBox.getSelectionModel().selectFirst();

        tableView.getSelectionModel().selectedItemProperty().addListener((obs,oldS,newS)->{

                    Vol vol = (Vol)tableView.getSelectionModel().getSelectedItem();
                    if(vol!=null) {
                        if(piloteComboBox.getValue() == null){
                            piloteComboBox.getSelectionModel().selectFirst();
                        }
                        if(villeDComboBox.getValue() == null){
                            villeDComboBox.getSelectionModel().selectFirst();
                        }
                        if(villeAComboBox.getValue() == null){
                            villeAComboBox.getSelectionModel().selectFirst();
                        }
                        numTextField.setText(vol.getNumVol().toString());
                        dateDTextField.setValue(vol.getDateDepart().toLocalDate());
                        dateATextField.setValue(vol.getDateArrivee().toLocalDate());
                        heureDTextField.setText(vol.getHeureDepart().toString());
                        heureATextField.setText(vol.getHeureArrivee().toString());
                        piloteComboBox.getSelectionModel().select(vol.getPilote());
                        villeDComboBox.getSelectionModel().select(vol.getVilleDepart());
                        villeAComboBox.getSelectionModel().select(vol.getVilleArrivee());
//                        piloteComboBox.setValue(vol.getPilote());
//                        villeAComboBox.setValue(vol.getVilleArrivee());
//                        villeDComboBox.setValue(vol.getVilleDepart());
                        isClear = false;
                    }
                }
        );
    }

    @FXML
    private void initialiserHandler(){
        numTextField.clear();
        dateDTextField.setValue(null);
        dateATextField.setValue(null);
        heureDTextField.clear();
        heureATextField.clear();
        piloteComboBox.valueProperty().set(null);
        villeDComboBox.valueProperty().set(null);
        villeAComboBox.valueProperty().set(null);
        isClear=true;

    }

    public boolean checkFields(){
        if(dateDTextField.getValue()==null){
            return true;
        }
        else if(dateATextField.getValue()==null){
            return true;
        }
        else if(heureDTextField.getText().equals("")){
            return true;
        }
        else if(heureATextField.getText().equals("")){
            return true;
        }
        else if(piloteComboBox.getSelectionModel().getSelectedItem()==null){
            return true;
        }
        else if(villeDComboBox.getSelectionModel().getSelectedItem()==null){
            return true;
        }
        else if(villeAComboBox.getSelectionModel().getSelectedItem()==null){
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

            Vol vol = new Vol();

            if(checkFields()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("veuillez saisir un vol pour l'ajouter");
                alert.showAndWait();
            }

            else{

                vol.setDateDepart(Date.valueOf(dateDTextField.getValue()));
                vol.setDateArrivee(Date.valueOf(dateATextField.getValue()));
                vol.setHeureDepart(Time.valueOf(heureDTextField.getText()));
                vol.setHeureArrivee(Time.valueOf(heureATextField.getText()));
                vol.setMatricule(piloteComboBox.getSelectionModel().getSelectedItem().getMatricule());
                vol.setCodeVilleDepart(villeDComboBox.getSelectionModel().getSelectedItem().getCodeVille());
                vol.setCodeVilleArrivee(villeAComboBox.getSelectionModel().getSelectedItem().getCodeVille());
                Vol.insererVol(vol);
                tableView.getItems().setAll(Vol.getVols());

            }
        }
    }

    @FXML
    private void modifierHandler(){
        if(isClear) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("veuillez sélectionner un vol sur la table");
            alert.showAndWait();
        }
        else{

            Vol vol=new Vol();
            if(checkFields()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("veuillez saisir un pilote pour l'ajouter");
                alert.showAndWait();
            }
            else{

                vol.setNumVol(Integer.parseInt(numTextField.getText()));
                vol.setDateDepart(Date.valueOf(dateDTextField.getValue()));
                vol.setDateArrivee(Date.valueOf(dateATextField.getValue()));
                vol.setHeureDepart(Time.valueOf(heureDTextField.getText()));
                vol.setHeureArrivee(Time.valueOf(heureATextField.getText()));
                vol.setMatricule(piloteComboBox.getSelectionModel().getSelectedItem().getMatricule());
                vol.setCodeVilleDepart(villeDComboBox.getSelectionModel().getSelectedItem().getCodeVille());
                vol.setCodeVilleArrivee(villeAComboBox.getSelectionModel().getSelectedItem().getCodeVille());
                Vol.updateVol(vol);
                tableView.getItems().setAll(Vol.getVols());

            }
        }
    }

    @FXML
    private void supprimerHandler(){

        if(isClear) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("veuillez sélectionner un vol sur la table");
            alert.showAndWait();
        }
        else{
            Vol vol = new Vol();
            vol.setNumVol(Integer.parseInt(numTextField.getText()));
            Vol.deleteVol(vol);
            tableView.getItems().setAll(Vol.getVols());
            initialiserHandler();
        }

    }



}
