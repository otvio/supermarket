
package client;

import java.io.InputStream;
import java.util.Scanner;


public class MyScanner 
{
    private final Scanner scanner;
    
    public MyScanner(InputStream is)
    {
        scanner = new Scanner(is);
    }
    
    public synchronized boolean hasNextLine()
    {
        return (scanner.hasNextLine());
    }
    
    public synchronized String nextLine()
    {
        return (scanner.nextLine());
    }
}
