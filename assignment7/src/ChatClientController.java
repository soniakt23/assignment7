import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
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
    private  ChoiceBox<String> roomUsers;

    @FXML
    private Button createRoomButton;

    @FXML
    private ListView<String> usersPane;

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
			incoming.appendText("Check who's available and make a new chat room!");
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
					String temp2;
					for (ChatRoom cr : chatRooms){
						//send to everyone in chatroom
						if(tabs.getSelectionModel().getSelectedItem().getContent().equals(cr.txtArea)){
						writer.println(cr.total + " :" + ChatClient.username + "- " + outgoing.getText());
						writer.flush();
						
						//send to chat history
						ChatHistory.setChatHistory(cr.total, outgoing.getText());
						
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
			        writer.println("NEW ROOM" + selectedChatRoom);
			        writer.flush();
			        selectedChatRoom=ChatClient.username;
			        roomUsers.getSelectionModel().clearSelection();
			        
			        //write to server new chat room with selected people
			      }
			    });
			 
			 roomUsers.setOnAction(new EventHandler <ActionEvent>() {
				 @Override
			      public void handle(ActionEvent event) {
					 if(!(roomUsers.getSelectionModel().getSelectedItem() ==null))
					 selectedChatRoom = (selectedChatRoom + " " + roomUsers.getSelectionModel().getSelectedItem());
					 System.out.println(selectedChatRoom);
					 //usersPane.setContent(selectedChatRoom);					 //roomUsers.getItems().remove(roomUsers.getSelectionModel().getSelectedIndex());
					 //roomUsers.getItems().remove(user);
				 }
			    });
			 
			 usersPane.getSelectionModel().getSelectedItem();
		}

		private void setUpNetworking() throws Exception {
			@SuppressWarnings("resource")
			//Socket sock = new Socket("2605:6000:101e:d4:4c11:905e:a913:9030", 4242);
			Socket sock = new Socket(ChatClient.ipAddress, 4242);
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
							message = message.replace("NEW USERNAMES", "");
							if(roomUsers.getItems()!=null)
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
										final Tab tab = new Tab("ChatRoom " + (tabs.getTabs().size()));
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
							chatRooms.add(new ChatRoom("ChatRoom " + (tabs.getTabs().size() + 1), child,people,message));
							
						}
						else{
							System.out.println(message);
							String people[];
							String realMsg[];
							realMsg = message.split(":");	///you're not allowed to type semicolons LOL
							System.out.println(realMsg[0]);
							people = realMsg[0].split("\\s+");
							System.out.println(people[0]);
							//check if first part of string is the same of any chatroom.people
							//if it is then append message to that chatroom
							//people = Arrays.copyOfRange(people, 1,people.length);
							Arrays.sort(people);
							System.out.println("NJEFN" +people[0]);
							for(ChatRoom cr: chatRooms){
								if(cr.sameChatRoom(people)){     //if I am in the peopleB
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

