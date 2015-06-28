
package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class InputReader 
{
    public BufferedReader reader;
    public StringTokenizer tokenizer;

    public InputReader(InputStream stream) 
    {
        reader = new BufferedReader(new InputStreamReader(stream));
        tokenizer = null;
    }

    public synchronized String nextLine() 
    {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) 
        {
            try 
            {
                tokenizer = new StringTokenizer(reader.readLine());
            } 
            catch (IOException e) 
            {
                throw new RuntimeException(e);
            }
        }
        return tokenizer.nextToken();
    }
    
//    public synchronized boolean hasNextLine() 
//    {
//        try {
//            return (reader.ready());
//        } catch (IOException ex) {
//            return (false);
//        }
//    }

}
