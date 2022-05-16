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
            System.out.print(" " + cur.data + " ");


            if (cur.rightThread == true)
                cur = cur.right;
            else 
                cur = getLeftMost(cur.right);
        }
    }

    /* Do not edit the code above */


    void convertAVLtoThreaded(Node<T> node) {
        if(node == null){
            return;
        }
        insert(node, node.data);
         
        if(node.right != null){convertAVLtoThreaded(node.right);}
        if(node.left != null){convertAVLtoThreaded(node.left);}
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

            adjustHeights(this.root);
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
        if(this.root == null){
            return null;
        }

        Node<T> prev = null;
        Node<T> curr = getLeftMost(root);
        while (curr != null && curr.data.compareTo(data) != 0) {
            if (curr.rightThread == true){
                curr = curr.right;
            }   
            else{
                curr = getLeftMost(curr.right);
            }     
        }

        if(curr == null){
            return this.root;
        }
        
        prev = getParent(curr);
        if(prev == null){
            if(this.root.right == null && this.root.left == null){
                this.root = deleteWithNoChildren(curr, null);
            }
            else{
                this.root = deleteWithChildren(curr, null);
            }
            
            
            Node<T> startPoint = curr;
            if(curr.left != null){
                startPoint = curr.left;
            }

            adjustHeights(this.root);
            Node<T> grandparent = null;
            Node<T> parent = startPoint;
            while(parent != null){
                grandparent = getParent(parent);
                parent = checkBalance(parent, grandparent);
                parent = grandparent;
            }
        }
        else if(curr.left != null || curr.rightThread == false){
            curr = deleteWithChildren(curr, prev);

            Node<T> startPoint = curr;
            if(curr.left != null){
                startPoint = curr.left;
            }

            adjustHeights(this.root);
            Node<T> grandparent = null;
            Node<T> parent = startPoint;
            while(parent != null){
                grandparent = getParent(parent);
                parent = checkBalance(parent, grandparent);
                parent = grandparent;
            }
        }
        else if(curr.left == null && curr.rightThread == true){
            curr = deleteWithNoChildren(curr, prev);

            adjustHeights(this.root);
            Node<T> grandparent = null;
            Node<T> parent = prev;
            while(parent != null){
                grandparent = getParent(parent);
                parent = checkBalance(parent, grandparent);
                parent = grandparent;
            }
        }

        adjustHeights(this.root);
        return this.root;
    }

    private Node<T> deleteWithNoChildren(Node<T> node, Node<T> parent){
        Node<T> nodeRef = node;

        if(parent == null){
            node = null;
        }
        else if(nodeRef == parent.right){
            parent.rightThread = true;
            parent.right = nodeRef.right;
        }
        else{
            parent.left = null;
        }

        node = null;
        return node;
    }

    private Node<T> deleteWithChildren(Node<T> node, Node<T> parent){
        Node<T> nodeRef = node;
        Node<T> child = null;
        Node<T> successor = null;

        if(nodeRef.left != null){
            child = nodeRef.left;
            successor = child;

            while(successor.right != null && successor.rightThread == false){
                successor = successor.right;
            }

            if(successor != child){
                if(successor.left != null){
                    Node<T> par = getParent(successor);
                    par.right = successor.left;
                }
                else{
                    if(child.right == successor){
                        child.rightThread = true;
                    }
                }
                successor.left = child;
            }
            successor.right = nodeRef.right;
            successor.rightThread = false;
        }
        else if(node.rightThread == false){
            successor = nodeRef.right;
        }

        if (parent == null){
            this.root = successor;
        }
        else if (nodeRef == parent.left){
            parent.left = successor;
        }  
        else{
            parent.right = successor;
        }

        return successor;
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

        if(grandparent == null){
            if(balanceFactor > 1 && getBalanceFactor(node.right) < 0){
                node.right = rightRotation(node.right);
                leftRotation(node);
                return node;
            }

            if(balanceFactor < -1 && getBalanceFactor(node.left) > 0){
                node.left = leftRotation(node.left);
                rightRotation(node);
                return node;
            }
    
            if(balanceFactor > 1){
                leftRotation(node);
                return node;
            }
    
            if (balanceFactor < -1){
                rightRotation(node);
                return node;
            }
        }

        if(balanceFactor > 1 && getBalanceFactor(node.right) <= 0){
            node.right = rightRotation(node.right);
            Node<T> temp = leftRotation(node);
            if(temp.data.compareTo(grandparent.data) < 0){
                grandparent.left = temp;
            }
            else{
                grandparent.right = temp;
            }
            adjustHeights(this.root);
            return node;
        }

        if(balanceFactor > 1 && getBalanceFactor(node.right) >= 0){
            Node<T> temp = leftRotation(node);
            if(temp.data.compareTo(grandparent.data) < 0){
                grandparent.left = temp;
            }
            else{
                grandparent.right = temp;
            }
            adjustHeights(this.root);
            return node;
        }

        if(balanceFactor < -1 && getBalanceFactor(node.left) >= 0){
            node.left = leftRotation(node.left);
            Node<T> temp = rightRotation(node);
            if(temp.data.compareTo(grandparent.data) < 0){
                grandparent.left = temp;
            }
            else{
                grandparent.right = temp;
            }
            adjustHeights(this.root);
            return node;
        }

        if (balanceFactor < -1 && getBalanceFactor(node.left) <= 0){
            Node<T> temp = rightRotation(node);
            if(temp.data.compareTo(grandparent.data) < 0){
                grandparent.left = temp;
            }
            else{
                grandparent.right = temp;
            }
            adjustHeights(this.root);
            return node;
        }

        return node;
    }

    private int adjustHeights(Node<T> node){
        if (node == null){
            return -1;
        }

        int leftHeight = -1;
        if(node.left != node){
            leftHeight = adjustHeights(node.left);
        }
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

    private Node<T> rightRotation(Node<T> node){
        Node<T> child = node.left;
        Node<T> grandChild = null;
        if(child.rightThread == false){
            grandChild = child.right;
        }
    
        if(child.rightThread == true){
            child.rightThread = false;
        }
        else{
            child.right = node;
        }

        if(grandChild != null){
            node.left = grandChild;
        }
        else{
            node.left = null;
        }

        if(node == this.root){
            this.root = child;
        }

        return child;
    }

    private Node<T> leftRotation(Node<T> node){
        Node<T> child = node.right;
        Node<T> grandChild = child.left;

        child.left = node;
        if(grandChild != null){
            node.right = grandChild;
        }
        else{
            node.rightThread = true;
        }

        if(node == root){
            this.root = child;
        }

        return child;
    }
}

