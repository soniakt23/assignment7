//package day23network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ChatServer {
	private ArrayList<PrintWriter> clientOutputStreams;
	private int maxClientsCount;
	Socket clientSocket;
	private final ChatClient[] threads = new ChatClient [maxClientsCount];

	public static void main(String[] args) {
		try {
			ChatServer chat = new ChatServer();
			chat.setUpNetworking();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	private void setUpNetworking() throws Exception {
//		clientOutputStreams = new ArrayList<PrintWriter>();
//		@SuppressWarnings("resource")
//		ServerSocket serverSock = new ServerSocket(9332);
//		while (true) {
//			try{
//			 clientSocket = serverSock.accept();
//			 int i = 0;
//		        for (i = 0; i < maxClientsCount; i++) {
//		          if (threads[i] == null) {
//		            (threads[i] = new ChatClient()).start();
//		            break;
//		          }
//		        }
//		        if (i == maxClientsCount) {
//		          PrintStream os = new PrintStream(clientSocket.getOutputStream());
//		          os.println("Server too busy. Try later.");
//		          os.close();
//		          clientSocket.close();
//		        }
//		      } catch (IOException e) {
//		        System.out.println(e);
//		      }
//			PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
//			clientOutputStreams.add(writer);
//
//			Thread t = new Thread(new ClientHandler(clientSocket));
//			t.start();
//			System.out.println("got a connection");
//			
//		    }
//			
//		}

private void setUpNetworking() throws Exception {
	clientOutputStreams = new ArrayList<PrintWriter>();
	@SuppressWarnings("resource")
	ServerSocket serverSock = new ServerSocket(4242);
	while (true) {
		Socket clientSocket = serverSock.accept();
		PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
		clientOutputStreams.add(writer);

		Thread t = new Thread(new ClientHandler(clientSocket));
		t.start();
		System.out.println("got a connection");
	}

}

	private void notifyClients(String message) {


		for (PrintWriter writer : clientOutputStreams) {
			writer.println(message);
			writer.flush();
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
			try {
				while ((message = reader.readLine()) != null) {
					System.out.println("read " + message);
					notifyClients(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
