

@SuppressWarnings("unchecked")
public class MinHeap<T extends Comparable<T>> extends Heap<T> {

    public MinHeap(int capacity) {
	super(capacity);
    }

    ////// You may not change any code above this line //////

    ////// Implement the functions below this line //////

    @Override
    public void insert(T elem) {
        if(currentSize == capacity) {
            return;
        }
        else{
            currentSize++;
            mH[currentSize] = elem;   
            reserveHeap(currentSize);
        }
    }

    public T removeMin() {
        Comparable<T> returnElement = mH[1];
        Comparable<T> lastElement = mH[currentSize];
        mH[1] = lastElement;
        mH[currentSize] = null;
        currentSize--;
        restructureHeap(1);
        return (T)returnElement;
    }

    public void delete(T elem) {
        for(int i = 1; i <= currentSize; i++){
            if(((Comparable) mH[i]).compareTo(elem) == 0){
                Comparable<T> lastElement = mH[currentSize];
                mH[i] = lastElement;
                mH[currentSize] = null;
                currentSize--;
                restructureHeap(i);
                return;
            }
        }
    }


    //Helper functions
    private void reserveHeap(int startIndex){
        if(startIndex == 1){
            return;
        }
        else{
            int parentIndex = getParentIndex(startIndex);
            Comparable<T> temp;
            if(((Comparable)mH[startIndex]).compareTo(mH[parentIndex]) < 0){
                temp = mH[parentIndex];
                mH[parentIndex] = mH[startIndex];
                mH[startIndex]= temp;
                reserveHeap(parentIndex);
            }
        }
    }

    private int getParentIndex(int currentIndex){
        return (currentIndex)/2;
    }

    private void restructureHeap(int startIndex){
        int largestIndex = startIndex;
        int leftIndex = 2 * startIndex;
        int rightIndex = 2 * startIndex + 1;

        if(leftIndex < capacity && mH[leftIndex] != null){
            if(((Comparable) mH[leftIndex]).compareTo(mH[largestIndex]) < 0){
                largestIndex = leftIndex;
            }
        }

        if(rightIndex < capacity && mH[rightIndex] != null){
            if(((Comparable) mH[rightIndex]).compareTo(mH[largestIndex]) < 0){
                largestIndex = rightIndex;
            }
        }

        if(largestIndex != startIndex){
            Comparable<T> temp = mH[startIndex];
            mH[startIndex] = mH[largestIndex];
            mH[largestIndex] = temp;

            restructureHeap(largestIndex);
        }
    }
}