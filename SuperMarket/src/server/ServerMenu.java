
package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static server.Server.PRODUCTS_FILE;
import supermarket.entities.Product;


public class ServerMenu {
    
    public static final String USERS_DESIRE = "desires.csv";
    
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
                
                product.addFileProduct();
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
            // percorrer a lista dada pelo método e procurar todos os caras na lista quando atualizar determinado produto
            else if(choice == 3){
                //Send notification to the client
            }
            
            
        }while(choice != 4);
    }
    
    public void GetDesireList(){
        List <ClientStruct> desireList = new ArrayList<>();
        String line;
        
        try{
            BufferedReader buffreader = new BufferedReader(new FileReader(USERS_DESIRE));
            
            while(buffreader.ready()){
                line = buffreader.readLine();
                
                String[] desires = line.split(",");
                
               // desireList.add(new ClientStruct(Integer.parseInt(desires[0]), Integer.parseInt(desires[1]), desires[2]));
            }
        }
        catch(Exception e){
            System.out.println("\n::: Can't get the desire's list :::");
        }
    }
    
    // Receber os dados do novo produto e gravar no arquivo as respectivas informações
    public void createFileDesire(ClientStruct clientStruct){
        
        try{
            File fp = new File(USERS_DESIRE);
            FileWriter fw = new FileWriter(fp, true);
            PrintWriter pw = new PrintWriter(fw); // cria um PrintWriter que irá escrever no arquivo
        
            if(!fp.exists()){
                fp.createNewFile();
            }
            
            pw.print(clientStruct.product.getCodProduct());
            pw.print(",");
            pw.println(clientStruct.product.getStockUnits());
        }
        catch(Exception e){
            System.out.println("\n:::Can't bring all the list to the server:::");
        }
    }
}
