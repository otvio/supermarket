
package supermarket;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;


public class User 
{
    private String name;   
    private String address;
    private String email;
    private int telephone;
    private int ID;
    private int password;

    public User(String name, String address, String email, int telephone, int ID, int password)
    {
        this.name = name;
        this.address = address;
        this.email = email;
        this.telephone = telephone;
        this.ID = ID;
        this.password = password;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getEmail() 
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getTelephone()
    {
        return telephone;
    }

    public void setTelephone(int telephone)
    {
        this.telephone = telephone;
    }

    public int getID()
    {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getPassword() 
    {
        return password;
    }

    public void setPassword(int password)
    {
        this.password = password;
    }
    
    public void addFileUser() 
    {
        try
        {
            File fp = new File("users.csv");
            FileWriter fw = new FileWriter(fp, true);
            PrintWriter pw = new PrintWriter(fw);    // cria um PrintWriter que irá escrever no arquivo
        
            
            if(fp.exists() == false) // caso o arquivo nao exista, cria um arquivo
                fp.createNewFile();
            
            
            
            // Os comandos abaixo salvam os dados no arquivo, após cada dado adicionado é acrescentada uma virgula para separa-los.
            pw.print(this.getName());
            pw.print(",");
            pw.print(this.getAddress());
            pw.print(",");
            pw.print(this.getEmail());
            pw.print(",");
            pw.print(this.getTelephone());
            pw.print(",");
            pw.print(this.getID());
            pw.print(",");
            pw.print(this.getPassword());
            pw.print(",");
            
            pw.close();
            fw.close();
  
        }
        catch (Exception e)
        {
            System.out.println("Can't write in the file.");
        }
    }
}
