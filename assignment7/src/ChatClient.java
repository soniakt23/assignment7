import java.util.ArrayList;

public class ChatClient {
	static String username;
	ChatClientGUI gui;
	ArrayList<ChatClient> friends = new ArrayList<ChatClient>();

	public ChatClient(String username, String password) {
		this.username = username;
		//ChatServer.clients.add(this);
		//ChatServer.usernameToID.put(username, ChatServer.clients.indexOf(this));
		gui = new ChatClientGUI();
		//gui.start(null);
	}
	
	public void setGUIClient() {
		gui.client = this;
	}
}