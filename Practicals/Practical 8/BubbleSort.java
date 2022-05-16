public class BubbleSort extends Sorting {
    public BubbleSort(){
        this.name = "BubbleSort";
    }

    @Override
    public String[] sortAcs(Vertex[] array){        
        for(int i = 0; i < array.length - 1; i++){
            for(int j = 0; j < array.length - i - 1; j++){
                if(array[j].getVName().compareTo(array[j+1].getVName()) > 0){
                    Vertex temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
                
            }
            printArr(vertexToString(array));
        }
        return vertexToString(array);
    }

    @Override
    public String[] sortDsc(Vertex[] array) {
        for(int i = 0; i < array.length - 1; i++){
            for(int j = 0; j < array.length - i - 1; j++){
                if(array[j].getVName().compareTo(array[j+1].getVName()) < 0){
                    Vertex temp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = temp;
                }
                
            }
            printArr(vertexToString(array));
        }
        return vertexToString(array);
    }
}
