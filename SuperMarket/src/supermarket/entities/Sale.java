
package supermarket.entities;

import java.io.File;
import java.io.FileWriter;
import java.util.Calendar;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import static server.Server.SALES_FILE;
import static supermarket.SuperMarket.*;

public class Sale 
{
    private int codSale;
    private int codUser;
    private int codProduct;
    private int quantityProducts;
    private Calendar dateSale;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Objeto para a data da valida

    
    public Sale(int codSale, int codUser, int codProduct, 
            int quantityProducts, String dateSale) 
    {
        String[] date;     // String para armazenar a data
        
        this.codSale = codSale;
        this.codUser = codUser;
        this.codProduct = codProduct;
        this.quantityProducts = quantityProducts;
        
        date = dateSale.split("/");           // Divisão com barras da data armazenada na variável date
        this.dateSale = new GregorianCalendar // Irá pegar a data de validade
        (
                Integer.parseInt(date[2]), 
                Integer.parseInt(date[1]) - 1, Integer.parseInt(date[0])
        );
    }
    
    public void addFileSale()
    {
        try
        {
            File fp = new File(SALES_FILE);
            FileWriter fw = new FileWriter(fp, true);
            PrintWriter pw = new PrintWriter(fw); // cria um PrintWriter que irá escrever no arquivo
            
            if(fp.exists() == false)
            { // caso o arquivo nao exista, cria um arquivo
                fp.createNewFile();
            }
            
            pw.print(this.codSale);
            pw.print(",");
            pw.print(this.codUser);
            pw.print(",");
            pw.print(this.codProduct);
            pw.print(",");
            pw.print(this.quantityProducts);
            pw.print(",");
            pw.println(dateFormat.format(this.dateSale.getTime()));
            
            pw.close();
            fw.close();
        }
        catch(Exception e)
		{
            System.out.println("Can't store in the file :(");
        }
    }
    
    public int getCodSale() 
    {
        return codSale;
    }

    public void setCodSale(int codSale) 
    {
        this.codSale = codSale;
    }

    public int getCodUser() 
    {
        return codUser;
    }

    public void setCodUser(int codUser) 
    {
        this.codUser = codUser;
    }

    public int getCodProduct() 
    {
        return codProduct;
    }

    public void setCodProduct(int codProduct) 
    {
        this.codProduct = codProduct;
    }

    public int getQuantityProducts()
    {
        return quantityProducts;
    }

    public void setQuantityProducts(int quantityProducts) 
    {
        this.quantityProducts = quantityProducts;
    }

    public Calendar getDateSale() 
    {
        return dateSale;
    }

    public void setDateSale(Calendar dateSale) 
    {
        this.dateSale = dateSale;
    }
    
    public String toString(User user, Product product)
    {
        String[] vec = new String[]{""};
        String profit = cutDecimalDigits(String.valueOf(this.quantityProducts * product.getUnitPrice()));
        
        printAdapted("Sale date: " + dateFormat.format(this.dateSale.getTime()), vec);
        printAdapted("User: " + user.getName(), vec);
        printAdapted("Product: " + product.getNameProduct(), vec);
        printAdapted("Quantity of products: " + String.valueOf(this.quantityProducts), vec);
        printAdapted("Profit: $" + profit, vec);
        
        return (vec[0]);
    }
}
