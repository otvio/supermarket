
package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.ServerSocket;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import supermarket.entities.Category;
import supermarket.entities.Product;

public class Server 
{
    // Singleton pattern:
    private static Server server = null;
    
    // defines para os nomes dos arquivos
    public static final String USERS_FILE = "users.csv";
    public static final String SALES_FILE = "sales.csv";
    public static final String DESIRE_FILE = "desires.csv";
    public static final String PRODUCTS_FILE = "products.csv";
    public static final String SUPPLIERS_FILE = "suppliers.csv";
    public static final String CATEGORIES_FILE = "categories.csv";
    
    private static Scanner scanner;
    private static ServerSocket serverSocket;
    private static List<ClientStruct> clientList;
    private static ClientConnection clientConnection = null;
    
    ////////////////////////////////////////////////////////////////////////////
    
    // tenta criar o server no construtor
    private Server() throws Exception 
    {
        Server.serverSocket = new ServerSocket(12345);
        Server.scanner = new Scanner(System.in);
        Server.clientList = new ArrayList<>();
    }
    
    public static synchronized Server getInstance() throws Exception
    {
        if (Server.server == null)
            Server.server = new Server();
        
        return Server.server;
    }
    
    public static void main (String[] args)
    {
        try
        {
            getInstance();
            System.out.println("::: Server Connected! :) :::");
            
            if (clientConnection == null)
            {
                clientConnection = ClientConnection.getInstance(serverSocket, clientList);
                new Thread(clientConnection).start();
            }
            
            ServerMenu servermenu = new ServerMenu(clientList);
            servermenu.showMenu();
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
	
    public static List<Product> BringList()
    {
        List<Product> list = new ArrayList<>();
        String line;
        BufferedReader buffreader;
        
        try
        {
            buffreader = new BufferedReader(new FileReader(PRODUCTS_FILE));
            
            while(buffreader.ready())
            {
                line = buffreader.readLine();
            
                String[] products = line.split(",");
                
                list.add(new Product(Integer.parseInt(products[0]), Integer.parseInt(products[1]), Integer.parseInt(products[2]), 
                    Integer.parseInt(products[3]), Integer.parseInt(products[4]), Double.parseDouble(products[5]), products[6],
                    products[7]));
            }
            
            buffreader.close();
        }
        catch(Exception e)
        {
            System.out.println("Something is wrong :(");
        }
        
        return list;
    }
    
    public static List<Category> BringCategoryList()
    {
        List<Category> list = new ArrayList<>();
        String line;
        BufferedReader buffreader;
        
        try
        {
            buffreader = new BufferedReader(new FileReader(CATEGORIES_FILE));
            
            while(buffreader.ready())
            {
                line = buffreader.readLine();
            
                String[] products = line.split(",");
                
                list.add(new Category(Integer.parseInt(products[0]), products[1], products[2]));
            }
            
            buffreader.close();
        }
        catch(Exception e)
        {
            System.out.println("Something is wrong :(");
        }
        
        return list;
    }
}