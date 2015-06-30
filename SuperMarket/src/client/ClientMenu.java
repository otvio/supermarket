
package client;

import java.util.*;
import command.Command;
import supermarket.entities.*;
import static command.Command.*;

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
        this.input = new Scanner(System.in);
        this.nameClient = nameClient;
    }


    public void menu()
    {
        do
        {
            System.out.print("\n\t:::::::::::::::::::::::::::::::::::");
            for (int i = 0; i < nameClient.length() + 9; i++) { System.out.print(":"); }
            System.out.println("\n\t:::::    Welcome to the LORMarket, " + nameClient + "    :::::");    
            System.out.print("\t:::::::::::::::::::::::::::::::::::");
            for (int i = 0; i < nameClient.length() + 9; i++) { System.out.print(":"); }
            System.out.println("\n\n");
            
            System.out.println(":::::::::::::::::::::::::::::::::::");
            System.out.println("::  1 - List all the products.   ::");   
            System.out.println("::  2 - View desire list.        ::");
            System.out.println("::  3 - Add to the desire list.  ::");
            System.out.println("::  4 - Buy a product.           ::");
            System.out.println("::  5 - Exit.                    ::");
            System.out.println(":::::::::::::::::::::::::::::::::::\n");
            
            System.out.print("::  Type your choice: ");
            cases = input.nextLine();

            switch (cases)
            {
                case "1":
                    listAllProducts();
                    break;

                case "2":
                    showDesireList();
                    break;
                    
                case "3":
                    addDesireList();
                    break;
                   
                case "4":
                    buyProduct();                    
                    break;
            }
            
            if (!("5").equals(cases))
            {
                System.out.println("\nPress ENTER to continue...");
                cases = input.nextLine();                
                cases = (cases.equals("5")) ? "-" : cases;
            }
        } while (!cases.equals("5"));
    }  
    
    public void listAllProducts()
    {
        int productnumber, units;
        
        System.out.println("\n\t::::::::::::::::::::::::::::::::::::::::");
        System.out.println(  "\t::::      Listing all products      ::::");
        System.out.println(  "\t::::      -Ordering by units-       ::::");
        System.out.println(  "\t::::::::::::::::::::::::::::::::::::::::\n\n");
        
        Collections.sort(productList, new Comparator<Product>()
        {
            @Override
            public int compare(Product e1, Product e2) 
            {
                return (e1.getStockUnits() < e2.getStockUnits()) ? 1 : -1;
            }
        });
        
        for(Product p : productList)
        {
            System.out.println("Product " + (p.getCodProduct())+ "." );
            p.printProduct(categoryList.get(p.getCodCategory()));
        }

//        do
//        {
//            System.out.println("Shop now any product? (Y)-Yes , (N)-No " );
//            cases = input.nextLine().toUpperCase();
//            
//        }while(!cases.equals("Y") && !cases.equals("N"));
//        
//        if(cases.equals("Y"))
//        {
//            System.out.println("What product do you want to buy? ");
//             
//            productnumber = input.nextInt();
//            System.out.println("How many units? ");
//            
//            units = input.nextInt();
//            
//            Collections.sort(productList, new Comparator<Product>()
//            {
//                @Override
//                public int compare(Product e1, Product e2) 
//                {
//                    return (e1.getCodProduct() < e2.getCodProduct()) ? -1 : 1;
//                }
//            });
//            
//            if(units > productList.get(productnumber).getStockUnits())
//            {
//                cases = input.nextLine().toUpperCase();
//                do{
//                    System.out.println("We have only (" + productList.get(productnumber).getStockUnits() + ") units of this product.");
//                    System.out.println("Do you want to be informed when we have more units? (Y)-Yes , (N)-No ");
//                
//                    cases = input.nextLine().toUpperCase();
//                    System.out.println(cases);
//                }while(!cases.equals("Y") && !cases.equals("N"));
//                
//                if(cases.equals("Y"))
//                {
//                    if(desireList.indexOf(productList.get(productnumber).getCodProduct()) == -1){
//
//                        desireList.add(productList.get(productnumber).getCodProduct());
//                        communicateWithServer.sendToServer(new Command(new String[]{
//                            ADD_DESIRE, nameClient, 
//                            String.valueOf(productList.get(productnumber).getCodProduct()),
//                        }).get());
//                    }
//                    else{
//                        System.out.println("This product is already in your list");
//                    }
//                }
//                
//                communicateWithServer.sendToServer(new Command(new String[]{
//                    PURCHASE, 
//                    String.valueOf(productList.get(productnumber).getCodProduct()),
//                    String.valueOf(productList.get(productnumber).getStockUnits()), 
//                    nameClient,
//                    String.valueOf(productList.get(productnumber).getCodSupplier())
//                }).get());
//            }
//            else if(productList.get(productnumber).getStockUnits() > 0 && productList.get(productnumber).getStockUnits() >= units){
//                communicateWithServer.sendToServer(new Command(new String[]{
//                    PURCHASE, 
//                    String.valueOf(productList.get(productnumber).getCodProduct()), 
//                    String.valueOf(units), 
//                    nameClient,
//                    String.valueOf(productList.get(productnumber).getCodSupplier())
//                }).get());
//            }
//            else
//            {
//                System.out.println("The product isn't available. =(");
//                cases = "=(";
//                do
//                {
//                    System.out.println("Do you want to be informed when the product be available? (Y)-Yes , (N)-No ");
//                    cases = input.nextLine().toUpperCase();
//
//                }while(!cases.equals("Y") && !cases.equals("N"));
//                 
//                if(cases.equals("Y"))
//                {
//                    if(desireList.indexOf(productList.get(productnumber).getCodProduct()) == -1){
//
//                        desireList.add(productList.get(productnumber).getCodProduct());
//                        communicateWithServer.sendToServer(new Command(new String[]{
//                            ADD_DESIRE, nameClient, String.valueOf(productList.get(productnumber).getCodProduct()),
//                        
//                        }).get());
//                    }
//                    else{
//                        System.out.println("This product is already in your list");
//                    }
//                }
//            }             
//        }   
    }
    
    public void updateProducts(int code, int units){
        for(Product product : productList){
            if(product.getCodProduct() == code){
                product.setStockUnits(units);
            }
        }
    }
    
    public void showDesireList()
    {
        int codeCategory = -1;
        for (Integer code : desireList) {
            for(Product product : productList){
                if(product.getCodProduct() == code){
                    System.out.println("Product : " + product.getNameProduct() + "\nPrice :" + product.getUnitPrice());
                    codeCategory = product.getCodCategory();
                }
            }
            for(Category category : categoryList){
                if(codeCategory == category.getCodCategory()){
                    System.out.println("Description : " + category.getDescription());
                    break;
                }
            }
            System.out.println("");
        }
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
    
    public void addDesireList(){
        
        listAllProducts();
        System.out.print("What product do you want to add in your desire list?\nType the code of the product: ");

        int productnumber = input.nextInt();

        Collections.sort(productList, new Comparator<Product>(){
            @Override
            public int compare(Product e1, Product e2){
                return (e1.getCodProduct() < e2.getCodProduct()) ? -1 : 1;
            }
        });

        if (desireList.indexOf(productnumber) == -1){

            desireList.add(productnumber);
            communicateWithServer.sendToServer(new Command(new String[]{
                ADD_DESIRE, nameClient, String.valueOf(productnumber)
            }).get());
        }
        else{
            System.out.println("This product is already in your list");
        }  
    }

    private void buyProduct() {
        listAllProducts();

        System.out.print("What product do you want to buy?\nType the code of the product: ");
        int productnumber = input.nextInt();

        System.out.print("Type how many units: ");
        int units = input.nextInt();

        Collections.sort(productList, new Comparator<Product>(){
            @Override
            public int compare(Product e1, Product e2){
                return (e1.getCodProduct() < e2.getCodProduct()) ? -1 : 1;
            }
        });

        if(productList.get(productnumber).getStockUnits() > 0 && productList.get(productnumber).getStockUnits() >= units){
            communicateWithServer.sendToServer(new Command(new String[]{
                PURCHASE, 
                String.valueOf(productnumber), 
                String.valueOf(units), 
                nameClient,
                String.valueOf(productList.get(productnumber).getCodSupplier())
            }).get());
            System.out.println("Purchased successfully! =)");
        }
        else
            System.out.println("Operation invalid! =(");
    }
}
