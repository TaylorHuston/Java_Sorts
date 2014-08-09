/**
 * Sandbox for the various search algorithms presented in section 2 of
 * <a href="http://algs4.cs.princeton.edu/home/">Algorithms, 4th Edition - Robert Sedgewick | Kevine Wayne</a>
 */

import java.util.Arrays;

public class Sorts {
    
    public static void main(String[] args) {
//      int[] masterArray = StdIn.readAllInts();  //Reading in a premade file of ints
//      int length = Integer.parseInt(args[0]);  //Passing in the length of the random array to be created
        
        StdOut.println("Length of array to be generated");
        int length = StdIn.readInt();
        
        int[] masterArray =  new int[length];
        
        for (int i = 0; i < length; i++) {
            masterArray[i] = StdRandom.uniform(length);
        }

//        StdOut.println("Master Array");        
//        for(int i=0; i < length; i++) {
//           StdOut.print(masterArray[i] + " ");
//        }
//        StdOut.println();
//        StdOut.println();

        
        Integer[] selectionArray = new Integer[length];  
        arrayCopy(masterArray, selectionArray);
        Stopwatch t1 = new Stopwatch();

        selectionSort(selectionArray);

        StdOut.println("Selection Sort"); 
//        for(int i=0; i < length; i++) {
//           StdOut.print(selectionArray[i] + " ");
//        }
        if (isSorted(selectionArray)){
            StdOut.println("Running time: " + t1.elapsedTime());
        }
        StdOut.println();
        
        Integer[] insertionArray = new Integer[length];  
        arrayCopy(masterArray, insertionArray);
        Stopwatch t2 = new Stopwatch();

        insertionSort(insertionArray);

        StdOut.println("Insertion Sort"); 
//        for(int i=0; i < length; i++) {
//            StdOut.print(insertionArray[i] + " ");
//        }
        if (isSorted(insertionArray)){
            StdOut.println("Running time: " + t2.elapsedTime());
        }
        StdOut.println();
        
        
        Integer[] shellArray = new Integer[length];  
        arrayCopy(masterArray, shellArray);
        Stopwatch t3 = new Stopwatch();

        shellSort(shellArray);

        StdOut.println("Shell Sort"); 
//        for(int i=0; i < length; i++) {
//           StdOut.print(shellArray[i] + " ");
//        }
        if (isSorted(shellArray)){
            StdOut.println("Running time: " + t3.elapsedTime());
        }
        StdOut.println();
        

        Integer[] mergeArray = new Integer[length];  
        arrayCopy(masterArray, mergeArray);
        Stopwatch t4 = new Stopwatch();

        mergeSort(mergeArray);

        StdOut.println("Merge Sort"); 
//        for(int i=0; i < length; i++) {
//           StdOut.print(mergeArray[i] + " ");
//        }
        if (isSorted(mergeArray)){
            StdOut.println("Running time: " + t4.elapsedTime());
        }
        StdOut.println();
        

        Integer[] quickArray = new Integer[length];  
        arrayCopy(masterArray, quickArray);
        Stopwatch t5 = new Stopwatch();

        quickSort(quickArray);

        StdOut.println("Quick Sort"); 
//        for(int i=0; i < length; i++) {
//           StdOut.print(quickArray[i] + " ");
//        }
        if (isSorted(quickArray)){
            StdOut.println("Running time: " + t5.elapsedTime());
        }
        StdOut.println();
    }
    
    
    //Simple function to duplicate an array
    public static void arrayCopy(int[] a, Integer[] b) {
        for(int i = 0; i < a.length; i++) {
           b[i] = a[i];
        }
    }
    
       
    /********************
    Sorting Algorithms
    ********************/
    
    /*Sorts passed array of any Comparable object by ascending order.
    Uses the Selection Sort method. For each iteration i we place the ith smallest
    item in array[i]
    
    Has approximately ~(N^2)/2 compares and N exchanges regardless of size of N */
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
    
    /*Sorts passed array of any Comparable object by ascending order.
    Uses the Insertion Sort method.  For each iterarion i swap array[i] with entries
    array[i-1  through 0] that are larger
    
    Has between ~(N^2)/2 compares and exchanges (worst case) to N-1 compares and 0
    exchanges (best case).  Average is approximately ~(N^2)/4 compares and exchanges */
    public static void insertionSort(Comparable[] toSort) {
        int N = toSort.length;
        
        for (int i=1; i < N; i++) {
            for(int j = i; j > 0 && less(toSort[j], toSort[j-1]); j--) {
                swap(toSort, j, j-1);
            }
        } 
    }
    
