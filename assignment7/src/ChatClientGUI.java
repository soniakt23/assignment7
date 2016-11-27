//package day23network;

import java.io.*;
import java.net.*;
import java.util.ResourceBundle;

import javax.swing.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.*;

public class ChatClientGUI extends Application {

		@Override
		public void start(Stage primaryStage) {
			Stage clientStage = new Stage();
			AnchorPane clientPane;
			try {
				clientPane = (AnchorPane) FXMLLoader.load(ChatClient.class.getResource("ChatClientController.fxml"));
				Scene clientScene = new Scene(clientPane);
				clientStage.setScene(clientScene);
					
				clientStage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public static void main(String[] args) {
			try {
				launch(args);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
