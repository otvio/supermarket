
package supermarket;


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
