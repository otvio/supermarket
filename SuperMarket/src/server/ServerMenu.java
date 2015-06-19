
package server;

import java.io.*;
import java.util.*;
import static server.Server.*;
import supermarket.entities.*;

public class ServerMenu {
    
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
        String nameProduct;
        String expirationdate;
        String nameSupplier;
        String nameContact;
        String contacting;
        String nameCategory;
        String descriptionCategory;
        int units;
        int numberOfUptades;
        int code = -1;
        int codeCategory;
        int codeSupplier;
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
        listCategory = getAllCategories();
        //List <User> listUser = new ArrayList<>();
        
        do{
            System.out.println("\n1 - Register new products");
            System.out.println("2 - List all the products");
            System.out.println("3 - Send a notification to the client");
            System.out.println("4 - Registering new Supplier");
            System.out.println("5 - Add a new category");
            System.out.println("6 - Uptade product in the stock");
            System.out.println("7 - Quit");
                
            choice = scanner.nextInt();
            
            if(choice == 1){
                
                code = (!listProducts.isEmpty()) ? (listProducts.get(listProducts.size() - 1).getCodProduct()+ 1) : 0;  // Código que será adicionado para o usuário
                
                System.out.println("\nQuantity of products to add in the store:");
                units = scanner.nextInt();
                
                System.out.println("The Price per unit:");
                price = scanner.nextDouble();
                
                System.out.println("The name of the product:");
                nameProduct = scannerstring.nextLine();
                
                System.out.println("The expiration date:");
                expirationdate = scannerstring.nextLine();
                
                System.out.println("The name of the supplier:");
                nameSupplier = scannerstring.nextLine();
                
                System.out.println("The name of the category:");
                nameCategory = scannerstring.nextLine();
                    
                codeSupplier = getSupplier(listSupplier, nameSupplier);
                codeCategory = getCategory(listCategory, nameCategory);
                
                if(codeCategory != -1 && codeSupplier != -1){
                
                    Product product = new Product(code, codeSupplier, codeCategory, units, units, price, nameProduct, expirationdate);
                
                    product.addFileProduct();
                
                    Collections.sort(listProducts, new Comparator<Product>(){

                        @Override
                        public int compare(Product o1, Product o2) {
                            return o1.getCodProduct() < o2.getCodProduct() ? -1 : 1;
                        }
                    });
                }
                
                if(codeCategory == -1){
                    System.out.println("\n:::The name of the category is invalid, please try another one:::");
                }
                if(codeSupplier == -1){
                    System.out.println("\n:::The name of the supplier is invalid, please try another one:::");
                }
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
                        listProducts.get(codProduct).setChanged();
                        listProducts.get(codProduct).notifyObservers();
                        listProducts.get(codProduct).deleteObservers();
                        System.out.println("E-mail sent to users. The product was removed from their desire list.\n");
                    }
                }
                
                catch(Exception e){
                    System.out.println("Can't list all the products!");
                }
            }
            
            else if(choice == 4){
                
                code = (!listSupplier.isEmpty()) ? (listSupplier.get(listSupplier.size() - 1).getCodSupplier()+ 1) : 0;  // Código que será adicionado para o usuário
                
                System.out.println("\nEnter with the name of the supplier:");
                nameSupplier = scannerstring.nextLine();
                
                System.out.println("Enter with the name of the nameContact:");
                nameContact = scannerstring.nextLine();
                
                System.out.println("Enter with the name of the contacting:");
                contacting = scannerstring.nextLine();
                
                listSupplier.add(new Supplier(code ,nameSupplier, nameContact, contacting));
                listSupplier.get(listSupplier.size() - 1).addFileSupplier();
                
                Collections.sort(listSupplier, new Comparator<Supplier>(){

                    @Override
                    public int compare(Supplier o1, Supplier o2) {
                        return o1.getCodSupplier() < o2.getCodSupplier() ? -1 : 1;
                    }
                });
                
            }
            
            else if(choice == 5){
                
                code = (!listCategory.isEmpty()) ? (listCategory.get(listCategory.size() - 1).getCodCategory()+ 1) : 0;  // Código que será adicionado para o usuário
           
                System.out.println("\nEnter with the name of the category:");
                nameCategory = scannerstring.nextLine();
                System.out.println("Give us a brief description of the category:");
                descriptionCategory = scannerstring.nextLine();
                
                listCategory.add(new Category(code, nameCategory, descriptionCategory));
                listCategory.get(listCategory.size() - 1).addFileCategory();
                
                Collections.sort(listCategory, new Comparator<Category>(){

                    @Override
                    public int compare(Category o1, Category o2) {
                        return o1.getCodCategory() < o2.getCodCategory() ? -1 : 1;
                    }
                });
            }
            
            else if(choice == 6){
                System.out.println("\n::Enter with the number of uptades::");
                numberOfUptades = scanner.nextInt();
                
                for (int i = 0; i < numberOfUptades; i++) {
                    
                    System.out.println("\n::: Enter the name of the product:::");
                    nameProduct = scannerstring.nextLine();
                    System.out.println("\n::: Enter the number of the products:::");
                    units = scanner.nextInt();
                    code = -1;
                    
                    for (Product product : listProducts) {
                        if(product.getNameProduct().equals(nameProduct)){
                            code = product.getCodProduct();
                        }
                    }
                    if(code != -1)
                        uptadeStock(code, units, listProducts);
                }
            }
        }while(choice != 7);
        
    }
    public Map<User, List<Integer>> getAllDesireList(List<User> userList){
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
    
    public List<Category> getAllCategories(){
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
    
    public void uptadeStock(int codeProduct, int units, List<Product> listProducts){
        
        for (Product product : listProducts) {
            if(product.getCodProduct() == codeProduct){
                product.setStockUnits(units + product.getStockUnits());
                
                if(product.getStockUnits() > product.getOrderedUnits()){
                    product.setOrderedUnits(product.getStockUnits());
                }
            }
        }
    }
    
    public int getSupplier(List<Supplier> listSupplier, String nameSupplier){
        int code = -1;
        for (Supplier supplier : listSupplier) {
            if(supplier.getNameSupplier().equals(nameSupplier)){
                code = supplier.getCodSupplier();
            }
        }
        return code;
    }
    
    public int getCategory(List<Category> listCategory, String nameCategory){
        int code = -1;
        
        for (Category category : listCategory) {
            if(category.getNameCategory().equals(nameCategory)){
                code = category.getCodCategory();
            }
        }
        return code;
    }
}
