package client;

import java.net.*;
import java.util.Hashtable;
import java.io.*;

public class FTP_Client {
	private Socket cs;
	private PrintWriter out;
	private BufferedReader in, userIn;
	private int portNum;
	private String host = "127.0.0.1";
	private String serverMsg;
	
	Hashtable<Integer, Socket> connections = new Hashtable<Integer, Socket>();

	public void requestConnection(String IPAddress, int portNum) {

		printMsg("Starting Client.");

		try {
			if (cs == null) {
				cs = new Socket(IPAddress, portNum);
				out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(cs.getOutputStream(), "UTF-8")), true);
				in = new BufferedReader(new InputStreamReader(cs.getInputStream()));

				this.portNum = portNum;
				if ((serverMsg = sendMsg("OPEN")) != null) {
					if (serverMsg.equals("CONACK")) {
						printMsg("Connection successfully made on port " + portNum);
					}
				}
			} else {
				printMsg("Client is already connected on port " + this.portNum + ". Use the command CLOSE before connecting again.");
			}
		} catch (Exception e) {
			System.out.println("\t" + e);
		}
	}

	public String sendMsg(String msg) {
		String response;

		try {
			out.println(msg);
			response = in.readLine();
			return response;
		} catch (IOException e) {
			printMsg("Exception occured : " + e);
			return null;
		}
	}

	public void closeConnection() {
		if (cs != null && !cs.isClosed()) {
			printMsg("Closing current connection on port : " + this.portNum);
			try {

				if ((serverMsg = sendMsg("CLOSE")) != null) {
					if (serverMsg.equals("CLOSEACK")) {
						printMsg("Connection Close Request received at server side.");
						cs.close();
						in.close();
						out.close();
						cs = null;
						this.portNum = -1;
						printMsg("Connection Closed.");
					}
				}
			} catch (Exception e) {
				printMsg("Exception Occured : " + e);
			}
		} else {
			printMsg("Client is not currently connected.");
		}
	}

	public void startClientApplication() {
		printWelcomeMsg();

		userIn = new BufferedReader(new InputStreamReader(System.in));

		while (true) {
			processInput();
		}
	}

	public void processInput() {
		String input;
		System.out.print("\n\nEnter Command >>");
		try {
			if ((input = userIn.readLine().toLowerCase()) != null) {
				if (input.contains("help")) {
					printCommands();
				} else if (input.substring(0, 4).equalsIgnoreCase("open")) {
					openConnection(input.substring(4).replaceAll("\\s", ""));
				} else if (input.substring(0, 3).equalsIgnoreCase("get")) {
					getRequest(input.substring(3).replaceAll("\\s", ""));
				} else if (input.substring(0, 3).equalsIgnoreCase("put")) {
					postRequest(input.substring(3).replaceAll("\\s", ""));
				} else if (input.contains("close")) {
					closeConnection();
				} else if (input.contains("quit")) {
					quit();
				} else {
					printMsg("Please enter a valid command.");
				}
			}
		} catch (Exception e) {
			printMsg("Exception occured : " + e);
		}
	}

	public void openConnection(String input) {
		printMsg("Opening Connection.");

		int portNum = 0;

		if (input != null && !input.trim().isEmpty()) {

			try {
				portNum = Integer.parseInt(input);
			} catch (Exception e) {
				printMsg("Please enter a valid port number.");
				return;
			}

			requestConnection(host, portNum);

		} else {
			printMsg("Please enter a valid port number.");
		}
	}

	public void getRequest(String input) {
		System.out.println(input);
	}

	public void postRequest(String input) {
		System.out.println(input);
	}

	public void quit() {
		closeConnection();
		printMsg("Exiting Client. Goodbye!");
		System.exit(0);
	}

	public void printCommands() {
		System.out.println(
				"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ HELP ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		System.out.println(
				"OPEN #	     - Attempts to open an FTP connection to 127.0.0.1 using the port number of your choosing.");
		System.out.println("GET filename - Request a file from the server. ");
		System.out.println("PUT filename - Sends a file from the server. ");
		System.out.println("CLOSE        - Closes the current connection, a new connection may be established.");
		System.out.println("QUIT         - Exits the client and closes any open connections.");
		System.out.println(
				"~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ HELP ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
	}
	
	public void printWelcomeMsg() {
		System.out.print(
				"***********************************"
				+ "\nWelcome to simple FTP for COSC 328.\n\n"
				+ "Enter 'help' for list of commands.\n"
				+ "***********************************"
				);
	}

	public void printMsg(String msg) {
		System.out.printf("\n\t...................\n\t%s\n\t....................", msg);
	}
}
