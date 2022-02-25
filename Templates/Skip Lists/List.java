public class List {
    public Node head;
    public int topLevel = 1;
    public int numNodes;

    public List(){
        head = new Node(Integer.MIN_VALUE, 1);
        numNodes = 0;
    }

    public Node search(int val){
        Node nodePtr = head;
        int level = topLevel;

        while(nodePtr != null){
            while (nodePtr.next[level] != null && nodePtr.next[level].val <= val ) {
                nodePtr = nodePtr.next[level];
            }

            if (nodePtr.val == val) {
                break;
            }

            level--;
            nodePtr = nodePtr.next[level];
        }

        return nodePtr;
    }

    public void insertNode(int val){

    }

    public void deleteNode(int val){

    }

    public String print(){
        return "";
    }
}
