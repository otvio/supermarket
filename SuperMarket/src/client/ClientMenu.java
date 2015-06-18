
package client;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import supermarket.entities.*;


public class ClientMenu
{
    private String nameClient;    
    String cases;
    
    Scanner sc = new Scanner(System.in);
    
    List <Category> categoryList = new ArrayList<>();
    List <Product> productList = new ArrayList<>();
    List <Product> desireList = new ArrayList<>();
        
    public ClientMenu(String nameClient)
    {
        this.nameClient = nameClient;
    }


    public void printMenu()
    {        
        do
        {
            System.out.println("\n\t:::::    Welcome to the LORMarket, " + nameClient + "    :::::\n");
            System.out.println("1 - Search a product");
            System.out.println("2 - List all the products.");
            System.out.println("3 - Place order.");
            System.out.println("4 - List desires.");
            System.out.println("5 - Exit.");
            
            cases = sc.nextLine();
            
        }while(!("1").equals(cases) && !("2").equals(cases) && !("3").equals(cases) && !("4").equals(cases));
        
        
        switch (cases)
        {
            case "1":
                searchProduct();
                break;
                
            case "2":
                listAllProducts();
                break;
                
            case "3":
                desireList();
                break; 
                
            case "4":
                placeOrder();
                break; 
        }
         if (!("5").equals(cases))
            {
                System.out.println("\nPressione ENTER para continuar...");
                cases = sc.nextLine();                
                cases = (cases.equals("10")) ? "-" : cases;
            }
    }
    
    public void searchProduct()
    {
        System.out.println(nameClient + ", digit the product category: ");
        
        
    }
    
    public void listAllProducts()
    {
        System.out.println("\n ...::: All products  :::...\n\n");
        for(Product p : productList)
            p.printProduct(categoryList.get(p.getCodCategory()));
        
        do
        {
            System.out.println("Shop now any product? (Y)-Yes , (N)-No" );
            cases = sc.nextLine().toUpperCase();
            
        }while(!cases.equals("Y") && !cases.equals("N"));
        
        if(cases == "Y")
        {
            
        } 
        else
            printMenu();
  
    }
    
    public void desireList()
    {
        
    
    }
    public void placeOrder()
    {
        
        
    }
   
}
