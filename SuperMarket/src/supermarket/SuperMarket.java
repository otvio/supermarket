
package supermarket;

import client.Client;
import java.io.PrintStream;
import java.util.Scanner;
import server.Server;

public class SuperMarket
{
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        String choice;
        do{
            System.out.println("Do you wanna be a:");
            System.out.println("    (1) client");
            System.out.println("    (2) server");
            
            choice = in.nextLine();
            
        } while (!choice.equals("1") && !choice.equals("2"));
        
        if (choice.equals("1"))
            Client.main(null);
        else
            Server.main(null);
    }
    
    public static void printAdapted(String str) // 42 caracteres
    {
        // º início parametros... \\
        int maxLineSize = 42;
        String border = "||";
        String fill = " ";
        String continuation = "...";
        PrintStream output = new PrintStream(System.out);
        // fim parâmetros      º \\       
        
        if (str.length() == 0) return ;
        
        if ((str.length() > 0) && ((str.length() + 2 * border.length()) <= maxLineSize))
        {
            output.print(border + str);
            int diff = maxLineSize - 2 * border.length() - str.length();
            
            for (int i = 0; i < diff; i++)
                output.print(fill);
            
            output.println(border);
        }
        else
            output.println(
                    border +
                    str.substring(0, maxLineSize - 2 * border.length() - (continuation).length()) +
                    continuation +
                    border
            );
    }
    
    public static void printAdapted(String str, String[] output) // 42 caracteres
    {
        // º início parametros... \\
        int maxLineSize = 42;
        String border = "||";
        String continuation = "...";
        // fim parâmetros      º \\       
        
        if (str.length() != 0)
        {
            if ((str.length() > 0) && ((str.length() + 2 * border.length()) <= maxLineSize))
                output[0] += (str);
            else
                output[0] += (str.substring(0, 15) + continuation);
            
            output[0] += "\n";
        }
    }
    
    public static String cutDecimalDigits(String source)
    {
        String str = source;
        String[] strSplitted = str.split("\\.");
        
        if ((strSplitted.length > 1) && (strSplitted[1].length() > 2))
            str = strSplitted[0] + "." + strSplitted[1].substring(0, 2);
        
        return (str);
    }
}
