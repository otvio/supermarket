
package server;

import supermarket.entities.Product;
import supermarket.entities.User;

// classe da qual será formada a lista de clientes
class ClientStruct
{
    protected User user;
    protected Product product;
    protected ClientListener communicate;

    public ClientStruct(User user, ClientListener communicate) 
    {
        this.user = user;
        this.communicate = communicate;
    }
}
