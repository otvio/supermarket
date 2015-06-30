
package client;

import login.LoginAttempt;
import command.*;
import static command.Command.*;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class Client
{
    private Socket socket;
    private Scanner scanner;
    private CommunicateWithServer communicate;
    
    public void connect() throws Exception
    {
        // tentando se conectar ao servidor
        socket = new Socket("localhost", 12345);
        System.out.println("::: Client Connected! :)");
        System.out.println("::: Awaiting the server... ");

        // criando a thread de comunicação com o servidor
        communicate = new CommunicateWithServer(this,
                        new PrintStream(socket.getOutputStream()), 
                        new Scanner(socket.getInputStream()));

        // iniciando a thread de comunicação com o servidor
        new Thread(communicate).start();
    }
    
    public void loginAttempt()
    {
        LoginAttempt login;
        String name, address;                       // Strings para os dados pessoais do usuário
        String email, telephone;                    // Strings para os dados pessoais do usuário
        String ID, password, passwordConfirm;       // Strings para a confirmação de acesso do usuário
        String user_choice;                         // String para a opção de acesso no sistema
        
        do
        {
            System.out.println("\n\t:::::::::::::::::::::::::::::::::::::::::::");
            System.out.println(  "\t:::::    Welcome to the LORMarket!    :::::");
            System.out.println(  "\t:::::::::::::::::::::::::::::::::::::::::::\n\n");
        
            System.out.println(":::::::::::::::::::::::::::::");
            System.out.print(  "::  You are a              ::\n"
                             + "::     (1). New user       ::\n"
                             + "::     (2). Existent user  ::\n"
                             + ":::::::::::::::::::::::::::::\n\n"
                             + "::  Type your choice: ");
            user_choice = scanner.nextLine();

        } while ((!user_choice.equals("1")) && (!user_choice.equals("2"))); // Loop para escolher uma das duas opções

        if (user_choice.equals("1"))  // Caso a opção escolhida seja 1, então será adicionado um novo usuário ao sistema
        {
            System.out.println("Please, answer according to what will be asked.");

            System.out.println("\n:::::: Personal information");

            System.out.print("::  Name: ");
            name = scanner.nextLine();       // Armazena o nome fornecido pelo usuário 

            System.out.print("::  Address: ");
            address = scanner.nextLine();    // Solicita o endereço

            System.out.print("::  E-mail: ");
            email = scanner.nextLine();      // Solicita o e-mail

            System.out.print("::  Telephone: ");
            telephone = scanner.nextLine();  // Solicita o telefone

            System.out.println("\n:::::: Login information");

            System.out.print("::  ID/Nickname: ");
            ID = scanner.nextLine();         // Solicita o ID

            do
            {
                System.out.print("::  Password: ");
                password = scanner.nextLine();          // Solicita a senha

                System.out.print("::  Confirm the password: ");
                passwordConfirm = scanner.nextLine();   // Solicita a confirmação da senha

                if (!password.equals(passwordConfirm))
                    System.out.println("::: Incorrect password! Try again. :::\n");

            } while(!password.equals(passwordConfirm));     // Loop para o usuário digitar e confirmar as senhas corretas

            communicate.sendToServer(
                new Command(new String[]{
                        NEWUSER, name, address, email, 
                        telephone, ID, password}
                ).get()
            );
        }
        else
        {
            System.out.print("::  ID/Nickname: ");
            ID = scanner.nextLine();         // Solicita o ID

            System.out.print("::  Password: ");
            password = scanner.nextLine();   // Solicita a senha

            communicate.sendToServer(
                new Command(new String[]{
                        LOGIN, ID, password}
                ).get()
            );
        }
    }
    
    public static void main (String[] args)
    {
        Client c;
        
        try 
        {
            c = new Client();
            c.scanner = new Scanner(System.in);
            c.connect();
            c.loginAttempt();
        } 
        catch (Exception ex) 
        {
            System.out.println("::: I'm so sorry! Client Error! :( :::");
        }
        finally
        {
            //System.out.println("\n\n:::Thank you for using this program. :::");
        }
    }
}
