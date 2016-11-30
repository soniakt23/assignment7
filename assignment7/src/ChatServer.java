
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import javafx.application.Application;


public class ChatServer extends Observable {
	private ArrayList<PrintWriter> clientOutputStreams;
	private int maxClientsCount;
	Socket clientSocket;
	private final ChatClient[] threads = new ChatClient [maxClientsCount];
	static ArrayList<ChatClient> clients = new ArrayList<ChatClient>();
	public static int numClients;
	private final HashMap<ChatClient, ChatRoom> clientWriters = new HashMap<ChatClient, ChatRoom>();
	private String userNameFile = "userlogins.txt";
	private String friendsList = "friends.txt";

	public static void main(String[] args) {
		try {
			ChatServer chat = new ChatServer();
			chat.setUpNetworking();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


private void setUpNetworking() throws Exception {
	@SuppressWarnings("resource")
	ServerSocket serverSock = new ServerSocket(4242);
	while (true) {
		Socket clientSocket = serverSock.accept();
		ClientObserver writer = new ClientObserver(clientSocket.getOutputStream());
		Thread t = new Thread(new ClientHandler(clientSocket));
		t.start();
		this.addObserver(writer);
		System.out.println("got a connection");
	}
}



	class ClientHandler implements Runnable {
		private BufferedReader reader;

		public ClientHandler(Socket clientSocket) throws IOException {
			Socket sock = clientSocket;
			reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		}

		public void run() {
			String message;
			String line;
			try {
				
				while ((message = reader.readLine()) != null) {
					System.out.println("read " + message);
					if(message.equals("NEW USER")){
						BufferedReader br = new BufferedReader(new FileReader(userNameFile)); 
						message = ("NEW USERNAMES");
						while ((line = br.readLine()) != null)
						{
					       message=(message +" "+ line);
						}
						
					}
					
					else if(message.startsWith("FRIENDSLIST")){
						String username = message.replace("FRIENDSLIST", "");
						BufferedReader fr = new BufferedReader(new FileReader(friendsList)); 
						while ((line = fr.readLine()) != null )
						{
							if(line.startsWith(username)){
								line = line.replace(username +" ", "");
							       message=(message +":"+ line);
							}
						}
						
					}
					
					setChanged();
					notifyObservers(message);
						
				
			}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
