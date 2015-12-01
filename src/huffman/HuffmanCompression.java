package huffman;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanCompression extends AbstractHuffman implements Compression {
	private Node root = null;

	public String getOutbit() {
		return outbit;
	}

	public void setOutbit(String outbit) {
		this.outbit = outbit;
	}

	public HashMap<String, String> getTable() {

		Node nd = getCompressionTree();
		root = nd;
		HashMap<String, String> hm = new HashMap<String, String>();
		dfs(nd, hm, new StringBuilder(""));

		return hm;

	}

	public void dfs(Node nd, HashMap<String, String> hm, StringBuilder Path) {

		if (nd.getLeftChild() == null) {
			hm.put(nd.getVal(), Path.toString());
		} else {

			dfs(nd.getLeftChild(), hm, Path.append("0"));
			Path.deleteCharAt(Path.length() - 1);
			dfs(nd.getRightChild(), hm, Path.append("1"));
			Path.deleteCharAt(Path.length() - 1);

		}

	}

	Integer currentOrder = new Integer(0);

	public synchronized void count() {
		currentOrder = currentOrder + 1;
	}

	public void decompress(String path) {
		// getCharacter(root, path);
	}

	String outbit = "";
	int ln = 0;

	public ByteArrayOutputStream convert(HashMap<String, String> hm, int size,
			String file) throws IOException {
		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		StringBuilder bit = new StringBuilder("");
		fileHandler.close();
		fileHandler = new FileHandler(file);
		for (int j = 0; j < size; j += 10000) {
			StringBuilder str = new StringBuilder("");

			byte[] buffer = new byte[10000];
			int chunkLength = fileHandler.read(buffer);
			for (int i = 0; i < chunkLength; i++) {
				int a = (short) Byte.valueOf(buffer[i]).intValue();

				str.append((String) hm.get(String.valueOf(a)).toString());

			}

			for (int i = 0; i < str.length(); i++) {

				if (bit.length() % 8 == 0 && bit.length() != 0) {

					bs.write(Integer.parseInt(bit.toString(), 2));
					bit = new StringBuilder("");
					ln += 8;

				}

				bit.append(String.valueOf(str.charAt(i)));
				System.out.print(str.charAt(i));

			}
		}

		System.out.println("\n\n");
		// ln+=bit.length();

		outbit = bit.toString();

		return bs;
	}

	public String Decompress(String compress) {
		return "asaSA";
	}

	public String getTree() {
		StringBuilder StringTreeRepr = new StringBuilder(" ");

		Iterator frequencyIterator = frequencyHashMap.entrySet().iterator();
		while (frequencyIterator.hasNext()) {
			Map.Entry pair = (Map.Entry) frequencyIterator.next();
			StringTreeRepr.append(pair.getKey() + " " + pair.getValue() + "\n");

		}

		return StringTreeRepr.toString();

	}

	public void writetoFile(String file, ByteArrayOutputStream bp, String length)
			throws IOException {

		ByteArrayOutputStream bs = new ByteArrayOutputStream();
		bs.write(bp.toByteArray());
		FileOutputStream pr = new FileOutputStream(new File(file));
		pr.write(bs.toByteArray());
		pr.close();

	}

	FileHandler fileHandler = null;

	public void compress(String file, String des) throws IOException {

		fileHandler = new FileHandler(file);

		int lenght = 0;
		byte buffer[] = new byte[1024];

		while ((lenght = fileHandler.read(buffer)) > 0) {
			addChunk(buffer, lenght, null);

		}

		HashMap<String, String> c = getTable();

		ByteArrayOutputStream bp = convert(c, size, file);

		writetoFile(des, bp, String.valueOf(size));

	}

	int size = 0;

	public void addChunk(byte[] buffer, int length, FileHandler fl)
			throws IOException {
		short integerByteRepr[] = new short[buffer.length];
		for (int i = 0; i < buffer.length; i++) {
			integerByteRepr[i] = (short) Byte.valueOf(buffer[i]).intValue();
		}
		size += length;
		if (fl != null) {
			fl.write(buffer);
		}
		getFrequency(integerByteRepr);
	}

	public void compressByte(String localFile, String destinationARchive,
			FileHandler fl) throws IOException {
		HashMap<String, String> c = getTable();
		fileHandler = fl;
		ByteArrayOutputStream bp = convert(c, size, localFile);
		writetoFile(destinationARchive, bp, String.valueOf(size));
	}

}
