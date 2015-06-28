
package server;

import static connection.Connection.*;
import java.io.PrintStream;
import java.util.Scanner;

public class CommunicateWithClient implements Runnable
{
    private final PrintStream toClient;
    private final Scanner fromClient;
    private String nameClient;

    public CommunicateWithClient(PrintStream sendToClient, Scanner receiveFromClient) 
    {
        this.toClient = sendToClient;
        this.fromClient = receiveFromClient;
    }
    
    public void sendHeader()
    {
        toClient.println("\n\t:::::    Welcome to the LORMarket, " + nameClient + "    :::::\n");
    }
    
    public void setNameClient(String nameClient)
    {
        this.nameClient = nameClient;
    }
    
    public void sendToClient(String message)
    {
        toClient.println(message);
    }
    
    public void sendToClient_SimpleText(String message)
    {
        toClient.println(SIMPLETEXT + DELIMITER + message);
    }

    public String receiveFromClient()
    {
        return ((fromClient.hasNext()) ? fromClient.next() : "");
    }

    @Override
    public void run()
    {
        String[] command;

        while(fromClient.hasNext()) // loop para ficar recebendo do cliente
        {
            String message = fromClient.nextLine();
            command = message.split("\\|");
            
            
            
            System.out.println(message);
            //toClient.println(message);
        }
    }
}
