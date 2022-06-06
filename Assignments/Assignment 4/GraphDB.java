import java.util.ArrayList;

public class GraphDB {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Integer> colours = new ArrayList<>();

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
        else return frientee.addFriend(friended, relationshipValue);
    }

    public User[][] clusterUsers(){
        //Loop Through All Users In Graph And Set Variables To Default Values
        for(User user : users){
            user.saturationDegree = 0;
            user.uncoloredDegree = user.friends.size();
            user.colour = -1;
        }

        //Make a Copy Of All Vertices In Graph To Process
        ArrayList<User> unprocessed = new ArrayList<>();
        for(int i = 0; i < users.size(); i++){
            unprocessed.add(users.get(i));
        }

        //While There Exists a User That Has Not Been Processed
        while(!unprocessed.isEmpty()){
            User user = getNextUser(unprocessed);
            unprocessed.remove(user);

            ArrayList<User> neighbours = new ArrayList<>(); 
            for(Relationship relationship: user.friends){
                neighbours.add(relationship.getFriend(user));
            }

            int colour = getMinColour(neighbours);
            System.out.println(user+" has neighbours: "+neighbours);
        }
        return null;
    }

    public Relationship[] minSpanningTree(){
        return null;
    }

    public User[] getUsersAtDistance(User fromUser, int distance){
        return null;
    }

    //========================Helper Functions==============================//

    /**
     * Gets the lowest possible colour that can be assigned to user that has the passed in list of neigbours.
     * @param users (ArrayList Of Adjacent Users)
     * @return Integer Representing Colour
     */
    private int getMinColour(ArrayList<User> users){
        int minColor = 0;
        int[] numbers = new int[users.size()];

        for(int i = 0; i < numbers.length; i++){
            numbers[i] = users.get(i).colour;
        }

        for(int i = 0; i < numbers.length; i++){
            System.out.println(numbers[i]);
        }
        
        return 0;
    }

    /**
     * Finds The Lowest Free Number In ArrayList
     * @param array
     * @param start
     * @param end
     * @return Lowest Number To Fill Gap
     */
    int findFirstMissing(int array[], int start, int end){
        if (start > end){
            return end + 1;
        }
        if (start != array[start]){
            return start;
        }

 
        int mid = (start + end) / 2;
 
        // Left half has all elements from 0 to mid
        if (array[mid] == mid)
            return findFirstMissing(array, mid+1, end);
 
        return findFirstMissing(array, start, mid);
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
}
