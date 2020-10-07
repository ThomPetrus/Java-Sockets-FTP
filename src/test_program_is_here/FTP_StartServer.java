package test_program_is_here;

import java.io.*;
import java.net.*;
import server.FTP_Server;

/**
 * 
 * @author Thomas Van De Crommenacker
 * 
 *         Program to start the server side of the FTP application.
 *
 */
public class FTP_StartServer {

	public static void main(String[] args) {
		startServer();
	}

	public static void startServer() {
		boolean valid = false;
		int portNum = 0;
		FTP_Server server;

		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Welcome to FTP server start. Please enter a valid port number to start the server on.");
		System.out.print(">>");

		while (!valid) {
			try {
				portNum = Integer.parseInt(in.readLine().replaceAll("\\s", ""));
				valid = true;
			} catch (IOException e) {
				System.out.println("IOException occured : " + e);
				e.printStackTrace();
			} catch (NumberFormatException e) {
				System.out.println("Please enter a valid port number.");
			}
		}

		System.out
				.println("...\nStarting Server.\nIf no exception occurs then the server has started succesfully.\n...");

		server = new FTP_Server();
		server.start(portNum);

	}
}
