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
	private PrintWriter outWriter;
	
	private OutputStream out;
	
	private InputStream inputStream;
	
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
		outWriter = new PrintWriter(client.getOutputStream(), true);
		out = client.getOutputStream();
		//input stream coming from client
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		inputStream = client.getInputStream();
	}
	
	/**
	 * Echos a char if there is one to be read
	 * @return true if there was a character echo'd
	 * @throws IOException 
	 */
	public byte echoChar() throws IOException {
		int data = inputStream.read();
		
		if (data != -1) {
			out.write(data);
			System.out.println((char) data);
		}
		return (byte) data;
	}
	
	
	public void sendFile(String filePath) throws FileNotFoundException{
		File file = new File(filePath);
		Scanner scanner = new Scanner(file);
		String text = "";
		while (scanner.hasNextLine()) {
			text += scanner.nextLine();
		}
		
		//printing as a test for now
		outWriter.print(text);
		scanner.close();
	}
	
	/**
	 * prints next line of input stream to console
	 * @throws IOException 
	 */
	public String readMessage() throws IOException {
		return in.readLine();
	}
	
	/**
	 * Gets a BufferedReader of the input stream
	 * @return Input stream as a BufferedReader
	 */
	public BufferedReader getIn() {
		return in;
	}
	
	/**
	 * Sends a message to the client
	 * @param msg - Message to be sent
	 */
	public void sendMessage(String msg) {
		outWriter.println(msg);
	}
	
	/**
	 * Takes the necessary actions to close out the websocket
	 * @throws IOException
	 */
	public void closeServer() throws IOException {
		server.close();
		client.close();
		in.close();
		outWriter.close();
	}

}
