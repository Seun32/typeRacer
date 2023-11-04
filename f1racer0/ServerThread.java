package f1racer0;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {

	private PrintWriter pw;
	private BufferedReader br;
	private ChatRoom cr;
	
	String response = "";
	boolean done = false;
	
	
	
	public ServerThread(Socket s, ChatRoom cr) {
		try {
			this.cr = cr;
			pw = new PrintWriter(s.getOutputStream());
			br = new BufferedReader(new InputStreamReader(s.getInputStream()));
			this.start();
		} catch (IOException ioe) {
			System.out.println("ioe in ServerThread constructor: " + ioe.getMessage());
		}
	}
	
	
	public void run() {
			while(!cr.winner) {
				String line = "";
				try {
					line = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				response += line;
				sendMessage(cr.prompt);
				sendMessage(response);
				if (cr.prompt.equals(response)) {
					done = true;
					cr.winner = true;
				}
			}
			if (done) {
				sendMessage("You win :) ");
			}
			else if (!done) {
				sendMessage("You loose :(");
			}
	}
	
	
	public void sendMessage(String message) {
		pw.println(message);
		pw.flush();
	}
	
	
	
}
