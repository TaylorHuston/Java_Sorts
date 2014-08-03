/**
 * Sandbox for the various search algorithms presented in section 2 of
 * <a href="http://algs4.cs.princeton.edu/home/">Algorithms, 4th Edition - Robert Sedgewick | Kevine Wayne</a>
 */

public class Sorts {
    
    public static void main(String[] args) {
//      int[] masterArray = StdIn.readAllInts();  //Reads in all of the integers, best if passed a text file
        int length = Integer.parseInt(args[0]);
        
        int[] masterArray =  new int[length];
        
        for (int i = 0; i < length; i++) {
            masterArray[i] = StdRandom.uniform(length);
        }
        

        
        //StdOut.println("Master Array");        
        for(int i=0; i < length; i++) {
        //   StdOut.print(masterArray[i] + " ");
        }
        StdOut.println();
        StdOut.println();

        
        Integer[] selectionArray = new Integer[length];  
        arrayCopy(masterArray, selectionArray);
        Stopwatch t1 = new Stopwatch();

        selectionSort(selectionArray);

        StdOut.println("After Selection Sort"); 
        for(int i=0; i < length; i++) {
        //   StdOut.print(selectionArray[i] + " ");
        }
        if (isSorted(selectionArray)){
            StdOut.println("Running time: " + t1.elapsedTime());
        }
        StdOut.println();
        
        Integer[] insertionArray = new Integer[length];  
        arrayCopy(masterArray, insertionArray);
        Stopwatch t2 = new Stopwatch();

        insertionSort(insertionArray);

        StdOut.println("After Insertion Sort"); 
        for(int i=0; i < length; i++) {
        //    StdOut.print(insertionArray[i] + " ");
        }
        if (isSorted(insertionArray)){
            StdOut.println("Running time: " + t2.elapsedTime());
        }
        StdOut.println();
        
        
        Integer[] shellArray = new Integer[length];  
        arrayCopy(masterArray, shellArray);
        Stopwatch t3 = new Stopwatch();

        shellSort(shellArray);

        StdOut.println("After Shell Sort"); 
        for(int i=0; i < length; i++) {
        //   StdOut.print(shellArray[i] + " ");
        }
        if (isSorted(shellArray)){
            StdOut.println("Running time: " + t3.elapsedTime());
        }
        StdOut.println();
    }
    
    
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
    
    Has an approximate running time of ~N^2 / 2 compares and N exchanges regardless of size of N */
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
    
    Has an approximate running time between ~N^2 / 2 compares and exchanges (worst case) to N-1 compares and 0
    exchanges (best case).  Average run time is approximately N^2 / 4 compares and exchanges */
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
        
        while (h < N/3) {
            h = h*3 + 1;  //1,4,13,40,121.....
        }
        
        while (h >= 1) {
            for (int i = h; i < N; i++) {
                for (int j = i; j >= h && less(toSort[j], toSort[j-h]); j-=h) {
                    swap(toSort, j, j-h);
                }
            }
        h = h/3;
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
