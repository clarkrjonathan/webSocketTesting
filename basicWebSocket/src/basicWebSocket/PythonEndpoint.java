package basicWebSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.security.*;
import java.util.Base64;
import java.util.HexFormat;

public class PythonEndpoint {
	private ServerSocket server;
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private Boolean initialized = false;
	
	/**
	 * Constructer initializes a test websocket server on given port
	 * @param port
	 * @throws IOException
	 */
	public PythonEndpoint(int port) throws IOException {
		
		
		System.out.println("Starting server...");
		server = new ServerSocket(port);
	
		client = server.accept();
		//busywait handshake
		
		
		System.out.println("Client connected:" + client.getLocalAddress().getHostAddress());
		//output stream to be sent to client
		out = new PrintWriter(client.getOutputStream(), true);
		//input stream coming from client
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		handshake();
	}
	
	/**
	 * performs handshake with python client
	 */
	private void handshake() {
		//start by printing out GET request headers
		String GETRequest = "";
		Scanner scan = new Scanner(in);
		String key = "";
		
		while(scan.hasNextLine()) {
			String header = scan.nextLine();
			GETRequest += header + "\n";
			
			//I am aware this is probably a really stupid thing to do but this is just for testing purposes
			//to learn how websocket communication protocols work
			if(header.contains("User-Agent")) {
				break;
			} else if (header.contains("Sec-WebSocket-Key: ")) {
				key = header.replace("Sec-WebSocket-Key: ", "");
			}
		}
		System.out.println(GETRequest);
		System.out.println("Key: " + key);
		
		responseCode(key);
		scan.close();
	}
	
	private void responseCode(String key) {
		String encryptedKey = encryptKey(key);
		String response = "HTTP/1.1 101 Switching Protocols\r\n"
				+ "Upgrade: websocket\r\n"
				+ "Connection: Upgrade\r\n"
				+ "Sec-WebSocket-Accept: " + encryptedKey;
		out.println(response);
		
		System.out.println("\nResponse: ");
		System.out.println(response);
	}
	
	public static String encryptKey(String password)
	{
		password += "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
	    String sha1 = "";
	    try
	    {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(password.getBytes("UTF-8"));
	        sha1 = byteToHex(crypt.digest());
	    }
	    catch(NoSuchAlgorithmException e)
	    {
	        e.printStackTrace();
	    }
	    catch(UnsupportedEncodingException e)
	    {
	        e.printStackTrace();
	    }
	    byte[] hexBytes = HexFormat.of().parseHex(sha1);
	    return Base64.getEncoder().encodeToString(hexBytes);
	}

	private static String byteToHex(final byte[] hash)
	{
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}
	
	public void closeServer() throws IOException {
		server.close();
		client.close();
		in.close();
		out.close();
	}
}
