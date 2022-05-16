public class SelectionSort extends Sorting {
    
    public SelectionSort(){
        this.name = "SelectionSort";
    }

    @Override
    public String[] sortAcs(Vertex[] array) {
        for(int i = 0; i <= array.length - 1; i++){
            int id = i;
            for(int j = i; j < array.length; j++){
                if(array[j].getVName().compareTo(array[id].getVName()) < 0){
                    id = j;
                }
            }

            Vertex temp = array[id];
            array[id] = array[i];
            array[i] = temp;
            printArr(vertexToString(array));
        }
        return vertexToString(array);
    }

    @Override
    public String[] sortDsc(Vertex[] array) {
        for(int i = 0; i <= array.length - 1; i++){
            int id = i;
            for(int j = i; j < array.length; j++){
                if(array[j].getVName().compareTo(array[id].getVName()) > 0){
                    id = j;
                }
            }

            Vertex temp = array[id];
            array[id] = array[i];
            array[i] = temp;
            printArr(vertexToString(array));
        }
        return vertexToString(array);
    }
}
