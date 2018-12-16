/* The Client Class - Written by Derek Molloy for the EE402 Module
 * See: ee402.eeng.dcu.ie
 * 
 * 
 */

package ee402a;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Client {
	
	private static int portNumber = 5050;
    private Socket socket = null;
    private ObjectOutputStream os = null;
    private ObjectInputStream is = null;
    private static String PATH = "/sys/class/thermal/thermal_zone0/temp";

    
    
    

    
    

	// the constructor expects the IP address of the server - the port is fixed
    public Client(String serverIP) {
    	if (!connectToServer(serverIP)) {
    		System.out.println("XX. Failed to open socket connection to: " + serverIP);            
    	}
    }

    private boolean connectToServer(String serverIP) {
    	try { // open a new socket to the server 
    		this.socket = new Socket(serverIP,portNumber);
    		this.os = new ObjectOutputStream(this.socket.getOutputStream());
    		this.is = new ObjectInputStream(this.socket.getInputStream());
    		System.out.println("00. -> Connected to Server:" + this.socket.getInetAddress() 
    				+ " on port: " + this.socket.getPort());
    		System.out.println("    -> from local address: " + this.socket.getLocalAddress() 
    				+ " and port: " + this.socket.getLocalPort());
    	} 
        catch (Exception e) {
        	System.out.println("XX. Failed to Connect to the Server at port: " + portNumber);
        	System.out.println("    Exception: " + e.toString());	
        	return false;
        }
		return true;
    }

    private void getDate() throws IOException {
    	
    	/*
    	Scanner sc;
    	File f = new File("/sys/class/thermal/thermal_zone0/temp");
    	try {
            

            sc = new Scanner(f);
            
            while(sc.hasNextLine())

            {

                    System.out.println(sc.nextLine());

            }
        }
        catch (FileNotFoundException ex) {
            System.out.println("File not found!");
        }
        */
    	
    	/*
    	
    	BufferedReader bw;
    	int read = 0;
		try {
			bw = new BufferedReader ( new FileReader ("PATH"));
			read = bw.read();
	        bw.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		*/
		
		System.out.println("Temp value to the server...");
		
		//float temp = read/1000;
        //System.out.println("Temperature is: " + temp);
    	
    	
    	
    	
    	String theDateCommand = "GetDate", theDateAndTime;
    	System.out.println("01. -> Sending Command (" + theDateCommand + ") to the server...");
    	Fish aFish = new Fish("Jerry", "Salmon");
    	//this.send(theDateCommand);
    	this.send(aFish);
    	/*try{
    		theDateAndTime = (String) receive();
    		System.out.println("05. <- The Server responded with: ");
    		System.out.println("    <- " + theDateAndTime);
    	}
    	catch (Exception e){
    		System.out.println("XX. There was an invalid object sent back from the server");
    	}*/
    	try{
    		aFish = (Fish) receive();
    		System.out.println("05. <- The Server responded with: ");
    		System.out.println("    <- " + aFish.getName());
    		System.out.println("    <- " + aFish.getType());
    	}
    	catch (Exception e){
    		System.out.println("XX. There was an invalid object sent back from the server");
    	}
    	System.out.println("06. -- Disconnected from Server.");
    }
	
    // method to send a generic object.
    private void send(Object o) {
		try {
		    System.out.println("02. -> Sending an object...");
		    System.out.println("02. -> Sending an object..." + o.getClass());
		    os.writeObject(o);
		    os.flush();
		} 
	    catch (Exception e) {
		    System.out.println("XX. Exception Occurred on Sending:" +  e.toString());
		}
    }

    // method to receive a generic object.
    private Object receive() 
    {
		Fish o = null;
		try {
			System.out.println("03. -- About to receive an object...");
		    o = (Fish)is.readObject();
		    
		    System.out.println("04. <- Object received...");
		} 
	    catch (Exception e) {
		    System.out.println("XX. Exception Occurred on Receiving:" + e.toString());
		}
		return o;
    }

    public static void main(String args[]) throws IOException 
    {
    	System.out.println("**. Java Client Application - EE402 OOP Module, DCU");
    	if(args.length==1){
    		Client theApp = new Client(args[0]);
		    theApp.getDate();
		}
    	else
    	{
    		System.out.println("Error: you must provide the address of the server");
    		System.out.println("Usage is:  java Client x.x.x.x  (e.g. java Client 192.168.7.2)");
    		System.out.println("      or:  java Client hostname (e.g. java Client localhost)");
    	}    
    	System.out.println("**. End of Application.");
    }
}