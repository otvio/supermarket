
package server;

import java.io.*;
import java.util.*;
import command.Command;
import extras.email.SendMail;
import static server.Server.*;
import supermarket.entities.*;
import static command.Command.*;
import extras.reportpdf.GeneratorPDF;
import java.text.SimpleDateFormat;

public class ServerMenu 
{
    private static List<ClientStruct> clientList;
    private static List<Sale> listSale = getAllSales();
    private static List<User> userList = getAllTheUser();
    private static List <Product> listProducts = BringList();
    private static List<Category> listCategory = getAllCategories();
    private static List <Supplier> listSupplier = getAllSupplier();
    private static List<Category> categoryList = getAllCategories();
    private static Map<User, List<Integer>> desireList = getAllDesireList(userList);
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Objeto para a data da valida

    public ServerMenu(List<ClientStruct> clientList)
    {
        ServerMenu.clientList = clientList;
    }
    
    public void showMenu() throws Exception
    {
        int units, code, codeCategory, codeSupplier, choice;
        
        double price;
        
        String nameProduct, expirationdate, nameSupplier, 
                nameContact, contacting, nameCategory, descriptionCategory;

        Scanner scanner = new Scanner(System.in);
        Scanner scannerstring = new Scanner(System.in);
        
        Server server = Server.getInstance();

        recoverAllLists(listProducts, listCategory, listSupplier, userList, listSale, desireList);
        
        Collections.sort(listProducts, new Comparator<Product>(){

            @Override
            public int compare(Product o1, Product o2) {
                return o1.getCodProduct() < o2.getCodProduct() ? -1 : 1;
            }
        });
        
        do
        {
            System.out.println("");
            System.out.println("::::::::::::::::::::::::::::::::::::::::::::");
            System.out.println("::    1 - Register new products.          ::");
            System.out.println("::    2 - List all the products.          ::");
            System.out.println("::    3 - Registering new Supplier.       ::");
            System.out.println("::    4 - Add a new category.             ::");
            System.out.println("::    5 - Generate PDF.                   ::");
            System.out.println("::    6 - Quit.                           ::");
            System.out.println("::    7 - See the list of users online.   ::");
            System.out.println("::::::::::::::::::::::::::::::::::::::::::::");
            
            System.out.print("\n:: Type your choice:");
                
            choice = scanner.nextInt();
            
            if(choice == 1){
                
                code = (!listProducts.isEmpty()) ? (listProducts.get(listProducts.size() - 1).getCodProduct()+ 1) : 0;  // Código que será adicionado para o usuário
                code = -1;
                for(Product p : listProducts){
                    if(code < p.getCodProduct()) code = p.getCodProduct();
                }
                code++;
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
                    
                codeSupplier = getSupplier(listSupplier, nameSupplier.toLowerCase());
                codeCategory = getCategory(listCategory, nameCategory.toLowerCase());
                
                if(codeCategory != -1 && codeSupplier != -1){
                
                    Product product = new Product(code, codeSupplier, codeCategory, units, units, price, nameProduct, expirationdate);
                
                    product.addFileProduct(true);
                
                    Collections.sort(listProducts, new Comparator<Product>(){

                        @Override
                        public int compare(Product o1, Product o2) {
                            return o1.getCodProduct() < o2.getCodProduct() ? -1 : 1;
                        }
                    });
                    System.out.println(Arrays.asList(listProducts));
                    
                    
                }
                
                if(codeCategory == -1){
                    System.out.println("\n:::The name of the category is invalid, please try another one:::");
                }
                //backup(listProducts, listCategory, listSupplier, userList, listSale, desireList);
                //recoverAllLists(listProducts, listCategory, listSupplier, userList, listSale, desireList);
                //System.exit(0);
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
                
                backup(listProducts, listCategory, listSupplier, userList, listSale, desireList);
                recoverAllLists(listProducts, listCategory, listSupplier, userList, listSale, desireList);
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
                GeneratorPDF.generate();
                System.out.println("\n::: PDF created successfully! ::");
            }
            else if (choice == 7)
            {
                for (User c : userList) 
                {
                    System.out.println(c.toString());
                }
                System.out.println("\n::: Users listed successfully! :::");
                
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
    
    // Receber os dados do novo produto e gravar no arquivo as respectivas informações
    public void createFileDesire(ClientStruct clientStruct){
        
        try{
            File fp = new File(DESIRE_FILE);
//            FileWriter fw = new FileWriter(fp, true);
//            PrintWriter pw = new PrintWriter(fw); // cria um PrintWriter que irá escrever no arquivo
        
            if(!fp.exists()){
                fp.createNewFile();
//                fw.close();
//                pw.close();
            }
            
        }
        catch(Exception e){
            System.out.println("\n:::Can't bring all the list to the server:::");
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
    
    //Falta verificar a condição se um produto tem menos que a quantidade mais baixa definida.
    
    public static void removeFromStock(int code, int units){
        listProducts = Server.BringList();
        
        for (Product listProduct : listProducts) {
            if (listProduct.getCodProduct() == code) {
                listProduct.setStockUnits(units);
            }
        }
        
        backup(listProducts, listCategory, listSupplier, userList, listSale, desireList);
        recoverAllLists(listProducts, listCategory, listSupplier, userList, listSale, desireList);
    }
    
    
    
    public static void addSale(String nameUser, int codeProduct, int quantityProducts, Calendar date){
        int codeUser = 0;
        int lastCode = (!listSale.isEmpty()) ? (listSale.get(listSale.size() - 1).getCodSale()+ 1) : 0;
        
        for(User user : userList){
            if(user.getName().equals(nameUser)){
                codeUser = user.getCodUser();
            }
        }
        listSale.add(new Sale(lastCode, codeUser, codeProduct, quantityProducts, dateFormat.format(date.getTime())));
    }

    public static List<ClientStruct> getClientList(){
        return clientList;
    }
    
    public static List<Sale> getSalesList(){
        return listSale;
    }
    
    public static List<User> getUsersList(){
        return userList;
    }
    
    public static List<Product> getProductsList(){
        return listProducts;
    }
    
    public static void addDesire(String nameUSer, int codeProduct){
        
        for (User u : userList) // para cada usuário
        {
            if(u.getName().equals(nameUSer)){
                desireList.get(u).add(codeProduct);
            }
        }
        
        backup(listProducts, listCategory, listSupplier, userList, listSale, desireList);
        recoverAllLists(listProducts, listCategory, listSupplier, userList, listSale, desireList);
    }
    
    public static void removeDesire(int codeUser, int codeProduct){
        for (ClientStruct client : clientList) {
            if(client.getUser().getCodUser() == codeUser){
                client.communicate.sendToClient(new Command( new String[]{
                    REMOVE_DESIRE, String.valueOf(codeProduct)
                }).get());
                break;
            }
        }
    }
    
    public static void notifyUsers(int code)
    {        
        for (User u : userList) // para cada usuário
        {
            if (desireList != null && 
                    desireList.get(u) != null && 
                    desireList.get(u).isEmpty()
                ) continue;
            
            for (Integer codProduct : desireList.get(u)) // para cada lista de desejos do usuário
            {
                if (codProduct == code) // se o usuário queria o produto que foi atualizado, notifica-o
                {
                    new SendMail().sendMail(u.getEmail(), 
                        "LORMarket:::Product Available!!!", "The product {" 
                        + listProducts.get(codProduct).getNameProduct() + 
                        "} was updated.\nCome to LORMarket and check it out!"
                        + "\n\nPS: The product was removed from your desire list."
                    );
                    
                    for (int i = 0; i < desireList.get(u).size(); i++)
                    {
                        if (desireList.get(u).get(i) == code)
                        {
                            desireList.get(u).remove(i);
                            break;
                        }
                    }
                    
                    removeDesire(u.getCodUser(), codProduct);

                    break;
                }
            }
        }
        
        backup(listProducts, listCategory, listSupplier, userList, listSale, desireList);
        recoverAllLists(listProducts, listCategory, listSupplier, userList, listSale, desireList);
    }
    
    public static void disconnectClient(String nameUser){
        for (ClientStruct client : clientList) {
            if (client.user.getName().equals(nameUser)) {
                clientList.remove(client);
                break;
            }
        }
    }

    
    public static List<User> getUserList() 
    {
        return userList;
    }
    
    public static Map<User,List<Integer>> getDesireList()
    {
        return (desireList);
    }
    
}
