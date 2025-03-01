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
	private Socket client;
	private PrintWriter out;
	private BufferedReader in;
	
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
	
	public void sendHandshake() {
		sendMessage("GET /chat HTTP/1.1");
		sendMessage("Host: 192.168.1.1:288");
		sendMessage("Upgrade: websocket");
		sendMessage("Connection: Upgrade");
		sendMessage("Sec-WebSocket-Key: sjafkljesioajfesioajfdskl");
		sendMessage("Sec-WebSocket-Version: 13");
		
		//should busy wait receiving line
	}
	
	public String readMessage() {
		Scanner scanner = new Scanner(in);
		while(!scanner.hasNextLine()) {};
		String inpt = scanner.nextLine();
		scanner.close();
		return inpt;
	}
	
	public BufferedReader getIn() {
		return in;
	}
	
	public void sendMessage(String msg) {
		out.println(msg);
	}
	
	public PrintWriter getOut() {
		return out;
	}
	
	public void closeClient() throws IOException {
		client.close();
		out.close();
		in.close();
	}
	

}
