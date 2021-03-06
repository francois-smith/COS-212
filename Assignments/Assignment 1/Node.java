import java.text.DecimalFormat;

public class Node {

	private int v1; // this is the first variable
	private int v2; // this is the second variable
	public Node left; // this is the node left of this node
	public Node right; // this is the node right of this node
	public Node up; // this is the node up of this node
	public Node down; // this is the node down of this node
	public Node nextVal; // this is the next value of the current node
	public Node prevVal; // this is the prev value of the current node
	private Function nodeFunction; // this is the function associated with the current node

	private String floatFormatter(float value){
		DecimalFormat df = new DecimalFormat("#.##");
		return df.format(value);
	}
	
	//DO NOT CHANGE THE ABOVE FUNCTION
	//Place your code below

	public Node(Function function, int v1, int v2) {
		if(function != null) {
			this.nodeFunction = function.clone();
		}	
		this.v1 = v1;
		this.v2 = v2;
	}

	public Function setFunction(Function function) {
		Function tempFunction = this.nodeFunction;
		this.nodeFunction = function;
		return tempFunction;
	}

	public float getValue() {
		if(nodeFunction == null)
		{
			return Float.NEGATIVE_INFINITY;
		}
		else
		{
			return nodeFunction.calculate(v1, v2);
		}
	}

	public int[] getVariables() {
		int vars[] = new int[2];
		vars[0] = v1;
		vars[1] = v2;
		return vars;
	}

	public Function getFunction(){
		return this.nodeFunction;
	}

	public String[] getNodeLinks(){
		String links[] = new String[6];

		//up
		if(up != null){
			links[0] = "U["+up.v1+"]["+up.v2+"]{"+floatFormatter(up.getValue())+"}";
		}
		else{
			links[0] = "U[][]{}";
		}

		//down
		if(down != null){
			links[1] = "D["+down.v1+"]["+down.v2+"]{"+floatFormatter(down.getValue())+"}";
		}
		else{
			links[1] = "D[][]{}";
		}

		//right
		if(right != null){
			links[2] = "R["+right.v1+"]["+right.v2+"]{"+floatFormatter(right.getValue())+"}";
		}
		else{
			links[2] = "R[][]{}";
		}

		//left
		if(left != null){
			links[3] = "L["+left.v1+"]["+left.v2+"]{"+floatFormatter(left.getValue())+"}";
		}
		else{
			links[3] = "L[][]{}";
		}

		//prev
		if(prevVal != null){
			links[4] = "P["+prevVal.v1+"]["+prevVal.v2+"]{"+floatFormatter(prevVal.getValue())+"}";
		}
		else{
			links[4] = "P[][]{}";
		}

		//next
		if(nextVal != null){
			links[5] = "N["+nextVal.v1+"]["+nextVal.v2+"]{"+floatFormatter(nextVal.getValue())+"}";
		}
		else{
			links[5] = "N[][]{}";
		}

		return links;
	}

}