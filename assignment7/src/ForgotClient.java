import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ForgotClient extends Application {
	static Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		stage = new Stage();
		AnchorPane pane;
		try {
			pane = (AnchorPane) FXMLLoader.load(ChatClient.class.getResource("ForgotController.fxml"));
			Scene scene = new Scene(pane);
			scene.getStylesheets().add("loginStyle.css");
			pane.getStyleClass().add("pane");
			stage.setScene(scene);
				
			stage.show();
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
