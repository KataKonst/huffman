package huffman;



public class CompresserFactory {
	static final String HuffmanCOmpression="huffman";
	
	public Compression  getCompression(Compressions compression){
		
		switch(compression)
		{
		   case Huffman: return new HuffmanCompression();
		}
		return null;
		
	}

}
