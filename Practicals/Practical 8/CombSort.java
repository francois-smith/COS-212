public class CombSort extends Sorting {
    public CombSort(){
        this.name = "CombSort";
    }

    @Override
    public String[] sortAcs(Vertex[] array) {
        int gap = array.length;
        Boolean swapped = true;

        while(gap > 1 || swapped){
            swapped = false;
            if(gap > 1){
                gap = (gap*10)/13;
            }

            for(int i=0; i < array.length-gap; i++){
                if(array[i].getVName().compareTo(array[i+gap].getVName()) > 0){
                    Vertex temp = array[i];
                    array[i] = array[i+gap];
                    array[i+gap] = temp;
                    
                    swapped = true;
                }
            }
            printArr(vertexToString(array));
            System.out.print("Gap: "+gap+"\n");
        }
        return vertexToString(array);
    }

    @Override
    public String[] sortDsc(Vertex[] array) {
        int gap = array.length;
        Boolean swapped = true;

        while(gap > 1 || swapped){
            swapped = false;
            if(gap > 1){
                gap = (gap*10)/13;
            }

            for(int i=0; i < array.length-gap; i++){
                if(array[i].getVName().compareTo(array[i+gap].getVName()) < 0){
                    Vertex temp = array[i];
                    array[i] = array[i+gap];
                    array[i+gap] = temp;
                    
                    swapped = true;
                }
            }
            printArr(vertexToString(array));
            System.out.print("Gap: "+gap+"\n");
        }
        return vertexToString(array);
    }
}
