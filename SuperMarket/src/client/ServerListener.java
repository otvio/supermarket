
package client;

import java.io.PrintStream;
import java.util.Scanner;

public class ServerListener implements Runnable
{
    private final PrintStream toServer;
    private final Scanner fromServer;
    
    public ServerListener(PrintStream sendToServer, Scanner receiveFromServer)
    {
        this.fromServer = receiveFromServer;
        this.toServer = sendToServer;
    }
    
    public void sendToServer(String message)
    {
        toServer.println(message);
    }

    public String receiveFromServer()
    {
        return ((fromServer.hasNextLine()) ? fromServer.nextLine() : "");
    }
    
    @Override
    public void run() 
    {
        while(fromServer.hasNextLine())
        {
            String message = fromServer.nextLine();
            System.out.println(message);
        }
    }
}
