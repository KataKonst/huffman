package huffman;

public class Node implements Comparable<Node> {
	
	private  int freq=0;
	private String val;
	private int ord;
	
	private Node leftChild;
	public int getFreq() {
		return freq;
	}
	public void setFreq(int freq) {
		this.freq = freq;
	}
	public String getVal() {
		return val;
	}
	public void setVal(String val) {
		this.val = val;
	}
	public Node getLeftChild() {
		return leftChild;
	}
	public Node getRightChild() {
		return rightChild;
	}
	private Node rightChild;
	public Node(Node pRightChild,Node pLeftChild,String  pValue,int pFreq)
	{
		setFrequency(pFreq);
		setLeftChild(pLeftChild);
		setRightChild(pRightChild);
		setValue(pValue);
		
		
	}
	public void setRightChild(Node pRightChild)
	{
		rightChild=pRightChild;
	}
	public void setLeftChild(Node pLeftChild)
	{
		leftChild=pLeftChild;
	}
	
	public void setValue(String pValue)
	{
		val=pValue;
	}
	
	public void setFrequency(int pFreq)
	{
		freq=pFreq;
	}
	@Override
	public int compareTo(Node arg0) {
		// TODO Auto-generated method stub
		return this.getFreq()-arg0.getFreq();
	}
	
	public String toString()
	{
		return (freq+" "+val);
	}
	
	public void setOrder(int i)
	{
		this.ord=i;
	}
	public int getOrder()
	{
	return this.ord;
	}
	

}
