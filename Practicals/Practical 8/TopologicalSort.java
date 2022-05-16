public class TopologicalSort extends Sorting {
    public TopologicalSort(){
    }

    @Override
    public String[] sortAcs(Vertex[] array) throws CycleException {

    }

    @Override
    public String[] sortDsc(Vertex[] array) throws CycleException{
    }

}

class CycleException extends Exception{
    public String message = "Cycle has been detected";
}
