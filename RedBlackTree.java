package cs146F20.shao.project4;

public class RedBlackTree<Key extends Comparable<Key>> 
{	
	private Node<Key> root;

	// Checks if Node n is a leaf.
	public boolean isLeaf(Node<Key> n){
		// If there is only 1 node in the red black tree and it is the root, return true.
		if (n.equals(root) && n.leftChild == null && n.rightChild == null) return true;
		// If n is the root and there are other nodes in the red black tree, return false.
		if (n.equals(root)) return false;
		// If n is not the root and it does not have left or right children, return true.
		if (n.leftChild == null && n.rightChild == null){
			return true;
		}
		// In all other situations, return false.
		return false;
	}

	public void visit(Node<Key> n){
		System.out.println(n.key);
	}

	// Prints tree in preorder format starting with the root.
	public void printTree(){  //preorder: visit, go left, go right
		Node<Key> currentNode = root;	
		printTree(currentNode);
	}

	// Prints tree in preorder format.
	public void printTree(Node<Key> node){
		System.out.print(node.key);
		if (node.isLeaf()){
			return;
		}
		if(node.leftChild != null) {
			printTree(node.leftChild);
		}
		if(node.rightChild != null) {
			printTree(node.rightChild);
		}
	}

	// place a new node in the RB tree with data the parameter and color it red. 
	public void addNode(Key data){  	//this < that  <0.  this > that  >0
		// Initialize new Node containing data (default is red).
		Node<Key> addedNode = new Node<Key>(data);
		
		// If root is null, set root to be data.
		if(root == null) {
			root = addedNode;
		} else {
			// Initialize currentNode and prevNode variables.
			Node<Key> currentNode = root;
			Node<Key> prevNode = null;
			
			// Continue to loop until currentNode is a leaf.
			while(currentNode != null) {
				// Sets prevNode to currentNode.
				prevNode = currentNode;
				
				// If value of addedNode is less than currentNode, 
				// move to left side of tree.
				if(addedNode.compareTo(currentNode) < 0) {
					// Set currentNode to left child of currentNode.
					currentNode = currentNode.leftChild;
					// If currentNode is a leaf, set left child of prevNode to addedNode.
					if(currentNode == null) {
						prevNode.leftChild = addedNode;
						prevNode.leftChild.parent = prevNode;
					}
				}
				// If value of addedNode is greater than or equal to currentNode, 
				// move to right side of tree.
				else {
					// Set currentNode to right child of currentNode.
					currentNode = currentNode.rightChild;
					// If currentNode is a leaf, set right child of prevNode to addedNode.
					if(currentNode == null) {
						prevNode.rightChild = addedNode;
						prevNode.rightChild.parent = prevNode;
					}
				}
			}
		}
		// Makes sure red black tree properties are still maintained after adding addedNode.
		fixTree(addedNode);
	}	

	public void insert(Key data){
		addNode(data);	
	}

	// Searches for a key and returns node with same key; else returns null.
	public Node<Key> lookup(Key k){ 
		// If tree is empty, return null.
		if(root == null) {
			return null;
		} else {
			// Initializes currentNode and foundNode.
			Node<Key> currentNode = root;

			// Loops until node with same key as k is found.
			while(k.compareTo(currentNode.key) != 0) {
				// If k is less than the key of currentNode, if bottom of tree is reached return null;
				// else let currentNode equal to leftChild of currentNode.
				if(k.compareTo(currentNode.key) < 0) {
					if(currentNode.leftChild == null) {
						return null;
					}
					currentNode = currentNode.leftChild;
				} 
				// If k is greater than the key of currentNode, if bottom of tree is reached return null;
				// else let currentNode equal to rightChild of currentNode.
				else if(k.compareTo(currentNode.key) > 0) {
					if(currentNode.rightChild == null) {
						return null;
					}
					currentNode = currentNode.rightChild;
				}
			}
			// Return currentNode.
			return currentNode;
		}
	}

// Returns the sibling of Node<Key> n; if not found return null.
public Node<Key> getSibling(Node<Key> n){
	// Initialize parent variable to be parent of Node n.
	Node<Key> parent = n.parent;

	// If Node n does not have a parent, return null.
	if(parent == null) {
		return null;
	} else {
		// If Node n is a left child of parent, return rightChild of parent.
		if(isLeftChild(parent, n)) {
			return parent.rightChild;
		} 
		// Else, return leftChild of parent.
		else {
			return parent.leftChild;
		}
	}
}

// Returns the aunt of Node<Key> n; if not found return null.
public Node<Key> getAunt(Node<Key> n){
	// Initialize parent variable to be parent of Node n.
	Node<Key> parent = n.parent;

	// If parent of n is null, return null.
	if(parent == null) {
		return null;
	} 
	// Else, use getSibling method to get sibling of parent of Node n.
	else {
		return getSibling(parent);
	}
}

	// Returns grandparent of Node n.
	public Node<Key> getGrandparent(Node<Key> n){
		return n.parent.parent;
	}

