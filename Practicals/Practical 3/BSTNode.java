public class BSTNode<T extends Comparable<T>> {
    private T value;
    public BSTNode<T> right;
    public BSTNode<T> left;

    public boolean recInsert(T val){
        if(val.compareTo(value) == 0)
            return false;

        if(val.compareTo(value) < 0)
        {
            if(left == null)
            {
                left = new BSTNode<>(val);
                return true;
            } else {
                return left.recInsert(val);
            }
        } else {
            if(right == null)
            {
                right = new BSTNode<>(val);
                return true;
            } else {
                return right.recInsert(val);
            }
        }
    }

    //Implement the following

    public BSTNode(T val){
        this.value = val;
    }

    public T getVal(){
        return this.value;
    }

    public String toString(){
        String returnValue = "";
        if(left != null){
            returnValue += "L[" + left.getVal() + "]";
        } 
        else {
            returnValue += "L[]";
        }

        returnValue += "V[" + this.getVal() + "]";

        if(right != null){
            returnValue += "R[" + right.getVal() + "]";
        }
        else {
            returnValue += "R[]";
        }

        return returnValue;
    }

    //ADD HELPER FUNCTIONS HERE
}
