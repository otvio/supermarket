
package client;

import java.io.PrintStream;
import java.util.Scanner;

public class CommunicateWithServer implements Runnable
{
    private final PrintStream toServer;
    private final Scanner fromServer;
    
    public CommunicateWithServer(PrintStream sendToServer, Scanner receiveFromServer)
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
        while(fromServer.hasNextLine())  // loop para ficar recebendo do servidor
        {
            String message = fromServer.nextLine();
            System.out.println(message);
        }
    }
}
