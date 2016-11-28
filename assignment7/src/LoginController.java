import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {
	@FXML
	private TextField username;
	
	@FXML
	private TextField password;
	
	@FXML
	private Label error;
	
	@FXML
	private Button loginButton;
		
		@Override
		public void initialize(URL fxmlFileLoction, ResourceBundle resources) {
			
			loginButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					error.setText("");
					//if have user but incorrect password
					if (hasUser(username.getText()) && !authenticate(username.getText(), password.getText())) {
						error.setText("Invalid password.");
						password.setText("");
					}
					//if don't have user
					else if (!hasUser(username.getText())) {
						ChatClient client = new ChatClient(username.getText(), password.getText());
						ChatServer.clients.add(client);
						ChatServer.numClients++;
						client.setGUIClient();
						client.gui.start(null);
						addUserToDB(username.getText(), password.getText());
						LoginClient.loginStage.close();
						
						//printlines for debugging
						System.out.println(ChatServer.numClients);
						System.out.println(ChatServer.clients.size());
					}
					//if have user and correct password
					else if (hasUser(username.getText()) && authenticate(username.getText(), password.getText())) {
						ChatClient client = new ChatClient(username.getText(), password.getText());
						client.setGUIClient();
						client.gui.start(null);
						//int id = ChatServer.usernameToID.get(username.getText());
						//ChatServer.clients.get(id).gui.start(null);
						System.out.println(ChatServer.numClients);
						LoginClient.loginStage.close();
					}
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
		
		private void addUserToDB(String username, String password) {
			FileWriter fw;
			try {
				fw = new FileWriter("userlogins.txt", true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);
				pw.println(username);
				pw.close();
				fw.close();
				
				fw = new FileWriter("usernamesToPasswords.txt", true);
				bw = new BufferedWriter(fw);
				pw = new PrintWriter(bw);
				pw.println(username + "//" + password);
				pw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
}