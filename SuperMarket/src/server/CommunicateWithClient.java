
package server;

import login.Login;
import login.LoginAttempt;
import command.*;
import static command.Command.*;

import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import static server.Server.*;
import supermarket.entities.*;

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
                    
                    sendToClient(new Command(new String[]{LOGIN, attempt.name(),
                        (attempt == LoginAttempt.SUCCESS) ? login.getNameClient() : ""}).get());

                    if (attempt == LoginAttempt.SUCCESS)
                    {
                        sendProducts();
                        sendCategories();
                        sendDesires(login.getCodClient());
                    }

                    break;
                    
                case NEWUSER:
                    login = new Login(command, this);
                    attempt = login.createNewUser();
                    
                    sendToClient(new Command(new String[]{NEWUSER, attempt.name(),
                        (attempt == LoginAttempt.SUCCESS) ? login.getNameClient() : ""}).get());
                    
                    if (attempt == LoginAttempt.SUCCESS)
                    {
                        sendProducts();
                        sendCategories();
                        sendDesires(login.getCodClient());
                    }
                    
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
    
    public void sendProducts()
    {
        List<Product> productlist = BringList();
        
        for (Product product : productlist)
        {
            sendToClient(new Command(new String[]{
                SEND_PRODUCT,
                String.valueOf(product.getCodProduct()), 
                String.valueOf(product.getCodSupplier()),
                String.valueOf(product.getCodCategory()), 
                String.valueOf(product.getStockUnits()), 
                String.valueOf(product.getOrderedUnits()),
                String.valueOf(product.getUnitPrice()), 
                String.valueOf(product.getNameProduct()), 
                String.valueOf(product.getValidity())
            }).get());
        }
    }

    private void sendCategories() 
    {
        List<Category> list = BringCategoryList();
        for (Category c : list)
        {
            sendToClient(new Command(new String[]{
                SEND_CATEGORY,
                String.valueOf(c.getCodCategory()),
                String.valueOf(c.getNameCategory()), 
                String.valueOf(c.getDescription())
            }).get());
        }
    }

    private void sendDesires(int codClient) 
    {
        List<Integer> list;
        try
        {
            list = ClientConnection.getUserDesires(codClient);
            
            for (Integer c : list)
                sendToClient(new Command(new String[]{SEND_DESIRE, String.valueOf(c)}).get());
            
        } catch (Exception ex) { }
    }
}
