package huffman;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;

public abstract class  AbstractHuffman {

	HashMap<Short, Integer> frequencyHashMap = new HashMap<Short, Integer>();
	
	public HashMap<Short, Integer> getFrequency(short[] nt) {

		for (int i = 0; i < nt.length; i++) {
			if (!frequencyHashMap.containsKey(nt[i]))
				frequencyHashMap.put(nt[i], 0);
			else {
				frequencyHashMap.put(nt[i], frequencyHashMap.get(nt[i]) + 1);

			}
		}

		return frequencyHashMap;
	}

	public Node getCompressionTree() {
		PriorityQueue<Node> queue = new PriorityQueue<Node>();

		Iterator frequencyIterator = frequencyHashMap.entrySet().iterator();
		while (frequencyIterator.hasNext()) {
			Map.Entry pair = (Map.Entry) frequencyIterator.next();
			Node nd = new Node(null, null, String.valueOf(pair.getKey()),
					(Integer) pair.getValue());
			queue.add(nd);

		}

		while (queue.size() > 1) {
			Node nd = queue.poll();
			Node nd2 = queue.poll();
			Node res = new Node(nd2, nd, "not", nd.getFreq() + nd2.getFreq());
			queue.add(res);
		}

		return queue.peek();

	}
	
	  public HashMap<Short, Integer> getFrequencyHashMap() {
			return frequencyHashMap;
		}

		public void setFrequencyHashMap(HashMap<Short, Integer> frequencyHashMap) {
			this.frequencyHashMap = frequencyHashMap;
		}

}
