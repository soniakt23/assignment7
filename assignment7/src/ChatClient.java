import java.util.ArrayList;

public class ChatClient {
	String username;
	String password;
	ChatClientGUI gui;
	ArrayList<String> chatHistory = new ArrayList<String>();
	ArrayList<ChatClient> friends = new ArrayList<ChatClient>();

	public ChatClient(String username, String password) {
		this.username = username;
		this.password = password;
		ChatServer.clients.add(this);
		ChatServer.usernameToID.put(username, ChatServer.clients.indexOf(this));
		gui = new ChatClientGUI();
		//gui.start(null);
	}
	
	public void setGUIClient() {
		gui.client = this;
	}
}
