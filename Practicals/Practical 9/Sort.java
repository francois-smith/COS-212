// Name:
// Student number:
import java.util.Arrays;
public class Sort
{
	
	////// Implement the functions below this line //////
	/********** MERGE **********/
	public static <T extends Comparable<? super T>> void mergesort(T[] data, int first, int last, boolean debug){
		if ((last - first) < 1){
			return;
		} 
		
		int middle = (first+last)/2;
		mergesort(data, first, middle, debug);
		mergesort(data, middle + 1, last, debug);
		merge(data, first, last, debug);
	}
     
	@SuppressWarnings("unchecked")
	private static <T extends Comparable<? super T>> void merge(T[] data, int first, int last, boolean debug){
		Object[] tmpArr = new Object[last-first+1]; 
		int middle = (first+last)/2;
		int index1 = first;
		int index2 = middle+1;
		int index3 = 0;

		while(index1 <= middle && index2 <= last){
			if(data[index1].compareTo(data[index2]) <= 0){
				tmpArr[index3] = data[index1++];
			}
			else{
				tmpArr[index3] = data[index2++];
			}

			index3++;
		}
		if(index1 <= middle && index2 > last){
			while (index1 <= middle){
				tmpArr[index3++] = data[index1++];
			}
		} 
		else{
			while (index2 <= last){
				tmpArr[index3++] = data[index2++];
			}
		}

		for(index3 = 0; index3 < tmpArr.length; index3++){
			data[index3+first] = (T)(tmpArr[index3]);
		}

		//DO NOT MOVE OR REMOVE!
		if (debug)
			System.out.println(Arrays.toString(data));
	}
     
	/********** COUNTING **********/
	public static void countingsort(int[] data, boolean debug){
		int[] temp = new int[data.length];
		int largest = data[0];
		int index;

		for(index = 1; index < data.length; index++){
			if(largest < data[index]){
				largest = data[index];
			}
		}

		int[] count = new int[largest+1];
		for(index = 0; index < largest; index++){
			count[index] = 0;
		}

		for(index = 0; index < data.length; index++){
			count[data[index]]++;
		}

		for(index = 1; index <= largest; index++){
			count[index] = count[index-1] + count[index];
		}

		for(index = data.length-1; index >= 0; index--){
			temp[count[data[index]]-1] = data[index];
			count[data[index]]--;
		}

		for(index = 0; index < data.length; index++){
			data[index] = temp[index];
		}

		//DO NOT MOVE OR REMOVE!
		if (debug)
			System.out.println(Arrays.toString(data));
	}

}