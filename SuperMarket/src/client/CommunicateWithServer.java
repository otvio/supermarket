
package client;

import static connection.Connection.*;
import java.io.PrintStream;
import java.util.Scanner;

public class CommunicateWithServer implements Runnable
{
    private ClientMenu clientMenu;
    private final PrintStream toServer;
    private final Scanner fromServer;
    private final Scanner input;
    private boolean stopThread;
    //myThread aux = new myThread();
    
    public CommunicateWithServer(PrintStream sendToServer, Scanner receiveFromServer, final Scanner input)
    {
        this.fromServer = receiveFromServer;
        this.toServer = sendToServer;
        this.input = input;
        this.stopThread = false;
        //Tentando arrumar o bug do CommunicateWithServer ... (thread não parando no momento desejado)
        
        //aux.start();
    }
    
    class myThread implements Runnable{
        
         private Thread t;
        @Override
        public synchronized void run()
        {
            while(t != null && !stopThread)
            {
                String command = input.nextLine();
                sendToServer(command);
            }

            System.out.println("ACABOOOOOOOOO");
        }
        
        public void stop(){
            t.interrupt();
            t = null;
        }

        public void start(){
            t = new Thread(this);
            t.start();
        }
    }
    
    public void sendToServer(String message)
    {
        toServer.println(message);
    }

    public String receiveFromServer()
    {
        return ((fromServer.hasNext()) ? fromServer.next() : "");
    }
    
    @Override
    public synchronized void run()
    {
        String[] command;
        boolean fromInput = false;
        while(fromServer.hasNext() || input.hasNext())  // loop para ficar recebendo do servidor
        {
            String message = (fromInput) ? input.nextLine() : fromServer.nextLine();
            command = (fromInput) ? new String[]{LOGIN} : message.split("\\|");
            //System.out.println("command[0]:{" + command[0] + "}\n" + "message:{" + message + "}\n");
            
            switch (command[0]) 
            {
                case LOGIN:
                    sendToServer(message);
                    fromInput = false;
                    break;
                
                case USERNAME:
                    fromInput = false;
                    //aux.stop();
                    
                    clientMenu = new ClientMenu(command[1], this, input);
                    System.out.println("antes do clientMenu.menu()...");
                    
                    new Thread()
                    {
                        @Override
                        public void run()
                        {
                            clientMenu.menu();
                        }
                    }.start();
                    System.out.println("dpois do clientMenu.menu()...");
                    break;
                case SIMPLETEXT:
//                    System.out.println("message:" + message);
//                    System.out.println("command.length" + command.length);
//                    System.out.println("command[1].length()" + command[1].length());
                    fromInput = (command.length > 1) && (command[1].length() > 1) && (command[1].charAt(command[1].length() - 1) == ':') && (command[1].charAt(command[1].length() - 2) != ':');
                    System.out.println((command.length == 1) ? "" : command[1]);
                    break;
                default:
                    System.out.println(message);
                    break;
            }
        }
    }
}
