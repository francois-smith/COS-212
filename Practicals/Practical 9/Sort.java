// Name:
// Student number:
import java.util.Arrays;
public class Sort
{
	
	////// Implement the functions below this line //////
	
	/********** MERGE **********/
	public static <T extends Comparable<? super T>> void mergesort(T[] data, int first, int last, boolean debug){

	}
     
	private static <T extends Comparable<? super T>> void merge(T[] data, int first, int last, boolean debug){
        

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