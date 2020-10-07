package test_program_is_here;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import client.FTP_Client;
import server.FTP_Server;

/**
 * 
 * @author Thomas Van De Crommenacker
 * 
 * Main Entry point for the FTP application. 
 * The program will warn you, but in order to establish a connection, the server should be started first.
 *
 */
public class FTP_MainProgram {

	public static void main(String[] args) {

		FTP_Client ftp = new FTP_Client();
		ftp.startClientApplication();

	}
	

}
