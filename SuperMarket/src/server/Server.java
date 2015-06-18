
package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import supermarket.entities.Product;

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
        
        ServerMenu servermenu = new ServerMenu();
        
        servermenu.showMenu();
    }
    public List<Product> BringList(){
        
        List <Product> list = new ArrayList<> ();
        String line;
        BufferedReader buffreader;
        
        try{
            
            buffreader = new BufferedReader(new FileReader(PRODUCTS_FILE));
            
            while(buffreader.ready()){
                
                line = buffreader.readLine();
            
                String[] products = line.split(",");
                
                list.add(new Product(Integer.parseInt(products[0]), Integer.parseInt(products[1]), Integer.parseInt(products[2]), 
                    Integer.parseInt(products[3]), Integer.parseInt(products[4]), Double.parseDouble(products[5]), products[6],
                    products[7]));
            }
        }
        
        catch(Exception e){
            System.out.println("Something is wrong :(");
        }
        
       
        
        return list;
    }
}
