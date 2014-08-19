/*
  Sandbox for the various search algorithms from Section 2 of
  <a href="http://algs4.cs.princeton.edu/home/">Algorithms, 4th Edition - Robert Sedgewick | Kevine Wayne</a>
 */

import java.util.Arrays;

public class Sorts {

    /********************
    Sorting Algorithms
    ********************/
    
    /*Selection Sort
    
    Sorts a passed array of any Comparable object by ascending order.Uses the Selection Sort method. For each iteration i we place the ith smallest item in array[i]*/
    public static void selectionSort(Comparable[] toSort) {        
        int N = toSort.length;
        int min; //Index of the minimal element during each run
        
        for (int i=0; i < N; i++) {
            min = i;  
            for(int j = i+1; j < N; j++) {
                if(less(toSort[j], toSort[min])) {
                    min = j;
                }
            }
            swap(toSort, i, min);
        } 
    }

    
    /*Insertion Sort
    
    Sorts a passed array of any Comparable object by ascending order. Uses the Insertion Sort method.  For each iterarion, i, swap array[i] with entries array[<i] that are larger.*/
    public static void insertionSort(Comparable[] toSort, int start, int end) {     
        for (int i=start; i <= end; i++) {
            for(int j = i; j > start && less(toSort[j], toSort[j-1]); j--) {
                swap(toSort, j, j-1);
            }
        } 
    }

    
    /*Shell Sort
    
    Sorts a passed array of any Comparable object by ascending order. Uses the Shell Sort method, which is essentially a modified Insertion Short. Rather then decrementing by 1, we decrement by decreasing values of h, breaking the array into smaller and smaller already sorted sub-arrays. Increased performance on larger arrays, especially when there are very small values at the end of the array*/
    public static void shellSort(Comparable[] toSort) {
        int N = toSort.length;
        int h = 1;
        
        while (h < N/3) {  //Computes the max h-size array
            h = h*3 + 1;  //1,4,13,40,121.....
        }
        
        while (h >= 1) {
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && less(toSort[j], toSort[j-h]); j-=h) {
                    swap(toSort, j, j-h);
                }
            }
        h = h/3;  //Shrinks to the next h-array size
        } 
    }
    
    
    /*Merge Sort
    
    Sorts a passed array of any Comparable object by ascending order.  Uses the Merge Sort method.  Recursively breaks the array into 1/2 sized sub arrays, then merges them in sorted order as the stack unwinds.
    
    Uses Insertion sort when it gets to a certain threshold for small arrays*/ 
    public static void mergeSort(Comparable[] toSort) {
        Comparable[] tempArray = new Comparable[toSort.length];  
        mergeSort(toSort, tempArray, 0, toSort.length-1);
    }
    
    public static void mergeSort(Comparable[] toSort, Comparable[] tempArray, int low, int high) {  //Recursively splits the array in half and then merges in proper order        
         //Cutoff to just Insertion Sort for smaller arrays
        if (high<=low + 15) {
           insertionSort(toSort, low, high);
           return;
        }
        
        int mid = low + (high - low)/2;  //Create the mid point
        
        mergeSort(toSort, tempArray, low, mid);  //Sort left half
        mergeSort(toSort, tempArray, mid+1, high); //Sort right half
        
        if(greater(toSort[mid],toSort[mid+1])) {  //Skips the merge if everything in the left is smaller then everything in the right
              mergeArrays(toSort, tempArray, low, mid, high);  //Merge results
        }
       
    }
    
    //Merges two sorted sub arrays into one larger sorted array
    public static void mergeArrays(Comparable[] toSort, Comparable[] tempArray, int low, int mid, int high){
        int i = low;
        int j = mid+1;
        
        for (int k = low; k <= high; k++) {  //Copy values into temporary array
            tempArray[k] = toSort[k];
        }
        
        for (int k = low; k <= high; k++) {  //Copy values back in sorted order
            if (i > mid) {  //No more left items
                toSort[k] = tempArray[j++];
            } 
            else if (j > high) { //No more right items
                toSort[k] = tempArray[i++];
            }
            else if (less(tempArray[j], tempArray[i])) {  //If the item on the right is smaller
                toSort[k] = tempArray[j++];
            }
            else {  //If the item on the left is smaller
                toSort[k] = tempArray[i++];
            }
        }
    }
    
    
    /*Quick Sort
    
    Sorts passed array of any Comparable object by ascending order. Uses the Quick Sort method. Recursively places an element in index array[v], known as the partition into it's proper place so that every element array[<v] is < array[v] and every element array[>v] is > array[v]*/
    public static void quickSort(Comparable[] toSort)  {
        //StdRandom.shuffle(toSort);   Unusued because in my current implementation the array starts off already random
        quickSort(toSort, 0, toSort.length - 1);
    }
    
    public static void quickSort(Comparable[] toSort, int low, int high) {       
        //Cutoff to just Insertion Sort for smaller arrays
        if (high<=low + 15) {
           insertionSort(toSort, low, high);
           return;
        }
        
        int j = quickPartition(toSort, low, high);  
        quickSort(toSort, low, j-1);  //Sorts to the left of partition
        quickSort(toSort, j+1, high);  //Sorts to the right od partition
    }
    
    /*Places the partition item in it's proper place.  Iterates through each element from both ends and swaps any elements on the right side that are < array[low] with any elements on the left side that are > array[low]. Returns the index, j, of the item that is now in it's proper place*/
    private static int quickPartition(Comparable[] toSort, int low, int high) {
        int i = low;
        int j = high+1;
        Comparable v = toSort[low];
        
        while(true) {
            while(less(toSort[++i],v)) {  //Scan left side until you find an item that's greater then v
                if(i==high) {  //Reached end of array
                    break;
                }
            }
            
            while(less(v, toSort[--j])) { //Scan right side until you find an item that's greater then v
                if(j==low) {
                    break;
                }
            }
            if(i>=j) {  //If the right side and left side poointers cross, entire array has been searched
                break;
            }
            swap(toSort, i, j);  //Swaps the two out of place elements
        }
        
        swap(toSort, low, j);  //Puts partition item into proper place
        return j;  //Returns position of now correct item
    }

    
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
            swap(toSort, 1, N--);  //Put the current top of the heap to the end of the array
            sink(toSort, 1, N);  //Re-heapify the remaining array
        }
    }
    
    private static void sink(Comparable[] toSort, int k, int end) {
        while(2*k <= end) {
            int j = 2*k;
//            StdOut.println(j);
//            StdOut.println(toSort.length);

            if (j < end && less(toSort[j], toSort[j+1])) {
                j++;
            }
            
            if(!less(toSort[k],toSort[j])) {
                break;
            }
            swap(toSort, k, j);
            k=j;
        }
    }
    
    /********************
    Helper methods
    ********************/
    private static boolean less(Comparable x, Comparable y) {
        return x.compareTo(y) < 0;
    }
    
    private static boolean equals(Comparable x, Comparable y) {
        return x.compareTo(y) == 0;
    }
    
    private static boolean greater(Comparable x, Comparable y) {
        return x.compareTo(y) > 0;
    }

    private static void swap(Comparable[] items, int x, int y){
        Comparable temp = items[x];
        items[x] = items[y];
        items[y] = temp;
    }
    
    private static boolean isSorted(Comparable[] a) {
        return isSorted(a, 0, a.length - 1);
    }

    private static boolean isSorted(Comparable[] a, int lo, int hi) {
        for (int i = lo + 1; i <= hi; i++) {
            if (less(a[i], a[i-1])) {
                return false;
            }
        }
        return true;
    }
   

    public static void main(String[] args) {
//      int[] masterArray = StdIn.readAllInts();  //Reading in a premade file of ints
//      int length = Integer.parseInt(args[0]);  //Passing in the length of the random array to be created
//      int range = length;

        StdOut.println("Length of array to be generated");
        int length = StdIn.readInt();
        
        
        StdOut.println("Range random values");
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

        selectionSort(selectionArray);

        if (isSorted(selectionArray)){
            StdOut.println("Successful, running time: " + t1.elapsedTime());
        }
        StdOut.println();
        
        
        //Insertion Sort
        StdOut.println("Insertion Sort"); 
              
        Integer[] insertionArray = new Integer[length];  
        arrayCopy(masterArray, insertionArray, false);
        Stopwatch t2 = new Stopwatch();

        insertionSort(insertionArray, 0, length-1);

        if (isSorted(insertionArray)){
            StdOut.println("Successful, running time: " + t2.elapsedTime());
        }
        StdOut.println();
        
        
        //Shell Sort
        StdOut.println("Shell Sort"); 
        
        Integer[] shellArray = new Integer[length];  
        arrayCopy(masterArray, shellArray, false);
        Stopwatch t3 = new Stopwatch();

        shellSort(shellArray);

        if (isSorted(shellArray)){
            StdOut.println("Successful, running time: " + t3.elapsedTime());
        }
        StdOut.println();
        

        //Merge Sort
        StdOut.println("Merge Sort"); 
        
        Integer[] mergeArray = new Integer[length];  
        arrayCopy(masterArray, mergeArray, false);
        Stopwatch t4 = new Stopwatch();

        mergeSort(mergeArray);

        if (isSorted(mergeArray)){
            StdOut.println("Successful, running time: " + t4.elapsedTime());
        }
        StdOut.println();

        
        //Quick Sort
        StdOut.println("Quick Sort"); 
                
        Integer[] quickArray = new Integer[length];  
        arrayCopy(masterArray, quickArray, false);
        Stopwatch t5 = new Stopwatch();

        quickSort(quickArray);

        if (isSorted(quickArray)){
            StdOut.println("Successful, running time: " + t5.elapsedTime());
        }
        StdOut.println();
        
        
        //Heap Sort
        StdOut.println("Heap Sort"); 
                
        Integer[] binHeap = new Integer[length+1];  
        arrayCopy(masterArray, binHeap, true);
        Stopwatch t6 = new Stopwatch();

        heapSort(binHeap);

        if (isSorted(binHeap,1, length)){
            StdOut.println("Successful, running time: " + t6.elapsedTime());
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