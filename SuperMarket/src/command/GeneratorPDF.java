
package command;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import supermarket.entities.Sale;


public class GeneratorPDF 
{
    
    static Scanner input = new Scanner (System.in);
    static String datachoice;
    static List<Sale> list = new ArrayList<>();
    
    
    public void GeneratorPDF()
    {
      
        list.add(new Sale(0,1,2,1,"28/06/2015"));
        list.add(new Sale(3,1,5,21,"12/06/2015"));
        list.add(new Sale(3,44,54,1,"24/06/2015"));
        list.add(new Sale(321,2,6,77,"28/05/2015"));
        
        for(Sale s: list)
        {
            System.out.println(s.toString());
        }
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
                               + "(2). Do mês\n");
            
            
            do
            {
                datachoice = input.next();
            }while((!datachoice.equals("1")) && (!datachoice.equals("2")));
            
            Calendar date = Calendar.getInstance();
            
            if(datachoice.equals("2"))
            {
                date.set(Calendar.DAY_OF_MONTH, 1);
            }           
         
              
            PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\8937402\\Desktop\\supermarket-master\\SuperMarket\\PDF_Teste.pdf"));
            document.open();
            
            document.add(new Paragraph("                 Lista de vendas - LORMarket\n\n"));
            
            for (Sale s : list)
            {
                if((datachoice.equals("1") && compareTo(s.getDateSale(), date) == 0) ||
                    datachoice.equals("2") && compareTo(s.getDateSale(), date) >= 0)
                {
                    System.out.println(s.toString());
                    document.add(new Paragraph(s.toString()));
                }
            }
            
            
            
            
//            Image figura = Image.getInstance("C:\\Users\\8937402\\Desktop\\poke.jpg");
//            document.add(figura);
            
//            document.newPage();
//            document.add(new Paragraph("Vendas efetuadas no mês: "));
            
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
