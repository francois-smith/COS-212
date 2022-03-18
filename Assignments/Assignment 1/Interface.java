import java.text.DecimalFormat;

public class Interface {

	private Node origin;
	public int numNodes = 0, counter = 0;

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
		Origin temp = new Origin();
		this.origin = new Node(temp, 0, 0);
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
		Node searchNodeV1 = origin, searchNodeV2 = origin;
		Node newNode = new Node(function, v1, v2);
		boolean alreadyInserted = false;

		if(v1 != 0 && v2 !=0){
			if(v2 > 0){
				if(origin.up != null){
					while(searchNodeV2.up != null && searchNodeV2.up.getVariables()[1] <= v2){
						searchNodeV2 = searchNodeV2.up;
					}
	
					if(searchNodeV2.getVariables()[1] == v2){

					}
					else if (searchNodeV2.up == null){
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
				else{
					V2Axis temp = new V2Axis();
					Node newHighwayNode = new Node(temp, 0, v2);
				
					searchNodeV2.up = newHighwayNode;
					newHighwayNode.down = searchNodeV2;
					searchNodeV2 = newHighwayNode;
				}
				
			} 
			else if(v2 < 0){
				if(origin.down != null){
					while(searchNodeV2.down != null && searchNodeV2.down.getVariables()[1] >= v2){
						searchNodeV2 = searchNodeV2.down;
					}	

					if(searchNodeV2.getVariables()[1] == v2){
						
					}
					else if (searchNodeV2.down == null){
						V2Axis temp = new V2Axis();
						Node newHighwayNode = new Node(temp, 0, v2);	

						searchNodeV2.down = newHighwayNode;
						newHighwayNode.up = searchNodeV2;
						searchNodeV2 = newHighwayNode;
					} 
					else if (searchNodeV2.down.getVariables()[1] < v2){
						V2Axis temp = new V2Axis();
						Node newHighwayNode = new Node(temp, 0, v2);

						newHighwayNode.down = searchNodeV2.down;
						searchNodeV2.down.up = newHighwayNode;
						searchNodeV2.down = newHighwayNode;
						newHighwayNode.up = searchNodeV2;

						searchNodeV2 = newHighwayNode;
					}
				}
				else{
					V2Axis temp = new V2Axis();
					Node newHighwayNode = new Node(temp, 0, v2);

					searchNodeV2.down = newHighwayNode;
					newHighwayNode.up = searchNodeV2;
					searchNodeV2 = newHighwayNode;
				}
			}

			if(v1 > 0){
				if(origin.right != null){
					while(searchNodeV1.right != null && searchNodeV1.right.getVariables()[0] <= v1){
						searchNodeV1 = searchNodeV1.right;
					}	

					if(searchNodeV1.getVariables()[0] == v1){
						
					}
					else if (searchNodeV1.right == null){
						V1Axis temp = new V1Axis();
						Node newHighwayNode = new Node(temp, v1, 0);
					
						searchNodeV1.right = newHighwayNode;
						newHighwayNode.left = searchNodeV1;
						searchNodeV1 = newHighwayNode;
					} 
					else if (searchNodeV1.right.getVariables()[0] > v1){
						V1Axis temp = new V1Axis();
						Node newHighwayNode = new Node(temp, v1, 0);

						newHighwayNode.right = searchNodeV1.right;
						searchNodeV1.right.left = newHighwayNode;
						searchNodeV1.right = newHighwayNode;
						newHighwayNode.left = searchNodeV1;

						searchNodeV1 = newHighwayNode;
					}
				}
				else {
					V1Axis temp = new V1Axis();
					Node newHighwayNode = new Node(temp, v1, 0);
				
					searchNodeV1.right = newHighwayNode;
					newHighwayNode.left = searchNodeV1;
					searchNodeV1 = newHighwayNode;
				}
			}
			else if(v1 < 0){
				if(origin.left != null){
					while(searchNodeV1.left != null && searchNodeV1.left.getVariables()[0] >= v1){
						searchNodeV1 = searchNodeV1.left;
					}


					if(searchNodeV1.getVariables()[0] == v1){
						
					}
					else if (searchNodeV1.left == null){
						V1Axis temp = new V1Axis();
						Node newHighwayNode = new Node(temp, v1, 0);
					
						searchNodeV1.left = newHighwayNode;
						newHighwayNode.right = searchNodeV1;
						searchNodeV1 = newHighwayNode;
					} 
					else if (searchNodeV1.left.getVariables()[0] < v1){
						V1Axis temp = new V1Axis();
						Node newHighwayNode = new Node(temp, v1, 0);

						newHighwayNode.left = searchNodeV1.left;
						searchNodeV1.left.right = newHighwayNode;
						searchNodeV1.left = newHighwayNode;
						newHighwayNode.right = searchNodeV1;

						searchNodeV1 = newHighwayNode;
					}
				}
				else {
					V1Axis temp = new V1Axis();
					Node newHighwayNode = new Node(temp, v1, 0);
				
					searchNodeV1.left = newHighwayNode;
					newHighwayNode.right = searchNodeV1;
					searchNodeV1 = newHighwayNode;	
				}
			}

//==============================================================Linking new node===============================================================
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
					else if(searchNodeV2.right.getVariables()[0] == newNode.getVariables()[0] && searchNodeV2.right.getVariables()[1] == newNode.getVariables()[1]){
						searchNodeV2 = searchNodeV2.right;
						insertIntoExistingPoint(newNode, searchNodeV2);
						alreadyInserted = true;
					}
				}
			}

			if(v1 < 0){
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
					else if(searchNodeV2.left.getVariables()[0] < newNode.getVariables()[0]){
						
						newNode.left = searchNodeV2.left;
						searchNodeV2.left.right = newNode;
						searchNodeV2.left = newNode;
						newNode.right = searchNodeV2;
					}
					else if(searchNodeV2.left.getVariables()[0] == newNode.getVariables()[0] && searchNodeV2.left.getVariables()[1] == newNode.getVariables()[1]){
						searchNodeV2 = searchNodeV2.left;
						insertIntoExistingPoint(newNode, searchNodeV2);
						alreadyInserted = true;
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
					else if(searchNodeV1.up.getVariables()[0] == newNode.getVariables()[0] && searchNodeV1.up.getVariables()[1] == newNode.getVariables()[1]){
						if(!alreadyInserted){
							searchNodeV1 = searchNodeV1.up;
							insertIntoExistingPoint(newNode, searchNodeV1);
						}	
					}
				}
			}

			if(v2 < 0){
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
					else if(searchNodeV1.down.getVariables()[1] < newNode.getVariables()[1]){
						newNode.down = searchNodeV1.down;
						searchNodeV1.down.up = newNode;
						searchNodeV1.down = newNode;
						newNode.up = searchNodeV1;
					}
					else if(searchNodeV1.down.getVariables()[0] == newNode.getVariables()[0] && searchNodeV1.down.getVariables()[1] == newNode.getVariables()[1]){
						if(!alreadyInserted){
							searchNodeV1 = searchNodeV1.down;
							insertIntoExistingPoint(newNode, searchNodeV1);
						}
					}
				}
			}

