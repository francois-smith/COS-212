/**
 * A B+ tree internal node
 * @param <TKey> the data type of the key
 * @param <TValue> the data type of the value
 */
class BPTreeInnerNode<TKey extends Comparable<TKey>, TValue> extends BPTreeNode<TKey, TValue> {
	
	protected Object[] references; 
	
	public BPTreeInnerNode(int order) {
		this.m = order;
		// The strategy used here first inserts and then checks for overflow. 
		// Thus an extra space is required i.e. m instead of m-1/m+1 instead of m.
		// You can change this if needed. 
		this.keys = new Object[m];
		this.references = new Object[m + 1];
	}
	
	@SuppressWarnings("unchecked")
	public BPTreeNode<TKey, TValue> getChild(int index) {
		return (BPTreeNode<TKey, TValue>)this.references[index];
	}

	public void setChild(int index, BPTreeNode<TKey, TValue> child) {
		this.references[index] = child;
		if (child != null)
			child.setParent(this);
	}
	
	@Override
	public boolean isLeaf() {
		return false;
	}

	////// You should not change any code above this line //////

	////// Implement functions below this line //////


	/**
	 * Override of default insert class.
	 */
	public BPTreeNode<TKey, TValue> insert(TKey key, TValue value){
		//node that will search indexes for leaf to insert into
		BPTreeInnerNode<TKey, TValue> nodePtr = (BPTreeInnerNode<TKey, TValue>)this;

		//Variables
		Boolean isLeaf = false;
		int insertPosition = 0;

		//loop until leaf is found
		while(!isLeaf){
			//loop through all keys of node
			for(int index = 0; index < nodePtr.getKeyCount(); index++){
				//if the key to insert is less than current key
				if(key.compareTo(nodePtr.getKey(index)) < 0){
					//if current node's child is a leaf then exit loop
					if(nodePtr.getChild(index).isLeaf()){
						insertPosition = index;
						isLeaf = true;
						break;
					}
					//else if the child is an inner nodes then move down to child node and run again
					else{
						nodePtr = (BPTreeInnerNode<TKey, TValue>) nodePtr.getChild(index);
						break;
					}
				}

				//check last child reference 
				if(index+1 == nodePtr.getKeyCount()){
					index++;
					if(nodePtr.getChild(index).isLeaf()){
						insertPosition = index;
						isLeaf = true;
						break;
					}
					//else if the child is an inner nodes then move down to child node and run again
					else{
						nodePtr = (BPTreeInnerNode<TKey, TValue>) nodePtr.getChild(index);
						break;
					}
				}
			}
		}

		BPTreeLeafNode<TKey, TValue> leafToInsertInto = (BPTreeLeafNode<TKey, TValue>)nodePtr.getChild(insertPosition);
		return insertIntoNode(leafToInsertInto, key, value);
	}

	/**
	 * Called from insert when position was found where node should be inserted.
	 * Specify leaf to be inserted into with key and value.
	 */
	private BPTreeNode<TKey, TValue> insertIntoNode(BPTreeLeafNode<TKey, TValue> node, TKey key, TValue value){
		//insert key into specified leaf node
		BPTreeNode<TKey, TValue> nodeRef = node.insert(key, value);

		//if the leaf is returned then no split happened, can just return this
		if(nodeRef == node){
			return this;
		}
		else{
			//if node returned is a innerNode and is also full then call inner split
			if(nodeRef.getKeyCount() == m){
				nodeRef = innerNodeSplit((BPTreeInnerNode<TKey, TValue>)nodeRef);
			}
			//loop until root and split nodes on the way up
			while(nodeRef.getParent() != null){
				nodeRef = nodeRef.getParent();
			}

			//return after all splits have happened
			return nodeRef;
		}
	}

