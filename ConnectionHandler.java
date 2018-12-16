 /* The Connection Handler Class - Written by Derek Molloy for the EE402 Module
 * See: ee402.eeng.dcu.ie
 */

package ee402a;

import java.net.*;
import java.io.*;

public class ConnectionHandler extends Thread
{
    private Socket clientSocket = null;    // Client socket object
    private ObjectInputStream is = null;   // Input stream
    private ObjectOutputStream os = null;   // Output stream
    private DateTimeService theDateService;
    private Fish zeFish = null;
    private Fish newFish = null;
   
// The constructor for the connection handler
    public ConnectionHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        //Set up a service object to get the current date and time
        theDateService = new DateTimeService();
        String v = null,b = null;
        newFish = new Fish("Garry","Bask");
       
    }

    // Will eventually be the thread execution method - can't pass the exception back
    public void run()  {
         try {
            this.is = new ObjectInputStream(clientSocket.getInputStream());
            this.os = new ObjectOutputStream(clientSocket.getOutputStream());
            this.readCommand();
         }
         catch (IOException e)
         {
         System.out.println("XX. There was a problem with the Input/Output Communication:");
            e.printStackTrace();
         }
    }

    // Receive and process incoming string commands from client socket
   
private boolean readCommand() {
 String s = null;
        String a = "1";
 
 
 try {
  //System.out.println(is.readObject().getClass());
 
  //if (is.readObject().getClass().getName().equals("ee402a.Fish"))
   
	 Object o = (Object)is.readObject();
	   
	   if (o.getClass().getName().equals("ee402a.Fish"))
	   {
		   zeFish = (Fish) o;
	   System.out.println(zeFish.getName());
	   }
   
  /*else if (is.readObject().getClass().getName().equals("java.lang.String"))
   
  {
   try {
             s = (String) is.readObject();
             System.out.println("ELse called!!");
             
         }
         catch (Exception e){    // catch a general exception
          this.closeSocket();
             return false;
         }
  }*/
  else {System.out.println("Else called!");}
 
 
 
 } catch (ClassNotFoundException e1) {
  // TODO Auto-generated catch block
  e1.printStackTrace();
 
 } catch (IOException e) {
  // TODO Auto-generated catch block
  e.printStackTrace();
 }
 
 
 
 
       
       
       
       
       
       
       
       
        //else {System.out.println("No class found!");}
        System.out.println("\nFish name is: "+ zeFish.getType());
       
        System.out.println("01. <- Received a String object from the client (" + s + ").");
        System.out.println("HELLO");
        // At this point there is a valid String object
        // invoke the appropriate function based on the command
        if (zeFish.getName().equalsIgnoreCase("jerry")){
            this.getDate();
        }      
        else {
            this.sendError("Invalid command: " + s);
        }
        return true;
    }

    // Use our custom DateTimeService Class to get the date and time
    private void getDate() { // use the date service to get the date
        //String currentDateTimeText = theDateService.getDateAndTime();
        //this.send(currentDateTimeText);
        
        Object j =  (Object)newFish;
        this.send(j);
    }

    // Send a generic object back to the client
    private void send(Object o) {
        try {
            System.out.println("02. -> Sending (" + o +") to the client.");
            this.os.writeObject(o);
            this.os.flush();
        }
        catch (Exception e) {
            System.out.println("XX." + e.getStackTrace());
        }
    }
   
    // Send a pre-formatted error message to the client
    public void sendError(String message) {
        this.send("Error:" + message); //remember a String IS-A Object!
    }
   
    // Close the client socket
    public void closeSocket() { //gracefully close the socket connection
        try {
            this.os.close();
            this.is.close();
            this.clientSocket.close();
        }
        catch (Exception e) {
            System.out.println("XX. " + e.getStackTrace());
        }
    }
}

