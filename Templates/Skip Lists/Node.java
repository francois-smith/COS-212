public class Node {
    public Node[] next;
    public int val;
    
    public Node(int val, int level){
        this.val = val;
        next = new Node[level];
        for (int i = 0; i < level; i++) {
            next[i] = null;
        }
    }
}