public class TopologicalSort extends Sorting {
    Integer i = 0, j = 0;

    public TopologicalSort(){
        this.name = "TopologicalSort";
    }

    @Override
    public String[] sortAcs(Vertex[] array) throws CycleException {
        try{
            for(int i = 0; i < array.length; i++){
                array[i].setNum(0);
                array[i].setTSNum(0);
            }
    
            i = 1;
            j = array.length;
    
            for(int i = 0; i < array.length; i++){
                if(array[i].getTSNum() == 0){
                    tsSort(array[i]);
                }
            }

            for(int i = 0; i < array.length - 1; i++){
                for(int j = 0; j < array.length - i - 1; j++){
                    if(array[j].getTSNum() > array[j+1].getTSNum()){
                        Vertex temp = array[j];
                        array[j] = array[j+1];
                        array[j+1] = temp;
                    }
                }
            }
    
            return vertexToString(array);
        }
        catch(Exception e){
            throw e;
        }
    }

    @Override
    public String[] sortDsc(Vertex[] array) throws CycleException{
        try{
            for(int i = 0; i < array.length; i++){
                array[i].setNum(0);
                array[i].setTSNum(0);
            }
    
            i = 1;
            j = array.length;
    
            for(int i = 0; i < array.length; i++){
                if(array[i].getTSNum() == 0){
                    tsSort(array[i]);
                }
            }

            for(int i = 0; i < array.length - 1; i++){
                for(int j = 0; j < array.length - i - 1; j++){
                    if(array[j].getTSNum() < array[j+1].getTSNum()){
                        Vertex temp = array[j];
                        array[j] = array[j+1];
                        array[j+1] = temp;
                    }
                }
            }
    
            return vertexToString(array);
        }
        catch(Exception e){
            throw e;
        }
    }

    private void tsSort(Vertex v) throws CycleException{
        v.setNum(i++);

        Edge[] edges = v.getEdges();
        for(int k = 0; k < edges.length; k++){
            Edge edge = edges[k];
            Vertex adjacent = getVertex(edge, v);
            if(adjacent.getNum() == 0){
                tsSort(adjacent);
            }
            else if(adjacent.getTSNum() == 0){
                throw new CycleException();
            }
        }
        v.setTSNum(j--);
    }

    private Vertex getVertex(Edge edge, Vertex v){
        if(edge.pointA.equals(v)){
            return edge.pointB;
        }
        else{
            return edge.pointA;
        }
    }
}

class CycleException extends Exception{
    public String message = "Cycle has been detected";
}
