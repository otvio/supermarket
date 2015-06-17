
package supermarket;

import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import org.omg.SendingContext.RunTime;


public class Client
{
    private Scanner input;
    private PrintStream output;
    private Socket socket;
    private Scanner scanner;
    
    public boolean connect()
    {
        try
        {
            socket = new Socket("localhost", 12345);
            System.out.println("Client Connected!");

            scanner = new Scanner (System.in);
            input = new Scanner (socket.getInputStream());
            output = new PrintStream(socket.getOutputStream());
            
            new Thread(new ClientSendThread()).start();
            new Thread(new ClientReadThread()).start();
            
            return (true);
        }
        catch (Exception ex)
        {
            System.out.println("Conection Error :(");
            return (false);
        }
    }     
       
    class ClientSendThread implements Runnable
    {
        @Override
        public void run()
        {
            while(scanner.hasNextLine()){
                String command = scanner.nextLine();
                
                output.println(command);
            }
        }
    }
    class ClientReadThread implements Runnable{

        @Override
        public void run() {
            while(input.hasNextLine()){
                System.out.println(input.nextLine());
            }
        }
        
    }
    public static void main (String[] args){
        Client c = new Client();
        c.connect();
    }
}
