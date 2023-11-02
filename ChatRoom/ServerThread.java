package ChatRoom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {

	private PrintWriter pw;
	private BufferedReader br;
	private ChatRoom cr;
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

	public void sendMessage(String message) {
		pw.println(message);
		pw.flush();
	}
	
	public void broadcast(String message) {
		if (message != null) {
			System.out.println(message);
			for(ServerThread threads : cr.serverThreads) {
				if (this != threads) {
					threads.sendMessage(message);
				}
			}
		}
	}
	
	public void run() {
		try {
			while(true) {
				String line = br.readLine();
				broadcast(line);
			}
		} catch (IOException ioe) {
			System.out.println("ioe in ServerThread.run(): " + ioe.getMessage());
		}
	}
}
