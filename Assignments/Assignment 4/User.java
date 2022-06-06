import java.util.ArrayList;

public class User {
    String userName;
    int userID;
    ArrayList<Relationship> friends = new ArrayList<>();

    //Custom Variables
    int saturationDegree = 0;
    int uncoloredDegree = 0;
    int colour = 0;

    @Override
    public String toString() {
        return userName + "[" + userID + "]";
    }
    
    public User(String userName, int userID){
        this.userID = userID;
        this.userName = userName;
    }

    public Relationship[] getFriends(){
        return friends.toArray(new Relationship[0]);
    }

    public Relationship addFriend(User friend, double friendshipValue){
        //If Null Is Passed In Then Return
        if(friend == null) return null;

        //Create A New RelationShip Between The Passed In Friend And Current Node
        Relationship relationship = new Relationship(this, friend, friendshipValue);

        //See If RelationShip Exists In The Friend List, If So Return It
        for(int i = 0; i < friends.size(); i++){
            if(friends.get(i).equals(relationship)){
                return friends.get(i);
            }
        } 

        //If RelationShip Was Not Found In List Add The New One To Current Node And Friend Node List
        addFriend(relationship);
        friend.addFriend(relationship);
        return relationship;
    }

    //=================Helper Functions=====================//

    public void addFriend(Relationship relationship){
        this.friends.add(relationship);
    }
}
