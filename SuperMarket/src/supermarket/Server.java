
package supermarket;

import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Server 
{
    
    private ServerSocket server;
    private Socket socketUser;
    private Scanner scanner = null;
    private PrintStream output;
    
    
    public boolean createServer()
    {
       try
       {
           server = new ServerSocket(12345);
           System.out.println("ServerConected");
           
           return (true);
       }
       catch (Exception ex)
       {
           System.out.println("::::: Conection Error! ::::");
           
           return (false);
       }
    }
      
    public boolean getClient()
    {
        try
        {
            socketUser = server.accept();
            System.out.println("Client connection accepted!\n");
            
            scanner = new Scanner(socketUser.getInputStream());
            output = new PrintStream(socketUser.getOutputStream());
            
            new Thread(new ServerReadThread(output, scanner)).start();
            
            return (true);
        }
        catch (Exception e)
        {
            System.out.println("Connection error!");
            return (false);
        }    
    }
    
    class CreateConnection implements Runnable
    {
        @Override
        public synchronized void run()
        {
            getClient();        
        }
    }
    
    class ServerReadThread implements Runnable
    {
        PrintStream ps;
        Scanner scan;

        public ServerReadThread(PrintStream ps, Scanner scan) {
            this.ps = ps;
            this.scan = scan;
        }
        
        @Override
        public synchronized void run()
        {
            System.out.println("print aqui antes");

            System.out.println("Para ver quando ele sai");
            while(scan.hasNextLine())
            {
                String message = scan.nextLine();

                System.out.println(message);
                ps.println(message);
            }  
        }
    }
    
    public static void main (String[] args){
        Server s = new Server();
        s.createServer();
        new Thread(s.new CreateConnection()).start();
    }
}
