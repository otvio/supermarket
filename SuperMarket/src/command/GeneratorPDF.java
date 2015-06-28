
package command;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import supermarket.entities.Product;
import supermarket.entities.Sale;
import supermarket.entities.User;


public class GeneratorPDF 
{
    static int count = 1;
    static String datachoice;
    
    static Scanner input = new Scanner (System.in);
    
    
    static List<Sale> list = new ArrayList<>();
    static List<User> user = new ArrayList<>();
    static List<Product> product = new ArrayList<>();
    
    public void GeneratorPDF(List<Sale> list, List<User> user , List <Product> product)
    {      
        this.list = list;
        this.user = user;
        this.product = product;
    }
    
    public static void main(String[] args) throws FileNotFoundException 
    {        
        list.add(new Sale(0,1,2,1,"28/06/2015"));
        list.add(new Sale(3,1,5,21,"12/06/2015"));
        list.add(new Sale(3,44,54,1,"24/06/2015"));
        list.add(new Sale(3,1,5,21,"12/06/2015"));
        list.add(new Sale(3,44,54,1,"24/06/2015"));
        list.add(new Sale(3,1,5,21,"12/06/2015"));
        list.add(new Sale(3,44,54,1,"24/06/2015"));
        list.add(new Sale(3,1,5,21,"12/06/2015"));
        list.add(new Sale(3,44,54,1,"24/06/2015"));
        list.add(new Sale(3,1,5,21,"12/06/2015"));
        list.add(new Sale(3,44,54,1,"24/06/2015"));
        list.add(new Sale(321,2,6,77,"28/05/2015"));
        
        for(Sale s: list)
        {
            System.out.println(s.toString());
        }
        
        Document document = new Document();
        
        try
        {
            System.out.println("  ...::: LORMarket - Historic Sale :::...");
            System.out.println("A data utilizada será: \n "
                               + "(1). Vendas do dia\n"
                               + " (2). Do mês\n");
            
            
            do
            {
                datachoice = input.next();
            }while((!datachoice.equals("1")) && (!datachoice.equals("2")));
            
            Calendar date = Calendar.getInstance();
            
            if(datachoice.equals("2"))
            {
                date.set(Calendar.DAY_OF_MONTH, 1);
            }           
         
              
            PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\8937402\\Desktop\\Clonar\\SuperMarket\\Report-" + new SimpleDateFormat("dd-MM-yyyy").format(date.getTime()) + ".pdf"));
            document.open();
            
            Image figura = Image.getInstance("C:\\Users\\8937402\\Desktop\\Clonar\\SuperMarket\\SuperMarket\\café.jpg");
            figura.setAbsolutePosition(50f, 720f);
            document.add(figura);
            
            document.add(new Paragraph("      \n\n                                                              Company Sales - Ltda\n"));
            document.add(new Paragraph("             \n\n\n\n\n\n                                                     --------------------------------------------------------"));
            document.add(new Paragraph("                                                                         Sale List - In general "));
            document.add(new Paragraph("                                                    --------------------------------------------------------"));
            
            
            if(datachoice.equals("1"))
                document.add(new Paragraph("Day sales: \n\n"));
            else
                document.add(new Paragraph("Month sales: \n\n"));
            
            Collections.sort(product, new Comparator<Product>()
            {
                @Override
                public int compare(Product e1, Product e2) 
                {
                    return (e1.getCodProduct()< e2.getCodProduct()) ? -1 : 1;
                }
            });
            
            Collections.sort(user, new Comparator<User>()
            {
                @Override
                public int compare(User e1, User e2) 
                {
                    return (e1.getCodUser()< e2.getCodUser()) ? -1 : 1;
                }
            });
            
            
            for (Sale s : list)
            {
                if((datachoice.equals("1") && compareTo(s.getDateSale(), date) == 0) ||
                    datachoice.equals("2") && compareTo(s.getDateSale(), date) >= 0)
                {
                    document.add(new Paragraph(count + "."));
                    System.out.println(s.toString());
                    document.add(new Paragraph(s.toString(user.get(s.getCodUser()), product.get(s.getCodProduct()))));
                }
                count++;
            }
            
            document.add(new Paragraph("\n                                       Copyright © 2015 - Company LORMarket. "));
            
        }catch(DocumentException de)
        {
            System.err.println(de.getMessage());
        }
        catch(IOException ioe)
        {
            System.err.println(ioe.getMessage());
        }
        
        document.close();
        
        
    }
    
    public static int compareTo(Calendar c1, Calendar c2)
    {
        int day1 = c1.get(Calendar.DAY_OF_MONTH);
        int day2 = c2.get(Calendar.DAY_OF_MONTH);
        
        int month1 = c1.get(Calendar.MONTH);
        int month2 = c2.get(Calendar.MONTH);
        
        int year1 = c1.get(Calendar.YEAR);
        int year2 = c2.get(Calendar.YEAR);
        
        if(year1 < year2 )
            return (-1);
        
        if(year2 < year1)
            return (1);
        
        if(month1 < month2)
            return (-1);
        
        if(month2 < month1)
            return(1);
        
        if(day1 < day2)
            return(-1);
        
        if(day2 < day1)
            return (1);
        
        return (0); 
    } 
}
