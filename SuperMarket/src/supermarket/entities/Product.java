
package supermarket.entities;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import static server.Server.PRODUCTS_FILE;

public class Product
{
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Objeto para a data da valida
    private int codProduct;
    private int codSupplier;
    private int codCategory;
    private int stockUnits;
    private int orderedUnits;
    private double unitPrice;
    private String nameProduct;
    private Calendar validityProduct;
    
    public Product(int codProduct, int codSupplier, int codCategory, 
            int stockUnits, int orderedUnits, double unitPrice, 
            String nameProduct, String validity) 
    {
        String[] date;     // String para armazenar a data
        
        this.codProduct = codProduct;
        this.codSupplier = codSupplier;
        this.codCategory = codCategory;
        this.stockUnits = stockUnits;
        this.orderedUnits = orderedUnits;
        this.unitPrice = unitPrice;
        this.nameProduct = nameProduct;
        
        date = validity.split("/");                  // Divisão com barras da data armazenada na variável date
        this.validityProduct = new GregorianCalendar // Irá pegar a data de validade
        (
                Integer.parseInt(date[2]), 
                Integer.parseInt(date[1]) - 1, Integer.parseInt(date[0])
        );
    }
    
	
    public void addFileProduct()
    {
        try
        {
            File fp = new File(PRODUCTS_FILE);
            FileWriter fw = new FileWriter(fp, true);
            PrintWriter pw = new PrintWriter(fw); // cria um PrintWriter que irá escrever no arquivo

            if(fp.exists() == false)
            { // caso o arquivo nao exista, cria um arquivo
                fp.createNewFile();
            }

            pw.print(this.codProduct);
            pw.print(",");
            pw.print(this.codSupplier);
            pw.print(",");
            pw.print(this.codCategory);
            pw.print(",");
            pw.print(this.stockUnits);
            pw.print(",");
            pw.print(this.orderedUnits);
            pw.print(",");
            pw.print(this.unitPrice);
            pw.print(",");
            pw.print(this.nameProduct);
            pw.print(",");
            pw.println(dateFormat.format(this.validityProduct.getTime()));

            pw.close();
            fw.close();
        }
        catch(Exception e)
        {
            System.out.println("Can't store in the file :(");
        }
    }
    
    public int getCodCategory() 
    {
        return codCategory;
    }

    public void setCodCategory(int codCategory) 
    {
        this.codCategory = codCategory;
    }
    
    public int getCodProduct() 
    {
        return codProduct;
    }

    public void setCodProduct(int codProduct) 
    {
        this.codProduct = codProduct;
    }

    public String getNameProduct() 
    {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) 
    {
        this.nameProduct = nameProduct;
    }

    public int getCodSupplier() 
    {
        return codSupplier;
    }

    public void setCodSupplier(int codSupplier) 
    {
        this.codSupplier = codSupplier;
    }

    public double getUnitPrice() 
    {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) 
    {
        this.unitPrice = unitPrice;
    }

    public int getStockUnits() 
    {
        return stockUnits;
    }

    public void setStockUnits(int stockUnits) 
    {
        this.stockUnits = stockUnits;
    }

    public int getOrderedUnits() 
    {
        return orderedUnits;
    }

    public void setOrderedUnits(int orderedUnits) 
    {
        this.orderedUnits = orderedUnits;
    }
	
    public void printProduct(Category c)
    {
        System.out.println("//--------------------------------------\\\\"); 
        printAdapted("Product Code: " + (this.getCodProduct()));
        printAdapted("Product name: " + this.getNameProduct());
        printAdapted("Price: " + this.getUnitPrice());
        printAdapted("Validity: " + dateFormat.format(this.validityProduct.getTime()));
        
        if (c != null)
            printAdapted("Category: " + c.getNameCategory());
        
        if (this.getStockUnits() > 0)
            printAdapted("Units: " + this.getStockUnits());    
        else
            printAdapted("Product Unavailable!");
        
        System.out.println("\\\\--------------------------------------//\n\n"); 
    }
    
    public void printAdapted(String str) // 42 caracteres
    {
        // º início parametros... \\
        int maxLineSize = 42;
        String border = "||";
        String fill = " ";
        String continuation = "...";
        PrintStream output = new PrintStream(System.out);
        // fim parâmetros      º \\       
        
        if (str.length() == 0) return ;
        
        if ((str.length() > 0) && ((str.length() + 2 * border.length()) <= maxLineSize))
        {
            output.print(border + str);
            int diff = maxLineSize - 2 * border.length() - str.length();
            
            for (int i = 0; i < diff; i++)
                output.print(fill);
            
            output.println(border);
        }
        else
            output.println(
                    border +
                    str.substring(0, maxLineSize - 2 * border.length() - (continuation).length()) +
                    continuation +
                    border
            );
    }
    
    //marcos.cesar.camargo@usp.br

    public String getValidity()
    {
        return dateFormat.format(validityProduct.getTime());
    }
}
