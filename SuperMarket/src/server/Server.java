
package server;

import java.io.*;
import java.util.*;
import java.net.ServerSocket;
import supermarket.entities.*;

public class Server 
{
    // Singleton pattern:
    private static Server server = null;
    
    // defines para os nomes dos arquivos
    public static final String USERS_FILE = "users.csv";
    public static final String SALES_FILE = "sales.csv";
    public static final String DESIRE_FILE = "desires.csv";
    public static final String PRODUCTS_FILE = "products.csv";
    public static final String SUPPLIERS_FILE = "suppliers.csv";
    public static final String CATEGORIES_FILE = "categories.csv";
    
    //define para a quantidade mínima de produtos (se um produto tiver menos, pedimos pro supplier)
    public static final int MIN_QUANTITY = 10;
    
    private static Scanner scanner;
    private static ServerSocket serverSocket;
    private static List<ClientStruct> clientList;
    private static ClientConnection clientConnection = null;
    
    ////////////////////////////////////////////////////////////////////////////
    
    // tenta criar o server no construtor
    private Server() throws Exception 
    {
        Server.serverSocket = new ServerSocket(12345);
        Server.scanner = new Scanner(System.in);
        Server.clientList = new ArrayList<>();
    }
    
    public static synchronized Server getInstance() throws Exception
    {
        Server serv;
        
        if (Server.server == null)
            serv = new Server();
        else
            serv = Server.server;
        
        return serv;
    }
    
    public static void main (String[] args)
    {
        try
        {
            Server.server = Server.getInstance();
            System.out.println("::: Server Connected! :) :::");
            
            if (clientConnection == null)
            {
                clientConnection = ClientConnection.getInstance(serverSocket, clientList);
                new Thread(clientConnection).start();
            }
            
            ServerMenu servermenu = new ServerMenu(clientList);
            servermenu.showMenu();
        }
        catch (Exception e)
        {
            System.out.println("::: I'm so sorry! Server Error! :( :::");
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////

    public static void backup(List<Product> listProducts, 
            List<Category> listCategory, List<Supplier> listSupplier,
            List<User> userList, Map<User, List<Integer>> desirelist)
    {
        new File(SALES_FILE).delete();
        new File(USERS_FILE).delete();
        new File(DESIRE_FILE).delete();
        new File(PRODUCTS_FILE).delete();
        new File(SUPPLIERS_FILE).delete();
        new File(CATEGORIES_FILE).delete();
        
        for(Product product : listProducts){
            product.addFileProduct();
        }
        for(Category category : listCategory){
            category.addFileCategory();
        }
        for (Supplier supplier : listSupplier){
            supplier.addFileSupplier();
        }
        for(User user : userList){
            user.addFileUser();
        }
        
        addFileAllDesireList(userList, desirelist);
    }
    
    public static void addFileAllDesireList(List<User> userList, Map<User, List<Integer>> desireList)
    {
        File fp;
        FileWriter fw;
        PrintWriter pw;
        
        try
        {
            fp = new File(DESIRE_FILE);
            fw = new FileWriter(fp, true);
            pw = new PrintWriter(fw); // cria um PrintWriter que irá escrever no arquivo

            if(fp.exists() == false)
            { // caso o arquivo nao exista, cria um arquivo
                fp.createNewFile();
            }
        
            for (User u : userList) // para cada usuário
            {
                for (Integer codProduct : desireList.get(u)) // para cada lista de desejos do usuário
                {
                    pw.print(u.getCodUser());
                    pw.print(",");
                    pw.println(codProduct);
                }
            }
            pw.close();
            fw.close();
        }
        
        catch(Exception e){System.out.println("addFileAllDesireList FAILED!");}
    }
    
    public static void recoverAllLists(List<Product> listProducts, 
            List<Category> listCategory, List<Supplier> listSupplier,
            List<User> userList, List<Sale> listSale, 
            Map<User, List<Integer>> desireList)
    {
        listProducts = BringList();
        listCategory = getAllCategories();
        listSupplier = getAllSupplier();
        userList = getAllTheUser();
        listSale = getAllSales();
        desireList = getAllDesireList(userList);
    }
    
    public static List<Product> BringList()
    {
        List<Product> list = new ArrayList<>();
        String line;
        BufferedReader buffreader;
        
        try
        {
            buffreader = new BufferedReader(new FileReader(PRODUCTS_FILE));
            
            while(buffreader.ready())
            {
                line = buffreader.readLine();
            
                String[] products = line.split(",");
                
                list.add(new Product(Integer.parseInt(products[0]), Integer.parseInt(products[1]), Integer.parseInt(products[2]), 
                    Integer.parseInt(products[3]), Integer.parseInt(products[4]), Double.parseDouble(products[5]), products[6],
                    products[7]));
            }
            
            buffreader.close();
        }
        catch(Exception e){ System.out.println("BringList FAILED! :("); }
        
        return list;
    }
    
    public static List<Category> BringCategoryList()
    {
        List<Category> list = new ArrayList<>();
        String line;
        BufferedReader buffreader;
        
        try
        {
            buffreader = new BufferedReader(new FileReader(CATEGORIES_FILE));
            
            while(buffreader.ready())
            {
                line = buffreader.readLine();
            
                String[] products = line.split(",");
                
                list.add(new Category(Integer.parseInt(products[0]), products[1], products[2]));
            }
            
            buffreader.close();
        }
        catch(Exception e){ System.out.println("BringCategoryList FAILED! :("); }
        
        return list;
    }
    
    public static List<Sale> getAllSales(){
        List<Sale> listSale = new ArrayList<>();
        String line;
        
        try{
            BufferedReader buffreader = new BufferedReader(new FileReader(SALES_FILE));
            
            while(buffreader.ready()){
                line = buffreader.readLine();
                String[] sales = line.split(",");
                
                listSale.add(new Sale(Integer.parseInt(sales[0]), Integer.parseInt(sales[1]), 
                            Integer.parseInt(sales[2]), Integer.parseInt(sales[3]), sales[4]));
            }
            
            buffreader.close();
        }
        catch(Exception e){ System.out.println("getAllSales FAILED! :("); }
        
        return listSale;
    }
    
    public static Map<User, List<Integer>> getAllDesireList(List<User> userList)
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
            buffreader.close();
        }
        catch(Exception e){ System.out.println("getAllDesireList FAILED! :("); }

        return desirelist;
    }
    
    public static List<User> getAllTheUser(){
        
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
            
            buffreader.close();
        }
        catch(Exception e){ System.out.println("getAllTheUser FAILED! :("); }

        return listUsers;
    }
    
    public static List<Supplier> getAllSupplier()
    {
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
            
            buffreader.close();
        }
        catch(Exception e){ System.out.println("getAllSupplier FAILED! :("); }

        return listSupplier;     
    }
    
    public static List<Category> getAllCategories(){
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
            
            buffreader.close();
        }
        
        catch(Exception e){ System.out.println("getAllCategories FAILED! :("); }

        return listCategory;     
    }
}