package sample.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;

public class Controller {


    @FXML
    private GridPane mainGridPane;

    @FXML
    private ImageView logo;

    public void initialize(){
        File file = new File("src/logo.png");
        Image image = new Image(file.toURI().toString());
        logo.setImage(image);
    }

   @FXML
   private void showVilleDialog(){
       Dialog<ButtonType> dialog = new Dialog<>();
       dialog.initOwner(mainGridPane.getScene().getWindow());
       dialog.setTitle("Gestion Villes");
       dialog.setResizable(true);
       dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
       FXMLLoader fxmlLoader = new FXMLLoader();
       fxmlLoader.setLocation(getClass().getResource("/sample/views/villeDialogWindow.fxml"));
       try {
           dialog.getDialogPane().setContent(fxmlLoader.load());
       }
       catch(Exception e){
           System.out.println("loading problem in dialog ....");
       }
       dialog.showAndWait();

   }

   @FXML
    private void showClientDialog(){
       Dialog<ButtonType> dialog = new Dialog<>();
       dialog.initOwner(mainGridPane.getScene().getWindow());
       dialog.setTitle("Gestion Clients");
       dialog.setResizable(true);
       dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
       FXMLLoader fxmlLoader = new FXMLLoader();
       fxmlLoader.setLocation(getClass().getResource("/sample/views/clientDialogWindow.fxml"));
       try {
           dialog.getDialogPane().setContent(fxmlLoader.load());
       }
       catch(Exception e){
           System.out.println("loading problem in dialog ....");
           e.printStackTrace();
       }
       dialog.showAndWait();
   }

    @FXML
    private void showPiloteDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainGridPane.getScene().getWindow());
        dialog.setTitle("Gestion Pilotes");
        dialog.setResizable(true);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/views/piloteDialogWindow.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }
        catch(Exception e){
            System.out.println("loading problem in dialog ....");
            e.printStackTrace();
        }
        dialog.showAndWait();
    }
    @FXML
    private void showVolDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainGridPane.getScene().getWindow());
        dialog.setTitle("Gestion Vols");
        dialog.setResizable(true);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/sample/views/volDialogWindow.fxml"));
        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }
        catch(Exception e){
            System.out.println("loading problem in dialog ....");
            e.printStackTrace();
        }
        dialog.showAndWait();
    }
}

