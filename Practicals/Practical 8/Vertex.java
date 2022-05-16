public class Vertex {
    private String vName;
    private Edge[] edges;
    private int Num = 0;
    private int TSNum = 0;

    public Vertex(String vName, int numedges){
        this.vName = vName;
        edges = new Edge[numedges];
    }

    public String getVName(){
        return vName;
    }

    public Edge[] getEdges(){
        return edges;
    }

    public boolean addEdge(Edge e){
        if(e == null)
            return false;

        for(int i=0; i < edges.length; i++)
        {
            if(edges[i] == null)
            {
                edges[i] = e;
                return true;
            }
        }
        return false;
    }

    //Do not change the above functions
    //Implement the functions below 

    public void setNum(int value){
        this.Num = value;
    }

    public int getNum(){
        return this.Num;
    }   

    public void setTSNum(int value){
        this.TSNum = value;
    }

    public int getTSNum(){
        return this.TSNum;
    }
}
