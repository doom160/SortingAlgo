package sg.com.renfa;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public final class UITestbed extends JPanel {

    private static final int WIDTH = 610;
    private static final int HEIGHT = 655;
    private static final int HORIZON = 655;
    private static final int SPEED = 2400;
    final int[] gap = {525, 233, 103, 46, 20, 9, 4, 1};
    //UI related variables
    private static int noOfItem = 10;
    private static int horIncre, vertiIncre;
    private JButton startButton;
    private Timer timer;
    private JButton resetButton;
    private JComboBox sortTypeCB, noOfItemCB, dataSetTypeCB;
    private JLabel messageLbl;
    private ImageIcon startIcon, resetIcon, stopIcon;
    int[] list, mergeList;
    int currentIndex = noOfItem - 1;
    int noCompare = 0, noSwap = 0;
    String message;
    boolean isSorted = false;
    boolean isRunning = false;
    //sorting related variables
    int noIteration = 0, j = 0, k = 0, m = 0, n = 0, p = 0, cap = 0, h = 0, root = 2;
    int swap1 = 0, swap2 = 0, compare1 = 0, compare2 = 0;
    int start = 0, end = 9, l = 9;
    boolean isInvert = false, isHeap = false, isOpt = false;
    static LinkedList<Integer> S = new LinkedList<>();
    static LinkedList<Integer> T = new LinkedList<>();
    static LinkedList<Integer> R = new LinkedList<>();
    
    
    public UITestbed() {
        this.setBackground(Color.WHITE);

        /* Initialize timer, when timer is on, each tick triggers  
         * the sort selected. At the end of the call, it refreshes the number of
         * comparison and swap and repaint the bars
         */
        timer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRunning) {
                    timer.stop();
                    startButton.setEnabled(false);
                } else {
                    switch (sortTypeCB.getSelectedIndex()) {
                        case 0:
                            bubbleSortOpt();
                            break;
                        case 1:
                            insertionSort();
                            break;
                        case 2:
                            selectionSortOpt();
                            break;
                        case 3:
                            quickSortOpt();
                            break;
                        case 4:
                            quickDPSortO();
                            break;
                        case 5:
                            mergeSortOpt();
                            break;
                        case 6:
                            shellSort();
                            break;
                        case 7:
                            heapSort();
                            break;
                        case 8:
                            bubbleSort();
                            break;
                        case 9:
                            cocktailSort();
                            break;
                        case 10:
                            selectionSort();
                            break;
                        case 11:
                            quickSort();
                            break;
                        case 12:
                            quickDPSort();
                            break;
                        case 13:
                            mergeSort();
                            break;
                    }
                }
                initMessage();
                repaint();
            }
        });

        /* initialize every JControls, such as icons, labels, combobox items
         * button and its event holder. Add all the components into the frame
         */
        startIcon = new ImageIcon(getClass().getClassLoader().getResource("images/enablePlay.png"));
        stopIcon = new ImageIcon(getClass().getClassLoader().getResource("images/enableStop.png"));
        resetIcon = new ImageIcon(getClass().getClassLoader().getResource("images/enableReset.png"));

        messageLbl = new JLabel();

        sortTypeCB = new JComboBox();
        sortTypeCB.addItem("Bubble Opt");
        sortTypeCB.addItem("Insertion");
        sortTypeCB.addItem("Selection Opt");
        sortTypeCB.addItem("Quick Opt");
        sortTypeCB.addItem("D.P. Quick Opt");
        sortTypeCB.addItem("Merge Opt");
        sortTypeCB.addItem("Shell");
        sortTypeCB.addItem("Heap");
        sortTypeCB.addItem("Bubble");
        sortTypeCB.addItem("Cocktail");
        sortTypeCB.addItem("Selection");
        sortTypeCB.addItem("Quick");
        sortTypeCB.addItem("Quick D.P.");
        sortTypeCB.addItem("Merge");

        dataSetTypeCB = new JComboBox();
        dataSetTypeCB.addItem("Unique Random");
        dataSetTypeCB.addItem("Random");
        dataSetTypeCB.addItem("Nearly Sorted");
        dataSetTypeCB.addItem("Reverse");
        dataSetTypeCB.addItem("Few Unique");
        dataSetTypeCB.addItem("Sorted");
        dataSetTypeCB.addItem("Glassian Random");
        dataSetTypeCB.addItem("Zero Entity");

        noOfItemCB = new JComboBox();
        noOfItemCB.addItem("10");
        noOfItemCB.addItem("25");
        noOfItemCB.addItem("100");
        noOfItemCB.addItem("300");
        noOfItemCB.addItem("600");

        startButton = new JButton();
        startButton.setIcon(startIcon);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isRunning) {
                    mergeList = list.clone();
                    R.clear();
                    if (sortTypeCB.getSelectedIndex() == 13) {
                        mergeList = mergeSortCheck2(mergeList, 0, noOfItem);
                    } else {
                        mergeList = mergeSortCheck(mergeList, 0, noOfItem);
                    }
                    start();
                } else {
                    stop();
                }
            }
        });

        resetButton = new JButton();
        resetButton.setIcon(resetIcon);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });

        add(sortTypeCB);
        add(dataSetTypeCB);
        add(noOfItemCB);
        add(startButton);
        add(resetButton);
        add(messageLbl);

        initList();
        initMessage();
        
        
    }

    //message updated every loop
    public void initMessage() {
        message = "<html>Compare: " + noCompare + "<br/>Swap: " + noSwap;
        message += "</html>";
        messageLbl.setText(message);
    }

    //to start sorting
    public void start() {

        timer.setDelay(SPEED / noOfItem);
        timer.start();
        isRunning = true;
        startButton.setIcon(stopIcon);
        resetButton.setEnabled(false);
        noOfItemCB.setEnabled(false);
        dataSetTypeCB.setEnabled(false);
        sortTypeCB.setEnabled(false);
    }

    //to stop sorting
    public void stop() {
        timer.stop();
        isRunning = false;
        resetButton.setEnabled(true);
        noOfItemCB.setEnabled(true);
        dataSetTypeCB.setEnabled(true);
        sortTypeCB.setEnabled(true);
        startButton.setEnabled(false);
        startButton.setIcon(startIcon);      
    }

    //reset all variables and data
    public void reset() {
        initList();
        currentIndex = noOfItem - 1;
        noIteration = 0;
        j = 0;
        k = 0;
        m = 0;
        n = 0;
        p = 0;
        h = 0;
        timer.stop();
        repaint();
        startButton.setEnabled(true);
        swap1 = 0;
        swap2 = 0;
        noSwap = 0;
        noCompare = 0;
        isSorted = false;
        isOpt = false;
        compare1 = 0;
        compare2 = 0;
        cap = 0;

        //quicksort
        start = 0;
        end = list.length - 1;
        isInvert = false;
        l = end;
        S.clear();
        T.clear();
        //heapsort
        root = (list.length - 1) / 2;
        
    }

    /* initialize the dataset and clone the dataset. the duplicated dataset 
     * is sorted and compared with original dataset to know which data is sorted
     */
    public void initList() {
        noOfItem = Integer.parseInt((String) noOfItemCB.getItemAt(noOfItemCB.getSelectedIndex()));
        list = new int[noOfItem];
        switch (dataSetTypeCB.getSelectedIndex()) {
            case 0:
                //Random Data
                list = GenerateData.generateIntUnique(noOfItem);
                break;
            case 1:
                //Unique Data
                list = GenerateData.generateIntRandom(noOfItem);
                break;
            case 2:
                //90% sorted data set
                list = GenerateData.generateIntNearlySorted(noOfItem, 10);
                break;
            case 3:
                //Reversed Data
                list = GenerateData.generateIntReverse(noOfItem);
                break;
            case 4:
                //2 + index of unique data
                list = GenerateData.generateIntFewUnique(noOfItem, noOfItemCB.getSelectedIndex() + 2);
                break;
            case 5:
                //Sorted Data
                list = GenerateData.generateIntSorted(noOfItem);
                break;
            case 6:
                //Glassian Data
                list = GenerateData.generateIntGlasRandom(noOfItem);
                break;
            case 7:
                //Zero Entity
                list = GenerateData.generateZero(noOfItem);
                break;
            default:
                break;
        }
        
        R.clear();
        mergeList = list.clone();
        if (sortTypeCB.getSelectedIndex() == 13) {
            mergeList = mergeSortCheck2(mergeList, 0, noOfItem);
        } else {
            mergeList = mergeSortCheck(mergeList, 0, noOfItem);
        }

    }

    /*this method draws a single bar, it draws the bar if it is unsorted, sorted,
     * compared or swapped
     */
    public void drawItem(Graphics g, int item, int index) {
        int height = item * vertiIncre;
        int y = HORIZON - height;
        int x = index * horIncre;

        //black as unsorted
        g.setColor(Color.black);

        if (list[index] == mergeList[index]) {
            //grey as sorted postion
            g.setColor(Color.GRAY);
        }
        if ((index == compare1 || index == compare2) && !(compare1 == 0 && compare2 == 0)) {
            //blue as compared
            g.setColor(Color.red);
        }

        g.fillRect(10 + x, y, horIncre, height);

        if ((index == swap1 || index == swap2) && (swap1 != 0 && swap2 != 0)) {
            //red as swapped
            g.setColor(Color.blue);
            g.fillPolygon(new int[]{(10 + x) + horIncre / 2, (5 + x) + horIncre / 2, (15 + x) + horIncre / 2}, new int[]{655, 665, 665}, 3);
        }
    }

    /* this method redraws all bars every ticks
     * 
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        horIncre = (WIDTH - 10) / noOfItem;
        vertiIncre = 600 / noOfItem;
        for (int i = 0; i < list.length; i++) {
            drawItem(g, list[i], i);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    //this create the JFrame
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("Sort");
                frame.add(new UITestbed());
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setResizable(false);
            }
        });
    }

    //increment swap count whenever there is a swap
    public void swap(int j, int k) {
        if (j == k) {
            return;
        }
        int temp = list[j];
        list[j] = list[k];
        list[k] = temp;
        swap1 = j;
        swap2 = k;
        noSwap++;
    }

    /* both method is to generate the sorted list and also used to create a list
     * for mergesort animation
     */
    public static int[] mergeSortCheck(int[] T, int first, int last) {
        if (T.length < 17) {
            R.add(first);
            R.add(0);
            R.add(last);
            SortAlgorithm.insertionSort(T, 0, T.length - 1);
            return T;
        }
        int[] P = null, Q = null;
        if (T.length > 1) {
            int[] U = new int[T.length / 2];
            int[] V = new int[T.length - U.length];

            System.arraycopy(T, 0, U, 0, U.length);
            System.arraycopy(T, U.length, V, 0, V.length);

            P = mergeSortCheck(U, first, first + U.length);
            Q = mergeSortCheck(V, first + U.length, last);
        }
        R.add(first);
        R.add(P.length + first);
        R.add(last);
        return merge(P, Q);
    }

    public static int[] mergeSortCheck2(int[] T, int first, int last) {
        if (T.length == 1) {
            R.add(first);
            R.add(0);
            R.add(last);
            return T;
        }
        int[] P = null, Q = null;
        if (T.length > 1) {
            int[] U = new int[T.length / 2];
            int[] V = new int[T.length - U.length];

            System.arraycopy(T, 0, U, 0, U.length);
            System.arraycopy(T, U.length, V, 0, V.length);

            P = mergeSortCheck2(U, first, first + U.length);
            Q = mergeSortCheck2(V, first + U.length, last);
        }
        R.add(first);
        R.add(P.length + first);
        R.add(last);
        return merge(P, Q);
    }

    public static int[] merge(int[] P, int[] Q) {
        int[] O = new int[P.length + Q.length];
        int pC = 0, qC = 0;
        for (int i = 0; i < O.length; i++) {

            if (pC == P.length) {
                O[i] = Q[qC++];
            } else if (qC == Q.length) {
                O[i] = P[pC++];
            } else if (P[pC] <= Q[qC]) {
                O[i] = P[pC++];

            } else {
                O[i] = Q[qC++];
            }
        }
        return O;
    }

    /* Remaining of the codes are the sorting algorithm. the codes will not be
     * documentated due to the complexity of the code.
     * 
     * there are 4 different insertion sort, first one is the original inseriton 
     * sort. the rest are for different sorting algorithm for optimization.
     * these codes have different implementation and therefore codes are needed
     */
    public void selectionSort() {

        if (noIteration == list.length - 1) {
            stop();
            isSorted = true;
            return;
        }

        if (k == 0 && noIteration == 0) {
            k++;
        }
        compare1 = k;
        compare2 = j;
        noCompare++;
        if (list[k] > list[j]) {
            j = k;
        }

        if (k == list.length - 1 - noIteration) {
            swap(list.length - 1 - noIteration, j);
            noIteration++;
            j = 0;
            k = 1;
        } else {
            k++;
        }
    }

    public void selectionSortOpt() {
        if (m == 0) {
            end++;
            m = 1;
        }

        if (end != 1) {

            if (S.isEmpty() && j != 0) {
                S.add(0);
                j = S.removeLast();
                k = j + 1;
            }
            if (k == end && j == 0) {
                swap(j, end - 1);
                k = 1;
                end--;
                return;
            }

            if (k < end) {
                compare1 = k;
                compare2 = j;
                noCompare++;
                if (list[k] > list[j]) {
                    S.add(k - 1);
                    swap(k - 1, j);
                    j = k;
                }
                k++;
            } else {
                swap(end - 1, j);
                end--;
                j = S.removeLast();
                k = j + 1;
            }

        } else {
            stop();
            isSorted = true;
        }
    }

    public void insertionSort() {
        if (noIteration < list.length - 1 && j == 0) {
            noIteration++;
            j = noIteration;
        } else if (noIteration == list.length - 1 && j == 0) {
            stop();
            isSorted = true;
            return;
        }
        compare1 = j - 1;
        compare2 = j;
        noCompare++;
        if (list[j] < list[j - 1]) {
            swap(j, j - 1);
        } else {
            j = 1;
        }
        j--;
    }

    public void insertionSort2(int x, int y) {
        if (end <= start) {
            p = 0;
            int smallestIndex = 0;
            for (int i = 2; i < S.size(); i += 2) {
                if (S.get(i) < S.get(smallestIndex)) {
                    smallestIndex = i;
                }
            }
            if (S.size() == 0) {
                stop();
                isSorted = true;
                return;
            }

            start = S.remove(smallestIndex);
            end = S.remove(smallestIndex);
            j = start;
            n = end;
            h = 0;
            isInvert = false;
            return;
        }

        if (p == 0) {
            h = start;
        }
        compare1 = h;
        compare2 = h + 1;
        noCompare++;
        if (list[h] > list[h + 1]) {
            swap(h, h + 1);
            h--;
        }
        if (h < start || list[h] <= list[h + 1]) {
            p++;
            h = p + start;
            if (p == end - start) {
                if (S.isEmpty()) {
                    stop();
                    isSorted = true;
                    return;
                }
                p = 0;
                int smallestIndex = 0;
                for (int i = 2; i < S.size(); i += 2) {
                    if (S.get(i) < S.get(smallestIndex)) {
                        smallestIndex = i;
                    }
                }

                if (S.size() == 0) {
                    stop();
                    isSorted = true;
                    return;
                }
                start = S.remove(smallestIndex);
                end = S.remove(smallestIndex);
                j = start;
                n = end;
                h = 0;
                isInvert = false;
            }
        }
    }

    public void insertionSort3(int x, int y) {

        if (end <= start) {
            if (S.isEmpty()) {
                stop();
                isSorted = true;
                return;
            }
            p = 0;
            int smallestIndex = 0;
            for (int i = 2; i < S.size(); i += 2) {
                if (S.get(i) < S.get(smallestIndex)) {
                    smallestIndex = i;
                }
            }

            start = S.remove(smallestIndex);
            end = S.remove(smallestIndex);
            isInvert = false;
            m = start + 1;
            n = start + 1;
            l = end - 1;
            h = 0;
            isOpt = false;
            return;
        }

        if (p == 0) {
            h = start;
        }
        compare1 = h;
        compare2 = h + 1;
        noCompare++;
        if (list[h] > list[h + 1]) {
            swap(h, h + 1);
            h--;
        }
        if (h < start || list[h] <= list[h + 1]) {
            p++;
            h = p + start;
            if (p == end - start) {
                if (S.isEmpty()) {
                    stop();
                    isSorted = true;
                    return;
                }
                p = 0;
                int smallestIndex = 0;
                for (int i = 2; i < S.size(); i += 2) {
                    if (S.get(i) < S.get(smallestIndex)) {
                        smallestIndex = i;
                    }
                }

                start = S.remove(smallestIndex);
                end = S.remove(smallestIndex);
                isInvert = false;
                isOpt = false;
                m = start + 1;
                n = start + 1;
                l = end - 1;
                h = 0;
            }
        }
    }

    public void insertionSort4(int x, int y) {

        if (noIteration < y - x - 1 && h == x) {
            noIteration++;
            h = noIteration + x;
        }
        if (noIteration == y - x - 1 && h == x) {
            if (R.isEmpty()) {
                stop();
                isSorted = true;
                return;
            }

            j = R.removeFirst();
            m = R.removeFirst();
            k = 0;
            n = R.removeFirst();
            start = j;
            end = n;
            h = j;
            noIteration = 0;
            isInvert = false;
            return;
        }
        compare1 = h - 1;
        compare2 = h;
        noCompare++;
        if (list[h] < list[h - 1]) {
            swap(h, h - 1);

        } else {
            h = x + 1;
        }
        h--;
    }

    public void bubbleSort() {
        noCompare++;
        compare1 = noIteration;
        compare2 = noIteration + 1;
        if (list[noIteration] > list[noIteration + 1]) {
            swap(noIteration, noIteration + 1);
            j++;
        }
        noIteration++;

        if (noIteration == list.length - 1 - k) {
            if (j == 0 || k == list.length - 2) {
                stop();
                isSorted = true;
                return;
            }
            noIteration = 0;
            j = 0;
            k++;
        }
    }

    public void cocktailSort() {

        if (!isInvert) {
            noCompare++;
            compare1 = k;
            compare2 = k + 1;
            if (list[k] > list[k + 1]) {
                swap(k, k + 1);
                m++;
            }
            k++;
            if (k == l) {
                isInvert = true;
                l--;
                if (j > l || m == 0) {
                    stop();
                    isSorted = true;
                    return;
                }
                m = 0;

            }
        } else {
            noCompare++;
            compare1 = k;
            compare2 = k - 1;
            if (list[k - 1] > list[k]) {
                swap(k, k - 1);
                m++;
            }
            k--;
            if (k == j) {
                isInvert = false;
                j++;
                if (j > l || m == 0) {
                    stop();
                    isSorted = true;
                    return;
                }
                m = 0;
            }
        }
    }

    public void bubbleSortOpt() {
        if (S.isEmpty()) {
            j = 0;
        }
        if (end == 0) {
            stop();
            isSorted = true;
            return;
        }
        if (j < end) {
            compare1 = j;
            compare2 = j + 1;
            noCompare++;
            if (list[j] > list[j + 1]) {
                swap(j, j + 1);
            } else {
                S.add(j);
            }
            j++;
        }
        if (j == end) {
            if (!S.isEmpty()) {
                j = S.removeLast();
            }
            end--;
        }

    }

    public void quickSort() {
        if (end <= start) {
            if (S.isEmpty()) {
                stop();
                isSorted = true;
                return;
            }
            int smallestIndex = 0;
            for (int i = 2; i < S.size(); i += 2) {
                if (S.get(i) < S.get(smallestIndex)) {
                    smallestIndex = i;
                }
            }

            start = S.remove(smallestIndex);
            end = S.remove(smallestIndex);
            j = start;
            n = end;
            isInvert = false;
            return;
        }


        if (start == j) {
            j = start + 1;
            n = end;
            return;
        }

        if (!isInvert) {
            compare1 = start;
            compare2 = j;
            noCompare++;
            if (n >= j && list[start] > list[j]) {
                j++;
            } else {
                isInvert = true;
            }
        } else {
            compare1 = start;
            compare2 = n;
            noCompare++;
            if (n >= j && list[n] > list[start]) {
                n--;
            } else {
                swap(j, n);
                j++;
                n--;
                isInvert = false;
            }
        }

        if (n < j) {
            swap(start, n);
            S.add(start);
            S.add(n - 1);
            S.add(j);
            S.add(end);

            int smallestIndex = 0;
            for (int i = 2; i < S.size(); i += 2) {
                if (S.get(i) < S.get(smallestIndex)) {
                    smallestIndex = i;
                }
            }
            start = S.remove(smallestIndex);
            end = S.remove(smallestIndex);
            j = start;
            n = end;
            isInvert = false;
        }
    }

    public void quickSortOpt() {

        if (end - start < 17) {
            insertionSort2(start, end);
            return;
        }

        if (start == j) {
            swap(start, (start + end) / 2);
            j = start + 1;
            n = end;
            return;
        }

        if (!isInvert) {
            compare1 = start;
            compare2 = j;
            noCompare++;
            if (n >= j && list[start] > list[j]) {
                j++;
            } else {
                isInvert = true;
            }
        } else {
            compare1 = start;
            compare2 = n;
            noCompare++;
            if (n >= j && list[n] > list[start]) {
                n--;
            } else {
                swap(j, n);
                j++;
                n--;
                isInvert = false;
            }
        }

        if (n < j) {
            swap(start, n);
            S.add(start);
            S.add(n - 1);
            S.add(j);
            S.add(end);

            int smallestIndex = 0;
            for (int i = 2; i < S.size(); i += 2) {
                if (S.get(i) < S.get(smallestIndex)) {
                    smallestIndex = i;
                }
            }
            start = S.remove(smallestIndex);
            end = S.remove(smallestIndex);
            j = start;
            n = end;
            isInvert = false;
        }
    }

    public void quickDPSort() {
        if (noCompare == 0) {
            m = start + 1;
            n = start + 1;
            l = end - 1;
        }


        while (start > end || start < 0 || end >= list.length) {
            if (S.size() == 0 && k > 0) {
                stop();
                isSorted = true;
                return;
            }

            int smallestIndex = 0;
            for (int i = 2; i < S.size(); i += 2) {
                if (S.get(i) < S.get(smallestIndex)) {
                    smallestIndex = i;
                }
            }
            start = S.remove(smallestIndex);
            end = S.remove(smallestIndex);
            isInvert = false;
            m = start + 1;
            n = start + 1;
            l = end - 1;
            // return;
        }

        if (list[start] > list[end]) {
            compare1 = end;
            compare2 = start;
            noCompare++;
            swap(start, end);
            return;
        }

        if (n > l) {
            swap(start, m - 1);
            swap(end, l + 1);


            S.add(start);
            S.add(m - 2);
            S.add(m);
            S.add(n - 1);
            S.add(l + 2);
            S.add(end);
            k++;
            int smallestIndex = 0;
            for (int i = 2; i < S.size(); i += 2) {
                if (S.get(i) < S.get(smallestIndex)) {
                    smallestIndex = i;
                }
            }
            start = S.remove(smallestIndex);
            end = S.remove(smallestIndex);
            isInvert = false;
            m = start + 1;
            n = start + 1;
            l = end - 1;
            return;
        }

        if (!isInvert) {
            compare1 = n;
            compare2 = start;
            noCompare++;
            if (list[n] < list[start]) {
                swap(m, n);
                m++;
                n++;
            } else {
                isInvert = true;
            }
        } else {
            compare1 = n;
            compare2 = end;
            noCompare++;
            if (list[n] > list[end]) {
                swap(l, n);
                l--;
            } else {
                n++;
            }
            isInvert = false;
        }
    }

    public void quickDPSortO() {
        if (noCompare == 0) {
            m = start + 1;
            n = start + 1;
            l = end - 1;
        }

        if (end - start < 13) {
            insertionSort3(start, end);
            return;
        }

        while (start > end || start < 0 || end >= list.length) {
            if (S.size() == 0 && k > 0) {
                stop();
                isSorted = true;
                return;
            }

            int smallestIndex = 0;
            for (int i = 2; i < S.size(); i += 2) {
                if (S.get(i) < S.get(smallestIndex)) {
                    smallestIndex = i;
                }
            }
            start = S.remove(smallestIndex);
            end = S.remove(smallestIndex);
            isInvert = false;
            m = start + 1;
            n = start + 1;
            l = end - 1;
            // return;
        }

        if (!isOpt) {
            swap(start, (end - start) / 3 + start);
            swap(end, 2 * (end - start) / 3 + start);
            isOpt = true;
        }

        if (list[start] > list[end]) {
            compare1 = end;
            compare2 = start;
            noCompare++;
            swap(start, end);
            return;
        }

        if (n > l) {
            swap(start, m - 1);
            swap(end, l + 1);
            S.add(start);
            S.add(m - 2);
            S.add(m);
            S.add(n - 1);
            S.add(l + 2);
            S.add(end);
            k++;
            int smallestIndex = 0;
            for (int i = 2; i < S.size(); i += 2) {
                if (S.get(i) < S.get(smallestIndex)) {
                    smallestIndex = i;
                }
            }

            start = S.remove(smallestIndex);
            end = S.remove(smallestIndex);
            isOpt = false;
            isInvert = false;
            m = start + 1;
            n = start + 1;
            l = end - 1;
            return;
        }

        if (!isInvert) {
            compare1 = n;
            compare2 = start;
            noCompare++;
            if (list[n] < list[start]) {
                swap(m, n);
                m++;
                n++;
            } else {
                isInvert = true;
            }
        } else {
            compare1 = n;
            compare2 = end;
            noCompare++;
            if (list[n] > list[end]) {
                swap(l, n);
                l--;
            } else {
                n++;
            }
            isInvert = false;
        }
    }

    public void shellSort() {
        boolean hasSwap = false;
        if (noCompare == 0) {
            for (int i = 0; i < gap.length; i++) {
                if (gap[i] * 9 / 4 > list.length) {
                    noIteration++;
                } else {
                    break;
                }
            }
        }
        noCompare++;
        compare1 = j;
        compare2 = j + gap[noIteration];
        if (list[j] > list[j + gap[noIteration]]) {
            swap(j, j + gap[noIteration]);
            hasSwap = true;
        }
        j -= gap[noIteration];
        if (j < 0 || !hasSwap) {
            k++;
            j = k;
        }
        if (j + gap[noIteration] == list.length) {
            if (noIteration == gap.length - 1) {
                stop();
                isSorted = true;
                return;
            }
            noIteration++;
            j = 0;
            k = 0;
        }
    }

    public void mergeSort() {
        if (noIteration == 0) {
            noIteration = 1;
            while (m == 0) {
                j = R.removeFirst();
                m = R.removeFirst();
                k = m;
                n = R.removeFirst();
                start = j;
                end = n;
            }
        }

        compare1 = j;
        compare2 = m;
        if (j == k) {
            noCompare++;
            T.add(list[m]);
            m++;
        } else if (m == n) {
            noCompare += 2;
            T.add(list[j]);
            j++;
        } else if (list[j] <= list[m]) {
            noCompare += 3;
            T.add(list[j]);
            j++;
        } else {
            noCompare += 4;
            T.add(list[m]);
            m++;
        }

        if (j == k && m == n) {
            for (int i = 0; i < end - start; i++) {
                list[i + start] = T.removeFirst();
            }

            if (R.isEmpty()) {
                stop();
                isSorted = true;
                return;
            }
            m = 0;
            while (m == 0) {
                j = R.removeFirst();
                m = R.removeFirst();
                k = m;
                n = R.removeFirst();

                start = j;
                end = n;
            }
        }
    }

    public void mergeSortOpt() {
        if (!isOpt) {
            isOpt = true;
            j = R.removeFirst(); //j is first
            m = R.removeFirst(); // m is mid
            k = 0;
            n = R.removeFirst(); // n is last
            start = j;
            end = n;
        }

        if (m == 0) {
            insertionSort4(j, n);
            return;
        }

        if (!isInvert) {
            isInvert = true;
            int[] R = new int[n - m];
            System.arraycopy(list, m, R, 0, n - m);
            for (int i = 0; i < n - m; i++) {
                list[n - 1 - i] = R[i];
            }
            n -= 1;
        }

        compare1 = j;
        compare2 = n;
        noCompare++;
        if (list[j] <= list[n]) {
            T.add(list[j++]);
            k++;
        } else {
            T.add(list[n--]);
            k++;
        }

        if (end - start == k) {
            for (int i = 0; i < end - start; i++) {
                list[i + start] = T.removeFirst();
            }

            if (R.isEmpty()) {
                stop();
                isSorted = true;
                return;
            }
            j = R.removeFirst();
            m = R.removeFirst();
            k = m;
            n = R.removeFirst();
            start = j;
            end = n;
            h = j;
            k = 0;
            isInvert = false;
        }
    }

    public void heapSort() {
        if (!isInvert) {
            heapify();
        } else {
            // left here
            if (end > 0) {
                if (!isHeap) {
                    j = 0;
                    swap(0, end--);
                }

                tranverseDown(j, end);
            }
            if (end == 0) {
                stop();
                isSorted = true;
            }
        }
    }

    public void heapify() {
        if (root >= 0) {
            if (noCompare == 0) {
                j = root;
            }
            if ((j * 2 + 1) <= end) {
                tranverseDown(j, end);
            } else {
                root--;
                j = root;
            }
        } else {
            isInvert = true;
        }
    }

    public void tranverseDown(int start, int end) {
        noCompare++;
        isHeap = true;
        if ((start * 2 + 1) <= end && start > -1) {
            int child = start * 2 + 1;
            if (child + 1 <= end && list[child] < list[child + 1]) {
                child = child + 1;
            }
            compare1 = start;
            compare2 = child;
            if (list[start] < list[child]) {
                swap(start, child);
                j = child;
            } else {
                root--;
                j = root;
            }
        } else {
            isHeap = false;
        }
    }
}