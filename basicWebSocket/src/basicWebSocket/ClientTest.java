package basicWebSocket;
import java.net.*;
import java.io.*;
import java.util.Scanner;

/**
 * Basic client setup for learning to work with websockets in java
 * @author Jonathan Clark
 *
 */

public class ClientTest {
	Socket client;
	PrintWriter out;
	BufferedReader in;
	
	/**
	 * Setup for client to given server ip on given port
	 * @param ip - ip of server
	 * @param port
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public ClientTest(String ip, int port) throws IOException {
		client = new Socket(ip, port);
		
		out = new PrintWriter(client.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
	}
	
	public void sendMessage(String msg) {
		out.println(msg);
	}
	
	public void closeClient() throws IOException {
		client.close();
		out.close();
		in.close();
	}
	

}
