
package server;

import java.util.List;
import supermarket.entities.User;

// classe da qual será formada a lista de clientes
class ClientStruct
{
    protected User user;
    protected CommunicateWithClient communicate;
    protected List<Integer> desires;

    public ClientStruct(User user, CommunicateWithClient communicate, List<Integer> desires) 
    {
        this.user = user;
        this.communicate = communicate;
        this.desires = desires;
    }

    public void setDesires(int codeProduct)
	{
        this.desires.add(codeProduct);
    }
    
    public int getCodeProduct(int i)
	{
        return desires.get(i);
    }
}
