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
		int keyCount = this.getKeyCount();

		if(keyCount < m){ //leaf has space for insert
			if(keyCount == 0){ //the leaf is empty
				this.setKey(0, key);
				this.setValue(0, value);
				this.keyTally++;
				return this;
			}

			for(int index = 0; index < keyCount; index++){
				if(this.getKey(index).compareTo(key) > 0){
					for(int moveIndex){

					}

					this.setKey(index, key);
					this.setValue(index, value);
					this.keyTally++;
					break;
				}
			}
		}
		return this;
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
