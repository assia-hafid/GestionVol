module GestionVolClient {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;
    requires com.jfoenix;

    opens sample;
    opens sample.controllers;
    opens sample.views;
    opens sample.models;
}