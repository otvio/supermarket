
package command;

public class Command 
{
    // defines para comunicar cliente com servidor
    public static final String DELIMITER = "|";
    
    public static final String LOGIN = "@LOR@MARKET@:::LOGIN";
    public static final String NEWUSER = "@LOR@MARKET@:::NEWUSER";
    
    public static final String PURCHASE = "@LOR@MARKET@:::PURCHASE";
    public static final String DISCONNECT = "@LOR@MARKET@:::DISCONNECT";
    public static final String ADD_DESIRE = "@LOR@MARKET@:::ADD_DESIRE";
    public static final String SIMPLETEXT = "@LOR@MARKET@:::SIMPLETEXT";
    public static final String SEND_UPDATE = "@LOR@MARKET@:::SEND_UPDATE";
    public static final String SEND_DESIRE = "@LOR@MARKET@:::SEND_DESIRE";
    public static final String SEND_PRODUCT = "@LOR@MARKET@:::SEND_PRODUCT";
    public static final String SEND_CATEGORY = "@LOR@MARKET@:::SEND_CATEGORY";
    public static final String REMOVE_DESIRE = "@LOR@MARKET@:::REMOVE_DESIRE";
  
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
