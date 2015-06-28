
package server;

import email.SendMail;
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
    
    public void showMenu() throws Exception
    {
        String nameProduct;
        String expirationdate;
        String nameSupplier;
        String nameContact;
        String contacting;
        String nameCategory;
        String descriptionCategory;
        int units;
        int numberOfUpdates;
        int code;
        int codeCategory;
        int codeSupplier;
        int choice;
        double price;
        
        Scanner scanner = new Scanner(System.in);
        Scanner scannerstring = new Scanner(System.in);
        
        Server server = Server.getInstance();
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
            System.out.println("3 - Registering new Supplier");
            System.out.println("4 - Add a new category");
            System.out.println("5 - Update product in the stock");
            System.out.println("6 - Quit");
            System.out.println("\n7 - See the list of users online");
                
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
                    System.out.println("\n ::: Show all the Products :: \n");

                    Collections.sort(listProducts, new Comparator<Product>()
                    {
                        @Override
                        public int compare(Product o1, Product o2) 
                        {
                            return (o1.getStockUnits() < o2.getStockUnits() ? -1 : 1);
                        }
                    });

                    Collections.sort(categoryList, new Comparator<Category>()
                    {
                        @Override
                        public int compare(Category o1, Category o2) 
                        {
                            return (o1.getCodCategory() < o2.getCodCategory() ? -1 : 1);
                        }
                    });

                    for(Product p : listProducts)
                    {
                        System.out.println("Product " + p.getCodProduct() + ". ");
                        p.printProduct(categoryList.get(p.getCodCategory()));
                    }
                }
                
                catch(Exception e){
                    System.out.println("Can't list all the products!");
                }
            }
            
            else if(choice == 3)
            {
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
            
            else if (choice == 4)
            {
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
            
            else if(choice == 5)
            {
                System.out.println("\n:: Enter with the number of updates to be made ::");
                numberOfUpdates = scanner.nextInt();
                
                for (int i = 0; i < numberOfUpdates; i++) 
                {
                    System.out.println("\n::: Enter the name of the product :::");
                    nameProduct = scannerstring.nextLine();
                    
                    System.out.println("\n::: Enter the number of the products :::");
                    units = scanner.nextInt();

                    if ((code = updateStock(nameProduct, units, listProducts)) != -1)
                    {
                        System.out.println("\n::: The product {" + nameProduct + "} was successfully updated.");
                        System.out.println("::: Notifying the users about the update...");
                        
                        for (User u : userList) // para cada usuário
                        {
                            for (Integer codProduct : desireList.get(u)) // para cada lista de desejos do usuário
                            {
                                if (codProduct == code) // se o usuário queria o produto que foi atualizado, notifica-o
                                {
                                    new SendMail().sendMail(u.getEmail(), "LORMarket:::Product Available!!!", "The product {" + listProducts.get(codProduct).getNameProduct() + "} was updated.\nCome to LORMarket and check it out!\n\nPS: The product was removed from your desire list.");
                                    System.out.println("E-mail sent to user {" + u.getName() + "}. The product was removed from his/her desire list.\n");
                                    desireList.get(u).remove(i);
                                    break;
                                }
                            }
                        }
                    }
                        
                    else
                        System.out.println("\n::: The product {" + nameProduct + "} wasn't found in the LORMarket!\n");
                }
            }
            else if (choice == 7)
            {
                for (ClientStruct c : clientList) 
                {
                    System.out.println(c.user.toString());
                }
                System.out.println("\n::: Users online listed successfully! :::");
            }
            
        } while (choice != 6);
    }
        
    public int updateStock(String nameProduct, int units, List<Product> listProducts)
    {
        for (Product product : listProducts)
        {
            if(product.getNameProduct().equals(nameProduct))
            {
                product.setStockUnits(units + product.getStockUnits());
                product.setOrderedUnits((product.getOrderedUnits() > units) ? product.getOrderedUnits() - units : 0);
                
                return (product.getCodProduct());
            }
        }
        
        return (-1);
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
