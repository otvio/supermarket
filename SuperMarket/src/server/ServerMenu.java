
package server;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static server.Server.PRODUCTS_FILE;
import supermarket.entities.Product;


public class ServerMenu {
    
    public void showMenu(){
        
        int choice = 0;
        String nameProduct;
        String expirationdate;
        int units;
        double price;
        
        Scanner scanner = new Scanner(System.in);
        Scanner scannerstring = new Scanner(System.in);
        
        Server server = new Server();
        List <Product> list = new ArrayList<>();
        
        do{
                System.out.println("\n1 - Register new products");
                System.out.println("2 - List all the products");
                System.out.println("3 - Send a notification to the client");
                System.out.println("4 - Quit");
            choice = scanner.nextInt();
            
            if(choice == 1){
                
                System.out.println("\nQuantity of products to add in the store:");
                units = scanner.nextInt();
                
                System.out.println("The Price per unit:");
                price = scanner.nextDouble();
                
                System.out.println("The name of the product:");
                nameProduct = scannerstring.nextLine();
                
                System.out.println("The expiration date:");
                expirationdate = scannerstring.nextLine();
                
                /*
                public Product(int codProduct, int codSupplier, int codCategory, 
                int stockUnits, int orderedUnits, double unitPrice, 
                String nameProduct, String validity) /*/
                
                
                Product product = new Product(/*fazer funçoes para pegar os codigo*/0, 0, 0, units, units, price, nameProduct, expirationdate);
                
                product.addProduct();
            }
            
            else if(choice == 2){
                try{
                    list = server.BringList();
                }
                
                catch(Exception e){
                    System.out.println("Can't list all the products!");
                }
                
                System.out.println("\n ::: Show all the Products :: \n");
                
                for(Product p : list){
                    System.out.println(p.getNameProduct() + " - " + p.getUnitPrice() + " - " + p.getStockUnits());
                }
            }
            
            else if(choice == 3){
                   //Send notification to the client
            }
            
            
        }while(choice != 4);
    }
    // Receber os dados do novo produto e gravar no arquivo as respectivas informações
}
