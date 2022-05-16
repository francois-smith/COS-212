public class TopologicalSort extends Sorting {
    public TopologicalSort(){
    }

    @Override
    public String[] sortAcs(Vertex[] array) throws CycleException {
        String[] returnArray = new String[]{"Dwadwad"};
        return returnArray;
    }

    @Override
    public String[] sortDsc(Vertex[] array) throws CycleException{
        String[] returnArray = new String[]{"Dwadwad"};
        return returnArray;
    }

}

class CycleException extends Exception{
    public String message = "Cycle has been detected";
}
