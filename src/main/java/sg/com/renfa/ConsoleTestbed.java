package sg.com.renfa;


//-XX:+PrintCompilation -server
public class ConsoleTestbed {

    static int size = 1000000;
    static int noRun = 50;
    static int growth = 1000000;
    static int noGrowth = 10;

    public static void main(String[] args) {
       /* double totalTime = 0;
        while (totalTime < 10000) {
            //int[] S = GenerateData.generateIntRandom(50);
            //int[] S = GenerateData.generateIntUnique(50);
            //int[] S = GenerateData.generateIntNearlySorted(50, 10);
            //int[] S = GenerateData.generateIntReverse(50);
            //int[] S = GenerateData.generateIntFewUnique(50, 10);
            //int[] S = GenerateData.generateIntSorted(50);

            SortAlgorithm.reset();
            //S = SortAlgorithm.bubbleSortOpt(S);
            //S = SortAlgorithm.cocktailSort(S);
            //S = SortAlgorithm.insertionSort(S);
            //S = SortAlgorithm.mergeSortOpt(S, 0, S.length - 1);
            //S = SortAlgorithm.selectionSortOpt(S);
            //S = SortAlgorithm.heapSort(S);
            //S = SortAlgorithm.shellSort(S);
            //S = SortAlgorithm.quickSortOpt(S, 0, S.length - 1);
            //S = SortAlgorithm.quickSortDualPOpt(S, 0, S.length - 1);
            //S = SortAlgorithm.bubbleSort(S);
            //S = SortAlgorithm.selectionSort(S);
            //SortAlgorithm.printSet(S);
            SortAlgorithm.bubbleSortCheck(S);
            totalTime++;
        }*/


        for (int l = 0; l < noGrowth; l++) {
            System.out.println("START of " + size);
            for (int i = 0; i < noRun; i++) {
                //int[] S = GenerateData.generateIntRandom(size);
                //int[] S = GenerateData.generateIntUnique(size);
                //int[] S = GenerateData.generateIntNearlySorted(size, 10);
                //int[] S = GenerateData.generateIntReverse(size);
                //int[] S = GenerateData.generateIntFewUnique(size, 10);
                //int[] S = GenerateData.generateIntSorted(size);

                SortAlgorithm.reset();

                //Start Running Time
                //long startTime = System.nanoTime();

                //S = SortAlgorithm.bubbleSortOpt(S);
                //S = SortAlgorithm.insertionSort(S);
                //S = SortAlgorithm.selectionSortOpt(S);
                //S = SortAlgorithm.heapSort(S);
                //S = SortAlgorithm.shellSort(S);
                //S = SortAlgorithm.mergeSortOpt(S, 0, S.length - 1);
                //S = SortAlgorithm.quickSortOpt(S, 0, S.length - 1);
                //S = SortAlgorithm.quickSortDualPOpt(S, 0, S.length - 1);
                //S = SortAlgorithm.bubbleSort(S);
                //S = SortAlgorithm.selectionSort(S);
                
                //Running Time
                //double difference = (System.nanoTime() - startTime) / 1000000.0;
                //System.out.println(difference);
                //SortAlgorithm.bubbleSortCheck(S);
                
                //Comparison and Swap
                //System.out.println(SortAlgorithm.noCompare + "\t" + SortAlgorithm.noSwap);
                
                //Memory testing
                //ensure that checkMemory() is uncommented in the algorithm System
                //System.out.println(SortAlgorithm.bytesToKilobytes(SortAlgorithm.getMemory()));
            }
            System.out.println("End of " + size);
            size += growth; 
        }
    }
}
