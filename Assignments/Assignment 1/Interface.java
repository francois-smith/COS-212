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
				int v1 = node.getVariables()[0], v2 = node.getVariables()[1];
				addPoint(node.getFunction(), v1, v2);
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
			if(v2 > 0){
				while(searchNodeV2.up != null && searchNodeV2.up.getVariables()[1] < v2){
					searchNodeV2 = searchNodeV2.up;
				}

				if(searchNodeV2.getVariables()[1] == v2){
					
				}
				else if (searchNodeV2.up == null && searchNodeV2.getVariables()[1] != v2){
					V2Axis temp = new V2Axis();
					Node newHighwayNode = new Node(temp, 0, v2);
				
					searchNodeV2.up = newHighwayNode;
					newHighwayNode.down = searchNodeV2;
					searchNodeV2 = newHighwayNode;
				} 
				else if (searchNodeV2.up.getVariables()[1] > v2){
					V2Axis temp = new V2Axis();
					Node newHighwayNode = new Node(temp, 0, v2);

					newHighwayNode.up = searchNodeV2.up;
					searchNodeV2.up.down = newHighwayNode;
					searchNodeV2.up = newHighwayNode;
					newHighwayNode.down = searchNodeV2;

					searchNodeV2 = newHighwayNode;
				}
			} 


			if(v2 < 0){
				while(searchNodeV2.down != null && searchNodeV2.down.getVariables()[1] > v2){
					searchNodeV2 = searchNodeV2.down;
				}

				if(searchNodeV2.getVariables()[1] == v2){
					
				}
				else if (searchNodeV2.down == null && searchNodeV2.getVariables()[1] != v2){
					V2Axis temp = new V2Axis();
					Node newHighwayNode = new Node(temp, 0, v2);
				
					searchNodeV2.down = newHighwayNode;
					newHighwayNode.up = searchNodeV2;
					searchNodeV2 = newHighwayNode;
				} 
				else if (searchNodeV2.up.getVariables()[1] > v2){
					V2Axis temp = new V2Axis();
					Node newHighwayNode = new Node(temp, 0, v2);

					newHighwayNode.down = searchNodeV2.down;
					searchNodeV2.down.up = newHighwayNode;
					searchNodeV2.down = newHighwayNode;
					newHighwayNode.up = searchNodeV2;

					searchNodeV2 = newHighwayNode;
				}
			}

			if(v1 > 0){
				while(searchNodeV1.right != null && searchNodeV1.right.getVariables()[0] < v1){
					searchNodeV1 = searchNodeV1.right;
				}

				if(searchNodeV1.getVariables()[0] == v1){
					
				}
				else if (searchNodeV1.right == null && searchNodeV1.getVariables()[0] != v1){
					V1Axis temp = new V1Axis();
					Node newHighwayNode = new Node(temp, v1, 0);
				
					searchNodeV1.right = newHighwayNode;
					newHighwayNode.left = searchNodeV1;
					searchNodeV1 = newHighwayNode;
				} 
				else if (searchNodeV1.up.getVariables()[0] > v1){
					V1Axis temp = new V1Axis();
					Node newHighwayNode = new Node(temp, v1, 0);

					newHighwayNode.right = searchNodeV1.right;
					searchNodeV1.right.left = newHighwayNode;
					searchNodeV1.right = newHighwayNode;
					newHighwayNode.left = searchNodeV1;

					searchNodeV1 = newHighwayNode;
				}
			}
			else if(v1 < 0){
				while(searchNodeV1.left != null && searchNodeV1.left.getVariables()[0] > v1){
					searchNodeV1 = searchNodeV1.left;
				}

				if(searchNodeV1.getVariables()[0] == v1){
					
				}
				else if (searchNodeV1.left == null && searchNodeV1.getVariables()[0] != v1){
					V1Axis temp = new V1Axis();
					Node newHighwayNode = new Node(temp, v1, 0);
				
					searchNodeV1.left = newHighwayNode;
					newHighwayNode.right = searchNodeV1;
					searchNodeV1 = newHighwayNode;
				} 
				else if (searchNodeV1.up.getVariables()[0] > v1){
					V1Axis temp = new V1Axis();
					Node newHighwayNode = new Node(temp, v1, 0);

					newHighwayNode.left = searchNodeV1.left;
					searchNodeV1.left.right = newHighwayNode;
					searchNodeV1.left = newHighwayNode;
					newHighwayNode.right = searchNodeV1;

					searchNodeV1 = newHighwayNode;
				}
			}

			if(v1 > 0){
				if(searchNodeV2.right == null){
					searchNodeV2.right = newNode;
					newNode.left = searchNodeV2;
				}
				else if(searchNodeV2.right != null){
					while(searchNodeV2.right != null && searchNodeV2.right.getVariables()[0] < v1){
						searchNodeV2 = searchNodeV2.right;
					}

					if(searchNodeV2.right == null){
						searchNodeV2.right = newNode;
						newNode.left = searchNodeV2;
					}
					else if(searchNodeV2.right.getVariables()[0] > newNode.getVariables()[0]){
						newNode.right = searchNodeV2.right;
						searchNodeV2.right.left = newNode;
						searchNodeV2.right = newNode;
						newNode.left = searchNodeV2;
					}
					else if(searchNodeV2.right.getVariables() == newNode.getVariables()){
						searchNodeV2 = searchNodeV2.right;
						insertIntoExistingPoint(newNode, searchNodeV2);
					}
				}
			}
			else if(v1 < 0){
				if(searchNodeV2.left == null){
					searchNodeV2.left = newNode;
					newNode.right = searchNodeV2;
				}
				else if(searchNodeV2.left != null){
					while(searchNodeV2.left != null && searchNodeV2.left.getVariables()[0] > v1){
						searchNodeV2 = searchNodeV2.left;
					}

					if(searchNodeV2.left == null){
						searchNodeV2.left = newNode;
						newNode.right = searchNodeV2;
					}
					else if(searchNodeV2.left.getVariables()[0] > newNode.getVariables()[0]){
						newNode.left = searchNodeV2.left;
						searchNodeV2.left.right = newNode;
						searchNodeV2.left = newNode;
						newNode.right = searchNodeV2;
					}
					else if(searchNodeV2.left.getVariables() == newNode.getVariables()){
						searchNodeV2 = searchNodeV2.left;
						insertIntoExistingPoint(newNode, searchNodeV2);
					}
				}
			}

			if(v2 > 0){
				if(searchNodeV1.up == null){
					searchNodeV1.up = newNode;
					newNode.down = searchNodeV1;
				}
				else if(searchNodeV1.up != null){
					while(searchNodeV1.up != null && searchNodeV1.up.getVariables()[1] < v2){
						searchNodeV1 = searchNodeV1.up;
					}

					if(searchNodeV1.up == null){
						searchNodeV1.up = newNode;
						newNode.down = searchNodeV1;
					}
					else if(searchNodeV1.up.getVariables()[1] > newNode.getVariables()[1]){
						newNode.up = searchNodeV1.up;
						searchNodeV1.up.down = newNode;
						searchNodeV1.up = newNode;
						newNode.down = searchNodeV1;
					}
					else if(searchNodeV1.up.getVariables() == newNode.getVariables()){
						searchNodeV1 = searchNodeV1.up;
						insertIntoExistingPoint(newNode, searchNodeV1);
					}
				}
			}
			else if(v2 < 0){
				if(searchNodeV1.down == null){
					searchNodeV1.down = newNode;
					newNode.up = searchNodeV1;
				}
				else if(searchNodeV1.down != null){
					while(searchNodeV1.down != null && searchNodeV1.down.getVariables()[1] > v2){
						searchNodeV1 = searchNodeV1.down;
					}

					if(searchNodeV1.down == null){
						searchNodeV1.down = newNode;
						newNode.up = searchNodeV1;
					}
					else if(searchNodeV1.down.getVariables()[1] > newNode.getVariables()[1]){
						newNode.down = searchNodeV1.down;
						searchNodeV1.down.up = newNode;
						searchNodeV1.down = newNode;
						newNode.up = searchNodeV1;
					}
					else if(searchNodeV1.down.getVariables() == newNode.getVariables()){
						searchNodeV1 = searchNodeV1.up;
						insertIntoExistingPoint(newNode, searchNodeV1);
					}
				}
			}

			return newNode.getValue();
		}
		else{
			return Float.NaN;
		}
	}

	public Node removePoint(int v1, int v2) {
		if(v1 == 0|| v2 == 0){
			return null;
		}
		else {
			Node searchNodeV1 = origin, searchNodeV2 = origin;
			Node returnNode = null;


			if(v1 > 0){
				while(searchNodeV1.up != null && searchNodeV1.up.getVariables()[0] < v1){
					searchNodeV1 = searchNodeV1.up;
				}
			} 
			else if(v1 < 0){
				while(searchNodeV1.down != null && searchNodeV1.down.getVariables()[0] < v1){
					searchNodeV1 = searchNodeV1.down;
				}
			}

			if(v2 > 0){
				while(searchNodeV2.right != null && searchNodeV2.right.getVariables()[1] < v2){
					searchNodeV2 = searchNodeV2.right;
				}
			}
			else if(v2 < 0){
				while(searchNodeV2.left != null && searchNodeV2.left.getVariables()[1] < v2){
					searchNodeV2 = searchNodeV2.left;
				}
			}
			
			if(searchNodeV2 != null && searchNodeV1 != null){
				if(v1 > 0){
					
				} 
				else if(v1 < 0){
					
				}
	
				if(v2 > 0){
					
				}
				else if(v2 < 0){
					
				}
			}

			return returnNode;
		}
	}

	public Node getPoint(int v1, int v2) {
		if(v1 == 0 || v2 == 0){
			return null;
		}
		else {
			Node searchNode = origin;
			Node returnNode = null;

			if(v2 > 0){
				while(searchNode.up != null && searchNode.up.getVariables()[1] <= v2){
					searchNode = searchNode.up;
				}
			} 
			else if(v2 < 0){
				while(searchNode.down != null && searchNode.down.getVariables()[1] >= v2){
					searchNode = searchNode.down;
				}
			}

			if(v1 > 0){
				while(searchNode.right != null && searchNode.right.getVariables()[0] <= v1){
					searchNode = searchNode.right;
				}
			}
			else if(v1 < 0){
				while(searchNode.left != null && searchNode.left.getVariables()[0] >= v1){
					searchNode = searchNode.left;
				}
			}

			if(searchNode != null){
				
				if(searchNode.getVariables()[0] == v1 && searchNode.getVariables()[1] == v2){
					returnNode = searchNode;
				}
			}

			return returnNode;
		}
	}

	public Node[] toArray() {
		return null;
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
		return Float.NaN;
	}

	public Node findMax() {
		return null;
	}

	public float findMinValue() {
		return Float.NaN;
	}

	public Node findMin() {
		return null;
	}

	public String printFunctionValues(String functionName) {
		return "";
	}

	public int removeAllFunctionPoints(String functionName){
		return 0;
	}

	public int countNumberOfPoints(){
		return 0;
	}

	public int[] numPointsPerQuadrant(){
		return null;
	}

	public void clearAllData(){
		origin.up = null;
		origin.down = null;
		origin.left = null;
		origin.right = null;
	}

	//ADD HELPER FUNCTIONS BELOW

	public void insertIntoExistingPoint(Node newNode, Node oldNode){
		if(newNode.getFunction().getFunctionName().compareTo(oldNode.getFunction().getFunctionName()) < 0){
			if(oldNode.left != null){
				newNode.left = oldNode.left;
				newNode.left.right = newNode;
				oldNode.left = null;
			}
			if(oldNode.right != null){
				newNode.right = oldNode.right;
				newNode.right.left = newNode;
				oldNode.right = null;
			}
			if(oldNode.up != null){
				newNode.up = oldNode.up;
				newNode.up.down = newNode;
				oldNode.up = null;
			}
			if(oldNode.down != null){
				newNode.down = oldNode.down;
				newNode.down.up = newNode;
				oldNode.down = null;
			}

			newNode.prevVal = oldNode;
			newNode.nextVal = null;
			oldNode.nextVal = newNode;
		}
		else{
			if(oldNode.prevVal == null){
				oldNode.prevVal = newNode;
				newNode.nextVal = oldNode;
			}
			else {
				Node temp = oldNode;
				while(temp != null && newNode.getFunction().getFunctionName().compareTo(temp.getFunction().getFunctionName()) < 0){
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

}