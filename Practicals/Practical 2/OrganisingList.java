/**
 * Name:
 * Student Number:
 */



public class OrganisingList {

    public ListNode root = null;

    public OrganisingList() {

    }
    
    /**
     * Calculate the sum of keys recursively, starting with the given node until the end of the list
     * @return The sum of keys from the current node to the last node in the list
     * NOTE: 'int' and not 'Integer' here because it cannot return 'null'
     */
    public static int sumRec(ListNode node) {
        if(node.next == null){
            return node.key;
        }
        else {
            return sumRec(node.next) + node.key;
        }
    }

    /**
     * Calculate and set the data for the given node.
     * @return The calculated data for the given node
     * NOTE: 'int' and not 'Integer' here because it cannot return 'null'
     */
    public static int dataRec(ListNode node) {
        if(node.next == null){
            node.data = node.key;
            return node.data;
        }
        else {
            node.data = (node.key * sumRec(node)) - dataRec(node.next);
            return node.data;
        }
    }

    /**
     * Calculate the data field of all nodes in the list using the recursive functions.
     * DO NOT MODIFY!
     */
    public void calculateData() {
        if (root != null) {
            dataRec(root);
        }
    }

    /**
     * Retrieve the data for the node with the specified key and swap the
     * accessed node with its predecessor, then recalculate data fields.
     * @return The data of the node before it has been moved,
     * otherwise 'null' if the key does not exist.
     */
    public Integer getData(Integer key) {  
        ListNode currNode = this.root;
        ListNode firstPrev = null;
        ListNode secondPrev = null;
        
        while (currNode != null) {
            if (currNode.key.equals(key)) {
                Integer data = currNode.data;

                
                calculateData();
                return data;
            }    
            else {
                if(firstPrev != null){
                    secondPrev = firstPrev;
                }
                firstPrev = currNode;
                currNode = currNode.next;
            }
        }

        return null;
    }

    /**
     * Delete the node with the given key.
     * calculateData() should be called after deletion.
     * If the key does not exist, do nothing.
     */
    public void delete(Integer key) {
        ListNode currNode = this.root, prev = null;
 
        while (currNode != null) {
            if (currNode.key == key) {
                if(prev == null){
                    root = null;
                    calculateData();
                    return;
                }
                else{
                    prev.next = currNode.next;
                    currNode.next = null;
                    calculateData();
                    return;
                }
            }
            else{
                prev = currNode;
                currNode = currNode.next;
            }
        }
    }


    /**
     * Insert a new key into the linked list.
     * 
     * New nodes should be inserted to the back
     * Duplicate keys should not be added.
     */
    public void insert(Integer key) {
        ListNode searchNode;
        ListNode newNode = new ListNode(key);
        newNode.next = null;

        if(root == null)
        {
            root = newNode;
            calculateData();
        }
        else
        {
            for (searchNode = root; searchNode != null; searchNode = searchNode.next) {
                if(searchNode.key == key)
                {
                    return;
                }
                if(searchNode.next == null){
                    searchNode.next = newNode;
                    calculateData();
                }        
            }    
        }
    }

    /**
     * Check if a key is contained in the list
     * @return true if the key is in the list, otherwise false
     */
    public Boolean contains(Integer key) {
        ListNode currNode = this.root;
 
        while (currNode != null) {
            if(currNode.key == key)
            {
                return true;
            }

            currNode = currNode.next;    
        }

        return false;
    }


    /**
     * Return a string representation of the Linked List.
     * DO NOT MODIFY!
     */
    public String toString() {
        if (root == null) {
            return "List is empty";
        }

        String result = "";
        for (ListNode node = root; node != null; node = node.next) {
            result = result.concat("[K: " + node.key + ", D: " + node.data + "]");

            if (node.next != null) {
                result = result.concat(" ");
            }
        }

        return result;
    }

    /**
     * Return a string representation of the linked list, showing only the keys of nodes.
     * DO NOT MODIFY!
     */
    public String toStringKeysOnly() {

        if (root == null) {
            return "List is empty";
        }

        String result = "";
        for (ListNode node = root; node != null; node = node.next) {
            result = result + node.key;

            if (node.next != null) {
                result = result.concat(", ");
            }
        }

        return result;
    }

    
}