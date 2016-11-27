import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginClient extends Application {
	@Override
	public void start(Stage primaryStage) {
		Stage loginStage = new Stage();
		AnchorPane loginPane;
		try {
			loginPane = (AnchorPane) FXMLLoader.load(ChatClient.class.getResource("LoginController.fxml"));
			Scene loginScene = new Scene(loginPane);
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
