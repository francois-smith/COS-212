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
		Origin temp = new Origin();
		this.origin = new Node(temp, 0, 0);
	}

	public Interface(Node[] arr) {
		for (Node node : arr) {
			if(node != null){

			}
		}
	}

	public Node getOrigin() {
		return this.origin;
	}

	public float addPoint(Function function, int v1, int v2) {
		Node searchNodeV1 = origin, searchNodeV2 = origin;;
		Node newNode = new Node(function, v1, v2);

		if(v1 != 0 && v2 !=0){
			if(v1 > 0){
				while(searchNodeV1.up != null && searchNodeV1.up.getVariables()[0] < v1){
					searchNodeV1 = searchNodeV1.up;
				}

				if(searchNodeV1.getVariables()[0] == v1){
					
				}
				else if (searchNodeV1.up == null && searchNodeV1.getVariables()[0] != v1){
					V1Axis temp = new V1Axis();
					Node newHighwayNode = new Node(temp, v1, 0);
				
					searchNodeV1.up = newHighwayNode;
					newHighwayNode.down = searchNodeV1;
					searchNodeV1 = newHighwayNode;
				} 
				else if (searchNodeV1.up.getVariables()[0] > v1){
					V1Axis temp = new V1Axis();
					Node newHighwayNode = new Node(temp, v1, 0);

					newHighwayNode.up = searchNodeV1.up;
					searchNodeV1.up.down = newHighwayNode;
					searchNodeV1.up = newHighwayNode;
					newHighwayNode.down = searchNodeV1;

					searchNodeV1 = newHighwayNode;
				}
			} 


			if(v1 < 0){
				while(searchNodeV1.down != null && searchNodeV1.down.getVariables()[1] > v1){
					searchNodeV1 = searchNodeV1.down;
				}

				if(searchNodeV1.getVariables()[0] == v1){
					
				}
				else if (searchNodeV1.down == null && searchNodeV1.getVariables()[0] != v1){
					V1Axis temp = new V1Axis();
					Node newHighwayNode = new Node(temp, v1, 0);
				
					searchNodeV1.down = newHighwayNode;
					newHighwayNode.up = searchNodeV1;
					searchNodeV1 = newHighwayNode;
				} 
				else if (searchNodeV1.up.getVariables()[0] > v1){
					V1Axis temp = new V1Axis();
					Node newHighwayNode = new Node(temp, v1, 0);

					newHighwayNode.down = searchNodeV1.down;
					searchNodeV1.down.up = newHighwayNode;
					searchNodeV1.down = newHighwayNode;
					newHighwayNode.up = searchNodeV1;

					searchNodeV1 = newHighwayNode;
				}
			}

			if(v2 > 0){
				while(searchNodeV2.right != null && searchNodeV2.right.getVariables()[1] < v2){
					searchNodeV2 = searchNodeV2.right;
				}

				if(searchNodeV2.getVariables()[1] == v2){
					
				}
				else if (searchNodeV1.right == null && searchNodeV1.getVariables()[1] != v1){
					V2Axis temp = new V2Axis();
					Node newHighwayNode = new Node(temp, 0, v2);
				
					searchNodeV2.right = newHighwayNode;
					newHighwayNode.left = searchNodeV2;
					searchNodeV2 = newHighwayNode;
				} 
				else if (searchNodeV2.up.getVariables()[1] > v1){
					V2Axis temp = new V2Axis();
					Node newHighwayNode = new Node(temp, 0, v2);

					newHighwayNode.right = searchNodeV2.right;
					searchNodeV2.right.left = newHighwayNode;
					searchNodeV2.right = newHighwayNode;
					newHighwayNode.left = searchNodeV2;

					searchNodeV2 = newHighwayNode;
				}
			}
			else if(v2 < 0){
				while(searchNodeV1.left != null && searchNodeV1.left.getVariables()[1] > v2){
					searchNodeV2 = searchNodeV1.left;
				}

				if(searchNodeV2.getVariables()[1] == v2){
					
				}
				else if (searchNodeV1.left == null && searchNodeV1.getVariables()[1] != v1){
					V2Axis temp = new V2Axis();
					Node newHighwayNode = new Node(temp, 0, v2);
				
					searchNodeV2.left = newHighwayNode;
					newHighwayNode.right = searchNodeV2;
					searchNodeV2 = newHighwayNode;
				} 
				else if (searchNodeV2.up.getVariables()[1] > v1){
					V2Axis temp = new V2Axis();
					Node newHighwayNode = new Node(temp, 0, v2);

					newHighwayNode.left = searchNodeV2.left;
					searchNodeV2.left.right = newHighwayNode;
					searchNodeV2.left = newHighwayNode;
					newHighwayNode.right = searchNodeV2;

					searchNodeV2 = newHighwayNode;
				}
			}
		}
		else{
			return Float.NaN;
		}

		/*
		
			if(v1 == 0){
				if(v2 > 0){
					while(searchNode.up != null && searchNode.up.getVariables()[1] < newNode.getVariables()[1]){
						searchNode = searchNode.up;
					}

					if(searchNode.up.getVariables()[1] == newNode.getVariables()[1])
					{
						if(newNode.getFunction().getFunctionName().compareTo(searchNode.getFunction().getFunctionName()) < 0){
							if(searchNode.left != null){
								newNode.left = searchNode.left;
								newNode.left.right = newNode;
							}
							if(searchNode.right != null){
								newNode.right = searchNode.right;
								newNode.right.left = newNode;
							}
							if(searchNode.up != null){
								newNode.up = searchNode.up;
								newNode.up.down = newNode;
							}
							if(searchNode.down != null){
								newNode.down = searchNode.down;
								newNode.down.up = newNode;
							}

							newNode.prevVal = searchNode;
							searchNode.nextVal = newNode;
						}
						else{
							if(searchNode.prevVal == null){
								searchNode.prevVal = newNode;
								newNode.nextVal = searchNode;
							}
							else {
								Node temp = searchNode;
								while(temp != null && newNode.getFunction().getFunctionName().compareTo(temp.getFunction().getFunctionName()) <= 0){
									temp = temp.prevVal;
								}

								if(temp.prevVal == null){
									temp.prevVal = newNode;
									newNode.nextVal = temp;
								}
								else {	
									newNode.nextVal = temp.nextVal;
									temp.nextVal.prevVal = newNode;
									temp.nextVal = newNode;
									newNode.prevVal = temp;
								}	
							}
						}
					} 
					else
					{
						if(searchNode.up == null){
							searchNode.up = newNode;
							newNode.down = searchNode;
						}
						else{
							searchNode.up.down = newNode;
							newNode.up = searchNode.up;
							newNode.down = searchNode;
							searchNode.up = newNode;
						}
					}
				}
				else if (v2 < 0){

				}
			}
			else if(v2==0){

			} 
			else{

			}
			return newNode.getValue();
		}
		else{
			return Float.NaN;
		}
		*/
	}

	public Node removePoint(int v1, int v2) {
		return null;
	}

	public Node getPoint(int v1, int v2) {
		if(v1 == 0|| v2 == 0){
			return null;
		}
		else {
			Node searchNode = origin;
			Node returnNode = null;

			if(v1 > 0){
				while(searchNode.up != null && searchNode.up.getVariables()[1] < v1){
					searchNode = searchNode.up;
				}
			} 
			else if(v1 < 0){
				while(searchNode.down != null && searchNode.down.getVariables()[1] < v1){
					searchNode = searchNode.down;
				}
			}

			if(v2 > 0){
				while(searchNode.right != null && searchNode.right.getVariables()[1] < v2){
					searchNode = searchNode.right;
				}
			}
			else if(v2 < 0){
				while(searchNode.left != null && searchNode.left.getVariables()[1] < v2){
					searchNode = searchNode.left;
				}
			}

			if(searchNode.getVariables()[0] == v1 && searchNode.getVariables()[1] == v2){
				returnNode = searchNode;
			}

			return returnNode;
		}
	}

	public Node[] toArray() {
	}

	public float calculateValue(Function function, int v1, int v2) {
		if(function == null){
			return Float.NaN;
		}
		else {
			return function.calculate(v1, v2);
		}
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
		origin.up = null;
		origin.down = null;
		origin.left = null;
		origin.right = null;
	}

	//ADD HELPER FUNCTIONS BELOW

}