
package client;

import java.util.*;
import command.Command;
import supermarket.entities.*;
import static command.Command.*;

public class ClientMenu
{
    private CommunicateWithServer communicateWithServer;      // Objeto para comunicar-se com o servidor
    private String nameClient;                                // String para armazenar o nome do cliente
    private String cases = "";                                // String para os casos do menu
    
    private Scanner input;                                      // Scanner para pegar as entradas
    
    private List <Category> categoryList = new ArrayList<>();   // Lista para armazenar as categorias
    private List <Product> productList = new ArrayList<>();     // Lista para armazenar os produtos
    private List <Integer> desireList = new ArrayList<>();      // Lista para armazenar os produtos desejados
        
    // Construtor da classe Cliente menu
    public ClientMenu(String nameClient, CommunicateWithServer communicateWithServer)
    {
        this.communicateWithServer = communicateWithServer;
        this.input = new Scanner(System.in);
        this.nameClient = nameClient;
    }

    // Método para o menu inicial
    public void menu()
    {
        do
        {
            printHeader();    // Método para imprimir o cabeçalho
            
            System.out.println(":::::::::::::::::::::::::::::::::::");
            System.out.println("::  1 - List all the products.   ::");   
            System.out.println("::  2 - View desire list.        ::");
            System.out.println("::  3 - Add to the desire list.  ::");
            System.out.println("::  4 - Buy a product.           ::");
            System.out.println("::  5 - Exit.                    ::");
            System.out.println(":::::::::::::::::::::::::::::::::::\n");
            
            System.out.print("::  Type your choice: ");
            cases = input.nextLine();
            // Cases para verificar a opção solicitada
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
                    
                case "5":
                    desconnect();
            }
            
            if (!("5").equals(cases))
            {
                System.out.println("\nPress ENTER to continue...");
                cases = input.nextLine();                
                cases = (cases.equals("5")) ? "-" : cases;
            }
        } while (!cases.equals("5"));
    }  
    // Método que irá imprimir todos os produtos
    public void listAllProducts()
    {
        int productnumber, units;
        
        System.out.println("\n\t::::::::::::::::::::::::::::::::::::::::");
        System.out.println(  "\t::::      Listing all products      ::::");
        System.out.println(  "\t::::      -Ordering by units-       ::::");
        System.out.println(  "\t::::::::::::::::::::::::::::::::::::::::\n\n");
        
        Collections.sort(productList, new Comparator<Product>()  // Método para ordenar a lista de produtos por unidades
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
    }
    // Método para atualizar a quantidade de um determinado produto
    public void updateProducts(int code, int units)
    {
        for(Product product : productList)
        {
            if(product.getCodProduct() == code)
            {
                product.setStockUnits(units);
            }
        }
    }
    // Método para imprimir o cabeçalho do menu
    public void printHeader()
    {
            System.out.print("\n\t:::::::::::::::::::::::::::::::::::");
            for (int i = 0; i < nameClient.length() + 9; i++) { System.out.print(":"); }
            System.out.println("\n\t:::::    Welcome to the LORMarket, " + nameClient + "    :::::");    
            System.out.print("\t:::::::::::::::::::::::::::::::::::");
            for (int i = 0; i < nameClient.length() + 9; i++) { System.out.print(":"); }
            System.out.println("\n\n");
        
    }
    
    //  Método que imprime a lista de desejos
    public void showDesireList()
    {
        int codeCategory = -1;
        for (Integer code : desireList)             // Para cada produto na lista de desejos
        {
            for(Product product : productList)      // Procuramos esse produto na lista
            {
                if(product.getCodProduct() == code) // Se encontrar o produto , imprime suas caracteristicas
                {
                    System.out.println("Product : " + product.getNameProduct() + "\nPrice :" + product.getUnitPrice());
                    codeCategory = product.getCodCategory();
                }
            }
            for(Category category : categoryList)   // Para cada categoria na lista de categoria
            {   
                if(codeCategory == category.getCodCategory())  // Se a categoria for a procurada, imprime ela.
                {
                    System.out.println("Description : " + category.getDescription());
                    break;
                }
            }
            System.out.println("");
        }
    }
   
    // Getters das três listas (Categoria, produtos, lista de desejos)
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
    // Método para adicionar na lista de desejos
    public void addDesireList()
    {        
        listAllProducts();   // Método para listar todos os produtos 
        System.out.print("What product do you want to add in your desire list?\nType the code of the product: ");

        int productnumber = input.nextInt();    // Int para armazenar o código do produto

        Collections.sort(productList, new Comparator<Product>()      // Ordena por código do produto
        {
            @Override
            public int compare(Product e1, Product e2)
            {
                return (e1.getCodProduct() < e2.getCodProduct()) ? -1 : 1;
            }
        });

        if (desireList.indexOf(productnumber) == -1)       // Se o produto não está na lista de desejos dele, então adiciona
        {
            desireList.add(productnumber);
            communicateWithServer.sendToServer(new Command(new String[]{
                ADD_DESIRE, nameClient, String.valueOf(productnumber)
            }).get());
        }
        else
        {
            System.out.println("This product is already in your list");
        }  
    }
    // Método para comprar um produto
    private void buyProduct() 
    {
        listAllProducts();
        int productnumber;
        
        do         // Loop para verificar qual produto vai ser comprado
        {
            System.out.print("What product do you want to buy?\nType the code of the product: ");
            productnumber = input.nextInt();
            if(productList.size() <= productnumber)
            {
                System.out.println("\nThis code doesn't exist.\nPlease try another one.\n");
            }
            
        }while(productList.size() <= productnumber);
           
        System.out.print("Type how many units: ");
        int units = input.nextInt();   // Armazena a quantidade

        Collections.sort(productList, new Comparator<Product>()  // Método que ordena de acordo com o código do produto
        {
            @Override
            public int compare(Product e1, Product e2)
            {                
                return (e1.getCodProduct() < e2.getCodProduct()) ? -1 : 1;
            }
        });
        // Caso a unidade em estoque for maior que zero e maior ou igual a quantidade de unidades pedidas, então a compra será efetuada
        if(productList.get(productnumber).getStockUnits() > 0 && productList.get(productnumber).getStockUnits() >= units)
        {   // Enviando a compra para o servidor
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
    // Método para desconectar um determinado cliente 
    public void desconnect()
    {
        communicateWithServer.sendToServer(new Command(new String[]{
            DISCONNECT, nameClient
        }).get());
        System.exit(0);
    }
}
