
package extras.reportpdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import javax.swing.filechooser.FileSystemView;
import server.ServerMenu;
import static supermarket.SuperMarket.cutDecimalDigits;
import supermarket.entities.Product;
import supermarket.entities.Sale;
import supermarket.entities.User;


public class GeneratorPDF 
{
    static int count;
    static String datachoice;
    static Scanner input = new Scanner (System.in);
    
    static List<Sale> sale = new ArrayList<>();
    static List<User> user = new ArrayList<>();
    static List<Product> product = new ArrayList<>();
    
    public static void generate() 
    {
        GeneratorPDF.sale = ServerMenu.getSalesList();
        GeneratorPDF.user = ServerMenu.getUsersList();
        GeneratorPDF.product = ServerMenu.getProductsList();
        
        Document document = new Document();
        
        try
        {
            do
            {
                System.out.println("\t:::::::::::::::::::::::::::::::::");
                System.out.println("\t::: LORMarket - Historic Sale :::");
                System.out.println("\t:::::::::::::::::::::::::::::::::\n\n");
            
                System.out.print(  "::::::::::::::::::::::::::::::\n" 
                                 + ":: Analyse the sales based: ::\n"
                                 + "::   (1). on today          ::\n"
                                 + "::   (2). at month          ::\n"
                                 + "::::::::::::::::::::::::::::::\n\n"
                                 + ":: Type your choice: ");
                
                datachoice = input.next();
            }while((!datachoice.equals("1")) && (!datachoice.equals("2")));
            
            Calendar date = Calendar.getInstance();
            
            if(datachoice.equals("2"))
                date.set(Calendar.DAY_OF_MONTH, 1);
            
            File home = FileSystemView.getFileSystemView().getHomeDirectory();
            File folder = new File(home.getPath() + "\\SuperMarket\\Reports\\");
            folder.mkdirs();
            
            PdfWriter.getInstance(document, 
                    new FileOutputStream(folder.getPath() 
                            + "\\Report-DAY(" + new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime()) 
                            + ")_HOUR(" + new SimpleDateFormat("hh-mm-ss").format(Calendar.getInstance().getTime()) 
                            + ").pdf"));
            document.open();
            
            Image figura = Image.getInstance("logo.jpg");
            figura.setAbsolutePosition(50f, 720f);
            document.add(figura);
            
            Font fontUnderline = FontFactory.getFont("Verdana", 14, Font.UNDERLINE);
            Font fontNormal = FontFactory.getFont("Verdana", 12, Font.NORMAL);
            
            document.add(new Paragraph("      \n\n                                                              Company Sales - Ltd.\n", fontNormal));
            document.add(new Paragraph("             \n\n\n\n\n\n                                                     --------------------------------------------------------", fontNormal));
            document.add(new Paragraph("                                                                         Sale List - In general ", fontNormal));
            document.add(new Paragraph("                                                    --------------------------------------------------------", fontNormal));
            
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            
            document.add(new Paragraph("Solicitation of report: " + 
                    new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime()), fontNormal));
            
            if(datachoice.equals("1"))
                document.add(new Paragraph("Analyzed day: " + 
                    new SimpleDateFormat("dd/MM/yyyy").format(date.getTime()) , fontNormal));
            else
                document.add(new Paragraph("Analyzed month: " + 
                    new SimpleDateFormat("MM/yyyy").format(date.getTime()) , fontNormal));
            
            document.add(Chunk.NEWLINE);
            
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
            
            document.addCreationDate();
            
            List<Sale> mylist = new ArrayList<>();
            double totalProfit = 0.0;
            
            for (Sale s : sale)
            {
                if((datachoice.equals("1") && compareTo(s.getDateSale(), date) == 0) ||
                    datachoice.equals("2") && compareTo(s.getDateSale(), date) >= 0)
                {
                    mylist.add(s);
                    totalProfit += (s.getQuantityProducts() * product.get(s.getCodProduct()).getUnitPrice());
                }
            }
            
            count = 1;
            for (Sale s : mylist)
            {
                document.add(new Paragraph("Sale " + count + ". ", fontUnderline));
                document.add(Chunk.NEWLINE);
                document.add(new Paragraph(s.toString(user.get(s.getCodUser()), product.get(s.getCodProduct())), fontNormal));
                document.add(Chunk.NEWLINE);
                count++;
            }
            
            Map<String, Integer> quantityPerProduct = new HashMap<>();
            for (Sale s : mylist)
                quantityPerProduct.put(product.get(s.getCodProduct()).getNameProduct(), 0);
            
            for (Sale s : mylist)
            {
                int quantity = quantityPerProduct.get(product.get(s.getCodProduct()).getNameProduct());
                quantityPerProduct.remove(product.get(s.getCodProduct()).getNameProduct());
                quantityPerProduct.put(product.get(s.getCodProduct()).getNameProduct(), quantity + s.getQuantityProducts());
            }
            
            document.add(new Paragraph("Statistics\n", fontUnderline));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph(">> Quantity per product:\n\n", fontNormal));
            
            for (Sale s : mylist)
            {
                String name = product.get(s.getCodProduct()).getNameProduct();
                if (quantityPerProduct.get(name) != null)
                {
                    String namePrint = (name.length() > 10) ? name.substring(0, 10) + "..." : name;
                    document.add(new Paragraph("     Product " + namePrint + " >> " + quantityPerProduct.get(name) + " units purchased\n\n", fontNormal));
                    quantityPerProduct.remove(name);
                }
            }
            
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph(">> Total profit: $" + cutDecimalDigits(String.valueOf(totalProfit)) + "\n\n", fontNormal));
            
            document.add(new Paragraph("\n                                       Copyright © 2015 - Company LORMarket. ", fontNormal));
            
        }
        catch(DocumentException | IOException de)
        {
            System.err.println(de.getMessage());
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
