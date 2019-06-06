package sg.com.renfa;


public class SortAlgorithm {

    final static int[] GAPS = {3931496, 1747331, 776591, 345152, 153401, 68178, 30301, 13467, 5985, 2660, 1182, 525, 233, 103, 46, 20, 9, 4, 1};
    static long noSwap = 0;
    static long noCompare = 0;
    final static long KILOBYTE = 1024L;
    static long maxMemory = 0;

    // Clear data 
    public static void reset() {
        Runtime.getRuntime().gc();
        noSwap = 0;
        noCompare = 0;
        maxMemory = 0;
    }

    //Basic Operations
    public static boolean isLeftMoreThanRight(int[] S, int l, int r) {
        //noCompare++;
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
            //noSwap++;
        }
    }

    //Print result
    public static void printSet(int[] S) {
        for (int i = 0; i < S.length; i++) {
            System.out.print(S[i] + " ");
            if ((i + 1) % 100 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

    //Print result as a heap layout
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
        System.out.println();
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

    public static int[] bubbleSortOpt(int[] S) {
        int top = -1;
        int[] stack = new int[S.length];
        for (int i = 0; i < S.length - 1; i++) {
            top = bubbleSortOpt2(S, stack, top, i);
        }
        return S;
    }

    public static int bubbleSortOpt2(int[] S, int[] stack, int top, int i) {
        if (top < 0) {
            stack[++top] = 0;
        } 
        checkMemory();
        int value = stack[top--];
        for (int j = value; j < S.length - i - 1; j++) {
           
            if (isLeftMoreThanRight(S, j, j + 1)) {
                swap(S, j, j + 1);
            } else {
                stack[++top] = j;
            }
        }
        return top;
    }

    //this method checks if list is fully sorted
    public static boolean bubbleSortCheck(int[] S) {
        for (int i = 1; i < S.length; i++) {
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
            // checkMemory();
            // it pushes biggest value to the back
            isUnsort = cocktailSort2(S, counter);

            //if no swapping occurs, it is full sorted
            if (!isUnsort) {
                break;
            }

            //  checkMemory();
            // then it pushes smallest value to the front
            // it take account of previous sorted values 
            // to prevent redundant comparison
            isUnsort = cocktailSort3(S, counter);

            counter++;
        }
        return S;
    }

    //first inner loop for cocktail sort. left to right
    public static boolean cocktailSort2(int[] S, int counter) {
        //counter keep tracks of number of sorted value infront

        boolean isUnsort = false;
        // checkMemory();

        for (int i = counter; i < S.length - 1 - counter; i++) {
            if (isLeftMoreThanRight(S, i, i + 1)) {
                swap(S, i, i + 1);
                isUnsort = true;
            }
        }

        //if no swapping occurs, it is full sorted

        return isUnsort;
    }

    //second inner loop for cocktail sort. right to left
    public static boolean cocktailSort3(int[] S, int counter) {
        //counter keep tracks of number of sorted value infront

        boolean isUnsort = false;
        // checkMemory();

        for (int i = S.length - 2 - counter; i > counter; i--) {
            if (isLeftMoreThanRight(S, i - 1, i)) {
                swap(S, i - 1, i);
                isUnsort = true;
            }
        }

        //if no swapping occurs, it is full sorted

        return isUnsort;
    }

    public static int[] insertionSort(int[] S) {
        // it has 2 for loops. if it cant swap, it will break loop 
        // and go to next iteration
        for (int i = 0; i < S.length - 1; i++) {
            checkMemory();
            insertionSort2(S, i);
        }
        return S;
    }

    //this is the inner loop for insertion sort
    public static void insertionSort2(int[] S, int i) {
        // it has 2 for loops. if it cant swap, it will break loop 
        // and go to next iteration

        //   checkMemory();
        for (int j = i; j > -1; j--) {
            if (isLeftMoreThanRight(S, j, j + 1)) {
                swap(S, j, j + 1);
            } else {
                break;
            }
        }
    }

    //this insertion perform insertion sort at certain area of the list
    public static int[] insertionSort(int[] S, int start, int end) {
        // it has 2 for loops. if it cant swap, it will break loop 
        // and go to next iteration
        for (int i = start; i < end; i++) {
            //   checkMemory();
            for (int j = i; j >= start ; j--) {
                if (isLeftMoreThanRight(S, j, j + 1)) {
                    swap(S, j, j + 1);
                } else {
                    break;
                }
            }
        }
        return S;
    }

    public static int[] insertionSortOpt(int[] S) {
        int left = S.length, right = S.length;
        int[] b = new int[S.length * 2];
        for (int i = 1; i < S.length; i++) {
            if (S[i] >= b[right]) {
                right++;
                b[right] = S[i];
                continue;
            }
            if (S[i] < b[left]) {
                left--;
                b[left] = S[i];
                continue;
            }
            int loc = right;
            while (S[i] < b[loc]) {
                loc--;
            }
            if (right - loc < loc - left) {
                int j = right + 1;
                while (j > loc + 1) {
                    b[j] = b[j - 1];
                    j--;
                }
                right++;
                b[loc + 1] = S[i];
            } else {
                int j = left - 1;
                while (j < loc) {
                    b[j] = b[j + 1];
                    j++;
                }
                left--;
                b[loc] = S[i];
            }
        }
        for (int i = 0; i < S.length; i++) {
            S[i] = b[left + i];
        }

        return S;
    }

    public static int[] selectionSort(int[] S) {

        for (int i = 0; i < S.length - 1; i++) {
            //index is used to store the index of the smallest value
            selectionSort2(S, i);
        }
        return S;
    }

    //inner loop for selection sort
    public static void selectionSort2(int[] S, int i) {
        int index = i;
        //index is used to store the index of the smallest value

        for (int j = i + 1; j < S.length; j++) {
            if (isLeftMoreThanRight(S, index, j)) {
                index = j;
            }
        }
        swap(S, index, i);

    }

    public static int[] selectionSortOpt(int[] S) {
        int length = S.length;
        int[] stack = new int[S.length];
        int counter = 0;

        while (length != 1) {
            checkMemory();
            counter = selectionSortOpt2(S, stack, length--, counter); 
        }
            
        return S;
    }

    public static int selectionSortOpt2(int[] S, int[] stack, int length, int counter) {
        int max, count;
        if (counter == 0) {
            stack[counter++] = 0;
        }
        max = stack[(counter--) - 1];

        count = max + 1;
        while (count < length) {
            if (isLeftMoreThanRight(S, count, max)) {
                stack[counter++] = count - 1;
                swap(S, count - 1, max);
                max = count;
            }
            count++;
        }
        swap(S, --length, max);
        return counter;
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
            shiftDown(S, 0, --counter);
        }
        return S;
    }

    public static int[] heapify(int[] S) {
        //parent is the starting point of maintaining the heap
        int parent = (S.length - 1) / 2;
        while (parent >= 0) {
            //the parent will decrease, meaning it will go higher and higher
            //and more tranversing is required
            shiftDown(S, parent--, S.length - 1);
        }
        return S;
    }

    public static void shiftDown(int[] S, int start, int end) {
        int parent = start;
          checkMemory();
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

    //classic merge sort
    public static int[] mergeSort(int[] S) {
        //recursive in nature, this is the returning point
        if (S.length < 26) {
            SortAlgorithm.insertionSort(S, 0, S.length - 1);
            //    checkMemory();
            return S;
        }
        int[] P = null, Q = null;
        //spliting the set into halves
        int[] R = new int[S.length / 2];
        int[] T = new int[S.length - R.length];

        //copy data to each subarrays
        for(int i = 0; i < R.length; i++)
                {
                    R[i] = S[i];
                }
        for(int i = 0; i < T.length; i++)
        {
             T[i] = S[R.length];
        }

        //it will further break down the set into smaller set
        P = mergeSort(R);
        Q = mergeSort(T);

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

    public static int[] mergeSortOpt(int[] S, int left, int right) {
        //recursive in nature, this is the returning point
        int midNo = right - left;
        int mid;
        if (midNo < 17) {
            SortAlgorithm.insertionSort(S, left, right);
            checkMemory();
            return S;
        } else {
            midNo /= 2;
            mid = left + midNo;
            mergeSortOpt(S, left, mid);
            mergeSortOpt(S, mid + 1, right);
        }

        int[] temp = new int[right + 1 - left];
        for(int i = 0; i < midNo + 1; i++)
        {
            temp[i] = S[left + i];
        }
        for (int i = 0; i < right - mid; i++) {
            temp[midNo + 1 + i] = S[right - i];
        }

        //once it becomes size 1 it will start merging
        return merge2(S, temp, left, right);
    }

    public static int[] merge2(int[] S, int[] temp, int left, int right) {
        //instantiate new array
        int i = 0, j = temp.length - 1;
   for (int k = 0; k < temp.length; k++){
    if (isLeftMoreThanRight(temp,i,j))
    {
        S[left + k] = temp[j--];
    }
    else
    {
        S[left + k] = temp[i++];
    }
   }
return S;
    }

    public static int[] shellSort(int[] S) {
        // perform an insertion sort for each gap sequence
        for (int gap : GAPS) {
            if (gap * 9 / 4 > S.length) {
                continue;
            }
            for (int i = gap; i < S.length; i++) {
                checkMemory();
                for (int j = i / gap; j > 0; j--) {
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
    
     //study guide quick sort
     public static int[] quickSort2(int[] S, int start, int end) {
     if (end - start < 17) {
     insertionSort(S, start, end);
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
     quickSort2(S, start, pivot - 1);
     quickSort2(S, pivot + 1, end);

     return S;
     }

    //sedwig
    public static int[] quickSortOpt(int[] S, int start, int end) {
        if (end - start < 18) {
             //  checkMemory();
            insertionSort(S, start, end);
            return S;
        }

        swap(S, start, (start + end) / 2);


        int v = start;
        int l = start + 1, r = end;
        while (true) {
            while (r >= l && isLeftMoreThanRight(S, v, l)) {
                l++;
            }
            while (r >= l && isLeftMoreThanRight(S, r, v)) {
                r--;
            }
            if (r < l) {
                break;
            }
            swap(S, l++, r--);
        }
        swap(S, start, r);

        quickSortOpt(S, start, r - 1);
        quickSortOpt(S, l, end);

        return S;
    }

    public static int[] quickSortDualPOpt(int[] S, int start, int end) {
        if (end - start < 13) {
                  checkMemory();
            insertionSort(S, start, end);
            return S;
        }

        swap(S, start, (end - start) / 3 + start);
        swap(S, end, 2 * (end - start) / 3 + start);

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

        quickSortDualPOpt(S, start, midL - 2);
        quickSortDualPOpt(S, midL, midR - 1);
        quickSortDualPOpt(S, rearL + 2, end);

        return S;
    }
}
