
package login;

import command.Command;
import server.*;
import java.util.*;
import supermarket.entities.User;

public class Login
{
    private int codClient;
    private String nameClient;
    private LoginAttempt attempt;
    private final Command command;
    private final List<User> userlist;
    private final List<ClientStruct> clientlist;
    private final CommunicateWithClient communicateWithClient;
    
    public Login(Command command, CommunicateWithClient cwc)
    {
        userlist = ClientConnection.getUserList();
        clientlist = ClientConnection.getClientList();
        this.command = command;
        this.communicateWithClient = cwc;
    }
    
    public LoginAttempt loginAttempt()
    {
        String ID = command.getArray()[1], password = command.getArray()[2];
        User user = null;
        
        for (User u : userlist) 
        {
            if (u.getID().equals(ID) && u.getPassword().equals(password))
            {
                user = u;
                break;
            }
        }
        
        if (user == null)
            attempt = LoginAttempt.FAILED;
        else
        {
            attempt = LoginAttempt.SUCCESS;
            
            for (ClientStruct client : clientlist) 
            {
                if (client.getUser().getCodUser() == user.getCodUser())
                {
                    attempt = LoginAttempt.ALREADY_LOGGED;
                    break;
                }
            }
            
            if (attempt == LoginAttempt.SUCCESS)
            {
                nameClient = user.getName();
                codClient = user.getCodUser();
                try
                {
                    clientlist.add(new ClientStruct(
                            user, communicateWithClient, 
                            ClientConnection.getUserDesires(user.getCodUser()))
                    );
                } catch (Exception ex) { }
            }
        }
        
        return (attempt);
    }
    
    public LoginAttempt createNewUser()
    {
        Collections.sort(userlist, new Comparator<User>()
        {
            @Override
            public int compare(User o1, User o2)
            {
                return o1.getCodUser() < o2.getCodUser() ? -1 : 1;
            }
        });
        
        User user = new User(userlist.get(userlist.size() - 1).getCodUser() + 1, 
                command.getArray()[1], command.getArray()[2], 
                command.getArray()[3], command.getArray()[4], 
                command.getArray()[5], command.getArray()[6]
        );
        
        attempt = LoginAttempt.SUCCESS;
        
        for (User u : userlist)
        {
            if (u.equals(user))
            {
                attempt = LoginAttempt.ALREADY_EXISTS;
                break;
            }
        }
        
        if (attempt == LoginAttempt.SUCCESS)
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
            } catch (Exception ex) { }
        }
        
        return (attempt);
    }
    
    public String getNameClient()
    {
        return nameClient;
    }
    
    public int getCodClient()
    {
        return codClient;
    }
}
