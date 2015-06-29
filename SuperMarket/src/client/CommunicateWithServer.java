
package client;

import command.Command;
import static command.Command.*;
import login.LoginAttempt;
import java.io.PrintStream;
import java.util.Scanner;
import supermarket.entities.*;

public class CommunicateWithServer implements Runnable
{
    private Client client;
    private ClientMenu clientMenu;
    private final PrintStream toServer;
    private final Scanner fromServer;
    private final CommunicateWithServer cws;
    
    public CommunicateWithServer(Client client, PrintStream sendToServer, Scanner receiveFromServer)
    {
        this.fromServer = receiveFromServer;
        this.toServer = sendToServer;
        this.client = client;
        this.cws = this;
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
    public void run()
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
                
                case SEND_PRODUCT:
                    addProduct(command);
                    break;
                
                case SEND_CATEGORY:
                    addCategory(command);
                    break;
                
                case SEND_DESIRE:
                    addDesire(command);
                    break;
                    
                case SEND_UPDATE:
                    clientMenu.updateProducts(Integer.parseInt(command.getArray()[1]), Integer.parseInt(command.getArray()[2]));
                    break;
                    
                case SIMPLETEXT:
                    System.out.println((command.getArray().length == 1) ? "" : command.getArray()[1]);
                    break;
                    
                case REMOVE_DESIRE:
                    clientMenu.getDesireList().remove(Integer.parseInt(command.getArray()[1]));
                    
                default:
                    System.out.println(command.get());
                    break;
            }
        }
    }

    private synchronized void checkLogin(final Command command, LoginAttempt login) 
    {
        switch (login)
        {
            case SUCCESS:
                System.out.println("\n::: You are now logged in! :::\n");
                clientMenu = new ClientMenu(command.getArray()[2], cws);
                new Thread()
                {
                    @Override
                    public void run()
                    {
                        clientMenu.menu();
                    }
                }.start();
                
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

    private synchronized void addProduct(Command command)
    {
        clientMenu.getProductList().add(new Product(
                Integer.parseInt(command.getArray()[1]), 
                Integer.parseInt(command.getArray()[2]),
                Integer.parseInt(command.getArray()[3]), 
                Integer.parseInt(command.getArray()[4]), 
                Integer.parseInt(command.getArray()[5]),
                Double.parseDouble(command.getArray()[6]), 
                command.getArray()[7], command.getArray()[8]));
    }

    private synchronized void addCategory(Command command)
    {
        clientMenu.getCategoryList().add(new Category(
                Integer.parseInt(command.getArray()[1]), 
                command.getArray()[2], command.getArray()[3]));
    }

    private synchronized void addDesire(Command command)
    {
        clientMenu.getDesireList().add(Integer.parseInt(command.getArray()[1]));
    }
}
