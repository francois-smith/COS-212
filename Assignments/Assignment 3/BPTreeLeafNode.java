/**
 * A B+ tree leaf node
 * @param <TKey> the data type of the key
 * @param <TValue> the data type of the value
 */
class BPTreeLeafNode<TKey extends Comparable<TKey>, TValue> extends BPTreeNode<TKey, TValue> {
	
	protected Object[] values;
	
	public BPTreeLeafNode(int order) {
		this.m = order;
		// The strategy used here first inserts and then checks for overflow. 
		// Thus an extra space is required i.e. m instead of m-1.
		// You can change this if needed.
		this.keys = new Object[m];
		this.values = new Object[m];
	}

	@SuppressWarnings("unchecked")
	public TValue getValue(int index) {
		return (TValue)this.values[index];
	}

	public void setValue(int index, TValue value) {
		this.values[index] = value;
	}
	
	@Override
	public boolean isLeaf() {
		return true;
	}

	////// You should not change any code above this line //////

	////// Implement functions below this line //////

	/**
	 * Override of default insert class.
	 * Inserts key and value pair into a leaf node.
	 * If leaf node is full after insert, then split it.
	 */
	public BPTreeNode<TKey, TValue> insert(TKey key, TValue value){
		//get current key count of node
		int keyCount = this.getKeyCount();

		//leaf has space for insert
		if(keyCount < m){ 
			//the leaf is empty(root case)(first insert)
			if(keyCount == 0){ 
				insertKey(key, value, 0);
				return this;
			}
			//if leaf is not empty
			else{
				for(int index = 0; index < keyCount; index++){
					//check if current index is greater than key to be inserted
					if(this.getKey(index).compareTo(key) > 0){
						//move up all values to make space for new key
						for(int moveIndex = keyCount; moveIndex > index; moveIndex--){
							this.setKey(moveIndex, this.getKey(moveIndex-1));
							this.setValue(moveIndex, this.getValue(moveIndex-1));
						}

						//insert key into empty space
						insertKey(key, value, index);
						break;
					}
					//if key to be inserted is larger than all existing keys
					else if(index + 1 == keyCount){
						//set index to be inserted into to be after all existing keys
						index++;
						//insert key into space after all keys
						insertKey(key, value, index);
						break;
					}	
				}
			}
		}

		//update keyCount variable after insert
		keyCount = this.getKeyCount();
		//if this leaf is now full after insert split it
		if(keyCount == m) return leafNodeSplit(this);
		else return this;
	}

	/**
	 * Override of default delete class.
	 */
	public BPTreeNode<TKey, TValue> delete(TKey key){
		//get current key count of node
		int keyCount = this.getKeyCount();
		int minOrder = m/2-1;

		//loop through all keys to see if one to be deleted is in here
		for(int index = 0; index < keyCount; index++){
			if(this.getKey(index).equals(key)){
				//move all indexes down to fill empty space(if one was made)
				for(int moveIndex = index; moveIndex < keyCount; moveIndex++){
					this.moveKey(this.getKey(moveIndex+1), this.getValue(moveIndex+1), moveIndex);
				}
				this.keyTally--;
				if(this.parentNode != null && this.getKeyCount() < minOrder){
					return this.underflow(key);
				}
				break;
			}
		}

		//return node(with or without delete)
		return this;
	}

	//=============================Helper Functions================================//

