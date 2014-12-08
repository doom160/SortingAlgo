
import java.util.LinkedList;
import java.util.Arrays;

public class SortAlgorithm {

    final static int[] cgaps = {3986213, 1811915, 823598, 374363, 170165, 77348, 35158, 15981, 7264, 3302, 1501, 701, 301, 132, 57, 23, 10, 4, 1};
    final static int[] gaps = {3931496, 1747331, 776591, 345152, 153401, 68178, 30301, 13467, 5985, 2660, 1182, 525, 233, 103, 46, 20, 9, 4, 1};
    final static int[] kgaps = {7174453, 2391484, 797161, 265720, 88573, 29524, 9841, 3280, 1093, 364, 121, 40, 13, 4, 1};
    static long noSwap = 0;
    static long noCompare = 0;
    private static final long MEGABYTE = 1024L * 1024L;
    private static final long KILOBYTE = 1024L;
    static long maxMemory = 0;

    public static void main(String[] args) {
        int[] S = GenerateDataSet.generateIntUnique(1000);
        //   S = mergeSort(S); //
        //  S = quickSort(S, 0, S.length - 1);

        S = mySort(S);
        printSet(S);
    }

    // Clear data 
    public static void reset() {
        Runtime.getRuntime().gc();
        noSwap = 0;
        noCompare = 0;
        maxMemory = 0;
    }

    //Basic Operations
    public static boolean isLeftMoreThanRight(int[] S, int l, int r) {
        //   noCompare++;
        if (S[l] > S[r]) {
            return true;
        }
        return false;
    }

    public static void swap(int[] S, int i, int j) {
        if (S[j] != S[i]) {
            int temp = S[j];
            S[j] = S[i];
            S[i] = temp;
            //   noSwap++;
        }
    }

    //Memory
    public static long getMemory() {
        return maxMemory;
    }

    public static void checkMemory() {
        Runtime runtime = Runtime.getRuntime();

        long memory = runtime.totalMemory() - runtime.freeMemory();
        if (memory > maxMemory) {
            maxMemory = memory;
        }
    }

    public static long bytesToMegabytes(long bytes) {
        return bytes / MEGABYTE;
    }

    public static long bytesToKilobytes(long bytes) {
        return bytes / KILOBYTE;
    }

    //Algorithms
    public static int[] combSort(int[] S) {
        //getting number of gap
        int gap = S.length - 1;

        while (gap > 1) {
            //continue sorting with shrinking gap
            gap = (int) (gap / 1.25);

            //  checkMemory();
            for (int j = gap; j < S.length - 1; j++) {
                if (isLeftMoreThanRight(S, j - gap, j)) {
                    swap(S, j - gap, j);
                }
            }
        }

        //when gap becomes 1, it will become a bubble sort
        S = bubbleSort(S);

        return S;
    }

    public static int[] bubbleSort(int[] S) {
        int counter = 0;
        boolean isUnsort = true;
        while (isUnsort) {
            isUnsort = false;
            //    checkMemory();
            for (int i = 0; i < S.length - 1 - counter; i++) {
                if (isLeftMoreThanRight(S, i, i + 1)) {
                    swap(S, i, i + 1);
                    isUnsort = true;
                }
            }
            counter++;
        }
        return S;
    }

    public static boolean bubbleSortCheck(int[] S) {
        for (int i = 1; i < S.length - 1; i++) {
            if (S[i - 1] > S[i]) {
                return false;
            }
        }
        return true;
    }

