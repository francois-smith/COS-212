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

	public BPTreeNode<TKey, TValue> insert(TKey key, TValue value){
		return null;
	}
}