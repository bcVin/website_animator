package com.ultisoft.controller;

import com.ultisoft.Exporter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainWindowController {

    public static final String LINEAR_SCROLLING = "Linear";
    public static final String PAGEWISE_SCROLLING = "Seitenweise";

    @FXML
    Button buttonSaveAs;

    @FXML
    TextField textWebAdress;

    @FXML
    CheckBox checkboxRepeat;

    @FXML
    TextField textAnimationDuration;

    @FXML
    ComboBox<String> comboAnimationType;

    @FXML
    TextField textRepeatPause;

    @FXML
    public void initialize(){
        ObservableList<String> animationTypes = FXCollections.observableArrayList(LINEAR_SCROLLING, PAGEWISE_SCROLLING);

        comboAnimationType.getItems().addAll(animationTypes);
        comboAnimationType.getSelectionModel().select(0);
    }

    @FXML
    public void onSaveButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Animierte Webseite speichern");

        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Webseiten (*.html)", "*.html"));

        try {
            URL url = new URL(textWebAdress.getText());

            File file = fileChooser.showSaveDialog(buttonSaveAs.getScene().getWindow());

            if (file != null){
                String animationType = comboAnimationType.getSelectionModel().getSelectedItem();
                Exporter.exportTo(file, url, animationType, Double.parseDouble(textAnimationDuration.getText()), checkboxRepeat.isSelected(), Double.parseDouble(textRepeatPause.getText()));
                new Alert(Alert.AlertType.INFORMATION, "Website wurde erfolgreich gespeichert.").show();
            }

        } catch (MalformedURLException e) {
            new Alert(Alert.AlertType.ERROR, "Die angegebene Website-Adresse ist keine gültige URL.").show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Die angegebene URL konnte nicht erreicht werden.").show();
        } catch (NumberFormatException e) {
            new Alert(Alert.AlertType.ERROR, "Bitte für die Dauer eine gültige Zahl eingeben.").show();
        }
    }
}
