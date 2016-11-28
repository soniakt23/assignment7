import javafx.scene.control.TextArea;

public class ChatRoom {
	String people;
	String chatRoomName;
	TextArea txtArea;
	
	public ChatRoom(  String chatRoomName, TextArea txtArea, String people){
		this.chatRoomName= chatRoomName;
		this.txtArea = txtArea;
		this.people = people;
		this.Greeting();
		
	}
	
	public void addMessage (String message){
		txtArea.appendText(message);
	}
	

	public void Greeting (){
		txtArea.appendText(people+", say hi to each other!"+"\n");
		return;
	}
	

	
}
