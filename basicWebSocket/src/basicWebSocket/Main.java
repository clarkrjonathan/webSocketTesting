package basicWebSocket;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {

		int port = 1824;
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Server(0) Client(1): ");
		
		
		int choice = scanner.nextInt();
		if(choice == 0) {
			runServer(port);
		} else {
			runClient(port);
		}
		scanner.close();
		
	}
	
	public static void runClient(int port) throws IOException {
		ClientTest client = new ClientTest("10.49.205.203", 1824);
		Scanner scanner = new Scanner(System.in);
		String msg = scanner.nextLine();
		while(msg != "-1") {
			client.sendMessage(msg);
		}
		
		scanner.close();
		client.closeClient();
	}
	
	public static void runServer(int port) throws IOException {
		ServerTest server = new ServerTest(port);
		String msg = server.readMessage();
		while(msg != "-1") {
			System.out.println(msg);
			msg = server.readMessage();
		}
		
	}

}
