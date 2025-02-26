package basicWebSocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Establishes one way communication from client to server forwarding console input from client to the server console
 * @author Jonathan Clark
 *
 */
public class Main {
	public static void main(String[] args) throws IOException {

		webMessageTest();
		
	}
	

	
	public static void webMessageTest() throws IOException {
		int port = 1824;
		String ip = "10.49.205.203";
		
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
		while(checkExit(msg)) {
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
				return true;
			} else {
				return false;
			}
		} catch (InputMismatchException e) {
			return false;
		}
		
	
	}
	
	public static void runServer(int port) throws IOException {
		ServerTest server = new ServerTest(port);
		BufferedReader in = server.getIn();
		Scanner scanner = new Scanner(in);
		
		String msg = scanner.nextLine();
		
		while(checkExit(msg)) {
			System.out.println(msg);
			msg = scanner.nextLine();
		}
		System.out.println("Closing");
		scanner.close();
		server.closeServer();
	}

}
