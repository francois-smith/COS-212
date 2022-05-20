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
		BPTreeNode<TKey, TValue> nodePtr = this;
		int insertPosition = 0;

		//loop until leaf is found
		while(!nodePtr.isLeaf()){
			//loop through all keys of node
			for(int index = 0; index < nodePtr.getKeyCount(); index++){
				//System.out.println(nodePtr.getKey(index));
				//if the key to insert is less than current key
				if(key.compareTo(nodePtr.getKey(index)) < 0){
					//if current node's child is a leaf then exit loop
					if(((BPTreeInnerNode<TKey, TValue>)nodePtr).getChild(index).isLeaf()){
						insertPosition = index;
						nodePtr = ((BPTreeInnerNode<TKey, TValue>)nodePtr).getChild(index);
						break;
					}
					//else if the child is an inner nodes then move down to child node and run again
					else{
						nodePtr = ((BPTreeInnerNode<TKey, TValue>)nodePtr).getChild(index);
						break;
					}
				}

				//check last child reference 
				if(index+1 == nodePtr.getKeyCount()){
					index++;
					if(((BPTreeInnerNode<TKey, TValue>)nodePtr).getChild(index).isLeaf()){
						insertPosition = index;
						nodePtr = ((BPTreeInnerNode<TKey, TValue>)nodePtr).getChild(index);
						break;
					}
					//else if the child is an inner nodes then move down to child node and run again
					else{
						nodePtr = ((BPTreeInnerNode<TKey, TValue>)nodePtr).getChild(index);
						break;
					}
				}
			}
		}

		BPTreeLeafNode<TKey, TValue> leafToInsertInto = (BPTreeLeafNode<TKey, TValue>)nodePtr;
		return insertIntoNode(leafToInsertInto, key, value);
	}

	/**
	 * Override of default delete class.
	 */
	public BPTreeNode<TKey, TValue> delete(TKey key){
		//search tree for key
		for(int index = 0; index < this.getKeyCount(); index++){
			if(getKey(index).compareTo(key) > 0){
				getChild(index).delete(key);
				break;
			}
			else if(getKey(index).equals(key)){
				getChild(index+1).delete(key);
				break;
			}

			if(index+1 == this.getKeyCount()){
				getChild(index+1).delete(key);
				break;
			}
		}

		int minOrder = m/2-1;

		if(this.getKeyCount() < minOrder){
			if(this.parentNode == null){
				return this.getChild(0);
			}
			else{
				underflow(key);
			}
		}

		return this;
	}

	//=============================Helper Functions================================//
	
	protected BPTreeNode<TKey, TValue> underflow(TKey deleteKey){
		//Variables needed
		BPTreeInnerNode<TKey, TValue> parentNode = (BPTreeInnerNode<TKey, TValue>)this.parentNode;
		int siblingKeyCount = 0;
		int minOrder = m/2-1;
		int parentPostion = parentNode.getPosition(this);
		int keyCount = this.getKeyCount();

		BPTreeInnerNode<TKey, TValue> leftSibling = null;
		//See if left sibling has enough keys to share
		if(parentPostion > 0){
			leftSibling = (BPTreeInnerNode<TKey, TValue>)parentNode.getChild(parentPostion-1);
			siblingKeyCount= leftSibling.getKeyCount();
			if(siblingKeyCount > minOrder){
				//Move all keys one index up
				for(int index = keyCount; index > 0; index--){
					this.setKey(index, this.getKey(index-1));
					this.setChild(index, this.getChild(index-1));
				}
				this.keyTally++;

				this.setKey(0, leftSibling.getKey(siblingKeyCount));
				leftSibling.keyTally--;
				parentNode.setKey(parentPostion, this.getKey(0));

				// //Replace parent key if key deleted was saved in parent
				// for(int parentIndex = 0; parentIndex < parentNode.getKeyCount(); parentIndex++){
				// 	if(parentNode.getKey(parentIndex).equals(deleteKey)){
				// 		parentNode.setKey(parentIndex, this.getKey(0));
				// 		break;
				// 	}
				// }

				//Remove key reference from sibling
				
				return this;
			}
			
		}

		BPTreeInnerNode<TKey, TValue> rightSibling = null;
		if(parentPostion < parentNode.getKeyCount()){
			rightSibling = (BPTreeInnerNode<TKey, TValue>)parentNode.getChild(parentPostion+1);
			siblingKeyCount= rightSibling.getKeyCount();

			if(siblingKeyCount > minOrder){
				//Copy first key from right sibling
				this.setKey(this.getKeyCount(), rightSibling.getKey(0));
				this.setChild(this.getKeyCount(), rightSibling.getChild(0));
				this.keyTally++;

				//Move all keys and value down one index to fill gap
				for(int moveIndex = 0; moveIndex < keyCount; moveIndex++){
					rightSibling.setChild(moveIndex, rightSibling.getChild(moveIndex+1));
					rightSibling.setKey(moveIndex, rightSibling.getKey(moveIndex+1));
				}
				rightSibling.keyTally--;

				// //Replace parent key if key deleted was saved in parent
				// for(int parentIndex = 0; parentIndex < parentNode.getKeyCount(); parentIndex++){
				// 	if(parentNode.getKey(parentIndex).equals(deleteKey)){
				// 		parentNode.setKey(parentIndex+1, rightSibling.getKey(0));
				// 		break;
				// 	}
				// }

				parentNode.setKey(parentPostion, rightSibling.getKey(0));
				return this;
			}
		}

		if(leftSibling != null){
			mergeLeft(deleteKey);
		}
		else{
			mergeRight(deleteKey);
		}

		return this;
	}

	protected BPTreeNode<TKey, TValue> mergeLeft( TKey deleteKey){
		BPTreeInnerNode<TKey, TValue> parentNode = (BPTreeInnerNode<TKey, TValue>)this.parentNode;
		int parentPostion = parentNode.getPosition(this);
		BPTreeInnerNode<TKey, TValue> leftSibling = (BPTreeInnerNode<TKey, TValue>)parentNode.getChild(parentPostion-1);
		leftSibling.merge(this);
		return leftSibling;
	}

	protected BPTreeNode<TKey, TValue> mergeRight( TKey deleteKey){
		BPTreeInnerNode<TKey, TValue> parentNode = (BPTreeInnerNode<TKey, TValue>)this.parentNode;
		int parentPostion = parentNode.getPosition(this);
		BPTreeInnerNode<TKey, TValue> rightSibling = (BPTreeInnerNode<TKey, TValue>)parentNode.getChild(parentPostion+1);
		this.merge(rightSibling);		
		return this;
	}

	protected void merge(BPTreeInnerNode<TKey, TValue> consumeNode){
		for (int index = 0; index < consumeNode.getKeyCount(); index++) {
			this.setKey(this.getKeyCount(), consumeNode.getKey(0));
			this.setChild(this.getKeyCount(), consumeNode.getChild(0));
			this.keyTally++;
		}
	}

	public void deleteIndex(int keyIndex, int child){

		for(int index = keyIndex; index < this.getKeyCount(); index++) {
			keys[index] = keys[index + 1];
			references[index + child] = references[index + child + 1];
		}

		if(child == 0){
			references[this.getKeyCount() - 1] = references[this.getKeyCount()];
		}
		this.keyTally--;
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
				for(int moveIndex = keyCount+1; moveIndex > index; moveIndex--){
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


	/**
	 * Called from child class.
	 * Send key to add.
	 * Send back index key was added to update references within child.
	 */
	public int getPosition(BPTreeNode<TKey, TValue> node){
		int keyCount = this.getKeyCount();

		for(int index = 0; index < keyCount+1; index++){
			if(this.getChild(index) == node){
				return index;
			}
		}

		return 0;
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
				if(nodeRef.getKeyCount() == m){
					nodeRef = innerNodeSplit((BPTreeInnerNode<TKey, TValue>)nodeRef);
				}
				else{
					nodeRef = nodeRef.getParent();
				}
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
		boolean parentInsert = false;

		//If old inner node was root
		if(innerNode.parentNode == null){
			newParent.setKey(0, innerNode.getKey(midKey));
			newParent.keyTally = 1;
		}
		//If old inner node had a parent node
		else{
			//get parent of node being split
			BPTreeInnerNode<TKey, TValue> parentNode = (BPTreeInnerNode<TKey, TValue>)innerNode.parentNode;

			//send middle key to parent node, parent will return the index that the key was inserted into
			parentInsertPosition = parentNode.addChildKey(innerNode.getKey(midKey));
			parentInsert = true;

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
		rightInner.setChild(rightInner.getKeyCount(), innerNode.getChild(innerNode.getKeyCount()));

		if(parentInsert){
			newParent.setChild(parentInsertPosition, leftInner);
			newParent.setChild(parentInsertPosition+1, rightInner);
		}
		else{
			newParent.setChild(0, leftInner);
			newParent.setChild(1, rightInner);
		}

		if(newParent.getKeyCount() == m){
			newParent = innerNodeSplit(newParent);
		}
		return newParent;
	}
}