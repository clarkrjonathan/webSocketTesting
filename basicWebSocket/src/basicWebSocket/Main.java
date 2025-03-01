package basicWebSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Establishes one way communication from client to server forwarding console input from client to the server console
 * @author Jonathan Clark
 *
 */
public class Main {
	public static void main(String[] args) throws IOException, InterruptedException {
		runServer(1824);
	}
	
	public static void charEcho() throws IOException, InterruptedException {
		ClientTest test = new ClientTest("192.168.1.1", 288);
		Scanner usrInput = new Scanner(System.in);
		Scanner cybotScan = new Scanner(test.getIn());
		PrintWriter out = test.getOut();
		
		out.println("B");
		
		
		String msg = "Hello World";
		char c = ')';
		int j = 0;
		while(j < msg.length()) {
			
			c = msg.charAt(j);
			out.println(msg.charAt(j));
			Thread.sleep(10);
			
			j++;
		}
		
		usrInput.close();
		cybotScan.close();
//		while(cybotInput.hasNext()) {
//			System.out.println(cybotInput.next());
//		}
		
	}
	
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
	 * returns true if should exit
	 * @param str
	 * @return if exit condition
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
