package assignment7;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginClient extends Application {
	static Stage loginStage;
	
	@Override
	public void start(Stage primaryStage) {
		loginStage = new Stage();
		AnchorPane loginPane;
		try {
			loginPane = (AnchorPane) FXMLLoader.load(ChatClient.class.getResource("LoginController.fxml"));
			Scene loginScene = new Scene(loginPane);
			loginScene.getStylesheets().add("loginStyle.css");
			loginPane.getStyleClass().add("pane");
			loginStage.setScene(loginScene);
				
			loginStage.show();
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