			numNodes++;
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

			if(v2 > 0){
				while(searchNodeV2.up != null && searchNodeV2.up.getVariables()[1] <= v2){
					searchNodeV2 = searchNodeV2.up;
				}
			} 
			else if(v2 < 0){
				while(searchNodeV2.down != null && searchNodeV2.down.getVariables()[1] >= v2){
					searchNodeV2 = searchNodeV2.down;
				}
			}

			if(v1 > 0){
				while(searchNodeV1.right != null && searchNodeV1.right.getVariables()[0] <= v1){
					searchNodeV1 = searchNodeV1.right;
				}
			}
			else if(v1 < 0){
				while(searchNodeV1.left != null && searchNodeV1.left.getVariables()[0] >= v1){
					searchNodeV1 = searchNodeV1.left;
				}
			}

			if(searchNodeV2 != null && searchNodeV1 != null){
				Node deleteNode = null;

				if(v1 > 0){
					if(searchNodeV2.right.getVariables()[0] == v1){
						deleteNode = searchNodeV2.right;
						if(deleteNode.prevVal != null){
							swapPrevNode(deleteNode, deleteNode.prevVal);
						}
						else{
							if(deleteNode.right != null){
								deleteNode.left.right = deleteNode.right;
								deleteNode.right.left = deleteNode.left;
							} 
							else {
								clearNodeHorizontal(deleteNode);
							}

							if(v2 > 0){
								if(deleteNode.up != null){
									deleteNode.up.down = deleteNode.down;
									deleteNode.down.up = deleteNode.up;
								} 
								else {
									clearNodeVertical(deleteNode);
								}
							}
							else if(v2 < 0){
								if(deleteNode.down != null){
									deleteNode.down.up = deleteNode.up;
									deleteNode.up.down = deleteNode.down;
								} 
								else {
									clearNodeVertical(deleteNode);
								}
							}
						}
					}
					else if(searchNodeV2.right.getVariables()[0] < v1){
						Node temp = searchNodeV2;
						while(temp.right != null && temp.right.getVariables()[0] < v1){
							temp = temp.right;
						}
	
						if(temp.right.getVariables()[0] == v1){
							deleteNode = searchNodeV2.right;
							if(deleteNode.prevVal != null){
								swapPrevNode(deleteNode, deleteNode.prevVal);
							}
							else{
								if(deleteNode.right != null){
									deleteNode.left.right = deleteNode.right;
									deleteNode.right.left = deleteNode.left;
								} 
								else {
									clearNodeHorizontal(deleteNode);
								}

								if(v2 > 0){
									if(deleteNode.up != null){
										deleteNode.up.down = deleteNode.down;
										deleteNode.down.up = deleteNode.up;
									} 
									else {
										clearNodeVertical(deleteNode);
									}
								}
								else if(v2 < 0){
									if(deleteNode.down != null){
										deleteNode.down.up = deleteNode.up;
										deleteNode.up.down = deleteNode.down;
									} 
									else {
										clearNodeVertical(deleteNode);
									}
								}
							}
						}
					}
				}
				else if(v1 < 0){
					if(searchNodeV2.left.getVariables()[0] == v1){
						deleteNode = searchNodeV2.left;
						if(deleteNode.prevVal != null){
							swapPrevNode(deleteNode, deleteNode.prevVal);
						}
						else{
							if(deleteNode.left != null){
								deleteNode.left.right = deleteNode.right;
								deleteNode.right.left = deleteNode.left;
							}
							else {
								clearNodeHorizontal(deleteNode);
							}

							if(v2 > 0){
								if(deleteNode.up != null){
									deleteNode.up.down = deleteNode.down;
									deleteNode.down.up = deleteNode.up;
								} 
							}
							else if(v2 < 0){
								if(deleteNode.down != null){
									deleteNode.down.up = deleteNode.up;
									deleteNode.up.down = deleteNode.down;
								} 
							}
						}
					}
					else if(searchNodeV2.left.getVariables()[0] > v1){
						Node temp = searchNodeV2;
						while(temp.left != null && temp.left.getVariables()[0] > v1){
							temp = temp.left;
						}
	
						if(temp.left.getVariables()[0] == v1){
							deleteNode = searchNodeV2.left;
							if(deleteNode.prevVal != null){
								swapPrevNode(deleteNode, deleteNode.prevVal);
							}
							else{
								if(deleteNode.left != null){
									deleteNode.right.left = deleteNode.left;
									deleteNode.left.right = deleteNode.right;
								} 
								else {
									clearNodeHorizontal(deleteNode);
								}
								if(v2 > 0){
									if(deleteNode.up != null){
										deleteNode.up.down = deleteNode.down;
										deleteNode.down.up = deleteNode.up;
									} 
								}
								else if(v2 < 0){
									if(deleteNode.down != null){
										deleteNode.down.up = deleteNode.up;
										deleteNode.up.down = deleteNode.down;
									} 
								}
							}
						}
					}
				}	

				if(searchNodeV1.left == null && searchNodeV1.right == null){
					if(v2 > 0){
						if(searchNodeV1.up == null){
							searchNodeV1.down.up = null;
							searchNodeV1.down = null;
							searchNodeV1 = null;
						}
						else {
							searchNodeV1.down.up = searchNodeV1.up;
							searchNodeV1.up.down = searchNodeV1.down;
							searchNodeV1 = null;
						}
					}
					else if(v2 < 0){
						if(searchNodeV1.down == null){
							searchNodeV1.up.down = null;
							searchNodeV1.up = null;
							searchNodeV1 = null;
						}
						else {
							searchNodeV1.up.down = searchNodeV1.down;
							searchNodeV1.down.up = searchNodeV1.up;
							searchNodeV1 = null;
						}
					}
				}

				if(searchNodeV2.up == null && searchNodeV2.down == null){
					if(v1 > 0){
						if(searchNodeV2.right == null){
							searchNodeV2.left.right = null;
							searchNodeV2.left = null;
							searchNodeV2 = null;
						}
						else {
							searchNodeV2.right.left = searchNodeV2.left;
							searchNodeV2.left.right = searchNodeV2.right;
							searchNodeV2 = null;
						}
					}
					else if(v1 < 0){
						if(searchNodeV2.left == null){
							searchNodeV2.right.left = null;
							searchNodeV2.right = null;
							searchNodeV2 = null;
						}
						else {
							searchNodeV2.left.right = searchNodeV2.right;
							searchNodeV2.right.left = searchNodeV2.left;
							searchNodeV2 = null;
						}
					}
				}
				
				numNodes--;
				return deleteNode;
			}

