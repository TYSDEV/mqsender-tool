package com.tysdev.tools.mqsender.frames;

import com.tysdev.tools.mqsender.JmsProviderFactory;
import com.tysdev.tools.mqsender.jmsproviders.JmsProvider;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * @author Tyryshkin Alexander
 */
public class MainFrameController implements Initializable {
    @FXML
    private Label       lblStatus;
    @FXML
    private TextField   txtBrokerHost;
    @FXML
    private TextField   txtBrokerPort;
    @FXML
    private TextField   txtQueueManager;
    @FXML
    private TextField   txtChannel;
    @FXML
    private TextField   txtSendTimes;
    @FXML
    private TextField   txtQueue;
    @FXML
    private TextArea    txtMessage;
    @FXML
    private RadioButton radioActiveMq;
    @FXML
    private RadioButton radioWebSphereMq;
    @FXML
    private Pane        paneWebSphereMq;


    @FXML
    private void sendButton_onClick(ActionEvent event) {
        try {
            JmsProvider jmsProvider;
            String      message = txtMessage.getText();
            String      queue   = txtQueue.getText();

            if (queue == null || queue.trim().length() == 0) {
                throw new IllegalArgumentException("Queue is not specified!");
            }

            // Establish JMS connection:
            if (radioActiveMq.isSelected()) {
                jmsProvider = JmsProviderFactory.getJmsProvider(JmsProviderFactory.JMS_PROVIDER_ACTIVEMQ);
                jmsProvider.connect(txtBrokerHost.getText(), txtBrokerPort.getText(), null, null);
            }
            else {
                jmsProvider = JmsProviderFactory.getJmsProvider(JmsProviderFactory.JMS_PROVIDER_WEBSPHEREMQ);
                jmsProvider.connect(txtBrokerHost.getText(), txtBrokerPort.getText(), txtQueueManager.getText(), txtChannel.getText());
            }

            int repeat = getSendTimes();

            // Send messages:
            for (int i = 0; i < repeat; i++) {
                jmsProvider.sendTextMessage(queue, message);
            }
            lblStatus.setText("SENT!  " + (new Date()).toString());
        }
        catch (Exception error) {
            lblStatus.setText("ERROR! " + error.getMessage());
            error.printStackTrace();
        }
    }


    @FXML
    private void radioActiveMq_onClick(ActionEvent event) {
        radioWebSphereMq.setSelected(false);
        paneWebSphereMq.setVisible(false);
    }


    @FXML
    private void radioWebSphereMq_onClick(ActionEvent event) {
        radioActiveMq.setSelected(false);
        paneWebSphereMq.setVisible(true);
    }


    @FXML
    private void menuProgram_exit_onClick(ActionEvent event) {
        ((Stage) lblStatus.getScene().getWindow()).close();
    }


    @FXML
    private void menuProgram_about_onClick(ActionEvent event) {
        try {
            Parent root  = FXMLLoader.load(getClass().getResource("AboutFrame.fxml"));
            Stage  stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("About");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(lblStatus.getScene().getWindow());
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }


    /**
     * Parse send times:
     */
    private int getSendTimes() {
        int result;
        try {
            result = Integer.parseInt(txtSendTimes.getText());
            if (result <= 0) {
                result = 1;
            }
        }
        catch (NumberFormatException error) {
            result = 1;
        }
        txtSendTimes.setText(Integer.toString(result));
        return result;
    }
}