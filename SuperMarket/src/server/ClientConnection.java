
package server;


import java.io.*;
import java.net.*;
import java.util.*;
import supermarket.entities.User;
import static server.Server.*;

// usada para ficar recebendo as conexões dos clientes
public class ClientConnection implements Runnable 
{
    //Singleton pattern:
    private static ClientConnection clientConnection = null;
    private static ServerSocket serverSocket;
    
    private static List<User> userList;                 //Lista de usuários
    private static List<ClientStruct> clientList;       // Lista de clientes

    
    private ClientConnection(ServerSocket serverSocket, 
            List<ClientStruct> clientList) throws Exception 
    {
        BufferedReader buffReader;

        ClientConnection.serverSocket = serverSocket;
        ClientConnection.clientList = clientList;
        
        buffReader = new BufferedReader(new FileReader(USERS_FILE));   // Leitura dos usuários no arquivo
        userList = getUsersList(buffReader);                           // Armazena os usuários na lista de usuários
    }
    
    public static synchronized ClientConnection getInstance(
            ServerSocket serverSocket, List<ClientStruct> clientList) throws Exception
    {
        if (ClientConnection.clientConnection == null)
            ClientConnection.clientConnection = new ClientConnection(serverSocket, clientList);
        
        return ClientConnection.clientConnection;
    }
    
    @Override
    public void run()
    {
        // loop para receber todos os clientes
        while(true)
        {
            try
            {
                getClient();
            }
            catch (Exception e)
            {
                System.out.println("::: ClientConnection->run() - Connection Error! :( :::");
            }
        }
    }

    // função que recebe cada cliente e cria um listener para cada
    public void getClient() throws Exception
    {
        // recebendo a conexão
        Socket socketUser = serverSocket.accept();

        // obtendo as streams para comunicação com o cliente
        Scanner receiveFromClient = new Scanner(socketUser.getInputStream());
        PrintStream sendToClient = new PrintStream(socketUser.getOutputStream());

        // criando o listener para o cliente
        CommunicateWithClient cl = new CommunicateWithClient(sendToClient, receiveFromClient);
        new Thread(cl).start();
    }   

    public static List<User> getUsersList(BufferedReader buffReader) throws Exception  // Irá criar uma lista de usuários a partir dos dados do arquivo
    {
        List<User> userlist = new ArrayList<>();  // Lista de usuário
        String line;                              // String para a linha do arquivo de dados
        String usertype;                
        
        while (buffReader.ready())                // enquanto tem todas as linhas do arquivo
        {
            line = buffReader.readLine();         // Pega os dados vindo do buffReader

            String[] userdata = line.split(",");  // Método que coloca em um vetor as string que estavam separadas por virgula

            userlist.add(
                    new User(Integer.parseInt(userdata[0]), userdata[1], userdata[2], 
                            userdata[3], userdata[4], userdata[5], userdata[6])
            );
        }
        buffReader.close();

        return (userlist);
    }
	// Método para pegar a lista de desejos do usuário
    public static List<Integer> getUserDesires(int codeUser) throws Exception
    {
	String line;
        List<Integer> desireList = new ArrayList<>();
        BufferedReader buffReader = new BufferedReader(new FileReader(DESIRE_FILE));
        
        while(buffReader.ready()) // Loop para ler enquanto houver dados no arquivo
	{
            line = buffReader.readLine();
            
            String[] desire = line.split(",");
            
            if(Integer.parseInt(desire[0]) == codeUser)
                desireList.add(Integer.parseInt(desire[1]));
        }
        buffReader.close();
        
        return desireList;
    }
}