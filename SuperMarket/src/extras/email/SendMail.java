
package extras.email;

import java.util.Properties;  
import javax.mail.Message;  
import javax.mail.Session;  
import javax.mail.Transport;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeMessage;   
import javax.mail.Authenticator;  
import javax.mail.PasswordAuthentication;  

public class SendMail 
{
    private String mailSMTPServer;  
    private String mailSMTPServerPort;  
    public static final String USERNAME_GMAIL = "lormarket@gmail.com";
    public static final String PASSWORD_GMAIL = "lordedalus";
    
    /* 
     * quando instanciar um Objeto ja sera atribuido o servidor SMTP do GMAIL  
     * e a porta usada por ele 
     */  
    public SendMail() { //Para o GMAIL
        mailSMTPServer = "smtp.gmail.com";  
        mailSMTPServerPort = "465";  
    }
      
    public void sendMail(String to, String subject, String message) {  
          
        Properties props = new Properties();  
  
                // quem estiver utilizando um SERVIDOR PROXY descomente essa parte e atribua as propriedades do SERVIDOR PROXY utilizado  
               /* 
                props.setProperty("proxySet","true"); 
                props.setProperty("socksProxyHost","localhost"); // IP do Servidor Proxy 
                props.setProperty("socksProxyPort","12345");  // Porta do servidor Proxy 
                  */
  
        props.put("mail.transport.protocol", "smtp"); //define protocolo de envio como SMTP  
        props.put("mail.smtp.starttls.enable","true");   
        props.put("mail.smtp.host", mailSMTPServer); //server SMTP do GMAIL  
        props.put("mail.smtp.auth", "true"); //ativa autenticacao  
        props.put("mail.smtp.user", USERNAME_GMAIL); //usuario ou seja, a conta que esta enviando o email (tem que ser do GMAIL)  
        props.put("mail.debug", "false");  
        props.put("mail.smtp.port", mailSMTPServerPort); //porta  
        props.put("mail.smtp.socketFactory.port", mailSMTPServerPort); //mesma porta para o socket  
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  
        props.put("mail.smtp.socketFactory.fallback", "false");  
          
        //Cria um autenticador que sera usado a seguir  
        SimpleAuth auth = null;  
        auth = new SimpleAuth (USERNAME_GMAIL, PASSWORD_GMAIL);  
          
        //Session - objeto que ira realizar a conexão com o servidor  
        /*Como há necessidade de autenticação é criada uma autenticacao que 
         * é responsavel por solicitar e retornar o usuário e senha para  
         * autenticação */  
        Session session = Session.getDefaultInstance(props, auth);  
        session.setDebug(false); //Habilita o LOG das ações executadas durante o envio do email  
  
        //Objeto que contém a mensagem  
        Message msg = new MimeMessage(session);  
  
        try {  
            //Setando o destinatário  
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));  
            //Setando a origem do email  
            msg.setFrom(new InternetAddress(USERNAME_GMAIL));  
            //Setando o assunto  
            msg.setSubject(subject);  
            //Setando o conteúdo/corpo do email  
            msg.setContent(message,"text/plain");  
  
        } catch (Exception e) {  
            System.out.println(">> Erro: Completar Mensagem");  
            e.printStackTrace();  
        }  
          
        //Objeto encarregado de enviar os dados para o email  
        Transport tr;  
        try {
            tr = session.getTransport("smtp"); //define smtp para transporte  
            /* 
             *  1 - define o servidor smtp 
             *  2 - seu nome de usuario do gmail 
             *  3 - sua senha do gmail 
             */  
            tr.connect(mailSMTPServer, USERNAME_GMAIL, PASSWORD_GMAIL);  
            msg.saveChanges(); // don't forget this  
            //envio da mensagem  
            tr.sendMessage(msg, msg.getAllRecipients());  
            tr.close();  
        } catch (Exception e) { }
    }  
}
  
//clase que retorna uma autenticacao para ser enviada e verificada pelo servidor smtp  
class SimpleAuth extends Authenticator 
{  
    public String username = null;  
    public String password = null;  
  
  
    public SimpleAuth(String user, String pwd) {
        username = user;  
        password = pwd;  
    }  
  
    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication (username,password);  
    }  
}