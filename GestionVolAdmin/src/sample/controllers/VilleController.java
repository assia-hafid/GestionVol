package sample.controllers;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import sample.models.Ville;

public class VilleController {
    @FXML
    private TableView tableView;
    @FXML
    private JFXTextField codeTextField;
    @FXML
    private JFXTextField nomTextField;


    private boolean isClear=true;


    public void initialize(){

        tableView.getItems().setAll(Ville.getVilles());

        tableView.getSelectionModel().selectedItemProperty().addListener((obs,oldS,newS)->{

          Ville ville = (Ville)tableView.getSelectionModel().getSelectedItem();
          if(ville!=null) {
              codeTextField.setText(ville.getCodeVille().toString());
              nomTextField.setText(ville.getNomVille());
              isClear = false;
          }
        }
        );

    }

    @FXML
    private void initialiserHandler(){

         codeTextField.clear();
         nomTextField.clear();
         isClear=true;

    }

    @FXML

    private void ajouterHandler(){
        if(!isClear) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("cliquer sur 'initialiser' pour ajouter");
            alert.showAndWait();
        }
        else{

            Ville ville = new Ville();
            if(nomTextField.getText().equals("")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("veuillez inserer un ville pour l'ajouter");
                alert.showAndWait();
            }
            else{
                ville.setNomVille(nomTextField.getText());
                Ville.insertVille(ville);
                tableView.getItems().setAll(Ville.getVilles());
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
           Ville ville = new Ville();
           if(nomTextField.getText().equals("")){
               Alert alert = new Alert(Alert.AlertType.INFORMATION);
               alert.setContentText("veuillez saisir une ville pour l'ajouter");
               alert.showAndWait();
           }
           else{
               ville.setNomVille(nomTextField.getText());
               ville.setCodeVille(Integer.parseInt(codeTextField.getText()));
               Ville.updateVille(ville);
               tableView.getItems().setAll(Ville.getVilles());
           }
       }
   }

   @FXML
    public void supprimerHandler(){
       if(isClear) {
           Alert alert = new Alert(Alert.AlertType.INFORMATION);
           alert.setContentText("veuillez sélectionner une ville sur la table");
           alert.showAndWait();
       }
       else{
           Ville ville = new Ville();
           ville.setCodeVille(Integer.parseInt(codeTextField.getText()));
           Ville.deleteVille(ville);
           tableView.getItems().setAll(Ville.getVilles());
           initialiserHandler();
       }


   }





}
