
package server;

import supermarket.entities.User;

// classe da qual será formada a lista de clientes
class ClientStruct
{
    protected User user;
    protected CommunicateWithClient communicate;

    public ClientStruct(User user, CommunicateWithClient communicate) 
    {
        this.user = user;
        this.communicate = communicate;
    }
}
