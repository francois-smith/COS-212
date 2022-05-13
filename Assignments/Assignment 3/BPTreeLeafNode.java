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

	public BPTreeNode<TKey, TValue> insert(TKey key, TValue value){
		//get current key count of node
		int keyCount = this.getKeyCount();

		//leaf has space for insert
		if(keyCount < m){ 
			//the leaf is empty(root case)(first insert)
			if(keyCount == 0){ 
				this.setKey(0, key);
				this.setValue(0, value);
				this.keyTally++;
				return this;
			}

			//if leaf is not empty
			for(int index = 0; index < keyCount; index++){
				//check if current index is greater than key to be inserted
				if(this.getKey(index).compareTo(key) > 0){
					//move up all values to make space for new key
					for(int moveIndex = keyCount; moveIndex > index; moveIndex--){
						this.setKey(moveIndex, this.getKey(moveIndex-1));
						this.setValue(moveIndex, this.getValue(moveIndex-1));
					}

					//insert key into empty space
					this.setKey(index, key);
					this.setValue(index, value);
					this.keyTally++;

					//break out of loop to complete insert
					break;
				}

				//if key to be inserted is larger than all existing keys
				if(index+1 == keyCount){
					//set index to be inserted into, to be after all existing values
					index++;

					//insert key into last index
					this.setKey(index, key);
					this.setValue(index, value);
					this.keyTally++;

					//break out of loop to complete insert
					break;
				}
			}
		}

		//update keyCount variable after insert
		keyCount = this.getKeyCount();

		//if this leaf is now full after insert split it
		if(keyCount == m){
			BPTreeInnerNode<TKey, TValue> splitNode = leafNodeSplit(this);
			return splitNode;
		}

		return this;
	}

	public BPTreeInnerNode<TKey, TValue> leafNodeSplit(BPTreeLeafNode<TKey, TValue> leaf){
		//create new nodes that will be used to split old leaf node
		BPTreeLeafNode<TKey, TValue> leftLeaf = new BPTreeLeafNode<TKey, TValue>(leaf.m);
		BPTreeLeafNode<TKey, TValue> rightLeaf= new BPTreeLeafNode<TKey, TValue>(leaf.m);
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
			leftLeaf.setKey(index, leaf.getKey(index));
			leftLeaf.setValue(index, leaf.getValue(index));
			leftLeaf.keyTally++;
		}

		//copy second half(including key sent to parent) of keys from old leaf node to new right leaf node
		for(int index = midKey; index < keyCount; index++){
			rightLeaf.setValue(rightLeaf.getKeyCount(), leaf.getValue(index));
			rightLeaf.setKey(rightLeaf.getKeyCount(), leaf.getKey(index));
			rightLeaf.keyTally++;
		}

		//link the new nodes' pointers to eachother
		leftLeaf.rightSibling = rightLeaf;
		leftLeaf.parentNode = newParent;
		rightLeaf.leftSibling = leftLeaf;
		rightLeaf.parentNode = newParent;

		//link new nodes to old leaf node references
		leftLeaf.leftSibling = leaf.leftSibling;
		if(leftLeaf.leftSibling != null){
			//link old leaf's sibling to new node
			leftLeaf.leftSibling.rightSibling = leftLeaf;
		}

		//link new nodes to old leaf node references
		rightLeaf.rightSibling = leaf.rightSibling;
		if(rightLeaf.rightSibling != null){
			//link old leaf's sibling to new node
			rightLeaf.rightSibling.leftSibling = rightLeaf;
		}

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

	////// Implement functions below this line //////
	// public BPTreeNode<TKey, TValue> delete(TKey key){
	// 	for(int i = 0; i < this.getKeyCount(); i++){
	// 		if(this.getKey(i).equals(key)){
	// 			for(j = i; j <this )
	// 		}
	// 	}

	// 	return this;
	// }
	
}
