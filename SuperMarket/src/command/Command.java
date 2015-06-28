
package command;

public class Command 
{
    // defines para comunicar cliente com servidor
    public static final String DELIMITER = "|";
    public static final String LOGIN = "@LOR@MARKET@:::LOGIN";
    public static final String NEWUSER = "@LOR@MARKET@:::NEWUSER";
    public static final String PURCHASE = "@LOR@MARKET@:::PURCHASE";
    //public static final String USERNAME = "@LOR@MARKET@:::USERNAME";
    public static final String SIMPLETEXT = "@LOR@MARKET@:::SIMPLETEXT";
    
    private String command;
    private String[] commandSplitted;
    
    public Command(String[] vec)
    {
        command = "";
        commandSplitted = vec;
        
        for (int i = 0; i < vec.length; i++) 
            command += vec[i] + ((i == vec.length - 1) ? "" : DELIMITER);
    }
    
    public Command(String str)
    {
        command = str;
        commandSplitted = str.split("\\" + DELIMITER);
    }
    
    public String get()
    {
        return command;
    }
    
    public String[] getArray()
    {
        return commandSplitted;
    }
}
