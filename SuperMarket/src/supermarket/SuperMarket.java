
package supermarket;

import java.io.PrintStream;

public class SuperMarket
{
    public static void main(String[] args)
    {
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
    
    public static void printAdapted(String str, String output) // 42 caracteres
    {
        // º início parametros... \\
        int maxLineSize = 42;
        String border = "||";
        String fill = " ";
        String continuation = "...";
        // fim parâmetros      º \\       
        
        if (str.length() != 0)
        {
            if ((str.length() > 0) && ((str.length() + 2 * border.length()) <= maxLineSize))
            {
                output += (border + str);
                int diff = maxLineSize - 2 * border.length() - str.length();

                for (int i = 0; i < diff; i++)
                    output += (fill);

                output += (border);
            }
            else
                output += (
                        border +
                        str.substring(0, maxLineSize - 2 * border.length() - (continuation).length()) +
                        continuation +
                        border
                );
        }
        
        output += "\n";
    }
}
