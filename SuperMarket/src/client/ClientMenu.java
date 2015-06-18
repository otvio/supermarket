
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


    public void menu()
    {        
        
        while(!cases.equals("4"))
        {
            do
            {
                System.out.println("\n\t:::::    Welcome to the LORMarket, " + nameClient + "    :::::\n");                
                System.out.println("1 - List all the products.");       
                System.out.println("2 - Desires list.");
                System.out.println("3 - Exit.");

                cases = sc.nextLine();

            }while(!("1").equals(cases) && !("2").equals(cases) && !("3").equals(cases) && !("4").equals(cases));


            switch (cases)
            {               
                case "1":
                    listAllProducts();
                    break;

                case "2":
                    desireList();
                    break;                 

            }
            if (!("3").equals(cases))
            {
                System.out.println("\nPressione ENTER para continuar...");
                cases = sc.nextLine();                
                cases = (cases.equals("3")) ? "-" : cases;
            }
        } 
    }  
    
    public void listAllProducts()
    {
        int index = 0, productnumber;
                
        System.out.println("\n ...::: All products  :::...\n\n");
        for(Product p : productList)
        {
            System.out.println("Product " + (index + 1)+ "." );
            p.printProduct(categoryList.get(p.getCodCategory()));
            index++;
        }            
        
        do
        {
            System.out.println("Shop now any product? (Y)-Yes , (N)-No " );
            cases = sc.nextLine().toUpperCase();
            
        }while(!cases.equals("Y") && !cases.equals("N"));
        
        if(cases.equals("Y"))
        {
             System.out.println("What product do you want to buy? ");
             
             productnumber = sc.nextInt();
             
             if(productList.get(productnumber -1).getStockUnits() > 0)
                productList.get(productnumber -1).setStockUnits(productList.get(productnumber - 1).getStockUnits() - 1);
             else
             {
                 System.out.println("The product isn't available. =(");
                 
                 do
                 {
                     System.out.println("Do you want to be informed when the product be available? (Y)-Yes , (N)-No ");
                     cases = sc.nextLine().toUpperCase();

                 }while(!cases.equals("Y") && !cases.equals("N"));
                 
                 if(cases.equals("Y"))
                 {
                     //fazer notificação do usuário caso ele solicite o produto não existente
                 }
                 else
                 {
                     
                 }
                 
             }             
        }   
    }
    
    public void desireList()
    {
        
    
    }
    public void placeOrder()
    {
        
        
    }
   
}
