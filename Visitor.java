package cs146F20.shao.project4;

public interface Visitor<Key extends Comparable<Key>> {
	/**
	This method is called at each node.
	@param n the visited node
	*/
	void visit(Node<Key> n);  
}
