package sample.controllers;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Main;
import sample.models.Client;
import sample.models.Reservation;
import sample.models.Vol;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class UserController {
    @FXML
    private JFXTextField nomTextField;
    @FXML
    private JFXTextField prenomTextField;
    @FXML
    private JFXTextArea adresseTextField;
    @FXML
    private JFXTextField usernameTextField;
    @FXML
    private JFXTextField passwordField;
    @FXML
    private JFXTextField cinTextField;
    @FXML
    private Label helloLabel;
    @FXML
    private TableView<Vol> volTableView;
    @FXML
    private Button logoutButton;

    @FXML
    private TableView<Reservation> reservationTableView;
    private Client user;

    public Client getUser() {
        return user;
    }

    public void setUser(Client user) {
        this.user = user;
    }


    public void initialize(){

        //setting the logged client
        user = Main.getLoggedClient();

        helloLabel.setText("Bonjour M."+user.getPrenomClient()+" "+user.getNomClient());
        //init user infos
        cinTextField.setText(user.getCinClient().toString());
        usernameTextField.setText(user.getUsername());
        passwordField.setText(user.getPassword());
        nomTextField.setText(user.getNomClient());
        prenomTextField.setText(user.getPrenomClient());
        adresseTextField.setText(user.getAdresseClient());

        loadDispoVols();

        reservationTableView.getItems().setAll(Reservation.getClientReservations(user.getCinClient()));


        ContextMenu contextMenu = new ContextMenu();
        MenuItem reserver = new MenuItem("Réserver");

        //faire une reservation
        reserver.setOnAction((event -> {
            Reservation reservation = new Reservation();
            reservation.setCinClient(user.getCinClient());
            reservation.setDateReservation(Date.valueOf(LocalDate.now()));
            Vol selectedVol = volTableView.getSelectionModel().getSelectedItem();
            reservation.setNumVol(selectedVol.getNumVol());
            Reservation.insererReservation(reservation);
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            reservationTableView.getItems().setAll(Reservation.getClientReservations(user.getCinClient()));

            volTableView.getItems().remove(selectedVol);
            confirmation.setHeaderText("Votre réservation est réussite !");
            confirmation.getButtonTypes().setAll(ButtonType.CLOSE);
            confirmation.showAndWait();
        }));

        contextMenu.getItems().setAll(reserver);
        volTableView.setContextMenu(contextMenu);




    }

    @FXML
    private void actualiser(){
        loadDispoVols();
    }

    @FXML
    private void modifierHandler(){
        if(checkFields()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Des champs sont null");
            alert.setContentText("Veuillez remplir tous le champs");
            alert.getButtonTypes().setAll(ButtonType.CLOSE);
            alert.showAndWait();
        }
        else{
            user.setPrenomClient(prenomTextField.getText());
            user.setNomClient(nomTextField.getText());
            user.setAdresseClient(adresseTextField.getText());
            Client.updateClient(user);
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setHeaderText("Vos informations sont mis à jours");
            confirmation.getButtonTypes().setAll(ButtonType.CLOSE);
            confirmation.showAndWait();
        }
    }

    public boolean checkFields(){
        if(nomTextField.getText().equals(""))
            return true;
        if(prenomTextField.getText().equals(""))
            return true;
        if(adresseTextField.getText().equals(""))
            return true;

        return false;
    }

    @FXML
    private void logout(){
        Stage stage = Main.getStage();
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/sample/views/login.fxml"));
            Parent scene = fxmlLoader.load();
            stage.setScene(new Scene(scene,680, 650));
            Main.setLoggedClient(null);
        }catch (Exception e){
            System.out.println("Error in loading user window");
            e.printStackTrace();
        }
    }

    public void loadDispoVols(){
        List<Vol> clientVols = Reservation.getClientReservations(user.getCinClient())
                .stream()
                .map(reservation -> reservation.getVol())
                .collect(Collectors.toList());

        List<Vol> volsDiposnible = Vol.getVols().stream().
                filter(vol -> !clientVols.contains(vol)).
                collect(Collectors.toList());

        volTableView.getItems().setAll(volsDiposnible);
        volTableView.getSelectionModel().selectFirst();
    }







}
