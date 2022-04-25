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
        if(node == null){
            return new Node<T>(data);
        } 

        if(data.compareTo(node.data) < 0){
            node.left = insert(node.left, data);
        }
        else if(data.compareTo(node.data) > 0){
            node.right = insert(node.right, data);
        } 
        else {
            return node;
        }

        node.height = getMax(getHeight(node.left), getHeight(node.right)) + 1;

        int balanceFactor = returnBalanceFactor(node);

        if(balanceFactor > 1 && node.left == null){
            return leftRotation(node);
        }

        if(balanceFactor > 1 && node.left != null && data.compareTo(node.left.data) < 0){
            return rightRotation(node);
        }
        
        if(balanceFactor > 1 && node.left != null && data.compareTo(node.left.data) > 0){
            node.left = leftRotation(node.left);
            return rightRotation(node);
        }


        if(balanceFactor < -1 && node.right == null){
            return rightRotation(node);
        }

        if(balanceFactor > 1 && node.left == null){
            return leftRotation(node);
        }

        if( balanceFactor < -1 && node.right != null && data.compareTo(node.right.data) < 0){
            node.right = rightRotation(node.right);
            return leftRotation(node);
        }

        if (balanceFactor < -1 && node.right != null && data.compareTo(node.right.data) > 0){
            return leftRotation(node);
        }
 
        return node;
    }


    /**
     * Remove / Delete the node based on the given data
     * Return the node / root if the data do not exist
     */

    Node<T> removeNode(Node<T> root, T data) {
        if(root == null){
            return null;
        }

        if(data.compareTo(root.data) < 0){
            root.left = removeNode(root.left, data);
        }
        else if(data.compareTo(root.data) > 0){
            root.right = removeNode(root.right, data);
        }
        else{
            if(root.left == null || root.right == null){
                Node<T> childNode = null;
                if(childNode == root.right){
                    childNode = root.left;
                }
                else{
                    childNode = root.right;
                }

                if(childNode == null){
                    childNode = root;
                    root = null;
                }
                else{
                    root = childNode;
                }
            }
            else{
                Node<T> tempNode = root.right;
                while (tempNode.left != null)
                {
                    tempNode = tempNode.left;
                }
                    
                root.data = tempNode.data;
                root.right = removeNode(root.right, tempNode.data);
            }
        }

        if(root == null){
            return root;
        }

        root.height = 1 + getMax(getHeight(root.left), getHeight(root.right));
        int balanceFactor = returnBalanceFactor(root);

        if(balanceFactor > 1 && data.compareTo(root.left.data) < 0){
            return rightRotation(root);
        }

        if(balanceFactor > 1 && data.compareTo(root.left.data) > 0){
            root.left = leftRotation(root.left);
            return rightRotation(root);
        }

        if( balanceFactor < -1 && data.compareTo(root.right.data) < 0){
            root.right = rightRotation(root.right);
            return leftRotation(root);
        }

        if (balanceFactor < -1 && data.compareTo(root.right.data) > 0){
            return leftRotation(root);
        }

        return root;
    }

    /* Helper Functions */

    Node<T> rightRotation(Node<T> node){
        Node<T> child = node.left;
        Node<T> grandChild = child.right;

        child.right = node;
        if(grandChild != null){
            node.left = grandChild;
        }
        else{
            node.left = null;
        }


        if(node.left == null && node.right == null){
            node.height = 0;
        }
        else{
            node.height = getMax(getHeight(node.left), getHeight(node.right)) + 1;
        }
        
        if(child.left == null && child.right == null){
            child.height = 0;
        }
        else{
            child.height = getMax(getHeight(child.left), getHeight(child.right)) + 1;
        }
        
        return child;
    }

    Node<T> leftRotation(Node<T> node){
        Node<T> child = node.right;
        Node<T> grandChild = child.left;

        child.left = node;
        if(grandChild != null){
            node.right = grandChild;
        }else{
            node.right = null;
        }

        if(node.left == null && node.right == null){
            node.height = 0;
        }
        else{
            node.height = getMax(getHeight(node.left), getHeight(node.right)) + 1;
        }
        
        if(child.left == null && child.right == null){
            child.height = 0;
        }
        else{
            child.height = getMax(getHeight(child.left), getHeight(child.right)) + 1;
        }
        return child;
    }

    int returnBalanceFactor(Node<T> node){
        int returnValue = 0;
        if(node.right != null){
            returnValue += getHeight(node.right) +1;
        }
        if(node.left != null){
            returnValue -= getHeight(node.left) +1;
        }

        return returnValue;
    }

    int getMax(int value1, int value2) {
        return (value1 > value2) ? value1 : value2;
    }
}
