package packClient;

import java.net.*;
import java.io.*;
import java.util.Scanner;



public class Client {
	
	public static void main(String[] args) throws IOException{
		System.out.println("client started");
		Socket s = new Socket("localhost", 4999);
		PrintWriter pr = new PrintWriter(s.getOutputStream());
		System.out.println("connected to server " + s);

		
		while(true) {
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			String m = input.readLine();
			pr.println(m);
			pr.flush();
			
			InputStreamReader in =new InputStreamReader(s.getInputStream());
			BufferedReader bf = new BufferedReader(in);
			
			String str = bf.readLine();
			System.out.println("server : " + str);
		}
	}
}