			return null;
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
		if(numNodes == 0){
			return null;
		}
		Node[] returnArray =  new Node[numNodes];
		Node searchNode = origin;
		counter = 0;

		while(searchNode.right != null){
			searchNode = searchNode.right;
		}

		while(searchNode != null){
			if(searchNode.getVariables()[0] == 0){
				searchNode = searchNode.left;
			}
			else{
				Node verticalSearch = searchNode;
				
				while(verticalSearch.up != null){
					verticalSearch = verticalSearch.up;
				}

				while(verticalSearch != null){	
					if(verticalSearch.getVariables()[1] == 0){
						verticalSearch = verticalSearch.down;
					}
					else{
						if(verticalSearch.prevVal == null){
							returnArray[counter] = verticalSearch;
							counter++;
							verticalSearch = verticalSearch.down;
						}
						else {
							Node temp = verticalSearch;
							while(temp != null)
							{	
								returnArray[counter] = temp;
								counter++;
								temp = temp.prevVal;
							}
							verticalSearch = verticalSearch.down;
						}
					}
					
				}
				searchNode = searchNode.left;
			}
		}

		return returnArray;
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
		if(numNodes == 0)
		{
			return Float.NaN;
		}
		else{
			float maxValue = -999999999;
			for (Node node : toArray()) {
				if(node.getValue() > maxValue){
					maxValue = node.getValue();
				}
			}
			return maxValue;
		}
	}

	public Node findMax() {
		if(numNodes == 0)
		{
			return null;
		}
		else{
			Node returnNode = null;

			float high = toArray()[0].getValue();
			for (Node node : toArray()){
				if (node.getValue() > high){
					high = node.getValue();
				}
			}
				
			Node[] highestValArray = new Node[numNodes];
			int i = 0;
			for (Node node : toArray()){
				if(node.getValue() == high){
					highestValArray[i] = node;
					i++;
				}
			}	

			int minV1 = 99999999;
			for (Node node : highestValArray){
				if(node != null){
					if(node.getVariables()[0] < minV1){
						minV1 = node.getVariables()[0];
					}
				}
			}

			Node[] minV1Array = new Node[i];
			i = 0;
			for (Node node : highestValArray) {
				if(node != null){
					if(node.getVariables()[0] == minV1){
						minV1Array[i] = node;
						i++;
					}
				}
			}

			int maxV2 = -999999999;
			for (Node node : minV1Array) {
				if(node != null){
					if(node.getVariables()[1] > maxV2){
						maxV2 = node.getVariables()[1];
					}
				}
			}

			Node[] maxV2Array = new Node[i];
			i = 0;
			for (Node node : minV1Array) {
				if(node != null){
					if(node.getVariables()[1] == maxV2){
						maxV2Array[i] = node;
						i++;
					}
				}
			}

			returnNode = maxV2Array[0];
			for (Node node : maxV2Array) {
				if(node != null){
					if(node.getFunction().getFunctionName().compareTo(returnNode.getFunction().getFunctionName()) < 0){
						returnNode = node;
					}
				}
			}

			return returnNode;
		}
	}

	public float findMinValue() {
		if(numNodes == 0)
		{
			return Float.NaN;
		}
		else{
			float minValue = 999999999;
			for (Node node : toArray()) {
				if(node.getValue() > minValue){
					minValue = node.getValue();
				}
			}
			return minValue;
		}
	}

	public Node findMin() {
		if(numNodes == 0)
		{
			return null;
		}
		else{
			Node returnNode = null;

			float min = toArray()[0].getValue();
			for (Node node : toArray()){
				if (node.getValue() < min){
					min = node.getValue();	
				}
			}
				
			Node[] minValArray = new Node[numNodes];
			int i = 0;
			for (Node node : toArray()){
				if(node.getValue() == min){
					minValArray[i] = node;
					i++;
				}
			}	

			int minV1 = 999999999;
			for (Node node : minValArray){
				if(node != null){
					if(node.getVariables()[0] < minV1){
						minV1 = node.getVariables()[0];
					}
				}
			}

			Node[] minV1Array = new Node[i];
			i = 0;
			for (Node node : minValArray) {
				if(node != null){
					if(node.getVariables()[0] == minV1){
						minV1Array[i] = node;
						i++;
					}
				}
			}

			int maxV2 = -99999999;
			for (Node node : minV1Array) {
				if(node != null){
					if(node.getVariables()[1] > maxV2){
						maxV2 = node.getVariables()[1];
					}
				}
			}


			Node[] maxV2Array = new Node[i];
			i = 0;
			for (Node node : minV1Array) {
				if(node != null){
					if(node.getVariables()[1] == maxV2){
						maxV2Array[i] = node;
						i++;
					}
				}
			}

			returnNode = maxV2Array[0];
			for (Node node : maxV2Array) {
				if(node != null){
					if(node.getFunction().getFunctionName().compareTo(returnNode.getFunction().getFunctionName()) < 0){
						returnNode = node;
					}
				}
			}

			return returnNode;
		}
	}

	public String printFunctionValues(String functionName) {
		String returnString = "";
		Node searchNode = origin;

		while(searchNode.left != null){
			searchNode = searchNode.left;
		}

		while(searchNode != null){
			if(searchNode.getVariables()[0] == 0){
				searchNode = searchNode.right;
			}
			else{
				Node verticalSearch = searchNode;
				while(verticalSearch.down != null){
					verticalSearch = verticalSearch.down;
				}

				while(verticalSearch != null){	
					if(verticalSearch.getVariables()[1] == 0){
						verticalSearch = verticalSearch.up;
					}
					else{
						if(verticalSearch.prevVal == null){
							if(verticalSearch.getFunction().getFunctionName().equals(functionName)){
								returnString += floatFormatter(verticalSearch.getValue()) + ";";
							}
							verticalSearch = verticalSearch.up;
						}
						else {
							Node temp = verticalSearch;
							while(temp != null)
							{	
								if(temp.getFunction().getFunctionName().equals(functionName)){
									returnString += floatFormatter(temp.getValue()) + ";";
								}
								temp = temp.prevVal;
							}
							verticalSearch = verticalSearch.up;
						}
					}
				}
				searchNode = searchNode.right;
			}
		}

		if(returnString != ""){
			returnString = returnString.substring(0, returnString.length() - 1);
		}
		
		return returnString;
	}

	public int removeAllFunctionPoints(String functionName){
		int numRemoved = 0;
		Node searchNode = origin;
		Node[] deleteList = new Node[numNodes];

		while(searchNode.left != null){
			searchNode = searchNode.left;
		}

		while(searchNode != null){
			if(searchNode.getVariables()[0] == 0){
				searchNode = searchNode.right;
			}
			else{
				Node verticalSearch = searchNode;
				
				while(verticalSearch.down != null){
					verticalSearch = verticalSearch.down;
				}

				while(verticalSearch != null){	
					if(verticalSearch.getVariables()[1] == 0){
						verticalSearch = verticalSearch.up;
					}
					else{
						if(verticalSearch.getFunction().getFunctionName().equals(functionName)){
							deleteList[numRemoved] = verticalSearch;
							numRemoved++;
						}
						verticalSearch = verticalSearch.up;
					}
					
				}
				searchNode = searchNode.right;
			}
		}

		for (Node node : deleteList) {
			if (node != null){
				removePoint(node.getVariables()[0], node.getVariables()[1]);
			}
		}

		return numRemoved;
	}

	public int countNumberOfPoints(){
		return numNodes;
	}

	public int[] numPointsPerQuadrant(){
		int topLeft = 0, bottomLeft = 0, topRight = 0, bottomRight = 0;

		Node v1Axis = origin;

		while(v1Axis.left != null){
			v1Axis = v1Axis.left;
		}

		while(v1Axis != null){
			if(v1Axis.getVariables()[0] == 0){
				v1Axis = v1Axis.right;		
			}
			else{
				Node v2Axis = v1Axis;
				while(v2Axis.up != null){
					v2Axis = v2Axis.up;
				}

				while(v2Axis != null){	
					if(v2Axis.getVariables()[1] == 0){
						v2Axis = v2Axis.down;
					}
					else if(v2Axis.getVariables()[1] > 0 && v2Axis.getVariables()[0] < 0){
						
						if(v2Axis.prevVal == null){
							topLeft++;
						}
						else {
							Node temp = v2Axis;
							while(temp != null)
							{	
								topLeft++;
								temp = temp.prevVal;
							}
						}
						v2Axis = v2Axis.down;
					}
					else if(v2Axis.getVariables()[1] < 0 && v2Axis.getVariables()[0] < 0){
						if(v2Axis.prevVal == null){
							bottomLeft++;
						}
						else {
							Node temp = v2Axis;
							while(temp != null)
							{	
								bottomLeft++;
								temp = temp.prevVal;
							}
						}
						v2Axis = v2Axis.down;
					}
					else if(v2Axis.getVariables()[1] > 0 && v2Axis.getVariables()[0] > 0){
						if(v2Axis.prevVal == null){
							topRight++;
						}
						else {
							Node temp = v2Axis;
							while(temp != null)
							{	
								topRight++;
								temp = temp.prevVal;
							}
						}
						v2Axis = v2Axis.down;
					}
					else if(v2Axis.getVariables()[1] < 0 && v2Axis.getVariables()[0] > 0){
						if(v2Axis.prevVal == null){
							bottomRight++;
						}
						else {
							Node temp = v2Axis;
							while(temp != null)
							{	
								bottomRight++;
								temp = temp.prevVal;
							}
						}
						v2Axis = v2Axis.down;
					}
					else {
						v2Axis = v2Axis.down;
					}
				}
				v1Axis = v1Axis.right;
			}		
		}

		int[] returnArray = new int[4];
		returnArray[0] = topLeft;
		returnArray[1] = bottomLeft;
		returnArray[2] = topRight;
		returnArray[3] = bottomRight;
		return returnArray;
	}

	public void clearAllData(){
		origin.up = null;
		origin.down = null;
		origin.left = null;
		origin.right = null;
	}

	//ADD HELPER FUNCTIONS BELOW

	private void insertIntoExistingPoint(Node newNode, Node oldNode){
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
		else if(newNode.getFunction().getFunctionName().compareTo(oldNode.getFunction().getFunctionName()) == 0 || newNode.getFunction().getFunctionName().compareTo(oldNode.getFunction().getFunctionName()) > 0){
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
				}
				else {
					newNode.prevVal = temp.prevVal;
					temp.prevVal = newNode;
					newNode.prevVal.nextVal = newNode;
				}	
			}
		}
	}

	private void swapPrevNode(Node currHead, Node newHead){
		if(currHead.left != null){
			newHead.left = currHead.left;
			newHead.left.right = newHead;
			currHead.left = null;
		}
		if(currHead.right != null){
			newHead.right = currHead.right;
			newHead.right.left = newHead;
			currHead.right = null;
		}
		if(currHead.up != null){
			newHead.up = currHead.up;
			newHead.up.down = newHead;
			currHead.up = null;
		}
		if(currHead.down != null){
			newHead.down = currHead.down;
			newHead.down.up = newHead;
			currHead.down = null;
		}

		currHead.prevVal = null;
		currHead.nextVal = null;
	}

	private void clearNodeHorizontal(Node node){
		if(node.left != null){
			node.left.right = null;
			node.left = null;
		}
		if(node.right != null){
			node.right.left = null;
			node.right = null;
		}
	}

	private void clearNodeVertical(Node node){
		if(node.up != null){
			node.up.down = null;
			node.up = null;
		}
		if(node.down != null){
			node.down.up = null;
			node.down = null;
		}
	}
}