    /*Sorts passed array of any Comparable object by ascending order.
    Uses the Shell Sort method, which is essentially a modified Insertion Short.
    Rather then decrementing by 1, we decrement by decreasing values of h, breaking
    the array into smaller and smaller already sorted sub-arrays. Increased performance
    on larger arrays, especially when there are very small values at the end of the array*/
    public static void shellSort(Comparable[] toSort) {
        int N = toSort.length;
        int h = 1;
        
        while (h < N/3) {  //Computers the size of the h-arrays
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
    
    
    /*Sorts passed array of any Comparable object by ascending order.
    Uses the Merge Sort method.  Recursively breaks the array into 1/2 sized sub arrays,
    then merges them in sorted order as the stack unwinds
    
    Uses between 1/2NlgN and NlgN compares. Uses at most 6NlgN total array accesses.
    Since the number of compares is !NlgN it is an asymptotically optimale compare-based sorting algorithm*/
    private static Comparable[] tempArray;  //Array to hold values temporarilly
    
    public static void mergeSort(Comparable[] toSort) {
        tempArray = new Comparable[toSort.length];  
        mergeSort(toSort, 0, toSort.length-1);
    }
    
    public static void mergeSort(Comparable[] toSort, int low, int high) {  //Recursively splits the array in half and then merges in proper order
        if(high <= low){  //When you get to a single entry array
            return;
        }
        
        int mid = low + (high - low)/2;  //Create the mid point
        
        mergeSort(toSort, low, mid);  //Sort left half
        mergeSort(toSort, mid+1, high); //Sort right half
        
        if(greater(toSort[mid],toSort[mid+1])) {  //Skips the merge if everything in the left is smaller then everything in the right
            mergeArrays(toSort, low, mid, high);  //Merge results
        }
    }
    
    public static void mergeArrays(Comparable[] toSort, int low, int mid, int high){
        int i = low;
        int j = mid+1;
        
        for (int k = low; k <= high; k++) {  //Copy values into temporary array
            tempArray[k] = toSort[k];
        }
        
        for (int k = low; k <= high; k++) {  //Copy values back in sorted order
            if (i > mid) {  //If you've ran out of left items
                toSort[k] = tempArray[j];
                j++;
            } 
            else if (j > high) { //If you're out of right items
                toSort[k] = tempArray[i];
                i++;
            }
            else if (less(tempArray[j], tempArray[i])) {  //If the item on the right is smaller
                toSort[k] = tempArray[j];
                j++;
            }
            else {  //If the item on the left is smaller
                toSort[k] = tempArray[i];
                i++;
            }
        }
    }
    
    
    /*Sorts passed array of any Comparable object by ascending order.
    Uses the Quick Sort method.  Recursively places an element in index array[v], known as the partition
    into it's proper place so that every element array[<v] is < array[v] and every element array[>v] is > array[v]
    
    Uses ~2NlnN (approx 1.39NlgN) compares on average.  Worst case is higher, but random shuffling makes that
    a stastical impossibility.  It makes roughly 1/6th as many excahges, making it usually faster then merge sort*/
    public static void quickSort(Comparable[] toSort)  {
        //StdRandom.shuffle(toSort);   Unusued because in my current implementation the array starts off already random
        quickSort(toSort, 0, toSort.length - 1);
    }
    
    public static void quickSort(Comparable[] toSort, int low, int high) {
        if (high <= low) {  //Single item array is already sorted
            return;
        }
        
        int j = quickPartition(toSort, low, high);  
        quickSort(toSort, low, j-1);  //Sorts to the left of partition
        quickSort(toSort, j+1, high);  //Sorts to the right od partition
    }
    
    /*Places the partition item in it's proper place.  Iterates through each element from both ends and
    swaps any elements on the right side that are < array[low] with any elements on the left side that are >
    array[low]. Returns the index, j, of the item that is now in it's proper place*/
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
    
    public static boolean isSorted(Comparable[] items) {
        for (int i = 1; i < items.length; i++) {
            if(less(items[i],items[i-1])) {
                return false;
            }
        }
    return true;
    }
   
}