    public static int[] cocktailSort(int[] S) {
        //counter keep tracks of number of sorted value infront
        int counter = 0;
        boolean isUnsort = true;

        while (isUnsort) {
            checkMemory();
            // it pushes biggest value to the back
            isUnsort = false;
            for (int i = counter; i < S.length - 1 - counter; i++) {
                if (isLeftMoreThanRight(S, i, i + 1)) {
                    swap(S, i, i + 1);
                    isUnsort = true;
                }
            }

            //if no swapping occurs, it is full sorted
            if (!isUnsort) {
                break;
            }

            //  checkMemory();
            // then it pushes smallest value to the front
            // it take account of previous sorted values 
            // to prevent redundant comparison
            isUnsort = false;
            for (int i = S.length - 2 - counter; i > counter; i--) {
                if (isLeftMoreThanRight(S, i - 1, i)) {
                    swap(S, i - 1, i);
                    isUnsort = true;
                }
            }
            counter++;
        }
        return S;
    }

    public static int[] insertionSort(int[] S) {
        // it has 2 for loops. if it cant swap, it will break loop 
        // and go to next iteration
        for (int i = 0; i < S.length - 1; i++) {
            //   checkMemory();
            for (int j = i; j > -1; j--) {
                if (isLeftMoreThanRight(S, j, j + 1)) {
                    swap(S, j, j + 1);
                } else {
                    break;
                }
            }
        }
        return S;
    }

    public static int[] selectionSort(int[] S) {
        for (int i = 0; i < S.length - 1; i++) {
            //index is used to store the index of the smallest value
            int index = i;
            for (int j = i + 1; j < S.length; j++) {
                if (isLeftMoreThanRight(S, index, j)) {
                    index = j;
                }
            }
            swap(S, index, i);
        }
        return S;
    }

    public static int[] heapSort(int[] S) {
        //counter is to keep track of last index it has to be a heap
        //the number slowly decreases
        int counter = S.length - 1;

        //it has to convert the numbers into heap first
        heapify(S);
        for (int i = S.length - 1; i > 0; i--) {
            //it swaps the first(biggest) value to the 
            //first index of sorted data
            swap(S, 0, i);
            //then it restores the heap structure
            tranverseDown(S, 0, --counter);
        }
        return S;
    }

    public static int[] heapify(int[] S) {
        //parent is the starting point of maintaining the heap
        int parent = (S.length - 1) / 2;
        while (parent >= 0) {
            //the parent will decrease, meaning it will go higher and higher
            //and more tranversing is required
            tranverseDown(S, parent--, S.length - 1);
        }
        return S;
    }

    public static void tranverseDown(int[] S, int start, int end) {
        int parent = start;
        //  checkMemory();
        while ((parent * 2 + 1) <= end) {
            int child = parent * 2 + 1; //get left child of parent
            //if parent has 2 children and right > left point to right else remain left
            if (child + 1 <= end && isLeftMoreThanRight(S, child + 1, child)) {
                child = child + 1;
            }
            if (isLeftMoreThanRight(S, child, parent)) {
                //out of max-heap order, swap the child with parent        
                swap(S, parent, child);
                //tranversing down
                parent = child;
            } else {
                return;
            }
        }
    }

    public static int[] mergeSort(int[] S) {
        //recursive in nature, this is the returning point
        if (S.length == 1) {
            //     checkMemory();
            return S;
        }
        int[] P = null, Q = null;
        if (S.length > 1) {
            //spliting the set into halves
            int[] R = new int[S.length / 2];
            int[] T = new int[S.length - R.length];

            //copy data to each subarrays
            System.arraycopy(S, 0, R, 0, R.length);
            System.arraycopy(S, R.length, T, 0, T.length);

            //it will further break down the set into smaller set
            P = mergeSort(R);
            Q = mergeSort(T);
        }
        //once it becomes size 1 it will start merging
        return merge(P, Q);
    }

