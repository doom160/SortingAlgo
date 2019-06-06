package sg.com.renfa;



import java.util.HashSet;
import java.util.Random;

public class GenerateData {

    public static void swap(int[] S, int i, int j) {
        if (j != i) {
            int temp = S[j];
            S[j] = S[i];
            S[i] = temp;
        }
    }

    public static int[] generateIntUnique(int size) {
        //return null if size is less than 1
        if (size < 1) {
            return null;
        }

        //declaration
        Random rdm = new Random();

        //initialize data from 1 to data size
        int[] S = generateIntSorted(size);

        //if set size = 1, no randomizing required
        if (size == 1) {
            return S;
        }

        //randomize position
        for (int i = 0; i < size; i++) {
            int j = rdm.nextInt(size);
            swap(S, i, j);
        }
        return S;
    }

    public static int[] generateIntRandom(int size) {
        //return null if size is less than 1
        if (size < 1) {
            return null;
        }

        //declaration
        Random rdm = new Random();
        int[] S = new int[size];

        //initialize random value between 1 to size
        for (int i = 0; i < size; i++) {
            S[i] = rdm.nextInt(size) + 1;
        }
        return S;
    }
    
    
     public static int[] generateIntGlasRandom(int size) {
        //return null if size is less than 1
        if (size < 1) {
            return null;
        }

        //declaration
        Random rdm = new Random();
        int[] S = new int[size];

        //initialize random value between 1 to size
        for (int i = 0; i < size; i++) {
                    int a = rdm.nextInt(size) + 1;
                    int b = rdm.nextInt(size) + 1;
                    int c = rdm.nextInt(size) + 1;
                    int d = rdm.nextInt(size) + 1;                  
                    S[i] = (a + b +c + d) / 4;
        }
        return S;
    }


    public static int[] generateIntNearlySorted(int size, int percentageOfUnsort) {
        //return null if size is less than 1
        if (size < 1) {
            return null;
        }

        //declaration
        Random rdm = new Random();
        HashSet<Integer> index = new HashSet<Integer>();

        //find out number of times to randomize, if it is 0, swap 1
        int noUnsortElement = size / percentageOfUnsort;
        noUnsortElement = (noUnsortElement == 0 || noUnsortElement == 1) ? 2 : noUnsortElement;

        //initialize data from 1 to size
        int[] S = generateIntSorted(size);

        //creating hashset where index are random
        for (int i = 0; i < noUnsortElement; i++) {
            int j = rdm.nextInt(size);
            while(index.contains(j))
            {
                j = rdm.nextInt(size);
            }
            index.add(j);
        }
        Object[] Q = index.toArray();
        //randomize index from 0 to noUnsortElement. 
        //then swap position in actual set
        //this forces swapped index to have possible two or more inter-swaps

        for (int i = 0; i < noUnsortElement - 1; i++) {
            int j = 0, k = 0;
            while (j == k) {
                j = rdm.nextInt(noUnsortElement);
                k = rdm.nextInt(noUnsortElement);
                
            }
            swap(S, (int)Q[j], (int)Q[k]);
        }
        
        return S;
    }

    public static int[] generateIntReverse(int size) {
        //return null if size is less than 1
        if (size < 1) {
            return null;
        }

        //declaration
        int[] S = new int[size];

        //initialize data in revere order
        for (int i = 0; i < size; i++) {
            S[i] = size - i;
        }
        return S;
    }

    public static int[] generateIntSorted(int size) {
        //return null if size is less than 1
        if (size < 1) {
            return null;
        }

        int[] S = new int[size];

        //initialize data in order
        for (int i = 0; i < size; i++) {
            S[i] = i + 1;
        }
        return S;
    }

    public static int[] generateIntFewUnique(int size, int noOfUnique) {
        //return null if size is less than 1
        if (size < 1) {
            return null;
        }

        //declaration
        Random rdm = new Random();
        int[] S = new int[size];
        //multiplier differentiate each unique. 
        //e.g. size 40, 3 unique means 13 as multiplier
        int multiplier = size / noOfUnique;


        //initialize data
        for (int i = 0; i < size; i++) {
            S[i] = (i % noOfUnique + 1) * multiplier;
        }

        //randomize index from 0 to size
        for (int i = 0; i < size; i++) {
            int j = rdm.nextInt(size);
            swap(S, i, j);
        }
        return S;
    }
    
    public static int[] generateZero(int size)
    {
         int[] S = new int[size];
         for(int i = 0; i < size; i++)
         {
             S[i] = size;
         }
         return S;
    }
}
