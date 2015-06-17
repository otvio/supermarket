
package supermarket;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Product 
{
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
}
