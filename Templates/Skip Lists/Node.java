public class Node<T> {
    public Node<T>[] next;
    public T val;
    
    public Node(T val, int level){
        this.val = val;
        next = new Node[level];
        for (int i = 0; i < level; i++) {
            next[i] = null;
        }
    }
}