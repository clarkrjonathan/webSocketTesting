package basicWebSocket;
import java.io.*;
import java.net.*;
import java.util.Scanner;

/**
 * Basic server setup for learning to work with websockets in java
 * @author Jonathan Clark
 *
 */
public class ServerTest {
	
	private ServerSocket server;
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	
	/**
	 * Constructer initializes a test websocket server on given port
	 * @param port
	 * @throws IOException
	 */
	public ServerTest(int port) throws IOException {
		System.out.println("Starting server...");
		server = new ServerSocket(port);
		
		client = server.accept();
		System.out.println("Client connected:" + client.getLocalAddress().getHostAddress());
		//output stream to be sent to client
		out = new PrintWriter(client.getOutputStream(), true);
		
		//input stream coming from client
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
	}
	
	public void sendFile(String filePath) throws FileNotFoundException{
		File file = new File(filePath);
		Scanner scanner = new Scanner(file);
		String text = "";
		while (scanner.hasNextLine()) {
			text += scanner.nextLine();
		}
		
		//printing as a test for now
		out.print(text);
		scanner.close();
	}
	
	/**
	 * prints next line of input stream to console
	 * @throws IOException 
	 */
	public String readMessage() throws IOException {
		return in.readLine();
	}
	
	public BufferedReader getIn() {
		return in;
	}
	
	public void sendMessage(String msg) {
		out.println(msg);
	}
	
	public void closeServer() throws IOException {
		server.close();
		client.close();
		in.close();
		out.close();
	}

}
