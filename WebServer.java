/************************************************************************************
 *	file: WebServer.java
 *	author: Daniel Spencer, David Shin
 *	class: CS 380 - computer networks
 *
 *	assignment: Exercise 8
 *	date last modified: 11/30/2017
 *
 *	purpose: In this exercise, we will create a simple web server.
 *
 ************************************************************************************/

import java.net.*;
import java.io.*;

public class WebServer {
    public static void main(String[] args) throws Exception {
        try(ServerSocket sSocket = new ServerSocket(8080)) {
            System.out.println("Server Socket Port 8080");
            while(true) {
                try(Socket socket = sSocket.accept()) {
                    PrintStream out = new PrintStream(socket.getOutputStream(), true, "UTF-8");
                    InputStreamReader isr = new InputStreamReader(socket.getInputStream(), "UTF-8");
                    BufferedReader br = new BufferedReader(isr);
                    String firstLine = br.readLine();
                    System.out.println(firstLine);
		    while(br.ready())
			{
			    System.out.println(br.readLine());
			}
                    String[] str = firstLine.split(" ");
                    File inputFile = new File("." + str[1]);
                    if(inputFile.isFile()) {
                        BufferedReader file = new BufferedReader(new FileReader(inputFile));
                        out.println("HTTP/1.1 200 OK");
                        out.println("Content-type: text/html");
                        out.println("Content-length: " + inputFile.length() + "\n");
			while(file.ready())
			    out.println(file.readLine());
                    }
                    else {
                        File FileNotFound = new File("Notfound.html");
                        BufferedReader NotFound = new BufferedReader(new FileReader(FileNotFound));
                        out.println("HTTP/1.1 404 Not Found");
                        out.println("Content-type: text/html");
                        out.println("Content-length: " + FileNotFound.length() + "\n");
			while(NotFound.ready())
			    out.println(NotFound.readLine());

                    }
                }
            }
        }
    }
}
