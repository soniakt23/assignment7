import java.lang.reflect.Array;
import java.util.Arrays;

import javafx.scene.control.TextArea;

public class ChatRoom {
	String [] people;
	String chatRoomName;
	TextArea txtArea;
	String total;
	
	public ChatRoom(  String chatRoomName, TextArea txtArea, String [] people, String total){
		this.chatRoomName= chatRoomName;
		this.txtArea = txtArea;
		this.people = people;
		this.total = total;
		this.Greeting();
		
	}
	
	public void addMessage (String message){
		txtArea.appendText(message);
	}
	

	public void Greeting (){
		txtArea.appendText(total+ " say hi to each other!"+"\n");
		return;
	}
	
	public boolean sameChatRoom(String [] list){
		return Arrays.equals(people, list);
	}
	

	
}
