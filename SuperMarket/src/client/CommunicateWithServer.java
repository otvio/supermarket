
package client;

import static connection.Connection.*;
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
            String aux;
            
            System.out.println(message);
            
            switch (message) 
            {
//                case LOGIN_ATTEMPT:
//                    aux = "";
//                    while (!aux.equals(LOGIN_ATTEMPT))
//                    {
//                        
//                    }
//                    break;
                default:
                    //System.out.println(message);
            }
            
            
            
        }
    }
}
