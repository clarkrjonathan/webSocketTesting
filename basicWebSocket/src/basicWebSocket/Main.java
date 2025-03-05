package basicWebSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * A collection of test methods for me to deepen my understanding of websockets
 * and of communication protocols
 * Both server and client implementations
 * @author Jonathan Clark
 *
 */
public class Main {
	
	/**
	 * IP of server
	 */
	public static final String IP = "10.49.179.103";
	
	/**
	 * Server port
	 */
	public static final int PORT = 1824;
	
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		charEchoTestServer(PORT);
		
	}
	
	/**
	 * Sends a byte of data to the server and waits an echo back
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void charEchoTestServer(int port) throws IOException, InterruptedException {
		ServerTest test = new ServerTest(1824);
		while(test.echoChar() != 33) {};
		
	}
	
	/**
	 * sends hello world and waits for echo back for each char
	 * @param ip
	 * @param port
	 * @throws IOException 
	 */
	public static void charEchoTestClient(String ip, int port) throws IOException {
		ClientTest client = new ClientTest(ip, port);
		int next = System.in.read();
		
		while (next != 48) {
			//busywait next char
			while (next == -1) {next = System.in.read();}
			client.sendByte((byte) next);
			next = System.in.read();
		}
	}
	
	/**
	 * Test method that reads console data from our lab robot in CPRE 288
	 * @throws IOException
	 */
	public static void consoleOut() throws IOException {
		ClientTest test = new ClientTest("192.168.1.1", 288);
		
		//test.sendHandshake();
		BufferedReader in = test.getIn();
		Scanner scanner = new Scanner(in);

		String inpt = scanner.nextLine();
		while(inpt != "fjdksaljeoin") {
			
			while(!scanner.hasNextLine()) {};
			inpt = scanner.nextLine();
			System.out.println(inpt);
		}
		
		scanner.close();
	}
	
	/**
	 * Runs a test echoing characters between server and client
	 * @param port
	 * @param ip - ip of server
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void charEchoTest(int port, String ip) throws IOException, InterruptedException {
		
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Server(0) Client(1): ");
		
		
		int choice = scanner.nextInt();
		if(choice == 0) {
			charEchoTestServer(port);
		} else {
			
			charEchoTestClient(ip, port);
		}
		scanner.close();
	}
	
	/**
	 * Simple message where the server receives messages from the client
	 * @param port
	 * @param ip - ip of server
	 * @throws IOException
	 */
	public static void webMessageTest(int port, String ip) throws IOException {
		
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Server(0) Client(1): ");
		
		
		int choice = scanner.nextInt();
		if(choice == 0) {
			runServer(port);
		} else {
			
			runClient(ip, port);
		}
		scanner.close();
	}
	
	/**
	 * Runs client side of the webMessageTest() method
	 * @param ip - ip of server
	 * @param port
	 * @throws IOException
	 */
	public static void runClient(String ip, int port) throws IOException {
		ClientTest client = new ClientTest(ip, 1824);
		Scanner scanner = new Scanner(System.in);
		String msg = scanner.nextLine();
		while(!checkExit(msg)) {
			client.sendMessage(msg);
			msg = scanner.nextLine();
		}
		System.out.println("Closing");
		scanner.close();
		client.closeClient();
	}
	
	/**
	 * Exit condition for the webMessageTest()
	 * @param str - Data from client
	 * @return if exit condition is true
	 */
	public static boolean checkExit(String str) {
		Scanner scanner = new Scanner(str);
		try {
			if(scanner.nextInt() == -1) {
				scanner.close();
				return true;
			} else {
				scanner.close();
				return false;
			}
		} catch (InputMismatchException e) {
			scanner.close();
			return false;
		}
		
	
	}
	
	/**
	 * Runs the server side of webMessageTest()
	 * @param port
	 * @throws IOException
	 */
	public static void runServer(int port) throws IOException {
		ServerTest server = new ServerTest(port);
		BufferedReader in = server.getIn();
		Scanner scanner = new Scanner(in);
		
		//Will loop until client closes in which case there will be a NoSuchElementException error
		try {
			String msg = scanner.nextLine();
			while(true) {
				System.out.println(msg);
				msg = scanner.nextLine();
			}
		} catch(NoSuchElementException e) {};
		
		System.out.println("Closing");
		scanner.close();
		server.closeServer();
	}

}
