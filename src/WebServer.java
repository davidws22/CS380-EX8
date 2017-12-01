/************************************************************************************
 *	file: WebServer.java
 *	author: Daniel Spencer, David Shin
 *	class: CS 380 - computer networks
 *
 *	assignment: Exercise 7
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
                    System.out.println(br.readLine());
                    String[] str = firstLine.split(" ");
                    File inFile = new File("." + str[1]);
                    if(inFile.isFile()) {
                        BufferedReader forFile = new BufferedReader(new FileReader(inFile));
                        out.println("HTTP/1.1 200 OK");
                        out.println("Content-type: text/html");
                        out.println("Content-length: " + inFile.length() + "\n");
                        out.println(forFile.readLine());
                    }
                    else {
                        File FileNotFound = new File("Notfound.html");
                        BufferedReader notFound = new BufferedReader(new FileReader(FileNotFound));
                        out.println("HTTP/1.1 404 Not Found");
                        out.println("Content-type: text/html");
                        out.println("Content-length: " + FileNotFound.length() + "\n");
                        out.println(notFound.readLine());

                    }
                }
            }
        }
    }
}