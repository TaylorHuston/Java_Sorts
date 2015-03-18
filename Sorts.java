/*
  Sandbox for the various search algorithms from Section 2 of
  Algorithms, 4th Edition
 */

import java.util.Arrays;

public class Sorts {
    static Helpers sortHelper = new Helpers();
    static BasicSorts basicSort = new BasicSorts();
    static MergeSort mergeSort = new MergeSort();
    static QuickSort quickSort = new QuickSort();
    static QuickThreeSort quick3 = new QuickThreeSort();
    



    /*Heap Sort
    
    Sorts passed array of any Comparable object by ascending order. Uses the Heap Sort method.  Starts by building a heap out of the array. It then takes each item from the top of the heap (the max item), places it on the end of the unsorted subarray, and re-heapifys the unsorted sub array.  Repeats until entire array is sorted. */
    public static void heapSort(Comparable[] toSort) {
        int N = toSort.length-1;
        //Heap construction
        for (int k = N/2; k >=1; k--) {
            sink(toSort, k, N); //Build the heap
        }
        
        //Sortdown
        while(N >=1) {
            sortHelper.swap(toSort, 1, N--);  //Put the current top of the heap to the end of the array
            sink(toSort, 1, N);  //Re-heapify the remaining array
        }
    }
    
    private static void sink(Comparable[] toSort, int k, int end) {
        while(2*k <= end) {
            int j = 2*k;
//            StdOut.println(j);
//            StdOut.println(toSort.length);

            if (j < end && sortHelper.less(toSort[j], toSort[j + 1])) {
                j++;
            }
            
            if(!sortHelper.less(toSort[k], toSort[j])) {
                break;
            }
            sortHelper.swap(toSort, k, j);
            k=j;
        }
    }
   

    public static void main(String[] args) {
        //For reading in a premade file of ints
        //int[] masterArray = StdIn.readAllInts();
        
        //For passing in the length of the random array to be created
        //int length = Integer.parseInt(args[0]);  
        //int range = length;

        StdOut.println("Length of array to be generated");
        int length = StdIn.readInt();
        
        
        StdOut.println("Range of random values (0 to: ");
        int range =  StdIn.readInt();
        
        int[] masterArray =  new int[length];
        for (int i = 0; i < length; i++) {
            masterArray[i] = StdRandom.uniform(range);
        }
        
        StdOut.println();
        StdOut.println();

        //Will display the array if it's reasonably short
        if(length <= 20) {
            StdOut.println("Master Array");        
            for(int i=0; i < length; i++) {
               StdOut.print(masterArray[i] + " ");
            }
            StdOut.println();
            StdOut.println();
        }

        
        //Selection Sort
        StdOut.println("Selection Sort"); 
                
        Integer[] selectionArray = new Integer[length];  
        arrayCopy(masterArray, selectionArray, false);
        Stopwatch t1 = new Stopwatch();

        basicSort.selectionSort(selectionArray);

        if (sortHelper.isSorted(selectionArray)){
            StdOut.println("Successful, running time: " + t1.elapsedTime());
        }
        StdOut.println();
        
        
        //Insertion Sort
        StdOut.println("Insertion Sort"); 
              
        Integer[] insertionArray = new Integer[length];  
        arrayCopy(masterArray, insertionArray, false);
        Stopwatch t2 = new Stopwatch();

        basicSort.insertionSort(insertionArray, 0, length - 1);

        if (sortHelper.isSorted(insertionArray)){
            StdOut.println("Successful, running time: " + t2.elapsedTime());
        }
        StdOut.println();
        
        
        //Shell Sort
        StdOut.println("Shell Sort"); 
        
        Integer[] shellArray = new Integer[length];  
        arrayCopy(masterArray, shellArray, false);
        Stopwatch t3 = new Stopwatch();

        basicSort.shellSort(shellArray);

        if (sortHelper.isSorted(shellArray)){
            StdOut.println("Successful, running time: " + t3.elapsedTime());
        }
        StdOut.println();
        

        //Merge Sort
        StdOut.println("Merge Sort"); 
        
        Integer[] mergeArray = new Integer[length];  
        arrayCopy(masterArray, mergeArray, false);
        Stopwatch t4 = new Stopwatch();

        mergeSort.sort(mergeArray);

        if (sortHelper.isSorted(mergeArray)){
            StdOut.println("Successful, running time: " + t4.elapsedTime());
        }
        StdOut.println();

        
        //Quick Sort
        StdOut.println("Quick Sort"); 
                
        Integer[] quickArray = new Integer[length];  
        arrayCopy(masterArray, quickArray, false);
        Stopwatch t5 = new Stopwatch();

        quickSort.sort(quickArray);

        if (sortHelper.isSorted(quickArray)){
            StdOut.println("Successful, running time: " + t5.elapsedTime());
        }
        StdOut.println();

        //3-Way Quick Sort
        StdOut.println("3-Way Quick Sort");

        Integer[] threeArray = new Integer[length];
        arrayCopy(masterArray, threeArray, false);
        Stopwatch t6 = new Stopwatch();

        quick3.sort(threeArray);

        if (sortHelper.isSorted(threeArray)){
            StdOut.println("Successful, running time: " + t6.elapsedTime());
        }
        StdOut.println();
        
        
        //Heap Sort
        StdOut.println("Heap Sort"); 
                
        Integer[] binHeap = new Integer[length+1];  
        arrayCopy(masterArray, binHeap, true);
        Stopwatch t7 = new Stopwatch();

        heapSort(binHeap);

        if (sortHelper.isSorted(binHeap, 1, length)){
            StdOut.println("Successful, running time: " + t7.elapsedTime());
        }
        StdOut.println();
    }
    
    public static void arrayCopy(int[] a, Integer[] b, boolean binHeap) {
        if(!binHeap) {
            for(int i = 0; i < a.length; i++) {
                b[i] = a[i];
            }
        } else {
            for(int i = 0; i < a.length; i++) {
                b[i+1] = a[i];
            }
        }
    }
}