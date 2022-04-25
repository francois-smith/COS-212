public class ThreadedAvlTree<T extends Comparable<T>> {
    public Node<T> root;

    public ThreadedAvlTree() {
        this.root = null;
    }


    int getHeight(Node<T> N) {
        if (N == null)
            return 0;

        return N.height;
    }

    static Node getLeftMost(Node node) {
        while (node != null && node.left != null)
            node = node.left;
        return node;
    }

    // Inorder traversal of a threaded avl tree
    void print(Node<T> node) {
        if (node == null)
            return;

        Node<T> cur = getLeftMost(node);

        while (cur != null) {
            //System.out.print(" " + cur.data + " ");
            System.out.println("Node: "+cur.data + " has a height of: " + cur.height + " and a balance of: " + getBalanceFactor(cur) + "\n");

            if (cur.rightThread == true)
                cur = cur.right;
            else 
                cur = getLeftMost(cur.right);
        }
    }

    /* Do not edit the code above */


    void convertAVLtoThreaded(Node<T> node) {

    }

    /**
     * Insert the given data into the tree.
     * Duplicate data is not allowed. just return the node.
     */
    Node<T> insert(Node<T> node, T data) {
        if(root == null){
            root = new Node<T>(data);
            root.left = root.right = null;
            root.rightThread = false;
            root.leftThread = false;
            return root;
        } 
        else{
            Node<T> nodePtr = root;
            Node<T> parent = null;

            while(nodePtr != null){
                if(data.compareTo(nodePtr.data) == 0){
                    return root;
                }
    
                parent = nodePtr;
    
                if(data.compareTo(nodePtr.data) < 0){
                    if(nodePtr.left != null){
                        nodePtr = nodePtr.left;
                    }
                    else{
                        break;
                    }
                }
                else{
                    if(nodePtr.rightThread == false){
                        nodePtr = nodePtr.right;
                    }
                    else{
                        break;
                    }
                }
            }

            Node<T> newNode = new Node<T>(data);
            newNode.rightThread = false;
            newNode.leftThread= false;

            if(data.compareTo(parent.data) < 0){
                parent.left = newNode;
                newNode.right = parent;
                newNode.rightThread = true;
            }
            else{
                newNode.right = parent.right;
                newNode.rightThread = true;
                parent.rightThread = false;
                parent.right = newNode;
            }

            adjustHeights(root);
            Node<T> grandparent = null;
            while(parent != null){
                grandparent = getParent(parent);
                parent = checkBalance(parent, grandparent);
                parent = grandparent;
            }
        }
        return root;
    }

    /**
     * Delete the given element \texttt{data} from the tree.  Re-balance the tree after deletion.
     * If the data is not in the tree, return the given node / root.
     */
    Node<T> removeNode(Node<T> root, T data) {
        return null;
    } 

    private Node<T> getParent(Node<T> node){
        Node<T> nodePtr = root;
        Node<T> parent = null;
        while(nodePtr != null){
            if(node.data.compareTo(nodePtr.data) < 0){
                parent = nodePtr;
                nodePtr = nodePtr.left;
            }
            else if(node.data.compareTo(nodePtr.data) > 0 && !nodePtr.rightThread){
                parent = nodePtr;
                nodePtr = nodePtr.right;
            }
            else{
                return parent;
            }
        }

        return null;
    }

    private Node<T> checkBalance(Node<T> node, Node<T> grandparent){
        int balanceFactor = getBalanceFactor(node);

        if(balanceFactor > 1 && getBalanceFactor(node.right) <= 0){
            node.right = rightRotation(node.right);
            grandparent.left = leftRotation(node);
        }

        if(balanceFactor > 1 && getBalanceFactor(node.right) > 0){
            grandparent.left = leftRotation(node);
        }

        if(balanceFactor < -1 && getBalanceFactor(node.left) >= 0){
            node.left = leftRotation(node.left);
            grandparent.right = rightRotation(node);
        }

        if (balanceFactor < -1 && getBalanceFactor(node.left) < 0){
            grandparent.right = rightRotation(node);
        }

        adjustHeights(root);
        return node;
    }

    private int adjustHeights(Node<T> node){
        if (node == null){
            return -1;
        }

        int leftHeight = adjustHeights(node.left);
        int rightHeight = -1;
        if(!node.rightThread){
            rightHeight = adjustHeights(node.right);
        }

        int result = getMax(rightHeight, leftHeight) + 1;
        node.height = result;
        return result;
    }

    private int getBalanceFactor(Node<T> node){
        int returnValue = 0;
        if(node.right != null && !node.rightThread){
            returnValue += node.right.height + 1;
        }
        if(node.left != null){
            returnValue -= node.left.height + 1;
        }

        return returnValue;
    }   

    private int getMax(int value1, int value2) {
        return (value1 > value2) ? value1 : value2;
    }

    Node<T> rightRotation(Node<T> node){
        Node<T> child = node.left;
        Node<T> grandChild = null;
        if(child.rightThread == false){
            grandChild = child.right;
        }
    
        child.right = node;
        child.rightThread = false;
        if(grandChild != null){
            node.left = grandChild;
        }
        else{
            node.left = null;
        }

        if(node == root){
            this.root = child;
        }

        adjustHeights(root);
        return child;
    }

    Node<T> leftRotation(Node<T> node){
        Node<T> child = node.right;
        Node<T> grandChild = child.left;

        child.left = node;
        child.rightThread = false;
        if(grandChild != null){
            node.right = grandChild;
        }
        else{
            node.rightThread = true;
        }

        if(node == root){
            this.root = child;
        }

        adjustHeights(root);
        return child;
    }
}
