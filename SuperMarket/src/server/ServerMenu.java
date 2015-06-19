
package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import static server.Server.CATEGORIES_FILE;
import static server.Server.DESIRE_FILE;
import static server.Server.PRODUCTS_FILE;
import static server.Server.SUPPLIERS_FILE;
import static server.Server.USERS_FILE;
import supermarket.entities.Category;
import supermarket.entities.Product;
import supermarket.entities.Supplier;
import supermarket.entities.User;


public class ServerMenu {

    public void showMenu(){
       
        String nameProduct;
        String expirationdate;
        String nameSupplier;
        String nameContact;
        String contacting;
        String nameCategory;
        String descriptionCategory;
        int units;
        int code;
        int choice;
        double price;
        
        Scanner scanner = new Scanner(System.in);
        Scanner scannerstring = new Scanner(System.in);
        
        Server server = new Server();
        List <Product> listProducts = new ArrayList<>();
        listProducts = server.BringList();
        
        List <Supplier> listSupplier = new ArrayList<>();
        listSupplier = getAllSupplier();
        
        List<Category> listCategory = new ArrayList<>();
        //List <User> listUser = new ArrayList<>();
        
        do{
            System.out.println("\n1 - Register new products");
            System.out.println("2 - List all the products");
            System.out.println("3 - Send a notification to the client");
            System.out.println("4 - Registering new Supplier");
            System.out.println("5 - Add a new category");
            System.out.println("6 - Quit");
                
            choice = scanner.nextInt();
            
            if(choice == 1){
                Collections.sort(listProducts, new Comparator<Product>(){

                    @Override
                    public int compare(Product o1, Product o2) {
                        return o1.getCodProduct() < o2.getCodProduct() ? -1 : 1;
                    }
                });
                
                code = (!listProducts.isEmpty()) ? (listProducts.get(listProducts.size() - 1).getCodProduct()+ 1) : 0;  // Código que será adicionado para o usuário
                
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
                
                Product product = new Product(code, 0, 0, units, units, price, nameProduct, expirationdate);
                
                product.addFileProduct();
            }
            
            else if(choice == 2){
               
                try{
                    listProducts = server.BringList();
                }
                
                catch(Exception e){
                    System.out.println("Can't list all the products!");
                }
                
                System.out.println("\n ::: Show all the Products :: \n");
                
                for(Product p : listProducts){
                    System.out.println(p.getNameProduct() + " - " + p.getUnitPrice() + " - " + p.getStockUnits());
                }
            }
            // percorrer a lista dada pelo método e procurar todos os caras na lista quando atualizar determinado produto
            else if(choice == 3){
                //Send notification to the client
            }
            
            else if(choice == 4){
                
                Collections.sort(listSupplier, new Comparator<Supplier>(){

                    @Override
                    public int compare(Supplier o1, Supplier o2) {
                        return o1.getCodSupplier() < o2.getCodSupplier() ? -1 : 1;
                    }
                });
                
                code = (!listSupplier.isEmpty()) ? (listSupplier.get(listSupplier.size() - 1).getCodSupplier()+ 1) : 0;  // Código que será adicionado para o usuário
                
                System.out.println("\nEnter with the name of the supplier:");
                nameSupplier = scannerstring.nextLine();
                
                System.out.println("Enter with the name of the nameContact:");
                nameContact = scannerstring.nextLine();
                
                System.out.println("Enter with the name of the contacting:");
                contacting = scannerstring.nextLine();
                
                listSupplier.add(new Supplier(code ,nameSupplier, nameContact, contacting));
                listSupplier.get(listSupplier.size() - 1).addFileSupplier();
            }
            
            else if(choice == 5){
                Collections.sort(listCategory, new Comparator<Category>(){

                    @Override
                    public int compare(Category o1, Category o2) {
                        return o1.getCodCategory() < o2.getCodCategory() ? -1 : 1;
                    }
                });
                
                code = (!listCategory.isEmpty()) ? (listCategory.get(listCategory.size() - 1).getCodCategory()+ 1) : 0;  // Código que será adicionado para o usuário
           
                System.out.println("\nEnter with the name of the category:");
                nameCategory = scannerstring.nextLine();
                System.out.println("Give us a brief description of the category:");
                descriptionCategory = scannerstring.nextLine();
                
                listCategory.add(new Category(code, nameCategory, descriptionCategory));
                listCategory.get(listSupplier.size() - 1).addFileCategory();
            }
            
        }while(choice != 6);
        
    }
    
    public List <ClientStruct> getDesireList(int codeProduct){
        
        List <ClientStruct> desireList = new ArrayList<>();
        String line;
        
        try{
            BufferedReader buffreader = new BufferedReader(new FileReader(DESIRE_FILE));
            
            while(buffreader.ready()){
                line = buffreader.readLine();
                
                String[] desires = line.split(",");
                // pegar o conteudo em arquivo e guardar na lista
               // desireList.add();
            }
        }
        catch(Exception e){
            System.out.println("\n::: Can't get the desire's list :::");
        }
        
        return desireList;
    }
    
    // Receber os dados do novo produto e gravar no arquivo as respectivas informações
    public void createFileDesire(ClientStruct clientStruct){
        
        try{
            File fp = new File(DESIRE_FILE);
            FileWriter fw = new FileWriter(fp, true);
            PrintWriter pw = new PrintWriter(fw); // cria um PrintWriter que irá escrever no arquivo
        
            if(!fp.exists()){
                fp.createNewFile();
            }
            
        }
        catch(Exception e){
            System.out.println("\n:::Can't bring all the list to the server:::");
        }
    }
    
    public List<User> getAllTheUser(){
        
        List<User> listUsers = new ArrayList<>();
        String line;
        
        try{
            BufferedReader buffreader = new BufferedReader(new FileReader(USERS_FILE));
            
            while(buffreader.ready()){
                line = buffreader.readLine();
                
                String[] desires = line.split(",");
                // pegar o conteudo em arquivo e guardar na lista
                listUsers.add(new User(Integer.parseInt(desires[0]), desires[1], desires[2],  desires[3],  desires[4], desires[5],  desires[6]));
            }
        }
        catch(Exception e){
            System.out.println("\n::: Can't get the desire's list :::");
        }

        return listUsers;
    }
    
    public List<Supplier> getAllSupplier(){
        
        List<Supplier> listSupplier = new ArrayList<>();
        String line;
        
        try{
            BufferedReader buffreader = new BufferedReader(new FileReader(SUPPLIERS_FILE));
            
            while(buffreader.ready()){
                line = buffreader.readLine();
                
                String[] desires = line.split(",");
                // pegar o conteudo em arquivo e guardar na lista
                listSupplier.add(new Supplier(Integer.parseInt(desires[0]), desires[1], desires[2],  desires[3]));
            }
        }
        catch(Exception e){
            System.out.println("\n::: Can't get the Supplier's list :::");
        }

        return listSupplier;     
    }
    
    public List<Category> getAllCategory(){
        List<Category> listCategory = new ArrayList<>();
        String line;
        
        try{
            BufferedReader buffreader = new BufferedReader(new FileReader(CATEGORIES_FILE));
            
            while(buffreader.ready()){
                line = buffreader.readLine();
                
                String[] desires = line.split(",");
                // pegar o conteudo em arquivo e guardar na lista
                listCategory.add(new Category(Integer.parseInt(desires[0]), desires[1], desires[2]));
            }
        }
        
        catch(Exception e){
            System.out.println("\n::: Can't get the Supplier's list :::");
        }

        return listCategory;     
    }
}
