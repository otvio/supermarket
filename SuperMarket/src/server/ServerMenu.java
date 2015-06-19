
package server;

import email.SendMail;
import java.io.*;
import java.util.*;
import static server.Server.*;
import supermarket.entities.*;


public class ServerMenu 
{
    private List<ClientStruct> clientList;
    private List<User> userList = getAllTheUser();
    private List<Category> categoryList = getAllCategories();
    private Map<User, List<Integer>> desireList = getAllDesireList(userList);
    
    
    public ServerMenu(List<ClientStruct> clientList)
    {
        this.clientList = clientList;
    }
    
    public void showMenu()
    {
        int choice;
        String nameProduct;
        String expirationdate;
        int units;
        double price;
        
        Scanner scanner = new Scanner(System.in);
        Scanner scannerstring = new Scanner(System.in);
        
        Server server = new Server();
        List <Product> listProducts = new ArrayList<>();
        listProducts = server.BringList();
        
        List <Supplier> listSupplier = new ArrayList<>();
        
        List <User> listUser = new ArrayList<>();
        
        do{
            
            System.out.println("\n1 - Register new products");
            System.out.println("2 - List all the products");
            System.out.println("3 - Send a notification to the client");
            System.out.println("4 - Registering new Supplier");
            System.out.println("5 - Quit");
                
            choice = scanner.nextInt();
            
            if(choice == 1){
                Collections.sort(listProducts, new Comparator<Product>(){

                    @Override
                    public int compare(Product o1, Product o2) {
                        return o1.getCodProduct() < o2.getCodProduct() ? -1 : 1;
                    }
                });
                
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
                
                Product product = new Product(/*fazer funçoes para pegar os codigo*/(listProducts.get(listProducts.size() - 1).getCodProduct() + 1), 0, 0, units, units, price, nameProduct, expirationdate);
                
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
            else if(choice == 3)
            {
                int bef, now, codProduct;
                
                //desireList = getAllDesireList(userList);
                System.out.println("Do you want to update what product?\n");
                
                try{
                    listProducts = server.BringList();
                    
                    System.out.println("\n ::: Show all the Products :: \n");
                    
                    Collections.sort(listProducts, new Comparator<Product>(){

                        @Override
                        public int compare(Product o1, Product o2) {
                            return (o1.getCodProduct() < o2.getCodProduct() ? -1 : 1);
                        }
                    });
                    
                    Collections.sort(categoryList, new Comparator<Category>(){

                        @Override
                        public int compare(Category o1, Category o2) {
                            return (o1.getCodCategory() < o2.getCodCategory() ? -1 : 1);
                        }
                    });
                    
                    for(Product p : listProducts){
                        System.out.println("Product " + p.getCodProduct() + ". ");
                        p.printProduct(categoryList.get(p.getCodCategory()));
                    }
                    
                    System.out.println("What is the product to be updated?");
                    codProduct = scanner.nextInt();
                    
                    System.out.println("What is the new quantity of the product?");
                    
                    bef = listProducts.get(codProduct).getStockUnits();
                    now = scanner.nextInt();
                    
                    listProducts.get(codProduct).setStockUnits(now);
                    
                    if (now > bef) 
                    {
                        for (User u : userList) {

                            for (Integer i : desireList.get(u)) {

                                if (i == codProduct) {

                                    SendMail sm = new SendMail();
                                    sm.sendMail(u.getEmail(), "Product Available!!", "The product {" + listProducts.get(codProduct).getNameProduct() + "} was updated.\nCome to LORMarket and check it out!\n\nPS: The product was removed from yours desire list.");
                                    System.out.println("E-mail sent to user {" + u.getName() + "}. The product was removed from his/her desire list.\n");
                                    desireList.get(u).remove(i);
                                    break;
                                }
                            }
                        }
                    }
                }
                
                catch(Exception e){
                    System.out.println("Can't list all the products!");
                }
            }
            else if(choice == 4){
                
                Collections.sort(listProducts, new Comparator<Product>(){

                    @Override
                    public int compare(Product o1, Product o2) {
                        return o1.getCodProduct() < o2.getCodProduct() ? -1 : 1;
                    }
                });
                
                System.out.println("\nEnter with the name of the supplier:");
            }
            
            
        }while(choice != 5);
    }
    
    public Map<User, List<Integer>> getAllDesireList(List<User> userList)
    {
        Map<User, List<Integer>> desirelist = new HashMap<>();
        String line;
        
        Collections.sort(userList, new Comparator<User>(){
            @Override
            public int compare(User o1, User o2) {
                return (o1.getCodUser() < o2.getCodUser() ? -1 : 1);
            }
        });
        
        for (User u : userList) 
            desirelist.put(u, new ArrayList<Integer>());
        
        try{
            BufferedReader buffreader = new BufferedReader(new FileReader(DESIRE_FILE));
            
            while(buffreader.ready()){
                line = buffreader.readLine();
                
                String[] desires = line.split(",");
                // pegar o conteudo em arquivo e guardar na lista
                desirelist.get(userList.get(Integer.parseInt(desires[0]))).add(Integer.parseInt(desires[1]));
            }
        }
        catch(Exception e){
            System.out.println("\n::: Can't get the desire's list :::");
        }

        return desirelist;
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
            System.out.println("\n::: Can't get the user's list :::");
        }

        return listUsers;
    }
    
            
    public List<Category> getAllCategories(){
        
        List<Category> listcat = new ArrayList<>();
        String line;
        
        try{
            BufferedReader buffreader = new BufferedReader(new FileReader(CATEGORIES_FILE));
            
            while(buffreader.ready()){
                line = buffreader.readLine();
                
                String[] desires = line.split(",");
                // pegar o conteudo em arquivo e guardar na lista
                listcat.add(new Category(Integer.parseInt(desires[0]), desires[1], desires[2]));
            }
        }
        catch(Exception e){
            System.out.println("\n::: Can't get the Category's list :::");
        }

        return listcat;     
    }
            
    public List<Supplier> getAllSuppler(){
        
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
    
}