    public static int[] merge(int[] P, int[] Q) {
        //instantiate new array
        int[] O = new int[P.length + Q.length];
        //pC and qC are counters to take note number of element are removed
        int pC = 0, qC = 0;
        for (int i = 0; i < O.length; i++) {

            //if p is empty, put in Q data into O
            if (pC == P.length) {
                noCompare++;
                O[i] = Q[qC++];
           
                //if q is empty, put in P data into O    
            } else if (qC == Q.length) {
                noCompare += 2;
                O[i] = P[pC++];
                
                //if both are not empty, see which is bigger
            } else if (P[pC] <= Q[qC]) {
                noCompare += 3;
                O[i] = P[pC++];
             
            } else {
                noCompare += 4;
                O[i] = Q[qC++];
            }
        }
        return O;
    }

    public static int[] shellSort(int[] S) {
        // perform an insertion sort for each gap sequence
        for (int gap : gaps) {
            for (int i = gap; i < S.length; i++) {
                for (int j = i / gap; j > 0; j--) {
                    //   checkMemory();
                    if (isLeftMoreThanRight(S, (j - 1) * gap + i % gap, j * gap + i % gap)) {
                        swap(S, j * gap + i % gap, (j - 1) * gap + i % gap);
                    } else {
                        break;
                    }
                }
            }
        }
        return S;
    }

    public static int[] quickSort(int[] S, int start, int end) {
        if (start > end) {
            return S;
        }
        boolean hasSwap = false;
        int pivot = start;
        int l = start, r = end;
        for (int i = start; i < end + 1; i++) {
            if (isLeftMoreThanRight(S, l, r)) {
                swap(S, l, r);
                if (hasSwap) {
                    pivot = r;
                } else {
                    pivot = l;
                }
                hasSwap = !hasSwap;

            }
            if (r == pivot) {
                l++;
            } else {
                r--;
            }
        }
        //checkMemory();
        quickSort(S, start, pivot - 1);
        quickSort(S, pivot + 1, end);

        return S;
    }

    public static int[] quickSortDualP(int[] S, int start, int end) {
        if (start > end) {
            return S;
        }

        if (S[start] > S[end]) {
            swap(S, start, end);
        }

        int midL = start + 1, midR = start + 1;
        int rearL = end - 1;


        while (midR <= rearL) {
            if (isLeftMoreThanRight(S, start, midR)) {
                swap(S, midL++, midR++);
            } else if (isLeftMoreThanRight(S, midR, end)) {
                swap(S, rearL--, midR);
            } else {
                midR++;
            }
        }

        swap(S, midL - 1, start);
        swap(S, rearL + 1, end);

        quickSortDualP(S, start, midL - 2);
        quickSortDualP(S, midL, midR - 1);
        quickSortDualP(S, rearL + 2, end);

        
        return S;
    }

   

    public static int[] heapifyMin(int[] S) {
        int root = (S.length - 1) / 2;
        while (root >= 0) {
            tranverseMinDown(S, root--, S.length - 1);
        }
        return S;
    }

    public static void tranverseMinDown(int[] S, int start, int end) {
        int root = start;

        while ((root * 2 + 1) <= end) {
            int child = root * 2 + 1;           //get left child of root
            //if root has 2 children and right > left point to right else remain left

            if (child + 1 <= end && isLeftMoreThanRight(S, child, child + 1)) {
                child = child + 1;
            }

            if (isLeftMoreThanRight(S, root, child)) {     //out of max-heap order
                swap(S, root, child);
                root = child;                //tranversing down
            } else {
                return;
            }
        }
    }

    public static int[] mySort(int[] S) {
        S = heapifyMin(S);
        S = cocktailSort(S);
        return S;
    }

    public static void printSet(int[] S) {
        for (int i = 0; i < S.length; i++) {
            System.out.print(S[i] + " ");
            if ((i + 1) % 100 == 0) {
                System.out.println();
            }
        }
    }

    public static void printHeapSet(int[] S) {
        int row = 1;
        for (int i = 0; i < S.length; i++) {
            if (i == row - 1) {
                row *= 2;
                System.out.println();
            }
            System.out.print(S[i] + " ");
            if (i == row - 1) {
                row *= 2;
                System.out.println();
            }
        }
    }
}
