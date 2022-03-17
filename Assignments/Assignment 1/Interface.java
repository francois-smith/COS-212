import java.text.DecimalFormat;

public class Interface {

	private Node origin;
	private int numNodes = 0, counter = 0;

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
		Node searchNodeV1 = origin, searchNodeV2 = origin;
		Node newNode = new Node(function, v1, v2);

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
					while(searchNodeV2.down != null && searchNodeV2.down.getVariables()[1] > v2){
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
					else if (searchNodeV2.up.getVariables()[1] < v2){
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
					while(searchNodeV1.right != null && searchNodeV1.right.getVariables()[0] < v1){
						searchNodeV1 = searchNodeV1.right;
					}

					if(searchNodeV1.getVariables()[0] == v1){
						
					}
					else if (searchNodeV1.right == null ){
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
					while(searchNodeV1.left != null && searchNodeV1.left.getVariables()[0] > v1){
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
					else if (searchNodeV1.up.getVariables()[0] < v1){
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
					else if(searchNodeV2.left.getVariables()[0] == newNode.getVariables()[0] && searchNodeV2.left.getVariables()[1] == newNode.getVariables()[1]){
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

					System.out.println(searchNodeV1.getVariables()[1]);
				
					 if(searchNodeV1.up == null){
						System.out.println("dwad");
						searchNodeV1.up = newNode;
						newNode.down = searchNodeV1;
					}
					else if(searchNodeV1.up.getVariables()[1] > newNode.getVariables()[1]){
						System.out.println("dwad");
						newNode.up = searchNodeV1.up;
						searchNodeV1.up.down = newNode;
						searchNodeV1.up = newNode;
						newNode.down = searchNodeV1;
					}
					else if(searchNodeV1.up.getVariables()[0] == newNode.getVariables()[0] && searchNodeV1.up.getVariables()[1] == newNode.getVariables()[1]){
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
					else if(searchNodeV1.down.getVariables()[0] == newNode.getVariables()[0] && searchNodeV1.down.getVariables()[1] == newNode.getVariables()[1]){
						searchNodeV1 = searchNodeV1.down;
						insertIntoExistingPoint(newNode, searchNodeV1);
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
				while(searchNode.left != null && searchNode.left.getVariables()[0] > v1){
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
		Node[] returnArray =  new Node[numNodes];
		Node searchNode = origin;
		counter = 0;

		while(searchNode.right != null){
			searchNode = searchNode.right;
		}

		while(searchNode.left != null){
			if(searchNode.getVariables()[0] == 0){
				searchNode = searchNode.left;
			}
			else{
				Node verticalSearch = searchNode;
				
				while(verticalSearch.up != null){
					verticalSearch = verticalSearch.up;
				}

				while(verticalSearch.down != null){
				
					if(verticalSearch.getVariables()[1] == 0){
						verticalSearch = verticalSearch.down;
					}
					else{
						returnArray[counter] = verticalSearch;
						counter++;
						verticalSearch = verticalSearch.down;
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