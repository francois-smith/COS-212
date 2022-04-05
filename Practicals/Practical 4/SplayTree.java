public class SplayTree<T extends Comparable<T>> {
    /*Tayla Orsmond u21467456*/
    public TreeNode<T> root;

    public SplayTree(){
        root = null;
    }

    /**
     * Returns true if the key exists in the tree, otherwise false
     */
    public Boolean contains(T key){
        if(root == null) return false;
        if(key.compareTo(root.key) == 0) return true;
        TreeNode<T> ptr = root;
        while(ptr != null)
        {
            if(key.compareTo(ptr.key) == 0)
                return true;
            else if(key.compareTo(ptr.key) < 0)
                ptr = ptr.left;
            else ptr = ptr.right;
        }
        return false;
    }

    /**
     * Insert the given key into the tree.
     * Duplicate keys should be ignored.
     * No Splaying should take place.
     */
    public void insert(T key){
        if(!contains(key))//no duplicates
        {
            TreeNode<T> ptr = root, prev = null;
            while(ptr != null)
            {
                prev = ptr;
                if(key.compareTo(ptr.key) < 0)
                    ptr = ptr.left;
                else ptr = ptr.right;
            }
            if(root == null)
            {
                root = new TreeNode<T>(key);
            }
            else if(key.compareTo(prev.key) < 0)
                prev.left = new TreeNode<T>(key);
            else prev.right = new TreeNode<T>(key);
        }
    }

    /**
     * Return the Predecessor of the given key.
     * If the given key does not exist return null.
     * If the given key does not have a Predecessor, return null.
     * A predecessor of a given key is defined as the largest key in the tree which is smaller than the given key
     */
    public T findPredecessor(T key){
        if(root == null) return null;//tree is empty
        TreeNode<T> ptr = root;
        T predecessor = null;//holds predecessor key
        if(key != null && contains(key))
        {
            while(ptr != null)
            {
                if(key.compareTo(ptr.key) < 0 || key.compareTo(ptr.key) == 0)
                {//have not passed predecessor, could have passed desired key in which case check children
                    ptr = ptr.left;
                }
                else{
                    predecessor = ptr.key;//possibly passed predecessor
                    ptr = ptr.right;
                }
            }
            return predecessor;
        }
        return null;//key not in tree
    }

    /**
     * Move the accessed key closer to the root using the splaying strategy.
     * If the key does not exist, insert it without splaying
     */
    public void access(T key){
        if(!contains(key))
            this.insert(key);
        else{
            T parentKey = findParent(key), grandparentKey = findParent(parentKey);
            TreeNode<T> child = find(key), parent = find(parentKey), grandparent = find(grandparentKey), temp = null;
            while(parent != null)//while child is not root
            {
                if(parent == root)//parent is root
                {
                    //SINGULAR SPAY
                    //left rotation
                    if(child == parent.right)
                    {
                        temp = child.left;
                        child.left = parent;
                        parent.right = temp;
                        root = child;

                    }
                    //right rotation
                    else if(child == parent.left)
                    {
                        temp = child.right;
                        child.right = parent;
                        parent.left = temp;
                        root = child;
                    }
                }
                else if((child == parent.right && parent == grandparent.right) || (child == parent.left && parent == grandparent.left))
                {
                    //HOMOGENEOUS CONFIG
                    //ROTATE P ABOUT G
                    //left rotation
                    if(parent == grandparent.right)
                    {
                        temp = parent.left;
                        parent.left = grandparent;
                        grandparent.right = temp;
                        //update grandparent to link
                        if(grandparent != root)//something above gp
                        {
                            TreeNode<T> oldgp = grandparent;
                            grandparentKey = findParent(grandparentKey);
                            grandparent = find(grandparentKey);
                            if(oldgp == grandparent.left)
                                grandparent.left = parent;
                            else grandparent.right = parent;
                        }
                        else root = parent;
                    }
                    //right rotation
                    else if(parent == grandparent.left)
                    {
                        temp = parent.right;
                        parent.right = grandparent;
                        grandparent.left = temp;
                        //update grandparent to link
                        if(grandparent != root)
                        {
                            TreeNode<T> oldgp = grandparent;
                            grandparentKey = findParent(grandparentKey);
                            grandparent = find(grandparentKey);
                            if(oldgp == grandparent.left)
                                grandparent.left = parent;
                            else grandparent.right = parent;
                        }
                        else root = parent;
                    }
                    //ROTATE C ABOUT P if parent !=root (if parent == root, perform in next iteration)
                    if(parent != root)
                    {
                        //left rotation
                        if(child == parent.right)
                        {
                            temp = child.left;
                            child.left = parent;
                            parent.right = temp;
                            //update new grandparent (if parent != root)
                            if(parent == grandparent.left)
                                grandparent.left = child;
                            else grandparent.right = child;
                        }
                        //right rotation
                        else if(child == parent.left)
                        {
                            temp = child.right;
                            child.right = parent;
                            parent.left = temp;
                            //update new grandparent (if parent != root)
                            if(parent == grandparent.left)
                                grandparent.left = child;
                            else grandparent.right = child;
                        }
                    }
                }
                else
                {
                    //HETEROGENEOUS CONFIG
                    //ROTATE C ABOUT P, AND THEN C ABOUT G
                    //ROTATE C ABOUT P
                    //left rotation
                    if(child == parent.right)
                    {
                        temp = child.left;
                        child.left = parent;
                        parent.right = temp;
                        //update grandparent
                        if(parent == grandparent.right)
                            grandparent.right = child;
                        else grandparent.left = child;
                    }
                    //right rotation
                    else if(child == parent.left)
                    {
                        temp = child.right;
                        child.right = parent;
                        parent.left = temp;
                        //update grandparent
                        if(parent == grandparent.right)
                            grandparent.right = child;
                        else grandparent.left = child;
                    }
                    //ROTATE C ABOUT G
                    //left rotation
                    if(child == grandparent.right)
                    {
                        temp = child.left;
                        child.left = grandparent;
                        grandparent.right = temp;
                        //update grandparent to link
                        if(grandparent != root)
                        {
                            TreeNode<T> oldgp = grandparent;
                            grandparentKey = findParent(grandparentKey);
                            grandparent = find(grandparentKey);
                            if(oldgp == grandparent.left)
                                grandparent.left = child;
                            else grandparent.right = child;
                        }
                        else root = child;
                    }
                    //right rotation
                    else if(child == grandparent.left)
                    {
                        temp = child.right;
                        child.right = grandparent;
                        grandparent.left = temp;
                        if(grandparent != root)
                        {
                            TreeNode<T> oldgp = grandparent;
                            grandparentKey = findParent(grandparentKey);
                            grandparent = find(grandparentKey);
                            if(oldgp == grandparent.left)
                                grandparent.left = child;
                            else grandparent.right = child;
                        }
                        else root = child;
                    }
                }
                parentKey = findParent(key);
                grandparentKey = findParent(parentKey);
                parent = find(parentKey);
                grandparent = find(grandparentKey);
                temp = null;//update parent and grandparent, keep track of child
            }
        }
    }
    //HELPER FUNCTIONS
    public T findParent(T key){
        if(root == null) return null;
        TreeNode<T> ptr = root;
        TreeNode<T> prev = null;
        if(key != null && contains(key))
        {
            if(key.compareTo(ptr.key) == 0 && ptr == root)
                return null;
            while(ptr != null)
            {
                if(key.compareTo(ptr.key) == 0 && prev != null)
                    return prev.key;
                else if(key.compareTo(ptr.key) < 0)
                {
                    prev = ptr;
                    ptr = ptr.left;
                }
                else{
                    prev = ptr;
                    ptr = ptr.right;
                }
            }
        }
        return null;
    }
    protected TreeNode<T> find(T key)
    {
        if(key == null) return null;
        TreeNode<T> ptr = root;
        while(ptr != null)
        {
            if(key.compareTo(ptr.key) == 0)
                break;
            else if(key.compareTo(ptr.key) < 0)
                ptr = ptr.left;
            else ptr = ptr.right;
        }
        return ptr;
    }
}