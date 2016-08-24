package com.tysdev.tools.mqsender.frames;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * ABOUT DIALOG
 *
 * @author Tyryshkin Alexander
 */
public class AboutFrameController implements Initializable {
    @FXML
    private Button buttonOk;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    @FXML
    private void okButton_onClick(ActionEvent event) {
        ((Stage) buttonOk.getScene().getWindow()).close();
    }

}
