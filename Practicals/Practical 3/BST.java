public class BST<T extends Comparable<T>> {
    protected BSTNode<T> root;
    protected String DFTOut = "";
    protected int numNodesOnHeight = 0;

    public BST(){};

    public boolean insert(T val){
        if(root == null){
            root = new BSTNode<>(val);
            return true;
        } else {
            return root.recInsert(val);
        }
    }

    //Place code below

    public int numNodes(){
        BSTNode<T> searchNode = root;
        return totalNumberOfNodes(searchNode);
    }

    public Object[] toArray(){
        Object BSTArray[] = new Object[numNodes()];
        BSTNode<T> searchNode = root;
        int index = 0;
        toArray(searchNode, BSTArray, index);
        return BSTArray;
    }


    public boolean contains(T val){
        BSTNode<T> searchNode = root;
        while(searchNode != null)
        {
            if(searchNode.getVal() == val){
                return true;
            } 
            if(searchNode.getVal().compareTo(val) > 0) {
                searchNode = searchNode.left;
            } 
            else {
                searchNode = searchNode.right;
            }
        }

        return false;
    }

    public boolean isEmpty(){
        if(root == null){
            return true;
        }
        else {
            return false;
        }
    }


    public BSTNode<T> find(T val){
        BSTNode<T> searchNode = root;
        while(searchNode != null)
        {
            if(searchNode.getVal() == val){
                return searchNode;
            } 
            if(searchNode.getVal().compareTo(val) > 0) {
                searchNode = searchNode.left;
            } 
            else {
                searchNode = searchNode.right;
            }
        }

        return null;
    }

    public int height(){
        if(root == null) 
            return -1;
        else {
            BSTNode<T> searchNode = root;
            return height(searchNode);
        }
    }


    public Object[] nodesOnHeight(int h){
        if(h < 0 || root == null || h > height(root)){
            return null;
        }

        //get amount of nodes on height
        BSTNode<T> searchNode = root;
        getNumNodesAtHeight(searchNode, 0, h);

        //create array and go get value on height
        Object[] returnArray = new Object[numNodesOnHeight];
        numNodesOnHeight = 0;
        searchNode = root;
        getNodesAtHeight(searchNode, returnArray, 0, h);
        
        return returnArray;
    }

    public String DFT(){
        BSTNode<T> searchNode = root;
        DFT(searchNode);
        String out = DFTOut.substring(0, DFTOut.length() - 1);
        return out;
    }
    
    public String BFT(){
        String out = "";
        for (int i = 0; i < this.height(); i++) {
            BSTNode<T> searchNode = root;
            getNumNodesAtHeight(searchNode, 0, i);
            Object[] returnArray = new Object[numNodesOnHeight];
            numNodesOnHeight = 0;
            searchNode = root;
            getNodesAtHeight(searchNode, returnArray, 0, i);
            for (int j = 0; j < numNodesOnHeight; j++) {
                out += returnArray[j] + ";";
            }
        }

        out = out.substring(0, out.length() - 1);
        return out;
    }
    
    public T maxVal(){
        BSTNode<T> searchNode = root;
        return maxVal(searchNode);
    }

    public T minVal(){
        BSTNode<T> searchNode = root;
        return minVal(searchNode);
    }
    

    //ADD HELPER FUNCTIONS HERE

    private int totalNumberOfNodes(BSTNode<T> root){
        if (root == null) return 0;
        return 1 + totalNumberOfNodes(root.left) + totalNumberOfNodes(root.right);
    }

    private int toArray(BSTNode<T> root, Object[] returnArray, int index){
        if (root.left != null) {
            index = toArray(root.left, returnArray, index);
        }
        returnArray[index++] = root.getVal() + ";";
        if (root.right != null) {
            index = toArray(root.right, returnArray, index);
        }
        return index;
    }

    private int height(BSTNode<T> height){
        if (height == null) {
            return 0;
        }
        return Math.max(height(height.left), height(height.right)) + 1;
    }

    private T minVal(BSTNode<T> node){
        if (node.left == null){
            return node.getVal();
        }
        return minVal(node.left);
    }

    private T maxVal(BSTNode<T> node){
        if (node.right == null){
            return node.getVal();
        }
        return maxVal(node.right);
    }

    private void DFT(BSTNode<T> node){
        if (node == null)
        {
            return;
        }
            
        DFT(node.left);
        DFTOut += node.toString() + ";";
        DFT(node.right);
    }

    private void getNumNodesAtHeight(BSTNode<T> root, int currentLevel, int height) {
        if(root == null) {
            return;   
        }  
        if(currentLevel == height) {
            numNodesOnHeight++;
            return;
        }
                    
        getNumNodesAtHeight(root.left, currentLevel+1, height);
        getNumNodesAtHeight(root.right, currentLevel+1, height);
    }

    private void getNodesAtHeight(BSTNode<T> root, Object[] returnArray, int currentLevel, int height) {
        if(root == null) {
            return;   
        }  
        if(currentLevel == height) {
            returnArray[numNodesOnHeight] = root;
            numNodesOnHeight++;
            return;
        }
                    
        getNodesAtHeight(root.left, returnArray, currentLevel+1, height);
        getNodesAtHeight(root.right, returnArray, currentLevel+1, height);
    }
}
    
