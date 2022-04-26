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
			BTreeNode<T> newNode = new BTreeNode<T>(m, false);
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

		//find a key greater than or equal to K
		while(index < keyTally && key.compareTo((T)keys[index]) > 0){
			index++;
		}

		//if the key is found then return this node
		if(keys[index-1] == key){
			return this;
		}

		//return null if key was not found and it is a leaf
		if(leaf){
			return null;
		}
		else{
			//Search child for key
			return references[index-1].search(key);
		}
	}

	// Function to traverse all nodes in a subtree rooted with this node
	public void traverse(){
		int index;

		//traverse the keys, if it is not a leaf go to child node first.
		for(index = 0; index < keyTally; index++){
			if(!leaf){
				references[index].traverse();
			}
			System.out.print(" " + keys[index]);
		}

		//traverse the missed node
		if(!leaf){
			references[index].traverse();
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

	//helper function for printing a node(only used for debugging)
	public void printNode(){
		String isLeaf = "";
		if(leaf){
			isLeaf = "is a leaf";
		}
		else{
			isLeaf = "is not a leaf";
		}

		String output = "Keys = ["; 
		for (int i = 0; i < keyTally; i++) {
			output += keys[i] + ",";
		}
		System.out.println(output + "] that " + isLeaf);

		output = "References = "; 
		int num = 0;
		for (BTreeNode<T> node : references) {
			if(node != null){
				num++;
			}
		}
		System.out.println(output + num);

		output = "Key Tally = "; 
		System.out.println(output + keyTally + "\n");
	}

	//helper function to split a full node
	public void splitNode(int index, BTreeNode<T> node){
		//create a new node node that will have the m - 1 amount of keys
		BTreeNode<T> newNode = new BTreeNode<T>(node.m, node.leaf);
		newNode.keyTally = m - 1;
	
		//copy the second half of keys to the new node
		for (int i = 0; i < m-1; i++){
			newNode.keys[i] = node.keys[i+m];
			node.keys[i+m] = null;
		}
	
		//if it was not a leaf then copy the corrisponding references
		if (!node.leaf)
		{
			for (int i = 0; i < m; i++){
				newNode.references[i] = node.references[i+m];
				node.references[i+m] = null;
			}
		}
	
		//set the new amount of keys in the passed in node
		node.keyTally = m - 1;
	
		//create new child space
		for (int i = keyTally; i >= index + 1; i--){
			references[i+1] = references[i];
		}
		
		//link the new child to this node
		references[index+1] = newNode;
	
		//a key from the inputted node will move to this one, insert it into place
		for (int i = keyTally-1; i >= index; i--){
			keys[i+1] = keys[i];
		}
	
		//copy the middle key of the inputted node to this one 
		keys[index] = node.keys[m-1];
		node.keys[m-1] = null;
	
		//increment the key tally
		keyTally++;
	}
}