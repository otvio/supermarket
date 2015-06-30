
package supermarket.entities;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import static server.Server.CATEGORIES_FILE;


public class Category 
{
    private int codCategory;
    private String nameCategory;
    private String description;

    
    public Category(int codCategory, String nameCategory, String description)
    {
        this.codCategory = codCategory;
        this.nameCategory = nameCategory;
        this.description = description;
    }
    
    public void addFileCategory(){
        try{
            File fp = new File(CATEGORIES_FILE);
            FileWriter fw = new FileWriter(fp, true);
            PrintWriter pw = new PrintWriter(fw); // cria um PrintWriter que irá escrever no arquivo
            
            if(fp.exists() == false)
            { // caso o arquivo nao exista, cria um arquivo
                fp.createNewFile();
            }
            
            pw.print(codCategory);
            pw.print(",");
            pw.print(nameCategory);
            pw.printf(",");
            pw.println(description);
            
            pw.close();
            fw.close();
        }
        catch(Exception e){
            System.out.println("Can't record in the file :/");    
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

    public String getNameCategory() 
    {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) 
    {
        this.nameCategory = nameCategory;
    }

    public String getDescription() 
    {
        return description;
    }

    public void setDescription(String description) 
    {
        this.description = description;
    }
}
