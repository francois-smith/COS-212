import java.text.DecimalFormat;

public class Interface {

	private Node origin;

	private String floatFormatter(float value){
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(value);
	}

	//DO NOT CHANGE THE ABOVE FUNCTION
	//Place your code below

	public Interface() {
	}

	public Interface(Node[] arr) {
	}

	public Node getOrigin() {
	}

	public float addPoint(Function function, int v1, int v2) {
	}

	public Node removePoint(int v1, int v2) {
	}

	public Node getPoint(int v1, int v2) {
	}

	public Node[] toArray() {
	}

	public float calculateValue(Function function, int v1, int v2) {
	}

	public float findMaxValue() {
	}

	public Node findMax() {
	}

	public float findMinValue() {
	}

	public Node findMin() {
	}

	public String printFunctionValues(String functionName) {
	}

	public int removeAllFunctionPoints(String functionName){
	}

	public int countNumberOfPoints(){
	}

	public int[] numPointsPerQuadrant(){
	}

	public void clearAllData(){
	}

	//ADD HELPER FUNCTIONS BELOW
}