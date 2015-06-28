
package supermarket.entities;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
        return (
        "//--------------------------------------"+ "\n" + 
        "||CodeSale: " + this.codSale+ "\n" + 
        "||User: " + user.getName()+ "\n" + 
        "||Product: " + product.getNameProduct() + "\n" + 
        "||QuantityProducts: " + this.quantityProducts+ "\n" +   
        "||DateSale: " + dateFormat.format(this.dateSale.getTime())+ "\n" + 
        "\\\\--------------------------------------\n\n"
        );
    }
}
