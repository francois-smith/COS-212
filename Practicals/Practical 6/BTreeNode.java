@SuppressWarnings("unchecked")
class BTreeNode<T extends Comparable<T>> {
	boolean leaf;
	int keyTally;
	Comparable<T> keys[];
	BTreeNode<T> references[];
	int m;
	static int level = 0;
	// Constructor for BTreeNode class
	public BTreeNode(int order, boolean leaf1)
	{
    		// Copy the given order and leaf property
		m = order;
    		leaf = leaf1;
 
    		// Allocate memory for maximum number of possible keys
    		// and child pointers
    		keys =  new Comparable[2*m-1];
    		references = new BTreeNode[2*m];
 
    		// Initialize the number of keys as 0
    		keyTally = 0;
	}

	// Function to print all nodes in a subtree rooted with this node
	public void print()
	{
		level++;
		if (this != null) {
			System.out.print("Level " + level + " ");
			this.printKeys();
			System.out.println();

			// If this node is not a leaf, then 
        		// print all the subtrees rooted with this node.
        		if (!this.leaf)
			{	
				for (int j = 0; j < 2*m; j++)
    				{
        				if (this.references[j] != null)
						this.references[j].print();
    				}
			}
		}
		level--;
	}

	// A utility function to print all the keys in this node
	private void printKeys()
	{
		System.out.print("[");
    		for (int i = 0; i < this.keyTally; i++)
    		{
        		System.out.print(" " + this.keys[i]);
    		}
 		System.out.print("]");
	}

	// A utility function to give a string representation of this node
	public String toString() {
		String out = "[";
		for (int i = 1; i <= (this.keyTally-1); i++)
			out += keys[i-1] + ",";
		out += keys[keyTally-1] + "] ";
		return out;
	}

	////// You may not change any code above this line //////

	////// Implement the functions below this line //////

	// Function to insert the given key in tree rooted with this node
	public BTreeNode<T> insert(T key){
		if (this.keyTally == 2*m-1)
		{
			BTreeNode<T> newNode = new BTreeNode(m, false);
			newNode.references[0] = this;
			newNode.splitNode(0, this);
	
			int i = 0;
			if (this.keys[0].compareTo(key) < 0){
				i++;
			}
				
			newNode.references[i].insertIntoNonFullNode(key);
			return newNode;
		}
		else {
			insertIntoNonFullNode(key);
			return this;
		}
	}

	// Function to search a key in a subtree rooted with this node
	public BTreeNode<T> search(T key){  
		int index = 0;
		while(index < this.keyTally-1 && this.keys[index].compareTo(key) < 0){
			index++;
		}
		if(keys[index] == key){
			return this;
		}
		if(leaf == true){
			return null;
		}
		return references[index].search(key);
	}

	// Function to traverse all nodes in a subtree rooted with this node
	public void traverse(){
		int index;
		for(index = 0; index < keyTally; index++){
			if(!leaf){
				references[index].traverse();
			}
			System.out.print(this.keys[index] + " ");
		}

		if(!leaf){
			this.references[index].traverse();
		}
	}

	//helper function to insert into a node that is non full
	public void insertIntoNonFullNode(T key){
		//set the index as the right most key of node
		int index = keyTally-1;

		//check if node is a leaf
		if(leaf){
			//The while loop should find the position where the key should be inserted
			//It then proceeds to move all keys up to make space for new key
			while(index >= 0 && this.keys[index].compareTo(key) > 0){
				keys[index+1] = keys[index];
				index--;
			}

			//inserts key into place and increments the amount of keys
			keys[index+1] = key;
			keyTally++;
		}
		else{ 
			//find the child node that will contain the new key
			while(index >= 0 && this.keys[index].compareTo(key) > 0){
				index--;
			}

			//if the child is full
			if(references[index+1].keyTally == 2*m-1){
				//if it is full then split it
				splitNode(index+1, references[index+1]);

				//the child node is split in two and it then decides what side of split new node will go into
				if(keys[index+1].compareTo(key) < 0){
					index++;
				}
			}

			//insert into the child node
			references[index+1].insertIntoNonFullNode(key);
		}
	}

	//helper function to split a full node
	public void splitNode(int i, BTreeNode<T> node){
		//create a new node node that will have the m - 1 amount of keys
		BTreeNode<T> newNode = new BTreeNode(node.m, node.leaf);
		newNode.keyTally = m - 1;
	
		//copy the second half of keys to the new node
		for (int j = 0; j < m-1; j++){
			newNode.keys[j] = node.keys[j+m];
		}
	
		//if it was not a leaf then copy the corrisponding references
		if (!node.leaf)
		{
			for (int j = 0; j < m; j++){
				newNode.references[j] = node.references[j+m];
			}
		}
	
		//set the new amount of keys in the passed in node
		node.keyTally = m - 1;
	
		//create new child space
		for (int j = keyTally; j >= i + 1; j--){
			references[j+1] = references[j];
		}
		
		//link the new child to this node
		references[i+1] = newNode;
	
		//a key from the inputted node will move to this one, insert it into place
		for (int j = keyTally-1; j >= i; j--){
			keys[j+1] = keys[j];
		}
	
		//copy the middle key of the inputted node to this one 
		keys[i] = node.keys[m-1];
		node.keys[m-1] = null;
	
		//increment the key tally
		keyTally++;
	}
}