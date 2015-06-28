
package client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import supermarket.entities.*;


public class ClientMenu
{
    private CommunicateWithServer communicateWithServer;
    private String nameClient;
    String cases = "";
    
    private Scanner input;
    
    List <Category> categoryList = new ArrayList<>();
    List <Product> productList = new ArrayList<>();
    List <Product> desireList = new ArrayList<>();
        
    
    public ClientMenu(String nameClient, CommunicateWithServer communicateWithServer, Scanner input)
    {
        this.communicateWithServer = communicateWithServer;
        this.nameClient = nameClient;
        this.input = new Scanner(System.in);
    }


    public void menu()
    {
        
        do{
            System.out.println("\n\t:::::    Welcome to the LORMarket, " + nameClient + "    :::::\n");                
            System.out.println("1 - List all the products.");       
            System.out.println("2 - Desires list.");
            System.out.println("3 - Exit.");
        
            cases = input.nextLine();
            
            System.out.println("cases:{" + cases + "}");

            switch (cases)
            {
                case "1":
                    listAllProducts();
                    break;

                case "2":
                    desireList();
                    break;                 
            }
            
//            if (!("3").equals(cases))
//            {
//                System.out.println("\nPress ENTER to continue...");
//                cases = input.nextLine();                
//                cases = (cases.equals("3")) ? "-" : cases;
//            }
        } while (!cases.equals("3"));
    }  
    
    public void listAllProducts()
    {
        System.out.println("listAllProducts--------");
//        int index = 0, productnumber;
//                
//        System.out.println("\n ...::: All products  :::...\n\n");
//        for(Product p : productList)
//        {
//            System.out.println("Product " + (index + 1)+ "." );
//            p.printProduct(categoryList.get(p.getCodCategory()));
//            index++;
//        }            
//        
//        do
//        {
//            System.out.println("Shop now any product? (Y)-Yes , (N)-No " );
//            cases = input.nextLine().toUpperCase();
//            
//        }while(!cases.equals("Y") && !cases.equals("N"));
//        
//        if(cases.equals("Y"))
//        {
//             System.out.println("What product do you want to buy? ");
//             
//             productnumber = input.nextInt();
//             
//             if(productList.get(productnumber -1).getStockUnits() > 0)
//                productList.get(productnumber -1).setStockUnits(productList.get(productnumber - 1).getStockUnits() - 1);
//             else
//             {
//                 System.out.println("The product isn't available. =(");
//                 
//                 do
//                 {
//                     System.out.println("Do you want to be informed when the product be available? (Y)-Yes , (N)-No ");
//                     cases = input.nextLine().toUpperCase();
//
//                 }while(!cases.equals("Y") && !cases.equals("N"));
//                 
//                 if(cases.equals("Y"))
//                 {
//                     //fazer notificação do usuário caso ele solicite o produto não existente
//                 }
//                 else
//                 {
//                     
//                 }
//                 
//             }             
//        }   
    }
    
    public void desireList()
    {
        System.out.println("desireList--------");
    
    }
    public void placeOrder()
    {
        
        
    }
   
}
