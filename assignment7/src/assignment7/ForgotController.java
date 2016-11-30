package assignment7;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
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

public class ForgotController implements Initializable {
	@FXML
	private TextField username;
	
	@FXML
	private PasswordField password;
	
	@FXML
	private PasswordField password2;
	
	@FXML
	private Label errorLabel;
	
	@FXML
	private Button resetButton;
	
	@FXML
	private DatePicker birthday;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		resetButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (username.getText().isEmpty()) {
					errorLabel.setText("Must enter a username.");
					username.setPromptText("Username");
				}
				else if (password.getText().isEmpty()) {
					errorLabel.setText("Must enter a password.");
					password.setPromptText("Password");
					password2.setPromptText("Retype password");
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
					password2.setPromptText("Retype password");
				}
				else if (validatedBirthday(birthday.getValue().toString())) {
					try {
						File uToPFile = new File("usernamesToPasswords.txt");
						List<String> uToP = new ArrayList<>(Files.readAllLines(uToPFile.toPath(), StandardCharsets.UTF_8));
						
						for (int i = 0; i < uToP.size(); i++) {
							if (uToP.get(i).startsWith(username.getText())) {
								uToP.set(i, username.getText() + "//" + password.getText());
								break;
							}
						}

						Files.write(uToPFile.toPath(), uToP, StandardCharsets.UTF_8);
						
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else {
					errorLabel.setText("Invalid birthday.");
				}
			}	
		});
	}
	
	private boolean validatedBirthday(String string) {
		String birthdayFromFile = null;
		FileReader fr;
		try {
			fr = new FileReader("usernamesToBirthdays.txt");
			Scanner sc = new Scanner(fr);
			while (sc.hasNext()) {
				String next = sc.nextLine();
				if (next.startsWith(username.getText())) {
					next = next.substring(username.getText().length() + 1, next.length());
					birthdayFromFile = next;
					break;
				}
			}
			
			if (birthdayFromFile.equals(string)) {
				return true;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return false;
	}

}
