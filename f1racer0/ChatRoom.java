package f1racer0;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.*;

public class ChatRoom {

	Vector<ServerThread> serverThreads;
	String prompt;
	boolean winner = false;
	
	
	public ChatRoom(int port) {
		try {
			System.out.println("Binding to port " + port);
			ServerSocket ss = new ServerSocket(port);
			System.out.println("Bound to port " + port);
			serverThreads = new Vector<ServerThread>();
			
			
			prompt = "a monkey plays f1racer";
			
			
			while(true) {
				Socket s = ss.accept(); // blocking
				System.out.println("Connection from: " + s.getInetAddress());
				ServerThread st = new ServerThread(s, this);
				serverThreads.add(st);
				st.sendMessage(prompt);
			}
			
			
		} catch (IOException ioe) {
			System.out.println("ioe in ChatRoom constructor: " + ioe.getMessage());
		}
	}
	

	
	public static void main(String [] args) {
		ChatRoom cr = new ChatRoom(6789);
	}
}
