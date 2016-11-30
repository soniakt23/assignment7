import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {
	@FXML
	private TextField username;
	
	@FXML
	private TextField ipTextField;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private Label error;
	
	@FXML
	private Button loginButton;
	
	@FXML
	private Button signUpButton;
	
	@FXML
	private Button forgotButton;
		
		@Override
		public void initialize(URL fxmlFileLoction, ResourceBundle resources) {
			
			loginButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					error.setText("");
					//if have user but incorrect password
					if (hasUser(username.getText()) && !authenticate(username.getText(), password.getText())) {
						//Platform.runLater(() -> { 
						error.setText("Invalid password.");
						password.setText("");
						//});
					}
					//if no host ip
					else if (ipTextField.getText().isEmpty()) {
						error.setText("Must set host IP address.");
					}
					//if don't have user
					else if (!hasUser(username.getText())) {
						error.setText("Invalid username.");
						//printlines for debugging
//						System.out.println(ChatServer.numClients);
	//					System.out.println(ChatServer.clients.size());
					}
					//if have user and correct password
					else if (hasUser(username.getText()) && authenticate(username.getText(), password.getText())) {
						ChatClient client = new ChatClient(username.getText(), password.getText(), ipTextField.getText());
						client.setGUIClient();
						client.gui.start(null);
						//int id = ChatServer.usernameToID.get(username.getText());
						//ChatServer.clients.get(id).gui.start(null);
						System.out.println(ChatServer.numClients);
						LoginClient.loginStage.close();
					}
				}
			});
			
			signUpButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					SignUpClient sClient = new SignUpClient();
					sClient.start(null);
					
				}
			});
			
			forgotButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					ForgotClient fClient = new ForgotClient();
					fClient.start(null);
					
				}
			});
		}
		
		private boolean hasUser(String username) {
			FileReader fr;
			try {
				fr = new FileReader("userlogins.txt");
				Scanner sc = new Scanner(fr);
				while (sc.hasNext()) {
					if (sc.next().equals(username)) {
						return true;
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return false;
		}
		
		private boolean authenticate(String username, String password) {
			FileReader fr;
			try {
				fr = new FileReader("usernamesToPasswords.txt");
				Scanner sc = new Scanner(fr);
				while (sc.hasNext()) {
					if (sc.next().equals(username + "//" + password)) {
						return true;
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return false;
		}
	
}