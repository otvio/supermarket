
package server;

import java.net.ServerSocket;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Server 
{
    // defines para os nomes dos arquivos
    public static final String USERS_FILE = "users.csv";
    public static final String SALES_FILE = "sales.csv";
    public static final String PRODUCTS_FILE = "products.csv";
    public static final String SUPPLIERS_FILE = "suppliers.csv";
    public static final String CATEGORIES_FILE = "categories.csv";
    
    private static Scanner scanner;
    private static String beNotified;
    private static ServerSocket serverSocket;
    private static List<ClientStruct> clientList;
    
    ////////////////////////////////////////////////////////////////////////////
    
    // tenta criar o server no construtor
    public static void createServer() throws Exception 
    {
        Server.serverSocket = new ServerSocket(12345);
    }
    
    public static void main (String[] args)
    {
        scanner = new Scanner(System.in);
        
        try
        {
            createServer();
            clientList = new ArrayList<>();
            
            System.out.println("::: Server Connected! :) :::");
            
            do
            {
                System.out.println("\nDo you want to be notified when a new user enter?\n"
                                   + "   (Y). Yes\n"
                                   + "   (N). No\n");
                beNotified = scanner.nextLine().toUpperCase();
            } while (!beNotified.equals("Y") && !beNotified.equals("N"));
            
            new Thread(new ClientConnection(serverSocket, clientList, beNotified.equals("Y"))).start();
        }
        catch (Exception e)
        {
            System.out.println("::: I'm so sorry! Server Error! :( :::");
        }
        finally
        {
            //System.out.println("\n\n:::Thank you for using this program. :::");
        }
    }
}
