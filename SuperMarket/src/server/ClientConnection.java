
package server;

import static connection.Connection.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import supermarket.entities.User;
import static server.Server.USERS_FILE;
import static server.Server.DESIRE_FILE;


// usada para ficar recebendo as conexões dos clientes
class ClientConnection implements Runnable 
{
    boolean beNotified;
    ServerSocket serverSocket;
    
    List<User> userList;
    List<ClientStruct> clientList;
    
    
    public ClientConnection(ServerSocket serverSocket, List<ClientStruct> clientList, 
            boolean beNotified) throws Exception 
    {
        BufferedReader buffReader;

        this.serverSocket = serverSocket;
        this.clientList = clientList;
        this.beNotified = beNotified;
        
        buffReader = new BufferedReader(new FileReader(USERS_FILE));   // Leitura dos usuários no arquivo
        userList = getUsersList(buffReader);                           // Armazena os usuários na lista de usuários
        buffReader.close();
    }
    
    
    @Override
    public void run()
    {
        ClientStruct cs;
        
        // loop para receber todos os clientes
        while(true)
        {
            try
            {
                cs = getClient();

                if (cs != null) // se foi possível receber algum cliente que fez login corretamente
                {
                    clientList.add(cs);                              // adicionando-o à lista de clientes
                    cs.communicate.setNameClient(cs.user.getName()); // setando o nome do cliente, para usá-lo nas mensagens
                    cs.communicate.sendHeader();                     // printando o cabeçalho do programa para o cliente
                    
                    if (beNotified)
                    {
                        System.out.println("\n::: ClientConnection # run() - New Connection Accepted! :) ");
                        System.out.println("::: User {" + cs.user.getName() + "} logged in. ");
                        System.out.println("::: Now, " + clientList.size() + " client(s) logged in! \n");
                    }
                }
            }
            catch (Exception e)
            {
                System.out.println("::: ClientConnection->run() - Connection Error! :( :::");
            }
        }
    }

    // função que recebe cada cliente e cria um listener para cada
    public ClientStruct getClient() throws Exception
    {
        // recebendo a conexão
        Socket socketUser = serverSocket.accept();

        // obtendo as streams para comunicação com o cliente
        Scanner receiveFromClient = new Scanner(socketUser.getInputStream());
        PrintStream sendToClient = new PrintStream(socketUser.getOutputStream());

        // criando o listener para o cliente
        CommunicateWithClient cl = new CommunicateWithClient(sendToClient, receiveFromClient);
        
        // obtendo o usuário no sistema
        User user = askUser(cl);
        
        // se o usuário não foi encontrado
        if (user == null)
        {
            cl.sendToClient("\n::: This user is already logged in.");
            cl.sendToClient("::: Try again later.\n");
            return (null);
        }
        else
        {
            new Thread(cl).start();
            List<Integer> listDesires = getUserDesires(user.getCodUser());
            return (new ClientStruct(user, cl, listDesires));
        }
    }
    
    public User askUser(CommunicateWithClient cl)
    {
        int codeUser;                               // Variável para o código do usuário
        String name, address;                       // Strings para os dados pessoais do usuário
        String email, telephone;                    // Strings para os dados pessoais do usuário
        String ID, password, passwordConfirm;       // Strings para a confirmação de acesso do usuário
        String user_choice;                         // String para a opção de acesso no sistema
        User user = null;
        
        cl.sendToClient("\n\t:::::    Welcome to the LORMarket!    :::::\n");
        
        cl.sendToClient("You are a:\n"
                         + "   (1). New user\n"
                         + "   (2). Existent user\n");
        
        do
        {
            user_choice = cl.receiveFromClient();
            
            if ((user_choice.equals("2")) && userList.isEmpty()) // Caso ainda não exista nenhum usuário na lista de usuários
            {
                cl.sendToClient("::: There's no users yet, create a new one! :::\n\n");
                user_choice = "3";
            }
                
        } while ((!user_choice.equals("1")) && (!user_choice.equals("2"))); // Loop para escolher uma das duas opções
        

        if (user_choice.equals("1"))  // Caso a opção escolhida seja 1, então será adicionado um novo usuário ao sistema
        {
            codeUser = (!userList.isEmpty()) ? (userList.get(userList.size() - 1).getCodUser() + 1) : 0;  // Código que será adicionado para o usuário
            
            cl.sendToClient("Please, answer according to what will be asked.");
            
            cl.sendToClient("\n::: Personal information");
            
            cl.sendToClient("Name: ");
            name = cl.receiveFromClient();       // Armazena o nome fornecido pelo usuário 
            
            cl.sendToClient("Address: ");
            address = cl.receiveFromClient();    // Solicita o endereço
            
            cl.sendToClient("E-mail: ");
            email = cl.receiveFromClient();      // Solicita o e-mail
            
            cl.sendToClient("Telephone: ");
            telephone = cl.receiveFromClient();  // Solicita o telefone
            
            cl.sendToClient("\n::: Login information");
            
            cl.sendToClient("ID/Nickname: ");
            ID = cl.receiveFromClient();         // Solicita o ID

            do
            {
                cl.sendToClient("Password: ");
                password = cl.receiveFromClient();          // Solicita a senha

                cl.sendToClient("Confirm the password: ");
                passwordConfirm = cl.receiveFromClient();   // Solicita a confirmação da senha
            
                if (!password.equals(passwordConfirm))
                    cl.sendToClient("::: Incorrect password! Try again. :::\n");
                
            } while(!password.equals(passwordConfirm));     // Loop para o usuário digitar e confirmar as senhas corretas
            
            user = new User(codeUser, name, address, email, telephone, ID, password);

            cl.sendToClient("\n::: New user inserted successfully! :::");
        }
        else
        {
            do
            {
                cl.sendToClient("ID/Nickname: ");
                ID = cl.receiveFromClient();         // Solicita o ID

                cl.sendToClient("Password: ");
                password = cl.receiveFromClient();   // Solicita a senha

                user = isValidUser(ID, password);    // Verifica se o usuário já existe no sistema
                
                if (user == null)  // Caso o usuário ou senha seja digitada errada ele informa que deve ser repetida a operação
                    cl.sendToClient("::: Incorrect ID/password! Try again. :::\n");
                                
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
	
    public List<Integer> getUserDesires(int codeUser) throws Exception
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
}