import java.util.Arrays;

import javafx.scene.control.TextArea;

public class ChatRoom {
	String[]  people;
	String total;
	String chatRoomName;
	TextArea txtArea;
	
	public ChatRoom(String chatRoomName, TextArea txtArea, String[] people, String total){
		this.chatRoomName= chatRoomName;
		this.txtArea = txtArea;
		this.total = total;
		this.people = people;
		this.Greeting();
		
	}
	
	public void addMessage (String message){
		txtArea.appendText(message);
	}
	

	public void Greeting (){
		txtArea.appendText(total+", say hi to each other!"+"\n");
		return;
	}
	
	public boolean sameChatRoom(String[] list) {
		Arrays.sort(list);
		return Arrays.equals(people,  list);
	}
	

	
}
