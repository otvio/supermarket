
package login;
// enum responsável pelas verificações de tentativa de login
public enum LoginAttempt
{
    SUCCESS(1), ALREADY_LOGGED(0), FAILED(-1), ALREADY_EXISTS(-2);
    
    public int value;
    
    LoginAttempt(int key)
    {
        value = key;
    }
}
