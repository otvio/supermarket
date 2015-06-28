
package supermarket.entities;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Objects;
import static server.Server.USERS_FILE;

public class User
{
    private int codUser;
    private String name;   
    private String address;
    private String email;
    private String telephone;
    private String ID;
    private String password;

    public User(int codUser, String name, String address, String email, String telephone, String ID, String password)
    {
        this.name = name;
        this.address = address;
        this.email = email;
        this.telephone = telephone;
        this.ID = ID;
        this.codUser = codUser;
        this.password = password;
    }

    public int getCodUser() 
    {
        return codUser;
    }

    public void setCodUser(int codUser) 
    {
        this.codUser = codUser;
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

    public String getTelephone()
    {
        return telephone;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    public String getID()
    {
        return ID;
    }

    public void setID(String ID) 
    {
        this.ID = ID;
    }

    public String getPassword() 
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public void addFileUser() 
    {
        try
        {
            File fp = new File(USERS_FILE);
            FileWriter fw = new FileWriter(fp, true);
            PrintWriter pw = new PrintWriter(fw);    // cria um PrintWriter que irá escrever no arquivo
        
            
            if(fp.exists() == false) // caso o arquivo nao exista, cria um arquivo
                fp.createNewFile();
            
            
            
            // Os comandos abaixo salvam os dados no arquivo, após cada dado adicionado é acrescentada uma virgula para separa-los.
            pw.print(this.getCodUser());
            pw.print(",");
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
            
            pw.close();
            fw.close();
  
        }
        catch (Exception e)
        {
            System.out.println("Can't write in the file.");
        }
    }

    @Override
    public String toString() 
    {
        return "User{" + "codUser=" + codUser + ", name=" + name + ", address=" + address + ", email=" + email + ", telephone=" + telephone + ", ID=" + ID + ", password=" + password + '}';
    }

    @Override
    public boolean equals(Object obj) 
    {
        if (obj == null) 
        {
            return false;
        }
        if (getClass() != obj.getClass()) 
        {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.name, other.name)) 
        {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) 
        {
            return false;
        }
        if (!Objects.equals(this.email, other.email)) 
        {
            return false;
        }
        if (!Objects.equals(this.telephone, other.telephone)) 
        {
            return false;
        }
        if (!Objects.equals(this.ID, other.ID)) 
        {
            return false;
        }
        return Objects.equals(this.password, other.password);
    }
    
    
}
