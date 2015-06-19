
package server;

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

    public String receiveFromClient()
    {
        return ((fromClient.hasNextLine()) ? fromClient.nextLine() : "");
    }

    @Override
    public void run()
    {
        while(fromClient.hasNextLine()) // loop para ficar recebendo do cliente
        {
            String message = fromClient.nextLine();
            System.out.println(message);
            toClient.println(message);
        }
    }
}
