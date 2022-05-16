public class InsertSort extends Sorting {
    public InsertSort(){
        this.name = "InsertSort";
    }

    @Override
    public String[] sortAcs(Vertex[] array) {
        for(int i = 1; i < array.length; i++){
            Vertex temp = array[i];
            int index = i-1;
            while(index >= 0 && array[index].getVName().compareTo(temp.getVName()) > 0){
                array[index+1] = array[index];
                index--;
            }
            array[index+1] = temp;
            printArr(vertexToString(array));
        }
        return vertexToString(array);
    }

    @Override
    public String[] sortDsc(Vertex[] array) {
        for(int i = 1; i < array.length; i++){
            Vertex temp = array[i];
            int index = i-1;
            while(index >= 0 && array[index].getVName().compareTo(temp.getVName()) < 0){
                array[index+1] = array[index];
                index--;
            }
            array[index+1] = temp;
            printArr(vertexToString(array));
        }
        return vertexToString(array);
    }
}