	/**
	 * Called from insert when position was found where node should be inserted.
	 * Specify leaf to be inserted into with key and value.
	 */
	private BPTreeInnerNode<TKey, TValue> innerNodeSplit(BPTreeInnerNode<TKey, TValue> innerNode){
		//create new nodes that will be used to split old inner node
		BPTreeInnerNode<TKey, TValue> leftInner = new BPTreeInnerNode<TKey, TValue>(innerNode.m);
		BPTreeInnerNode<TKey, TValue> rightInner = new BPTreeInnerNode<TKey, TValue>(innerNode.m);
		BPTreeInnerNode<TKey, TValue> newParent = new BPTreeInnerNode<TKey, TValue>(innerNode.m);

		//some variables that are used
		int midKey = innerNode.getKeyCount()/2;
		int parentInsertPosition = 0;

		//If old inner node was root
		if(innerNode.parentNode == null){
			newParent.setKey(0, innerNode.getKey(midKey));
			newParent.setChild(0, innerNode.getChild(midKey));
			newParent.keyTally = 1;
		}
		//If old inner node had a parent node
		else{
			//get parent of node being split
			BPTreeInnerNode<TKey, TValue> parentNode = (BPTreeInnerNode<TKey, TValue>)innerNode.parentNode;
			//send middle key to parent node, parent will return the index that the key was inserted into
			parentInsertPosition = parentNode.addChildKey(innerNode.getKey(midKey));
			//update newParent node to the parent of node being split
			newParent = parentNode;
		}

		//copy first half of keys from old inner node to new left inner node
		for(int index = 0; index < midKey; index++){
			leftInner.setKey(index, innerNode.getKey(index));
			leftInner.setChild(index, innerNode.getChild(index));
			leftInner.keyTally++;
		}
		//updates last reference of old inner node references
		leftInner.setChild(leftInner.getKeyCount(), innerNode.getChild(midKey));

		//copy second half of keys from old inner node to new right inner node
		for(int index = midKey+1; index < innerNode.getKeyCount(); index++){
			rightInner.setChild(rightInner.getKeyCount(), innerNode.getChild(index));
			rightInner.setKey(rightInner.getKeyCount(), innerNode.getKey(index));
			rightInner.keyTally++;
		}
		//updates last reference of old inner node references
		leftInner.setChild(rightInner.getKeyCount(), innerNode.getChild(midKey));

		//specific cases where siblings need to be relinked
		if(parentInsertPosition + 2 < innerNode.getKeyCount()){
			newParent.getChild(parentInsertPosition + 2).leftSibling = rightInner;
			rightInner.rightSibling = newParent.getChild(parentInsertPosition + 2);
		}
		if(parentInsertPosition - 1 >= 0){
			newParent.getChild(parentInsertPosition - 1).rightSibling = leftInner;
			leftInner.leftSibling = newParent.getChild(parentInsertPosition - 1);
		}

		if(newParent.getKeyCount() == m){
			newParent = innerNodeSplit(newParent);
		}
		return newParent;
	}

	/**
	 * Called from child class.
	 * Send key to add.
	 * Send back index key was added to update references within child.
	 */
	public int addChildKey(TKey key){
		//get current key count of node
		int keyCount = this.getKeyCount();

		for(int index = 0; index < keyCount; index++){
			//check if current index is greater than key to be inserted
			if(this.getKey(index).compareTo(key) > 0){
				//move up all values to make space for new key
				for(int moveIndex = keyCount; moveIndex > index; moveIndex--){
					this.setKey(moveIndex, this.getKey(moveIndex-1));
				}

				//move up all references to make space for new reference
				for(int moveIndex = keyCount; moveIndex > index; moveIndex--){
					this.setChild(moveIndex, this.getChild(moveIndex-1));
				}

				this.setKey(index, key);
				this.keyTally++;
				return index;
			}

			//if key to be inserted is larger than all existing keys
			if(index+1 == keyCount){
				//set index to be inserted into, to be after all existing values
				index++;
				this.setKey(index, key);
				this.keyTally++;
				return index;
			}
		}

		return 0;
	}
}