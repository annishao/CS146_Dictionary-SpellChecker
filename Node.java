package cs146F20.shao.project4;

public class Node<Key extends Comparable<Key>> { //changed to static 
	
	  public Key key;  		  
	  public Node<Key> parent;
	  public Node<Key> leftChild;
	  public Node<Key> rightChild;
	  boolean isRed;
	  int color;
	  
	  public Node(Key data){
		  this.key = data;
		  parent = null;
		  leftChild = null;
		  rightChild = null;
		  isRed = true;
		  color = 0;
	  }		
	  
	  public int compareTo(Node<Key> n){ 	//this < that  <0
	 		return key.compareTo(n.key);  	//this > that  >0
	  }
	  
	  public boolean isLeaf(){
		  if (this.equals(parent) && this.leftChild == null && this.rightChild == null) return true;
		  if (this.equals(parent)) return false;
		  if (this.leftChild == null && this.rightChild == null){
			  return true;
		  }
		  return false;
	  }
}