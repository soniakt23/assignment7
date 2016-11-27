import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {
	@FXML
	private TextField username;
	
	@FXML
	private TextField password;
	
	@FXML
	private Button loginButton;
		
		@Override
		public void initialize(URL fxmlFileLoction, ResourceBundle resources) {
			
			loginButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					new ChatClient(username.getText());
					ChatServer.numClients++;
				}
			});
		}
	
}