	// Rotates red black tree left.
	public void rotateLeft(Node<Key> x){
		// y is initialized to be the right child of x.
		Node<Key> y = x.rightChild;
		
		// x's right child becomes y's left child.
		x.rightChild = y.leftChild;
		
		// If y has a left child, then the parent of y's left child becomes x.
		if(y.leftChild != null) {
			y.leftChild.parent = x;
		}
		
		// Link x's parent to y's parent.
		y.parent = x.parent;
		
		// If x was the root, change it so y is the new root.
		if(x.parent == null) {
			root = y;
		} 
		// If x is the left child of its parent, set left child of x's parent to y.
		else if(x.compareTo(x.parent.leftChild) == 0) {
			x.parent.leftChild = y;
		} 
		// Else, set right child of x's parent to y.
		else {
			x.parent.rightChild = y;
		}
		// Set left child of y to x.
		y.leftChild = x;
		// Set parent of x to y.
		x.parent = y;
	}

	// Rotates red black tree right.
	public void rotateRight(Node<Key> y){
		// x is initialized to be y's left child.
		Node<Key> x = y.leftChild;
		// Left child of y is now right child of x.
		y.leftChild = x.rightChild;
		// If x has a right child, set its' parent to y.
		if(x.rightChild != null) {
			x.rightChild.parent = y;
		}
		// Link y's parent to x's parent.
		x.parent = y.parent;
		
		// If y is the root, change it so x is the new root.
		if(y.parent == null) {
			root = x;
		} 
		// If y is the right child, set right child of y's parent to x.
		else if(y.compareTo(y.parent.rightChild) == 0) {
			y.parent.rightChild = x;
		} 
		// Else, set left child of y's parent to x.
		else {
			y.parent.leftChild = x;
		}
		// Set right child of x to y.
		x.rightChild = y;
		// Set parent of y to x.
		y.parent = x;
	}

	// Recursively traverses tree to make it a red black tree.
	public void fixTree(Node<Key> current) {
		// If current is root, make it black and quit.
		if(current == root) {
			current.isRed = false;
			current.color = 1;
			return;
		} 
		
		// If parent of current is black, quit.
		if(!current.parent.isRed) {
			return;
		} 
		
		// If current is red and parent of current is red, fixing is needed.
		if(current.isRed && current.parent.isRed) {
			// If aunt of current is empty or black:
			if(getAunt(current) == null || !getAunt(current).isRed) {
				// If current is the right child of its parent and its parent is the left child of the grandparent:
				if(!isLeftChild(current.parent, current) && isLeftChild(getGrandparent(current), current.parent)) {
					// Rotate the parent left.
					rotateLeft(current.parent);
					// Recursively fix tree starting with parent of current.
					fixTree(current.parent);
				} 
				// If current is the left child of its parent and its parent is the right child of the grandparent:
				else if(isLeftChild(current.parent, current) && !isLeftChild(getGrandparent(current), current.parent)) {
					// Rotate the parent right.
					rotateRight(current.parent);
					// Recursively fix tree starting with parent of current.
					fixTree(current.parent);
				} 
				// If current is the left child of its parent and its parent is the left child of the grandparent:
				else if(isLeftChild(current.parent, current) && isLeftChild(getGrandparent(current), current.parent)) {
					// Make parent of current black.
					current.parent.isRed = false;
					current.parent.color = 1;
					// Make grandparent of current red.
					getGrandparent(current).isRed = true;
					getGrandparent(current).color = 0;
					// Rotate the grandparent to the right and quit.
					rotateRight(getGrandparent(current));
					return;
				} 
				// If current is the right child of its parent and its parent is the right child of the grandparent:
				else if(!isLeftChild(current.parent, current) && !isLeftChild(getGrandparent(current), current.parent)) {
					// Make parent of current black.
					current.parent.isRed = false;
					current.parent.color = 1;
					// Make grandparent of current red.
					getGrandparent(current).isRed = true;
					getGrandparent(current).color = 0;
					// Rotate the grandparent to the left and quit.
					rotateLeft(getGrandparent(current));
					return;
				}
			} 
			// If aunt of current is red:
			else if(getAunt(current).isRed){
				// Make the parent of current black.
				current.parent.isRed = false;
				current.parent.color = 1;
				// Make the aunt of current black.
				getAunt(current).isRed = false;
				getAunt(current).color = 1;
				// Make the grandparent of current red.
				getGrandparent(current).isRed = true;
				getGrandparent(current).color = 0;
				// Recursively fix the tree starting with grandparent of current.
				fixTree(getGrandparent(current));
			}
		}
	}

	public boolean isEmpty(Node<Key> n){
		if (n.key == null){
			return true;
		}
		return false;
	}

	public boolean isLeftChild(Node<Key> parent, Node<Key> child)
	{
		if (child.compareTo(parent) < 0 ) {//child is less than parent
			return true;
		}
		return false;
	}

	public void preOrderVisit(Visitor<Key> v) {
		preOrderVisit(root, v);
	}
	
	private void preOrderVisit(Node<Key> n, Visitor<Key> v) {
		if (n == null) {
			return;
		}
		v.visit(n);
		preOrderVisit(n.leftChild, v);
		preOrderVisit(n.rightChild, v);
	} 
}