	protected BPTreeNode<TKey, TValue> underflow(TKey deleteKey){
		//Variables needed
		BPTreeInnerNode<TKey, TValue> parentNode = (BPTreeInnerNode<TKey, TValue>)this.parentNode;
		int siblingKeyCount = 0;
		int minOrder = m/2-1;
		int keyCount = this.getKeyCount();

		//See if left sibling has enough keys to share
		BPTreeLeafNode<TKey, TValue> leftSibling = (BPTreeLeafNode<TKey, TValue>)this.leftSibling;
		if(leftSibling != null){
			//If left sibling exists check if it has enough keys to share
			siblingKeyCount= leftSibling.getKeyCount();
			//if it has enough
			if(siblingKeyCount > minOrder && leftSibling.parentNode == this.parentNode){
				
				//Move all keys one index up
				for(int index = keyCount; index > 0; index--){
					this.moveKey(this.getKey(index-1), this.getValue(index-1), index);
				}

				//Copy key from left sibling
				this.insertKey(leftSibling.getKey(siblingKeyCount-1), leftSibling.getValue(siblingKeyCount-1), 0);

				//Replace parent key if key deleted was saved in parent
				for(int parentIndex = 0; parentIndex < parentNode.getKeyCount(); parentIndex++){
					if(parentNode.getKey(parentIndex).equals(deleteKey)){
						parentNode.setKey(parentIndex, this.getKey(0));
						break;
					}
				}

				//Remove key reference from sibling
				leftSibling.keyTally--;
				return this;
			}
			
		}

		//If not see if right sibling has enough keys to share
		BPTreeLeafNode<TKey, TValue> rightSibling = (BPTreeLeafNode<TKey, TValue>)this.rightSibling;
		if(rightSibling != null && rightSibling.parentNode == this.parentNode){
			//If right sibling exists check if it has enough keys to share
			siblingKeyCount = rightSibling.getKeyCount();

			//if it has enough
			if(siblingKeyCount > minOrder){
				//Copy first key from right sibling
				this.insertKey(rightSibling.getKey(0), rightSibling.getValue(0), keyCount);

				//Move all keys and value down one index to fill gap
				for(int moveIndex = 0; moveIndex < keyCount; moveIndex++){
					rightSibling.moveKey(rightSibling.getKey(moveIndex+1), rightSibling.getValue(moveIndex+1), moveIndex);
				}
				rightSibling.keyTally--;

				//Replace parent key if key deleted was saved in parent
				for(int parentIndex = 0; parentIndex < parentNode.getKeyCount(); parentIndex++){
					if(parentNode.getKey(parentIndex).equals(deleteKey)){
						parentNode.setKey(parentIndex+1, rightSibling.getKey(0));
						break;
					}
				}

				return this;
			}
		}

		if(leftSibling != null){
			return mergeLeft(deleteKey);
		}
		else{
			return mergeRight(deleteKey);
		}
	}

	protected BPTreeNode<TKey, TValue> mergeLeft(TKey deleteKey){
		((BPTreeLeafNode<TKey, TValue>)this.leftSibling).merge(this);
		BPTreeNode<TKey, TValue> returnNode = this.leftSibling;
		BPTreeInnerNode<TKey, TValue> parent = (BPTreeInnerNode<TKey, TValue>)this.parentNode;
		int parentPostion = parent.getPosition(this);
		parent.deleteIndex(parentPostion-1, 1);
		return returnNode;
	}

	protected BPTreeNode<TKey, TValue> mergeRight(TKey deleteKey){
		((BPTreeLeafNode<TKey, TValue>)this).merge((BPTreeLeafNode<TKey, TValue>)this.rightSibling);
		BPTreeNode<TKey, TValue> returnNode = this;
		BPTreeInnerNode<TKey, TValue> parent = (BPTreeInnerNode<TKey, TValue>)this.parentNode;
		int parentPostion = parent.getPosition(this);
		parent.deleteIndex(parentPostion, 1);
		return returnNode;
	}

	protected void merge(BPTreeLeafNode<TKey, TValue> consumeNode){
		for (int index = 0; index < consumeNode.getKeyCount(); index++) {
			insertKey(consumeNode.getKey(index), consumeNode.getValue(index), getKeyCount());
		}
		this.rightSibling = consumeNode.rightSibling;
	}

	/**
	 * Insert Key and Value into passed in index.
	 */
	public void moveKey(TKey key, TValue value, int index){
		this.setKey(index, key);
		this.setValue(index, value);
	}

