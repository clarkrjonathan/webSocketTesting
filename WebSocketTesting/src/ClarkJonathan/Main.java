package ClarkJonathan;
import java.net.*;
import java.io.*;

public class Main {
	private static ServerSocket socket;
	private static Socket clientSocket;
	private static BufferedReader in;
	private static PrintWriter socketout;
	
	public static void main(String[] args) throws IOException{
		System.out.println("Starting Socket...");
		initSocket(1824);
		
		while(true) {
			System.out.println(in.readLine());
		}
	}
	
	public static void initSocket(int port) throws IOException {
		socket = new ServerSocket(port);
		System.out.println("Waiting for client...");
		clientSocket = socket.accept();
		System.out.println("Connected to Client: " + clientSocket.getLocalAddress().getHostAddress());
		
		
		socketout = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		System.out.println(in.readLine());
		socketout.println();
	}
	
	public static void closeSocket() throws IOException {
		in.close();
		socketout.close();
		socket.close();
		clientSocket.close();
	}
}
