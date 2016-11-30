import java.io.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ChatClientGUI extends Application {
	Stage clientStage = new Stage();
	AnchorPane clientPane;
	ChatClientController controller;
	ChatClient client;

		@Override
		public void start(Stage primaryStage) {
			try {
				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ChatClient.fxml"));
				clientPane = (AnchorPane) fxmlLoader.load();
				Scene clientScene = new Scene(clientPane);
				clientScene.getStylesheets().add("clientStyle.css");
				clientStage.setScene(clientScene);
				clientStage.setTitle(ChatClient.username);
				
				controller = (ChatClientController) fxmlLoader.getController();
				controller.setClient(client);
				
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