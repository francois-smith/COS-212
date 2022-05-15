public class AvlTree<T extends Comparable<T>> {
    public Node<T> root;

    public AvlTree() {
        this.root = null;
    }


    int getHeight(Node<T> N) {
        if (N == null)
            return 0;

        return N.height;
    }

    /*Printing AvlTree in inorder*/
    void print(Node<T> node) {
        if (node == null)
            return;

        print(node.left);

        System.out.print(node.data + " ");

        print(node.right);
    }

    /* Do not edit the code above */

    /**
     * Insert the given data into the tree.
     * Duplicate data is not allowed. just return the node.
     */

    Node<T> insert(Node<T> node, T data) {
        if(root == null){
            root = new Node<T>(data);
            root.left = root.right = null;
            return root;
        } 
        else{
            Node<T> nodePtr = this.root;
            while(nodePtr != null){
                if(data.compareTo(nodePtr.data) == 0){
                    return this.root;
                }
                else if(data.compareTo(nodePtr.data) < 0){
                    if(nodePtr.left != null){
                        nodePtr = nodePtr.left;
                    }
                    else{
                        break;
                    }
                }
                else{
                    if(nodePtr.right != null){
                        nodePtr = nodePtr.right;
                    }
                    else{
                        break;
                    }
                }
            }

            Node<T> newNode = new Node<T>(data);
            Node<T> parent = nodePtr;
            if(data.compareTo(parent.data) < 0){
                parent.left = newNode;
            }
            else{
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
     * Remove / Delete the node based on the given data
     * Return the node / root if the data do not exist
     */

    Node<T> removeNode(Node<T> root, T data) {
        if(this.root == null){
            return null;
        }

        Node<T> prev = null;
        Node<T> curr = this.root;
        while (curr != null) {
            if(data.compareTo(curr.data) == 0){
                break;
            }
            else if(data.compareTo(curr.data) < 0){
                prev = curr;
                curr = curr.left;
            }
            else{
                prev = curr;
                curr = curr.right;
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
        else if(curr.left != null || curr.right != null){
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
        else if(curr.left == null && curr.right == null){
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
            parent.right = null;
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
        Node<T> par = null;

        if(nodeRef.left != null){
            child = nodeRef.left;
            successor = child;

            while(successor.right != null){
                successor = successor.right;
            }

            if(successor != child){
                par = getParent(successor);
                if(successor.left != null){
                    par.right = successor.left;
                }
                else{
                    par.right = null;
                }
                successor.left = child;
            }
            successor.right = nodeRef.right;
        }
        else if(node.right != null){
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
            else if(node.data.compareTo(nodePtr.data) > 0){
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

        int leftHeight = adjustHeights(node.left);
        int rightHeight = adjustHeights(node.right);

        int result = getMax(rightHeight, leftHeight) + 1;
        node.height = result;
        return result;
    }

    private int getBalanceFactor(Node<T> node){
        if(node == null){
            return 0;
        }

        int returnValue = 0;
        if(node.right != null){
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
        Node<T> grandChild = child.right;

        child.right = node;
        node.left = grandChild;

        if(node == this.root){
            this.root = child;
        }

        adjustHeights(this.root);
        return child;
    }

    private Node<T> leftRotation(Node<T> node){
        Node<T> child = node.right;
        Node<T> grandChild = child.left;

        child.left = node;
        node.right = grandChild;

        if(node == root){
            this.root = child;
        }

        adjustHeights(this.root);
        return child;
    }
}
