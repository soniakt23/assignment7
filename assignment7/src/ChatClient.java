//package day23network;

import java.io.*;
import java.net.*;
import java.util.ResourceBundle;

import javax.swing.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.*;

public class ChatClient extends Application implements Initializable {
//	private JTextArea incoming;
//	private JTextField outgoing;
//	private BufferedReader reader;
//	private PrintWriter writer;
//	private final ChatClient[] threads;
//	private int maxClientsCount;
//	private Socket clientSocket;
	
	private static BufferedReader reader;
	private static PrintWriter writer;
	private static InputStreamReader streamReader;
	private String message;

	@FXML
	private TextArea incoming = new TextArea();
	
	@FXML
	private TextField outgoing = new TextField();
	
	@FXML
	private TextField username = new TextField();
	
	@FXML
	private Button sendButton;
	
//	  public ChatClient(Socket clientSocket, ChatClient[] threads) {
//	    this.clientSocket = clientSocket;
//	    this.threads = threads;
//	    maxClientsCount = threads.length;
//	  }
	  

	public void run() {
			try {
//			initView();
			setUpNetworking();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	  
//	public void run() {
//		initView();
//		try {
//			setUpNetworking();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
		
		@Override
		public void initialize(URL fxmlFileLoction, ResourceBundle resources) {
			incoming.setEditable(false);
			//outgoing.requestFocus();
			
//			sendButton.addActionListener(new SendButtonListener());
			sendButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					writer.println(outgoing.getText());
					writer.flush();
					outgoing.setText("");
					outgoing.requestFocus();
				}
			});
		}

//	private void initView() {
//		JFrame frame = new JFrame("Ludicrously Simple Chat Client");
//		JPanel mainPanel = new JPanel();
//		incoming = new JTextArea(15, 50);
//		incoming.setLineWrap(true);
//		incoming.setWrapStyleWord(true);
//		incoming.setEditable(false);
//		JScrollPane qScroller = new JScrollPane(incoming);
//		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
//		outgoing = new JTextField(20);
//		JButton sendButton = new JButton("Send");
//		sendButton.addActionListener(new SendButtonListener());
//		mainPanel.add(qScroller);
//		mainPanel.add(outgoing);
//		mainPanel.add(sendButton);
//		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);
//		frame.setSize(650, 500);
//		frame.setVisible(true);
//
//	}
		
		private void setUpNetworking() throws Exception {
			@SuppressWarnings("resource")
			//Socket sock = new Socket("2605:6000:101e:d4:4c11:905e:a913:9030", 4242);
			Socket sock = new Socket("127.0.0.1", 4242);
			streamReader = new InputStreamReader(sock.getInputStream());
			reader = new BufferedReader(streamReader);
			writer = new PrintWriter(sock.getOutputStream());
			System.out.println("networking established");
			Thread readerThread = new Thread(new IncomingReader());
			readerThread.start();
		}

//	private void setUpNetworking() throws Exception {
//		@SuppressWarnings("resource")
//		//Socket sock = new Socket("127.0.0.1", 9343);
//		InputStreamReader streamReader = new InputStreamReader(clientSocket.getInputStream());
//		reader = new BufferedReader(streamReader);
//		writer = new PrintWriter(clientSocket.getOutputStream());
//		System.out.println("networking established");
//		Thread readerThread = new Thread(new IncomingReader());
//		readerThread.start();
//	}

//	class SendButtonListener implements ActionListener {
//		public void actionPerformed(ActionEvent ev) {
//			writer.println(outgoing.getText());
//			writer.flush();
//			outgoing.setText("");
//			outgoing.requestFocus();
//		}
//	}
		
		class IncomingReader implements Runnable {
			public void run() {
				try {
					while ((message = (new BufferedReader(streamReader)).readLine()) != null) {
					//while ((message = reader.readLine()) != null) {
						Platform.runLater(() -> { 
							System.out.println(incoming.getText());
							incoming.setText(incoming.getText());
							incoming.appendText(message + "\n");
							//System.out.println(incoming.getText());
						});
							
					}
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
		}

//		class IncomingReader implements Runnable {
//			public void run() {
//				String message;
//				try {
//					while ((message = reader.readLine()) != null) {
//						
//							incoming.append(message + "\n");
//					}
//				} catch (IOException ex) {
//					ex.printStackTrace();
//				}
//			}
//		}
		
		@Override
		public void start(Stage primaryStage) {
			Stage clientStage = new Stage();
			AnchorPane clientPane;
			try {
				clientPane = (AnchorPane) FXMLLoader.load(ChatClient.class.getResource("ChatClient.fxml"));
				Scene clientScene = new Scene(clientPane);
				clientStage.setScene(clientScene);
					
				clientStage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		public static void main(String[] args) {
			try {
				new ChatClient().run();
				launch(args);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
}
