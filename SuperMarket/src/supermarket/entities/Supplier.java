
package supermarket.entities;

public class Supplier 
{
    private int codSupplier;
    private String nameSupplier;
    private String nameContact;
    private String contacting;

    
    public Supplier(int codSupplier, String nameSupplier, 
            String nameContact, String contacting) 
    {
        this.codSupplier = codSupplier;
        this.nameSupplier = nameSupplier;
        this.nameContact = nameContact;
        this.contacting = contacting;
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
}
