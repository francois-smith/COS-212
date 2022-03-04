import java.util.Random;

public class List<T> {
    private Node<T>[] head;
    private int topLevel = 1;
    private int numNodes;
    private int[] powers;
    private Random random = new Random();

    public List(int topLevel){
        this.topLevel = topLevel;
        head = new Node[topLevel];
        powers = new int[topLevel];
        for (int i = 0; i < topLevel; i++) {
            head[i] = null;
        }
        choosePower();
    } 

    public boolean isEmpty(){
        return head[0] == null;
    }

    public void choosePower(){
        powers[topLevel - 1] = (2 << (topLevel - 1));
        for (int i = topLevel - 1, j = 0; i >= 0; i--, j++) {
            powers[i] = powers[i+1] - (2 << j);
        }
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
