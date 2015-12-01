package huffman;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.BufferUnderflowException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

public class Decompress extends AbstractHuffman
{
	
	

  

	String files = "";
    Node root = null;
    int fileArrayContor = 0;
    int length = 0;

    byte[] fileChunkArray;
    StringBuilder chunkStringBUilder = new StringBuilder();
    int chunkStringContor = 0;
    boolean lastBitFlag = true;

   public  Decompress(String file)
    {
        files = file;
    }

    HashMap<String, Node> tree = new HashMap<String, Node>();

    

    String boss="";
    String lastbit="";
    String decompressedsize = "";
    

    public String getLastbit() {
		return lastbit;
	}

	public void setLastbit(String lastbit) {
		this.lastbit = lastbit;
	}

	public String getDecompressedsize() {
		return decompressedsize;
	}

	public void setDecompressedsize(String decompressedsize) {
		this.decompressedsize = decompressedsize;
	}

	public void decompressFile(String fil, String dest) throws IOException
    {
        File file = new File(fil);
        FileInputStream fis = null;

        fis = new FileInputStream(file);
        BufferedOutputStream destprint = new BufferedOutputStream(new FileOutputStream(new File(dest)));
        byte[] buffer2 = new byte[10000];
        ByteArrayOutputStream filearray = new ByteArrayOutputStream();
        int rad = 0;
        while((rad = fis.read(buffer2)) > 0)
        {
            length += rad;
            filearray.write(buffer2,0,rad);
        }
        filearray.flush();
root=this.getCompressionTree();
        fileChunkArray = filearray.toByteArray();

        int len = Integer.parseInt(decompressedsize);
        for(int i = 0; i < len; i++)
        {
        	System.out.println(i+" "+len);
            getCharacter(len, root, destprint, lastbit);

        }
        destprint.flush();
        fis.close();

    }


int hk=0;
int ct=0;
    public char getNext(String lasBit, int len)
    {      
    	ct++;

        if(fileArrayContor >=fileChunkArray.length && lastBitFlag&&chunkStringBUilder.length() == chunkStringContor)
        {
        	chunkStringBUilder.setLength(0);
            chunkStringBUilder.append(lasBit);
            chunkStringContor = 0;
            lastBitFlag = false;
            
          
        }
         if(chunkStringBUilder.length() == chunkStringContor&&lastBitFlag)
        {
            chunkStringBUilder.setLength(0);

            for(int i = 0; i < 10000 && fileArrayContor < fileChunkArray.length; i++)
            {

                chunkStringBUilder.append(Integer.toBinaryString(fileChunkArray[fileArrayContor] & 255 | 256).substring(1));
                fileArrayContor++;
            }
            chunkStringContor = 0;


        }
        char nextChar = chunkStringBUilder.charAt(chunkStringContor);
        chunkStringContor++;
        return nextChar;
    }
    public void getCharacter(int len, Node nd, BufferedOutputStream stream, String lastBit)
            throws NumberFormatException,
                IOException
    {

        if(nd.getLeftChild() == null)
        {
            stream.write(Integer.parseInt(nd.getVal()));

        }
        else
        {
            char b = getNext(lastBit, len);
            if(b == '0')
            {
                getCharacter(len, nd.getLeftChild(), stream, lastBit);
            }
            else
            {
                getCharacter(len, nd.getRightChild(), stream, lastBit);
            }

        }

    }

}
