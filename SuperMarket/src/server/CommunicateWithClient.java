
package server;

import login.Login;
import login.LoginAttempt;
import command.*;
import static command.Command.*;

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
        Login login;
        Command command;
        LoginAttempt attempt;
        
        while(fromClient.hasNext()) // loop para ficar recebendo do cliente
        {
            command = new Command(fromClient.nextLine());
            
            switch (command.getArray()[0]) 
            {
                case LOGIN:
                    login = new Login(command, this);
                    attempt = login.loginAttempt();
                    sendToClient(new Command(new String[]{LOGIN, attempt.name(), (attempt == LoginAttempt.SUCCESS) ? login.getNameClient() : ""}).get());
                    break;
                    
                case NEWUSER:
                    login = new Login(command, this);
                    attempt = login.createNewUser();
                    sendToClient(new Command(new String[]{NEWUSER, attempt.name(), (attempt == LoginAttempt.SUCCESS) ? login.getNameClient() : ""}).get());
                    break;
                    
                case SIMPLETEXT:
                    System.out.println((command.getArray().length == 1) ? "" : command.getArray()[1]);
                    break;
                    
                default:
                    System.out.println(command.get());
                    break;
            }
        }
    }
}
