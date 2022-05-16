/**
 * A B+ tree generic node
 * Abstract class with common methods and data. Each kind of node implements this class.
 * @param <TKey> the data type of the key
 * @param <TValue> the data type of the value
 */
abstract class BPTreeNode<TKey extends Comparable<TKey>, TValue> {
	
	protected Object[] keys;
	protected int keyTally;
	protected int m;
	protected BPTreeNode<TKey, TValue> parentNode;
	protected BPTreeNode<TKey, TValue> leftSibling;
	protected BPTreeNode<TKey, TValue> rightSibling;
	protected static int level = 0; // do not modify this variable's value as it is used for printing purposes. You can create another variable with a different name if you need to store the level.
	
	protected BPTreeNode() 
	{
		this.keyTally = 0;
		this.parentNode = null;
		this.leftSibling = null;
		this.rightSibling = null;
	}

	public int getKeyCount() 
	{
		return this.keyTally;
	}
	
	@SuppressWarnings("unchecked")
	public TKey getKey(int index) 
	{
		return (TKey)this.keys[index];
	}

	public void setKey(int index, TKey key) 
	{
		this.keys[index] = key;
	}

	public BPTreeNode<TKey, TValue> getParent() 
	{
		return this.parentNode;
	}

	public void setParent(BPTreeNode<TKey, TValue> parent) 
	{
		this.parentNode = parent;
	}	
	
	public abstract boolean isLeaf();
	
	/**
	 * Print all nodes in a subtree rooted with this node
	 */
	@SuppressWarnings("unchecked")
	public void print(BPTreeNode<TKey, TValue> node)
	{
		level++;
		if (node != null) {
			System.out.print("Level " + level + " ");
			node.printKeys();
			System.out.println();

			// If this node is not a leaf, then 
        		// print all the subtrees rooted with this node.
        		if (!node.isLeaf())
			{	BPTreeInnerNode inner = (BPTreeInnerNode<TKey, TValue>)node;
				for (int j = 0; j < (node.m); j++)
    				{
        				this.print((BPTreeNode<TKey, TValue>)inner.references[j]);
    				}
			}
		}
		level--;
	}

	/**
	 * Print all the keys in this node
	 */
	protected void printKeys()
	{
		System.out.print("[");
    		for (int i = 0; i < this.getKeyCount(); i++)
    		{
        		System.out.print(" " + this.keys[i]);
    		}
 		System.out.print("]");
	}


	////// You may not change any code above this line. You may add extra variables if need be //////

	////// Implement the functions below this line //////
	
	
	
	/**
	 * Search a key on the B+ tree and return its associated value using the index set. If the given key is not found, null should be returned.
	 */
	public TValue search(TKey key) {
		int index = 0;
        while (index < getKeyCount() && key.compareTo(getKey(index)) >= 0){
			index++;
		}

		//If this is not a leaf then traverse down
        if(!this.isLeaf()){
			return ((BPTreeInnerNode<TKey, TValue>) this).getChild(index).search(key);
		}

        //If this is a leaf and the value found is correct then return it
        if(getKey(index-1).equals(key)){
			return ((BPTreeLeafNode<TKey, TValue>) this).getValue(index-1);
		}
		
        //Otherwise return null
        return null;

		//====================Traversal Approach =====================
		// //variables needed
		// int keyCount = 0;
		// Boolean isLeaf = false;

		// //only runs if root is a leaf node
		// if(this.isLeaf()){
		// 	keyCount = this.getKeyCount();
		// 	for(int index = 0; index < keyCount; index++){
		// 		if(this.getKey(index).equals(key)){
		// 			TValue returnValue = ((BPTreeLeafNode<TKey, TValue>) this).getValue(index);
		// 			return returnValue;
		// 		} 
		// 	}

		// 	//if key was not found in the root
		// 	return null;
		// }

		// //variable to loop down in tree
		// BPTreeInnerNode<TKey, TValue> nodePtr = (BPTreeInnerNode<TKey, TValue>) this;

		// //loop until leftmost leaf is found
		// while(!isLeaf){
		// 	//if the child is an inner node then move down to child node and run again
		// 	if(!nodePtr.getChild(0).isLeaf()){
		// 		nodePtr = (BPTreeInnerNode<TKey, TValue>) nodePtr.getChild(0);
		// 	}
		// 	else{
		// 		isLeaf = true;
		// 	}
		// }

		// //When traverse a the leaf node level to look for value
		// //Returns value or null
		// return leafTraverse((BPTreeLeafNode<TKey, TValue>)nodePtr.getChild(0), key);
	}

	/**
	 * Insert a new key and its associated value into the B+ tree. The root node of the
	 * changed tree should be returned.
	 */
	public BPTreeNode<TKey, TValue> insert(TKey key, TValue value){
		return this; //Will never be a BPTreeNode, rather an inherited class
	}

	/**
	 * Delete a key and its associated value from the B+ tree. The root node of the
	 * changed tree should be returned.
	 */
	public BPTreeNode<TKey, TValue> delete(TKey key){
		return this; //Will never be a BPTreeNode, rather an inherited class
	}

	/**
	 * Return all associated key values on the B+ tree in ascending key order using the sequence set. An array
	 * of the key values should be returned.
	 */
	@SuppressWarnings("unchecked")
	public TValue[] values(){
		return null;
	}


	//=============================Helper Functions================================//

	/**
	 * Traverses tree one on leaf level from specified node.
	 * If key is found return value otherwise null.
	 * Tree always gets traversed from left to right.
	 */
	public TValue leafTraverse(BPTreeLeafNode<TKey, TValue> startingNode, TKey key){
		int keyCount = 0;

		BPTreeLeafNode<TKey, TValue> nodePtr = startingNode;
		while(nodePtr != null){
			keyCount = nodePtr.getKeyCount();
			for(int index = 0; index < keyCount; index++){
				if(nodePtr.getKey(index).equals(key)){
					TValue returnValue = nodePtr.getValue(index);
					return returnValue;
				}
			}
			nodePtr = (BPTreeLeafNode<TKey, TValue>)nodePtr.rightSibling;
		}
		return null;
	}
}