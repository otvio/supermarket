
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
            /*
            public Product(int codProduct, int codSupplier, int codCategory, 
            int stockUnits, int orderedUnits, double unitPrice, 
            String nameProduct, String validity) /*/
            
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
        System.out.println("//--------------------------------------"); 
        System.out.println("||Product Code: " + (this.getCodProduct()));
        System.out.println("||Product name : " + this.getNameProduct());
        System.out.println("||Price: " + this.getUnitPrice());
        System.out.println("||Validity: " + dateFormat.format(this.validityProduct.getTime()));
        
        if (c != null)
            System.out.println("||Category: " + c.getNameCategory());
        
        if (this.getStockUnits() > 0)
            System.out.println("||Units: " + this.getStockUnits());    
        else
            System.out.println("||Product Unavailable!");
        
        System.out.println("\\\\--------------------------------------\n\n"); 
    }

    public String getValidity()
    {
        return dateFormat.format(validityProduct.getTime());
    }
}