	/**
	 * Takes in a leaf and links it references to 2 new leaf node being reparented.
	 */
	private void relinkNodes(BPTreeLeafNode<TKey, TValue> oldLeaf, BPTreeLeafNode<TKey, TValue> leftLeaf, BPTreeLeafNode<TKey, TValue> rightLeaf){
		//link the nodes' pointers to eachother
		leftLeaf.rightSibling = rightLeaf;
		rightLeaf.leftSibling = leftLeaf;

		//link new nodes to old leaf node references
		leftLeaf.leftSibling = oldLeaf.leftSibling;
		if(leftLeaf.leftSibling != null){
			//link old leaf's sibling to new node
			leftLeaf.leftSibling.rightSibling = leftLeaf;
		}

		//link new nodes to old leaf node references
		rightLeaf.rightSibling = oldLeaf.rightSibling;
		if(rightLeaf.rightSibling != null){
			//link old leaf's sibling to new node
			rightLeaf.rightSibling.leftSibling = rightLeaf;
		}
	}

	/**
	 * Recieves a key, value and index.
	 * Inserts key value pair into specified index.
	 */
	public void insertKey(TKey key, TValue value, int index){
		this.setKey(index, key);
		this.setValue(index, value);
		this.keyTally++;
	}

	/**
	 * Recieves a full leaf node.
	 * Splits it into 2 leaf nodes parented by 1 inner node, returns parent.
	 * If parent existed then new references are added
	 */
	private BPTreeInnerNode<TKey, TValue> leafNodeSplit(BPTreeLeafNode<TKey, TValue> leaf){
		//create new nodes that will be used to split old leaf node
		BPTreeLeafNode<TKey, TValue> rightLeaf= new BPTreeLeafNode<TKey, TValue>(leaf.m);
		BPTreeLeafNode<TKey, TValue> leftLeaf = new BPTreeLeafNode<TKey, TValue>(leaf.m);
		BPTreeInnerNode<TKey, TValue> newParent = new BPTreeInnerNode<TKey, TValue>(leaf.m);

		//some variables that are used
		int midKey = leaf.getKeyCount()/2;
		int keyCount = leaf.getKeyCount();
		int parentInsertPosition = 0;

		//If no parent existed for the old leaf node
		if(leaf.parentNode == null){
			newParent.setKey(0, leaf.getKey(midKey));
			newParent.keyTally = 1;
		}
		//If old leaf node has a parent
		else{
			//get parent node of old leaf node
			BPTreeInnerNode<TKey, TValue> parentNode = (BPTreeInnerNode<TKey, TValue>)leaf.parentNode;

			//send middle key to parent node, parent will return the index that the key was inserted into
			parentInsertPosition = parentNode.addChildKey(leaf.getKey(midKey));

			//update newParent node to the parent of leaf node
			newParent = parentNode;
		}

		//copy first half of keys from old leaf node to new left leaf node
		for(int index = 0; index < midKey; index++){
			leftLeaf.insertKey(leaf.getKey(index), leaf.getValue(index), index);
		}

		//copy second half(including key sent to parent) of keys from old leaf node to new right leaf node
		for(int index = midKey; index < keyCount; index++){
			rightLeaf.insertKey(leaf.getKey(index), leaf.getValue(index), rightLeaf.getKeyCount());
		}

		//Call relative linkages
		relinkNodes(leaf, leftLeaf, rightLeaf);
		leftLeaf.parentNode = newParent;
		rightLeaf.parentNode = newParent;

		//relink parent references to new leaf nodes
		if(leaf.parentNode == null){
			//if parent did not exist(root case)
			newParent.setChild(1, rightLeaf);
			newParent.setChild(0, leftLeaf);
		}
		else{
			//if leaf had a parent, relink references
			newParent.setChild(parentInsertPosition, leftLeaf);
			newParent.setChild(parentInsertPosition+1, rightLeaf);
		}

		//return the parent
		return newParent;
	}
}
