package assignment7;
import java.util.ArrayList;

public class ChatClient {
	static String username;
	static String ipAddress;
	ChatClientGUI gui;
	ArrayList<ChatClient> friends = new ArrayList<ChatClient>();

	public ChatClient(String username, String password, String ip) {
		this.username = username;
		this.ipAddress = ip;
		//ChatServer.clients.add(this);
		//ChatServer.usernameToID.put(username, ChatServer.clients.indexOf(this));
		gui = new ChatClientGUI();
	}
	
	public void setGUIClient() {
		gui.client = this;
	}
}