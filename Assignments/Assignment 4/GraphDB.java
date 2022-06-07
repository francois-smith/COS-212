import java.util.ArrayList;

public class GraphDB {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Integer> colours = new ArrayList<>();
    private ArrayList<Relationship> edges = new ArrayList<>();

    public User addUser(String userName, int ID){
        // See If Specified ID Exists In Graph, If So Then Return It
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).userID == ID){
                return users.get(i);
            }
        } 

        //If No ID Was Found That Matches The Passed In ID, Add New User To Graph
        User newUser = new User(userName, ID);
        users.add(newUser);
        return newUser;
    }

    public User getUser(int userID){
        //Search Graph For Passed In ID
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).userID == userID){
                return users.get(i);
            }
        }

        //If Not Found Then User Is Not In Graph
        return null;
    }

    public User getUser(String userName){
        //Search Graph For Passed In userName
        for(int i = 0; i < users.size(); i++) {
            if(users.get(i).userName == userName){
                return users.get(i);
            }
        }

        //If Not Found Then User Is Not In Graph
        return null;
    }

    public Relationship addFriendship(int frienteeID, int friendedID, double relationshipValue){
        //Get Users In Graph(Even If They Dont Exist)
        User frientee = getUser(frienteeID);
        User friended = getUser(friendedID);


        //If Users Either One Of User Does Not Exist Then No Relationship Can Be Made
        if(frientee == null || friended == null) return null;
        //If Both Users Were Found, Try And Add Relationship
        else{
            Relationship returnRelationship = frientee.addFriend(friended, relationshipValue);
            if(returnRelationship != null && !edges.contains(returnRelationship)){
                edges.add(returnRelationship);
            }
            return returnRelationship;
        }
    }

    public User[][] clusterUsers(){
        //Loop Through All Users In Graph And Set Variables To Default Values
        for(User user : users){
            user.saturationDegree = 0;
            user.uncoloredDegree = user.friends.size();
            user.colour = -1;
        }
        colours.clear();

        //Make a Copy Of All Vertices In Graph To Process
        ArrayList<User> unprocessed = new ArrayList<>();
        for(int i = 0; i < users.size(); i++){
            unprocessed.add(users.get(i));
        }

        //While There Exists a User That Has Not Been Processed
        while(!unprocessed.isEmpty()){
            //Get The User To Process Next And Remove Them From Unprocessed
            User user = getNextUser(unprocessed);
            unprocessed.remove(user);

            //Get All Neigbours Of The Selected User
            ArrayList<User> neighbours = new ArrayList<>(); 
            for(Relationship relationship: user.friends){
                neighbours.add(relationship.getFriend(user));
            }

            //Look At All Neighbours And Find The Min Colour To Apply To The User
            int colour = getMinColour(neighbours);

            for(User neighbour: neighbours){
                updateValues(neighbour, colour);
            }
            user.colour = colour;

            //If List Is Empty Or New Colour Needs To Be Registered Then Add Index To List
            if(colours.isEmpty() || colour == colours.size()){
                colours.add(0);
            }
            colours.set(colour, colours.get(colour)+1);
        }

        User[][] returnArray = new User[colours.size()][];
        for(int index = 0; index < colours.size(); index++){
            returnArray[index] = new User[colours.get(index)];
            int i = 0;
            for(int position = 0; position < users.size(); position++){
                if(users.get(position).colour == index){
                    returnArray[index][i] = users.get(position);
                    i++;
                }
            }
            usersQuickSort(returnArray[index], 0, colours.get(index)-1);
        }
        return returnArray;
    }

    public Relationship[] minSpanningTree(){
        //Create A List To Hold a Copy Of All Edges In Graph
        Relationship processTree[] = new Relationship[edges.size()];
        Relationship returnTree[] = new Relationship[users.size()-1];

        //Copy All Of The Edges To New Array
        int index = 0;
        for(index = 0; index < edges.size(); ++index){
            processTree[index] = edges.get(index);
        }
        //Sort Edges in Order
        relationshipQuickSort(processTree, 0,  edges.size()-1);
        int numEdges = 0;

        //Create Copy Of All Users And Set Default Values
        ArrayList<User> userList = new ArrayList<User>();
        for(index = 0; index < users.size(); ++index){
            userList.add(users.get(index));
            userList.get(index).rank = 0;
            userList.get(index).parent = userList.get(index);
        }
        
        //Loop Until Min Spanning Tree Is Found
        index = 0;
        while(numEdges < users.size()-1){
            //Select Smallest Edge And Add It To List
            Relationship currentRelationship = processTree[index++];
            User A = find(userList, currentRelationship.friendA);
            User B = find(userList, currentRelationship.friendB);

            if(A != B){
                returnTree[numEdges++] = currentRelationship;
                Union(userList, A, B);
            }
        }

        return returnTree;
    }

    public User[] getUsersAtDistance(User fromUser, int distance){
        if(fromUser == null) return null;

        ArrayList<User> userList = new ArrayList<User>();
        for(int index = 0; index < users.size(); ++index){
            if(!users.get(index).equals(fromUser)){
                userList.add(users.get(index));
            }
        }

        ArrayList<User> correctDistanceList = new ArrayList<User>();

        while(!userList.isEmpty()){
            User target = userList.get(0);
            userList.remove(0);

            int distanceToTarget = distanceBFS(users, fromUser, target);
            if(distanceToTarget == distance){
                correctDistanceList.add(target);
            }
        }

        User[] returnArray = new User[correctDistanceList.size()];
        for(int index = 0; index < correctDistanceList.size(); ++index){
            returnArray[index] = correctDistanceList.get(index);
        }

        return returnArray;
    }

    //========================Helper Functions==============================//

    /**
     *  Utility function to find set of an user.
     * @param userList (Array Of Users)
     * @param user (User To Find Subset Of)
     * @return Index
     */
    private User find(ArrayList<User> userList, User user){
        if(userList.get(userList.indexOf(user)).parent != user){
            userList.get(userList.indexOf(user)).parent = find(userList, userList.get(userList.indexOf(user)).parent);
        }
        return userList.get(userList.indexOf(user)).parent;
    }

    /**
     * Finds min number of vertices between a passed in root and a destination vertice.
     * @param edges
     * @param root
     * @param destination
     * @param size
     * @return Integer representing number of nodes
     */
    private int distanceBFS(ArrayList<User> users, User root, User destination){
        ArrayList<Boolean> visited = new ArrayList<>();
        for (int i = 0; i < users.size(); i++){
            visited.add(false);
        }
      
        ArrayList<Integer> distances = new ArrayList<Integer>();
        for (int i = 0; i < users.size(); i++){
            distances.add(0);
        }
      
        ArrayList<User> queue = new ArrayList<>();
        distances.set(users.indexOf(root), 0);
      
        queue.add(root);
        visited.set(users.indexOf(root), true);
        
        while (!queue.isEmpty()){
            User current = queue.get(0);
            queue.remove(0);

            //Get All Neigbours Of The Selected User
            ArrayList<User> neighbours = new ArrayList<>(); 
            for(Relationship relationship: current.friends){
                neighbours.add(relationship.getFriend(current));
            }

            for (int index = 0; index < neighbours.size(); index++){
                if(visited.get(users.indexOf(neighbours.get(index)))){
                    Integer newDistance = new Integer(distances.get(users.indexOf(current))).intValue()+1;
                    if(newDistance < distances.get(users.indexOf(neighbours.get(index)))){
                        distances.set(users.indexOf(neighbours.get(index)), newDistance);
                    }
                    continue;
                }
                else{
                    Integer newDistance = new Integer(distances.get(users.indexOf(current))).intValue()+1;
                    distances.set(users.indexOf(neighbours.get(index)), newDistance);
                    queue.add(neighbours.get(index));
                    visited.set(users.indexOf(neighbours.get(index)), true);
                } 
            }   
        }

        return distances.get(users.indexOf(destination));
    }

    /**
     * A function that does union of two sets of A and B.
     * @param userList
     * @param A (User)
     * @param B (User)
     */
    void Union(ArrayList<User> userList, User A, User B){
        User rootA = find(userList, A);  
        User rootB = find(userList, B);

        if(userList.get(userList.indexOf(rootA)).rank < userList.get(userList.indexOf(rootB)).rank){
            userList.get(userList.indexOf(rootA)).parent = rootB;
        }
        else if(userList.get(userList.indexOf(rootA)).rank > userList.get(userList.indexOf(rootB)).rank){
            userList.get(userList.indexOf(rootB)).parent = rootA;
        }
        else{
            userList.get(userList.indexOf(rootB)).parent = rootA;
            userList.get(userList.indexOf(rootA)).rank++;
        }
    }

    /**
     * Takes in a vertice and colour, looks through all of the connected nodes for vertice of colour already exists, if not then update saturation value.
     * @param neighbour
     * @param colour
     */
    private void updateValues(User neighbour, int colour){
        boolean exists = false;
        for(Relationship relationship: neighbour.friends) if(relationship.getFriend(neighbour).colour == colour) exists = true;
        if(exists == false) neighbour.saturationDegree++;
        neighbour.uncoloredDegree--;
    }

    /**
     * Gets the lowest possible colour that can be assigned to user that has the passed in list of neigbours.
     * @param users (ArrayList Of Adjacent Users)
     * @return Integer Representing Colour
     */
    private int getMinColour(ArrayList<User> users){
        int[] numbers = new int[users.size()];
        int maxNumber = Integer.MIN_VALUE;

        for(int i = 0; i < numbers.length; i++){
            numbers[i] = users.get(i).colour;
            if(users.get(i).colour > maxNumber) maxNumber = users.get(i).colour;
        }
        if(maxNumber == -1){
            return 0;
        }
        else{
            int colour = findFirstMissing(numbers);
            return colour;
        }
    }

    /**
     * Finds The Lowest Free Number In ArrayList
     * @param array
     * @param start
     * @param end
     * @return Lowest Number To Fill Gap
     */
    private int findFirstMissing(int array[]){
        int n = array.length;
        int N = 1000010;
 
        boolean[] present = new boolean[N];
        int max = Integer.MIN_VALUE;
 
        for (int i = 0; i < n; i++) {
            if (array[i] >= 0 && array[i] <= n) present[array[i]] = true;
            max = Math.max(max, array[i]);
        }

        for (int i = 0; i < N; i++){
            if (!present[i]) return i;
        }
        return max + 1;
    }

    /**
     * Finds the next user to be processed in a passed in list based on Highest Saturation Degree and Maximum Uncolored Degree
     * @param users
     * @return User
     */
    private User getNextUser(ArrayList<User> users){
        //Variables Needed To Get User
        ArrayList<User> saturation = new ArrayList<>();
        int higestSat = -1;
        User returnUser = null;

        //Get The Highest Saturation Degree in List
        for(User user: users){
            if(user.saturationDegree > higestSat){
                higestSat = user.saturationDegree;
            }
        }

        //Get List Of All Users That Have Saturations Degree TO Find User With Highest Uncoloured Degree In Users
        for(User user: users){
            if(user.saturationDegree == higestSat){
                saturation.add(user);
            }
        }

        returnUser = saturation.get(0);
        //Get User With Max Uncolored Degree To Return
        for(User user: saturation){
            if(user.uncoloredDegree > returnUser.uncoloredDegree){
                returnUser = user;
            }
        }

        return returnUser;
    }

   /**
    * This function takes last element as pivot, places the pivot element at its correct position in sorted array, and places all smaller (smaller than pivot) to left of pivot and all greater elements to right of pivot.
    * @param arr ()
    * @param low ()
    * @param high ()
    */
    private int relationshipPartition(Relationship[] array, int start, int end){
        double pivot = array[end].friendshipValue;
        int pos = start-1;
    
        for(int index = start; index <= end-1; index++){
            if(array[index].friendshipValue < pivot){
                pos++;
                Relationship temp = array[pos];
                array[pos] = array[index];
                array[index] = temp;
            }
        }

        Relationship temp = array[pos + 1];
        array[pos + 1] = array[end];
        array[end] = temp;
        return (pos + 1);
    }

    /**
     * Quickort Helper Function
     * @param array (Array To Be Sorted)
     * @param start (Starting Index)
     * @param end (Ending Index)
     */
    private void relationshipQuickSort(Relationship[] array, int start, int end){
        if(start < end){
            int index = relationshipPartition(array, start, end);
            relationshipQuickSort(array, start, index - 1);
            relationshipQuickSort(array, index + 1, end);
        }
    }

    /**
     * Quicksort Helper Function
     * @param array (Array To Be Sorted)
     * @param start (Starting Index)
     * @param end (Ending Index)
     */
    private void usersQuickSort(User[] array, int start, int end){
        if(start < end){
            int index = usersPartition(array, start, end);
            usersQuickSort(array, start, index - 1);
            usersQuickSort(array, index + 1, end);
        }
    }

    /**
    * This function takes last element as pivot, places the pivot element at its correct position in sorted array, and places all smaller (smaller than pivot) to left of pivot and all greater elements to right of pivot.
    * @param arr ()
    * @param low ()
    * @param high ()
    */
    private int usersPartition(User[] array, int start, int end){
        double pivot = array[end].userID;
        int pos = start-1;
    
        for(int index = start; index <= end-1; index++){
            if(array[index].userID < pivot){
                pos++;
                User temp = array[pos];
                array[pos] = array[index];
                array[index] = temp;
            }
        }

        User temp = array[pos + 1];
        array[pos + 1] = array[end];
        array[end] = temp;
        return (pos + 1);
    }
}
