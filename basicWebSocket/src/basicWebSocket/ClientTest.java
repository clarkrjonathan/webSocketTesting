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
	
	/**
	 * Steam socket thats the basis of the client
	 */
	private Socket client;
	
	/**
	 * Output print writer
	 */
	private PrintWriter outWriter;
	
	private OutputStream out;
	
	private BufferedReader inReader;
	
	private InputStream inputStream;
	
	/**
	 * Setup for client to given server ip on given port
	 * @param ip - ip of server
	 * @param port
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public ClientTest(String ip, int port) throws IOException {
		client = new Socket(ip, port);
		
		outWriter = new PrintWriter(client.getOutputStream(), true);
		out = client.getOutputStream();
		
		inputStream = client.getInputStream();
		inReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
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
	
	/**
	 * Reads a message assuming it was sent with a linebreak
	 * @return the message
	 */
	public String readMessage() {
		Scanner scanner = new Scanner(inReader);
		while(!scanner.hasNextLine()) {};
		String inpt = scanner.nextLine();
		scanner.close();
		return inpt;
	}
	
	/**
	 * Gets the input stream as a buffered reader
	 * @return the buffered input stream
	 */
	public BufferedReader getIn() {
		return inReader;
	}
	
	/**
	 * Sends a byte of data to server and awaits an echo back
	 * @return true if byte was echo'd back
	 * @throws IOException 
	 */
	public boolean sendByte(byte b) throws IOException {
		out.write(b);
		int echo;
		
		echo = inputStream.read();
		
		while (echo == -1) {
			echo =  inputStream.read();
		};
		System.out.print((char) echo);
		return b == (byte) echo;
	}
	
	/**
	 * Input stream accessor
	 * @return The raw input stream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}
	
	/**
	 * Sends a string to the printwriter
	 * @param msg - string to be sent
	 */
	public void sendMessage(String msg) {
		outWriter.println(msg);
	}
	
	/**
	 * Getter for output stream
	 * @return output PrintWriter
	 */
	public PrintWriter getOut() {
		return outWriter;
	}
	
	/**
	 * Closes the client
	 * @throws IOException
	 */
	public void closeClient() throws IOException {
		client.close();
		outWriter.close();
		inReader.close();
	}
	

}
