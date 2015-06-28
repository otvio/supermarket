
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
    
    private static List<User> userList;
    private static List<ClientStruct> clientList;

    
    private ClientConnection(ServerSocket serverSocket, 
            List<ClientStruct> clientList) throws Exception 
    {
        BufferedReader buffReader;

        ClientConnection.serverSocket = serverSocket;
        ClientConnection.clientList = clientList;
        
        buffReader = new BufferedReader(new FileReader(USERS_FILE));   // Leitura dos usuários no arquivo
        userList = getUsersList(buffReader);                           // Armazena os usuários na lista de usuários
        buffReader.close();
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
//        
//        // obtendo o usuário no sistema
//        User user = askUser(cl);
//        
//        // se o usuário não foi encontrado
//        if (user == null)
//        {
//            cl.sendToClient_SimpleText("\n::: This user is already logged in.");
//            cl.sendToClient_SimpleText("::: Try again later.\n");
//            return (null);
//        }
//        else
//        {
//            new Thread(cl).start();
//            List<Integer> listDesires = getUserDesires(user.getCodUser());
//            return (new ClientStruct(user, cl, listDesires));
//        }
    }
    
    public User askUser(CommunicateWithClient cl)
    {
        int codeUser;                               // Variável para o código do usuário
        String name, address;                       // Strings para os dados pessoais do usuário
        String email, telephone;                    // Strings para os dados pessoais do usuário
        String ID, password, passwordConfirm;       // Strings para a confirmação de acesso do usuário
        String user_choice;                         // String para a opção de acesso no sistema
        User user = null;
        
        cl.sendToClient_SimpleText("\n\t:::::    Welcome to the LORMarket!    :::::\n");
        
        do
        {
            cl.sendToClient_SimpleText("You are a\n"
                                 + "   (1). New user\n"
                                 + "   (2). Existent user\n\n"
                                 + "Choice:");
            
            user_choice = cl.receiveFromClient();
            
            if ((user_choice.equals("2")) && userList.isEmpty()) // Caso ainda não exista nenhum usuário na lista de usuários
            {
                cl.sendToClient_SimpleText("::: There's no users yet, create a new one! :::\n\n");
                user_choice = "3";
            }
                
        } while ((!user_choice.equals("1")) && (!user_choice.equals("2"))); // Loop para escolher uma das duas opções
        

        if (user_choice.equals("1"))  // Caso a opção escolhida seja 1, então será adicionado um novo usuário ao sistema
        {
            codeUser = (!userList.isEmpty()) ? (userList.get(userList.size() - 1).getCodUser() + 1) : 0;  // Código que será adicionado para o usuário
            
            cl.sendToClient_SimpleText("Please, answer according to what will be asked.");
            
            cl.sendToClient_SimpleText("\n::: Personal information");
            
            cl.sendToClient_SimpleText("Name:");
            name = cl.receiveFromClient();       // Armazena o nome fornecido pelo usuário 
            
            cl.sendToClient_SimpleText("Address:");
            address = cl.receiveFromClient();    // Solicita o endereço
            
            cl.sendToClient_SimpleText("E-mail:");
            email = cl.receiveFromClient();      // Solicita o e-mail
            
            cl.sendToClient_SimpleText("Telephone:");
            telephone = cl.receiveFromClient();  // Solicita o telefone
            
            cl.sendToClient_SimpleText("\n::: Login information");
            
            cl.sendToClient_SimpleText("ID/Nickname:");
            ID = cl.receiveFromClient();         // Solicita o ID

            do
            {
                cl.sendToClient_SimpleText("Password:");
                password = cl.receiveFromClient();          // Solicita a senha

                cl.sendToClient_SimpleText("Confirm the password:");
                passwordConfirm = cl.receiveFromClient();   // Solicita a confirmação da senha
            
                if (!password.equals(passwordConfirm))
                    cl.sendToClient_SimpleText("::: Incorrect password! Try again. :::\n");
                
            } while(!password.equals(passwordConfirm));     // Loop para o usuário digitar e confirmar as senhas corretas
            
            user = new User(codeUser, name, address, email, telephone, ID, password);

            cl.sendToClient_SimpleText("\n::: New user inserted successfully! :::");
        }
        else
        {
            do
            {
                cl.sendToClient_SimpleText("ID/Nickname:");
                ID = cl.receiveFromClient();         // Solicita o ID

                cl.sendToClient_SimpleText("Password:");
                password = cl.receiveFromClient();   // Solicita a senha

                user = isValidUser(ID, password);    // Verifica se o usuário já existe no sistema
                
                if (user == null)  // Caso o usuário ou senha seja digitada errada ele informa que deve ser repetida a operação
                    cl.sendToClient_SimpleText("::: Incorrect ID/password! Try again. :::\n");
                                
            } while (user == null); // Loop para o usuário digitar corretamente seu username e password
            
            
            // loop para verificar se o usuário já está logado
            for (ClientStruct cs : clientList)
            {
                if (cs.user.getCodUser() == user.getCodUser())
                {
                    user = null;
                    break;
                }
            }
        }
        
        return(user);
    }
    
    public User isValidUser(String ID, String password) // Método para verificar a existência do usuário no sistema
    {
        if (userList == null)             // Caso a lista de usuários não exista é retornado null
            return (null);
        
        for (User user : userList)        // Loop para verificar se existe o usuário solicitado no sistema
            if (user.getID().equals(ID) && user.getPassword().equals(password)) 
                return(userList.get(user.getCodUser()));               

        return (null);                    // Retorna null caso não ache o usuário
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

        return (userlist);
    }
	
    public static List<Integer> getUserDesires(int codeUser) throws Exception
    {
	String line;
        List<Integer> desireList = new ArrayList<>();
        BufferedReader buffreader = new BufferedReader(new FileReader(DESIRE_FILE));
        
        while(buffreader.ready())
	{
            line = buffreader.readLine();
            
            String[] desire = line.split(",");
            
            if(Integer.parseInt(desire[0]) == codeUser)
                desireList.add(Integer.parseInt(desire[1]));
        }
        
        return desireList;
    }
    
    public static List<User> getUserList() 
    {
        return userList;
    }
    
    public static List<ClientStruct> getClientList() 
    {
        return clientList;
    }
}