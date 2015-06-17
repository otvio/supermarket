
package client;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    private Socket socket;
    private Scanner scanner;
    ServerListener sl;
    
    public void connect() throws Exception
    {
            socket = new Socket("localhost", 12345);
            System.out.println("::: Client Connected! :)");
            System.out.println("::: Awaiting the server... ");

            sl = new ServerListener(new PrintStream(socket.getOutputStream()), new Scanner(socket.getInputStream()));
            new Thread(sl).start();
            new Thread(new ClientSendThread()).start();
            
    }     
       
    class ClientSendThread implements Runnable
    {
        @Override
        public void run()
        {
            while(scanner.hasNextLine())
            {
                String command = scanner.nextLine();
                sl.sendToServer(command);
            }
        }
    }
    
    public static void main (String[] args)
    {
        Client c;
        
        try 
        {
            c = new Client();
            c.scanner = new Scanner(System.in);
            c.connect();
        } 
        catch (Exception ex) 
        {
            System.out.println("::: I'm so sorry! Client Error! :( :::");
        }
        finally
        {
            //System.out.println("\n\n:::Thank you for using this program. :::");
        }
    }
}
