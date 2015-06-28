
package client;

import command.Command;
import static command.Command.*;
import login.LoginAttempt;
import java.io.PrintStream;
import java.util.Scanner;

public class CommunicateWithServer implements Runnable
{
    private Client client;
    private ClientMenu clientMenu;
    private final PrintStream toServer;
    private final Scanner fromServer;
    
    public CommunicateWithServer(Client client, PrintStream sendToServer, Scanner receiveFromServer)
    {
        this.fromServer = receiveFromServer;
        this.toServer = sendToServer;
        this.client = client;
    }
    
    public void sendToServer(String message)
    {
        toServer.println(message);
    }

    public String receiveFromServer()
    {
        return ((fromServer.hasNext()) ? fromServer.next() : "");
    }
    
    @Override
    public synchronized void run()
    {
        Command command;
        LoginAttempt login;
        
        while(fromServer.hasNextLine())  // loop para ficar recebendo do servidor
        {
            command = new Command(fromServer.nextLine());
            
            switch (command.getArray()[0]) 
            {
                case LOGIN:
                    login = LoginAttempt.valueOf(command.getArray()[1]);
                    checkLogin(command, login);
                    break;
                
                case NEWUSER:
                    login = LoginAttempt.valueOf(command.getArray()[1]);
                    checkLogin(command, login);
                    break;
                
//                case USERNAME:
//                    clientMenu = new ClientMenu(command[1], this);
//                    clientMenu.menu();
//                    break;
                    
                case SIMPLETEXT:
                    System.out.println((command.getArray().length == 1) ? "" : command.getArray()[1]);
                    break;
                    
                default:
                    System.out.println(command.get());
                    break;
            }
        }
    }

    private void checkLogin(Command command, LoginAttempt login) 
    {
        switch (login)
        {
            case SUCCESS:
                System.out.println("\n::: You are now logged in! :::\n");
                clientMenu = new ClientMenu(command.getArray()[2], this);
                clientMenu.menu();
                break;

            case ALREADY_LOGGED:
                System.out.println("\n::: This user is already logged in.");
                System.out.println("::: Try again later.\n");
                client.loginAttempt();
                break;

            case FAILED:
                System.out.println("\n::: Incorrect ID/password! :::\n");
                client.loginAttempt();
                break;

            case ALREADY_EXISTS:
                System.out.println("\n::: This user already exists! :::");
                client.loginAttempt();
                break;
        }
    }
}
