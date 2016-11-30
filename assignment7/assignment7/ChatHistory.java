package assignment7;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class ChatHistory {

	static String getChatHistory(String[] people) {
		String history = "";
		FileReader fr;
		try {
			String fileName = historyFileString(people);
			fr = new FileReader(fileName);
			Scanner sc = new Scanner(fr);
			while (sc.hasNext()) {
				history += sc.nextLine() + "\n";
			}
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return history;	
	}
	
	static void setChatHistory(String people, String textMessage) {
		PrintWriter pw;
		String historyFileName = historyFileString(people);
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(historyFileName, true)));
			pw.println(ChatClient.username + "- " + textMessage);
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void initializeChatHistory(String[] people) {
		String fileName = historyFileString(people);
		PrintWriter chatRoomHistory;
		try {
			chatRoomHistory = new PrintWriter(fileName);
			chatRoomHistory.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	static String historyFileString(String[] people) {
		Arrays.sort(people);
		String pString = "";
		for (int i = 0; i < people.length; i++) {
			pString += people[i];
		}
		return pString + ".txt";
	}
	
	static String historyFileString(String people) {
		String[] peopleArray = people.split(" ");
		return historyFileString(peopleArray);
	}

	public static Boolean HistoryFileExists(String[] people) {
		String historyFileName = historyFileString(people);
		File historyFile = new File(historyFileName);
		return historyFile.exists();
	}
	
}
