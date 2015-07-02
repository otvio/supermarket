
package supermarket.entities;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Random;
import static server.Server.SUPPLIERS_FILE;

public class Supplier 
{
    private int codSupplier;
    private String nameSupplier;
    private String nameContact;
    private String contacting;
    
    // define para a quantidade mínima de produtos que o supplier deverá
    // enviar para o mercado "LORMarket"
    private static final int QUANTITY_TO_SEND = 50;
    
    public Supplier(int codSupplier, String nameSupplier, 
            String nameContact, String contacting) 
    {
        this.codSupplier = codSupplier;
        this.nameSupplier = nameSupplier;
        this.nameContact = nameContact;
        this.contacting = contacting;
    }

    
    public void addFileSupplier()
    {
        try
        {
            File fp = new File(SUPPLIERS_FILE);
            FileWriter fw = new FileWriter(fp, true);
            PrintWriter pw = new PrintWriter(fw); // cria um PrintWriter que irá escrever no arquivo

            if(fp.exists() == false)
            { // caso o arquivo nao exista, cria um arquivo
                fp.createNewFile();
            }

            pw.print(this.codSupplier);
            pw.print(",");
            pw.print(this.nameSupplier);
            pw.print(",");
            pw.print(this.nameContact);
            pw.print(",");
            pw.println(this.contacting);

            pw.close();
            fw.close();
        }
        catch(Exception e)
        {
            System.out.println("Can't store in the file :(");
        }
    }
	
    public int getCodSupplier() 
    {
        return codSupplier;
    }

    public void setCodSupplier(int codSupplier) 
    {
        this.codSupplier = codSupplier;
    }

    public String getNameSupplier() 
    {
        return nameSupplier;
    }

    public void setNameSupplier(String nameSupplier) 
    {
        this.nameSupplier = nameSupplier;
    }

    public String getNameContact() 
    {
        return nameContact;
    }

    public void setNameContact(String nameContact) 
    {
        this.nameContact = nameContact;
    }

    public String getContacting() 
    {
        return contacting;
    }

    public void setContacting(String contacting) 
    {
        this.contacting = contacting;
    }
    
    public int getMoreProducts(int codeProduct)
    {
        return (QUANTITY_TO_SEND + (new Random().nextInt(100)) + codeProduct);
    }
}
