package huffman;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileHandler
{

    private BufferedInputStream inputStream;
    private BufferedOutputStream outputStream;

    private File file;

    FileHandler(String name) throws FileNotFoundException
    {
        file = new File(name);

    }

    public void writeToFile(int octet) throws IOException
    {
        if(outputStream == null)
        {
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
        }
        outputStream.write(octet);
    }
    
    public void write(byte[] octet) throws IOException
    {
        if(outputStream == null)
        {
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
        }
        outputStream.write(octet);
    }

    public int read() throws IOException
    {
        if(inputStream == null)
        {
            inputStream = new BufferedInputStream(new FileInputStream(file));
        }
        return inputStream.read();
    }
    
    public int  read(byte[] buffer) throws IOException
    {
        if(inputStream == null)
        {
            inputStream = new BufferedInputStream(new FileInputStream(file));
        }
        return inputStream.read(buffer);
    }
    
    public void close() throws IOException
    {
    	if(inputStream!=null)
        inputStream.close();
        if(outputStream!=null)
        outputStream.close();
    }


}
