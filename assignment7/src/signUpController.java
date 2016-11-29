import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class signUpController implements Initializable {
	
	@FXML
	private TextField username;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private PasswordField password2;
	
	@FXML
	private Label errorLabel;
	
	@FXML
	private Button signUpButton;
	
	@FXML
	private Button backButton;
	
	@FXML
	private DatePicker birthday;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		signUpButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (username.getText().isEmpty()) {
					errorLabel.setText("Must enter a username.");
					username.setPromptText("Username");
				}
				else if (password.getText().isEmpty()) {
					errorLabel.setText("Must enter a password.");
					password.setPromptText("Password");
					password.setPromptText("Retype password");
				}
				else if (password2.getText().isEmpty()) {
					errorLabel.setText("Must retype password.");
					password.setPromptText("Retype password");

				}
				else if (birthday.getValue() == null) {
					errorLabel.setText("Must enter birthday.");
				}
				else if (!password.getText().equals(password2.getText())) {
					errorLabel.setText("Passwords must match.");
					password.setPromptText("Password");
					password.setPromptText("Retype password");
				}
				else if (usernameTaken(username.getText())) {
					errorLabel.setText("Username already taken.");
					username.setPromptText("Username");
				}
				else {
					addUserToDB(username.getText(), password.getText(), birthday.getValue());
					SignUpClient.signUpStage.close();
				}
			}
		});		
	}
	
	private void addUserToDB(String username, String password, LocalDate localDate) {
		FileWriter fw;
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter("userlogins.txt", true)));
			pw.println(username);
			pw.close();
			
			pw = new PrintWriter(new BufferedWriter(new FileWriter("usernamesToPasswords.txt", true)));
			pw.println(username + "//" + password);
			pw.close();
			
			pw = new PrintWriter(new BufferedWriter(new FileWriter("usernamesToBirthdays.txt", true)));
			pw.println(username + " " + localDate.toString());
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean usernameTaken(String username) {
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
			e.printStackTrace();
		}
		return false;
	}
	
}
