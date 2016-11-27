import java.util.ArrayList;

public class ChatClient {
	String username;
	String password;
	ChatClientGUI gui;
	ArrayList<String> chatHistory = new ArrayList<String>();
	ArrayList<ChatClient> friends = new ArrayList<ChatClient>();

	public ChatClient(String username) {
	super();
	this.username = username;
	ChatServer.clients.add(this);
	gui = new ChatClientGUI();
	gui.start(null);
}
}
