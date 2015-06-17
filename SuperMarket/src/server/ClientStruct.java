
package server;

import supermarket.User;

// classe da qual será formada a lista de clientes
class ClientStruct
{
    protected User user;
    protected ClientListener communicate;

    public ClientStruct(User user, ClientListener communicate) 
    {
        this.user = user;
        this.communicate = communicate;
    }
}
