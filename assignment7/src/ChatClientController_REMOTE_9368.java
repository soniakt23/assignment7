import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

public class ChatClientController implements Initializable {

	String[] friends ={"friends"};
	ArrayList <ChatRoom> chatRooms= new ArrayList <ChatRoom>();
	private String selectedChatRoom;
	private static BufferedReader reader;
	private static PrintWriter writer;
	private static InputStreamReader streamReader;
	private String message;
	private ChatClient client;

	@FXML
    private TextArea incoming;

    @FXML
    private TextField outgoing;

    @FXML
    private Button sendButton;

    @FXML
    private  ComboBox<String> roomUsers;

    @FXML
    private Button createRoomButton;

    @FXML
    private TitledPane usersPane;

    @FXML
    private TabPane tabs;
	
	


	public void run() {
			try {
//			initView();
//			setUpNetworking();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		@Override
		public void initialize(URL fxmlFileLoction, ResourceBundle resources) {
			incoming.setEditable(false);
			selectedChatRoom = ChatClient.username;
			//ADD username when the new client is added
			
			try {
				setUpNetworking();
				writer.println("NEW USER");
				writer.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			sendButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					for (ChatRoom cr : chatRooms){
						if(tabs.getSelectionModel().getSelectedItem().getContent().equals(cr.txtArea)){
							//send to everyone in chatroom
							writer.println(cr.people + ":" + ChatClient.username + "- " + outgoing.getText());
							writer.flush();
							
							//send to chat history
							ChatHistory.setChatHistory(cr.people, outgoing.getText());
							
							//reset outgoing textbox
							outgoing.setText("");
							outgoing.requestFocus();
						}
					}
					//get selected tab pane
					//iterate through chat rooms
					//find the arraylist of people usernames to send to
					//write to those names
					
				}
			});
			
			 createRoomButton.setOnAction(new EventHandler<ActionEvent>() {
			      @Override
			      public void handle(ActionEvent event) {
			        writer.println("NEW ROOM" + " " + selectedChatRoom);
			        writer.flush();
			        selectedChatRoom="";
			        
			        //write to server new chat room with selected people
			      }
			    });
			 
			 roomUsers.setOnAction(new EventHandler <ActionEvent>() {
				 @Override
			      public void handle(ActionEvent event) {
					 selectedChatRoom = (selectedChatRoom + " " + roomUsers.getSelectionModel().getSelectedItem());
				 }
			    });
		}

		private void setUpNetworking() throws Exception {
			@SuppressWarnings("resource")
			//Socket sock = new Socket("128.62.23.11", 4242);
			Socket sock = new Socket("127.0.0.1", 4242);
			streamReader = new InputStreamReader(sock.getInputStream());
			reader = new BufferedReader(streamReader);
			writer = new PrintWriter(sock.getOutputStream());
			System.out.println("networking established");
			Thread readerThread = new Thread(new IncomingReader());
			readerThread.start();
		}

		void setClient(ChatClient client2) {
			this.client = client2;
		}

		
		class IncomingReader implements Runnable {
			public void run() {
				try {
	
					while ((message = (new BufferedReader(streamReader)).readLine()) != null) {
						if(message.startsWith("NEW USERNAMES")){
							Platform.runLater(() -> { 
							roomUsers.getItems().removeAll(friends);
							friends= message.split("\\s+");
							roomUsers.getItems().addAll(friends);
							});
							}
						else if (message.startsWith("NEW ROOM")){
							message = message.replace("NEW ROOM", "");
							String people[];
							people = message.split("\\s+");
							TextArea child = new TextArea();
							Boolean historyFileExists = ChatHistory.HistoryFileExists(people);
							for(int i =0; i < people.length; i++){
								if(people[i].equals(ChatClient.username)){
									System.out.println("Adding chatroom for"+ people[i]);
									Platform.runLater(() -> { 
										final Tab tab = new Tab("ChatRoom " + (tabs.getTabs().size() + 1));
								        tabs.getTabs().add(tab);
								        tabs.getSelectionModel().select(tab);
								        if (historyFileExists) {
								        	String chatHistory = ChatHistory.getChatHistory(people);
								        	child.setText(chatHistory);
								        }
								        child.setEditable(false);
								        tab.setContent(child);
										});	
									if (!historyFileExists) {
										ChatHistory.initializeChatHistory(people);
									}
								}
							}
							chatRooms.add(new ChatRoom("ChatRoom " + (tabs.getTabs().size() + 1), child,message));
							
						}
						else{
							String people[];
							String realMsg[];
							realMsg = message.split(":");	///you're not allowed to type semicolons LOL
							people = realMsg[0].split("\\s+");	
							//check if first part of string is the same of any chatroom.people
							//if it is then append message to that chatroom
							for(ChatRoom cr: chatRooms){
								if(realMsg[0].equals(cr.people)){     //if I am in the people
									Platform.runLater(() -> { 
										cr.addMessage(realMsg[1] + "\n");
										System.out.println(realMsg[0]);
									});
								}	
							}
						}
						
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			

	
		}
}

