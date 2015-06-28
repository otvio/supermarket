
package client;

import java.util.*;
import supermarket.entities.*;


public class ClientMenu
{
    private CommunicateWithServer communicateWithServer;
    private String nameClient;
    private String cases = "";
    
    private Scanner input;
    
    private List <Category> categoryList = new ArrayList<>();
    private List <Product> productList = new ArrayList<>();
    private List <Integer> desireList = new ArrayList<>();
        
    
    public ClientMenu(String nameClient, CommunicateWithServer communicateWithServer)
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
            
            System.out.println("4 - List all the products list and desires list (JUST TESTING)");
        
            cases = input.nextLine();

            switch (cases)
            {
                case "1":
                    listAllProducts();
                    break;

                case "2":
                    desireList();
                    break;
                   
                case "4":
                    System.out.println("Product List:");
                    
                    Collections.sort(productList, new Comparator<Product>()
                    {
                        public int compare(Product e1, Product e2) 
                        {
                            return (e1.getCodProduct() < e2.getCodProduct()) ? -1 : 1;
                        }
                    });
                    
                    
                    for (Product product : productList) 
                        product.printProduct(categoryList.get(product.getCodCategory()));

                    System.out.println("Desire List:");
                    
                    for (Integer i : desireList) 
                        System.out.println(i);
                    
                    System.out.println("::: :::");
                    
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
        int index = 0, productnumber;
                
        System.out.println("\n ...::: All products  :::...\n\n");
        
        Collections.sort(productList, new Comparator<Product>()
        {
            public int compare(Product e1, Product e2) 
            {
                return (e1.getStockUnits() < e2.getStockUnits()) ? 1 : -1;
            }
        });
        
        for(Product p : productList)
        {
            System.out.println("Product " + (index + 1)+ "." );
            p.printProduct(categoryList.get(p.getCodCategory()));
            index++;
        }            
        
        do
        {
            System.out.println("Shop now any product? (Y)-Yes , (N)-No " );
            cases = input.nextLine().toUpperCase();
            
        }while(!cases.equals("Y") && !cases.equals("N"));
        
        if(cases.equals("Y"))
        {
             System.out.println("What product do you want to buy? ");
             
             productnumber = input.nextInt();
             
             if(productList.get(productnumber -1).getStockUnits() > 0)
                productList.get(productnumber -1).setStockUnits(productList.get(productnumber - 1).getStockUnits() - 1);
             else
             {
                 System.out.println("The product isn't available. =(");
                 
                 do
                 {
                     System.out.println("Do you want to be informed when the product be available? (Y)-Yes , (N)-No ");
                     cases = input.nextLine().toUpperCase();

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
   
    public List<Category> getCategoryList()
    {
        return categoryList;
    }

    public List<Product> getProductList() 
    {
        return productList;
    }

    public List<Integer> getDesireList() 
    {
        return desireList;
    }
}
