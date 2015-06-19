
package client;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    private Socket socket;
    private Scanner scanner;
    private CommunicateWithServer communicate;
    
    public void connect() throws Exception
    {
		// tentando se conectar ao servidor
		socket = new Socket("localhost", 12345);
		System.out.println("::: Client Connected! :)");
		System.out.println("::: Awaiting the server... ");
		
		// criando a thread de comunicação com o servidor
		communicate = new CommunicateWithServer(
				new PrintStream(socket.getOutputStream()), 
				new Scanner(socket.getInputStream()));
		
		// iniciando a thread de comunicação com o servidor
		new Thread(communicate).start();
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
