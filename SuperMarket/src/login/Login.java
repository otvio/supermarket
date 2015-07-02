
package login;

import command.Command;
import server.*;
import java.util.*;
import supermarket.entities.User;

public class Login
{
    private int codClient;                                     // Int para armazenar o codigo do cliente
    private String nameClient;                                 // String para armazenar o nome do cliente
    private LoginAttempt attempt;                              // attempt para verificar a tentativa de login
    private final Command command;                             // Command para verificar a tentativa de login
    private final List<User> userlist;                         // Lista de usuários
    private final List<ClientStruct> clientlist;               // Lista de clientes
    private final CommunicateWithClient communicateWithClient; // Responsável para comunicar com o servidor
    // Construtor da classe login
    public Login(Command command, CommunicateWithClient cwc)
    {
        userlist = ServerMenu.getUserList();
        clientlist = ServerMenu.getClientList();
        this.command = command;
        this.communicateWithClient = cwc;
    }
    // Método para tentativa de login
    public LoginAttempt loginAttempt()
    {
        String ID = command.getArray()[1], password = command.getArray()[2];
        User user = null;
        
        for (User u : userlist) // Loop para verificar se usuário e senha é válido
        {
            if (u.getID().equals(ID) && u.getPassword().equals(password))
            {
                user = u;
                break;
            }
        }
        // Se Usuário for null da falha de conexão
        if (user == null)
            attempt = LoginAttempt.FAILED;
        else
        {
            attempt = LoginAttempt.SUCCESS;
            
            for (ClientStruct client : clientlist) // Loop para verificar o código do usuário e deixar ele logado
            {
                if (client.getUser().getCodUser() == user.getCodUser())
                {
                    attempt = LoginAttempt.ALREADY_LOGGED;
                    break;
                }
            }
            
            if (attempt == LoginAttempt.SUCCESS) // Caso tenha sido efetuado o login, então
            {                                   //  o usuário será inserido na lista de usuários online
                nameClient = user.getName();
                codClient = user.getCodUser();
                List<Integer> list;
                try
                {
                    list = ClientConnection.getUserDesires(user.getCodUser());
                    clientlist.add(new ClientStruct(
                            user, communicateWithClient, list)
                    );
                    ServerMenu.getDesireList().put(user, list);
                } catch (Exception ex) { }
            }
        }
        
        return (attempt);
    }
    // Método para criar um novo usuario
    public LoginAttempt createNewUser()
    {   // ordena o usuário conforme o seu código
        Collections.sort(userlist, new Comparator<User>()
        {
            @Override
            public int compare(User o1, User o2)
            {
                return o1.getCodUser() < o2.getCodUser() ? -1 : 1;
            }
        });
        // Armazena os dados do usuário
        User user = new User(userlist.get(userlist.size() - 1).getCodUser() + 1, 
                command.getArray()[1], command.getArray()[2], 
                command.getArray()[3], command.getArray()[4], 
                command.getArray()[5], command.getArray()[6]
        );
        
        attempt = LoginAttempt.SUCCESS;
        
        for (User u : userlist) // Loop que verifica se o usuário já está na lista
        {
            if (u.equals(user))
            {
                attempt = LoginAttempt.ALREADY_EXISTS;
                break;
            }
        }
        
        if (attempt == LoginAttempt.SUCCESS)  // Se o usuário for válido será adicionado no arquivo
        {
            nameClient = user.getName();
            codClient = user.getCodUser();
            userlist.add(user);
            user.addFileUser();
            
            try
            {
                clientlist.add(new ClientStruct
                        (user, communicateWithClient, new ArrayList<Integer>())
                );
                ServerMenu.getDesireList().put(user, new ArrayList<Integer>());
            } catch (Exception ex) { }
        }
        
        return (attempt);
    }
    // Método para pegar o nome do cliente
    public String getNameClient()
    {
        return nameClient;
    }
    // Método para pegar o código do cliente
    public int getCodClient()
    {
        return codClient;
    }
}
