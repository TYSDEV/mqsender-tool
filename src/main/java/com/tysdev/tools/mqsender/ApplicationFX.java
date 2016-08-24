package com.tysdev.tools.mqsender;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * @author Tyryshkin Alexander
 */
public class ApplicationFX extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }


    @Override
    public void start(Stage stage) {
        stage.setTitle("MQ Sender");

        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("frames/MainFrame.fxml"));
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        stage.setScene(new Scene(root));
        stage.show();
    }
}
