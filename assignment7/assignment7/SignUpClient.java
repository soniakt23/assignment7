package assignment7;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SignUpClient extends Application {
	static Stage signUpStage;
	
	@Override
	public void start(Stage primaryStage) {
		signUpStage = new Stage();
		AnchorPane signUpPane;
		try {
			signUpPane = (AnchorPane) FXMLLoader.load(ChatClient.class.getResource("SignUpController.fxml"));
			Scene signUpScene = new Scene(signUpPane);
			signUpScene.getStylesheets().add("loginStyle.css");
			signUpPane.getStyleClass().add("pane");
			signUpStage.setScene(signUpScene);
				
			signUpStage.show();
		} catch (IOException e) {
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
