package server;

import java.net.*;
import java.io.*;

public class FTP_Server {
	private ServerSocket ss;
	private Socket cs = null;
	private PrintWriter out;
	private BufferedReader in;
	private String clientMsg;

	public void start(int portNum) {

		// Open server socket on start
		try {
			ss = new ServerSocket(portNum);
		} catch (IOException e) {
			System.out.println("Exception occured : " + e + "\n\n");
			e.printStackTrace();
		}

		// Server stays alive forever
		while (true) {

			try {
				
				// If clientSocket set to null, open new connection.
				if (cs == null) {
					cs = ss.accept();
					in = new BufferedReader(new InputStreamReader(cs.getInputStream()));
					out = new PrintWriter(cs.getOutputStream(), true);
				} else if ((clientMsg = in.readLine()) != null) {
					// Otherwise process the client's request
					processRequest(clientMsg);
				}
			} catch (Exception e) {
				System.out.println("Exception occured : " + e);
				System.out.println("Fatal Error - Server is shutting down. Goodbye.");
				System.exit(0);
			}
		}
	}

	public void processRequest(String msg) {
		if(msg.equals("OPEN")){
			confirmConnection();
		}else if (msg.equals("CLOSE")) {
			closeConnection();
		}
	}
	
	public void confirmConnection() {
		out.println("CONACK");
	}

	public void closeConnection() {
		try {
			out.println("CLOSEACK");
			
			cs.close();
			in.close();
			out.close();
			cs = null;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void stopServer() {
		try {

			cs.close();
			ss.close();
			out.close();
			in.close();

		} catch (IOException e) {
			System.out.println("Exception occured : " + e);
		}
	}
}
