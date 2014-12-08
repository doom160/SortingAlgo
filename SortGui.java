
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

public class SortGui extends JPanel {

    private static final int WIDTH = 600;
    private static final int HEIGHT = 650;
    private static final int HORIZON = 655;
    private static int noOfItem = 6;
    private static int horIncre, vertiIncre;
    private JButton startButton;
    private Timer timer;
    private JButton resetButton;
    private JComboBox sortTypeCB, noOfItemCB, dataSetTypeCB;
    private JLabel messageLbl;
    private ImageIcon startIcon, resetIcon, stopIcon;
    int[] list, checkList;
    int currentIndex = noOfItem - 1;
    int noIteration = 0, j = 0, k = 0, m = 0, n = 0, p = 0, cap = 0;
    int noCompare = 0, noSwap = 0;
    boolean isRunning = false;
    int swap1 = 0, swap2 = 0, compare1 = 0, compare2 = 0;
    boolean isSorted = false;
    String message;
    int start = 0, end = 5, l = 5;
    boolean isInvert = false, isHeap = false;
    LinkedList<Integer> S = new LinkedList<>();
    LinkedList<Integer> T = new LinkedList<>();
    int[] gap = {301, 132, 57, 23, 10, 4, 1};
    int root = 2;

    public SortGui() {
        this.setBackground(Color.WHITE);

        timer = new Timer(200, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isRunning) {
                    timer.stop();
                    startButton.setEnabled(false);
                } else {
                    switch (sortTypeCB.getSelectedIndex()) {
                        case 0:
                            bubbleSort();
                            break;
                        case 1:
                            cocktailSort();
                            break;
                        case 2:
                            insertionSort();
                            break;
                        case 3:
                            selectionSort();
                            break;
                        case 4:
                            quickSort();
                            break;
                        case 5:
                            quickSort2();
                            break;
                        case 6:
                            mergeSort();
                            break;
                        case 7:
                            shellSort();
                            break;
                        case 8:
                            heapSort();
                            break;
                    }
                }
                initMessage();
                repaint();
            }
        });

        startIcon = new ImageIcon(getClass().getResource("Resources/enablePlay.png"));
        stopIcon = new ImageIcon(getClass().getResource("Resources/enableStop.png"));
        resetIcon = new ImageIcon(getClass().getResource("Resources/enableReset.png"));

        messageLbl = new JLabel();

        sortTypeCB = new JComboBox();
        sortTypeCB.addItem("Bubble");
        sortTypeCB.addItem("Cocktail");
        sortTypeCB.addItem("Insertion");
        sortTypeCB.addItem("Selection");
        sortTypeCB.addItem("Quick");
        sortTypeCB.addItem("Quick Dual Pivot");
        sortTypeCB.addItem("Merge");
        sortTypeCB.addItem("Shell");
        sortTypeCB.addItem("Heap");

        dataSetTypeCB = new JComboBox();
        dataSetTypeCB.addItem("Random");
        dataSetTypeCB.addItem("Unique Random");
        dataSetTypeCB.addItem("Nearly Sorted");
        dataSetTypeCB.addItem("Reverse");
        dataSetTypeCB.addItem("Few Unique");
        dataSetTypeCB.addItem("Sorted");

        noOfItemCB = new JComboBox();
        noOfItemCB.addItem("6");
        noOfItemCB.addItem("30");
        noOfItemCB.addItem("100");
        noOfItemCB.addItem("300");
        noOfItemCB.addItem("600");

        startButton = new JButton();
        startButton.setIcon(startIcon);
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!isRunning) {
                    start();
                } else {
                    stop();
                }
            }
        });


        resetButton = new JButton();
        resetButton.setIcon(resetIcon);
        resetButton.addActionListener(new ActionListener() {
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

    public void initMessage() {
        message = "<html>Compare: " + noCompare + "<br/>Swap: " + noSwap;
        message += "</html>";
        messageLbl.setText(message);
    }

    public void start() {

        timer.setDelay(2400 / noOfItem);
        timer.start();
        isRunning = true;
        startButton.setIcon(stopIcon);
        resetButton.setEnabled(false);
        noOfItemCB.setEnabled(false);
        dataSetTypeCB.setEnabled(false);
        sortTypeCB.setEnabled(false);
    }

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

    public void reset() {
        initList();
        currentIndex = noOfItem - 1;
        noIteration = 0;
        j = 0;
        k = 0;
        m = 0;
        n = 0;
        p = 0;
        timer.stop();
        repaint();
        startButton.setEnabled(true);
        swap1 = 0;
        swap2 = 0;
        noSwap = 0;
        noCompare = 0;
        isSorted = false;
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

    public void initList() {
        noOfItem = Integer.parseInt((String) noOfItemCB.getItemAt(noOfItemCB.getSelectedIndex()));
        list = new int[noOfItem];
        switch (dataSetTypeCB.getSelectedIndex()) {
            case 0:
                //Random Data
                list = GenerateDataSet.generateIntRandom(noOfItem);
                break;
            case 1:
                //Unique Data
                list = GenerateDataSet.generateIntUnique(noOfItem);
                break;
            case 2:
                //90% sorted data set
                list = GenerateDataSet.generateIntNearlySorted(noOfItem, 10);
                break;
            case 3:
                //Reversed Data
                list = GenerateDataSet.generateIntReverse(noOfItem);
                break;
            case 4:
                //2 + index of unique data
                list = GenerateDataSet.generateIntFewUnique(noOfItem, noOfItemCB.getSelectedIndex() + 2);
                break;
            case 5:
                //Sorted Data
                list = GenerateDataSet.generateIntSorted(noOfItem);
                break;

            default:
                break;
        }
        checkList = list.clone();
        checkList = SortAlgorithm.quickSort(checkList, 0, checkList.length - 1);
    }

    public void drawItem(Graphics g, int item, int index) {
        int height = item * vertiIncre;
        int y = HORIZON - height;
        int x = index * horIncre;

        g.setColor(Color.black);
        if (list[index] == checkList[index]) {
            g.setColor(Color.GRAY);
        }
        if ((index == compare1 || index == compare2) && !(compare1 == 0 && compare2 == 0)) {
            g.setColor(Color.blue);
        }
        if ((index == swap1 || index == swap2) && (swap1 != 0 && swap2 != 0)) {
            g.setColor(Color.red);
        }
        g.fillRect(5 + x, y, horIncre, height);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Sort");
                frame.add(new SortGui());
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.setResizable(false);
            }
        });
    }

    public void swap(Integer[] list, int j, int k) {
        Integer temp = list[j].intValue();
        list[j] = list[k].intValue();
        list[k] = temp;
    }

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

    public void selectionSort() {

        //min index = j, k = current index;
        //noIteration = number of loops

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
        if (list[k] < list[j]) {
            j = k;
        }

        if (k == list.length - 1) {
            swap(noIteration, j);
            noIteration++;
            j = noIteration;
            k = noIteration + 1;
        } else {
            k++;
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        horIncre = WIDTH / noOfItem;
        vertiIncre = 600 / noOfItem;
        for (int i = 0; i < list.length; i++) {
            drawItem(g, list[i], i);
        }
    }

    public void insertionSort() {
        if (noIteration < list.length) {
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

    public void cocktailSort() {// l is end, k is start

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

    public void quickSort() {
        compare1 = l;
        compare2 = j;
        noCompare++;
        if (!isInvert) {
            if (list[l] < list[j]) {
                swap(j, l);
                j++;
                isInvert = true;
            } else {
                l--;
            }
        } else {
            if (list[l] < list[j]) {
                swap(j, l);
                l--;
                isInvert = false;
            } else {
                j++;
            }
        }
        if (j == l) {
            isInvert = false;
            if (start < j - 1) {
                S.add(start);
                S.add(j - 1);
            }
            if (l + 1 < end) {
                S.add(l + 1);
                S.add(end);
            }
            if (S.size() == 0) {
                stop();
                isSorted = true;
                return;
            } else {
                start = S.removeFirst();
                end = S.removeFirst();
                j = start;
                l = end;
                noIteration++;
            }
        }
    }

    public void quickSort2() {//start jkmn  pl end
        if (noCompare == 0) {
            m = start + 1;
            n = start + 1;
            l = end - 1;
        }
        if (S.size() == 0 && k > 0) {
            stop();
            isSorted = true;
            return;
        }


        if (start > end || start < 0 || end >= list.length) {
            start = S.removeFirst();
            end = S.removeFirst();
            isInvert = false;
            m = start + 1;
            n = start + 1;
            l = end - 1;
            return;
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
            start = S.removeFirst();
            end = S.removeFirst();
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
                if (gap[i] > list.length) {
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
            if (noIteration == 6) {
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
            m = 1;
            k = 1;
            n = 2;
        }
        if (noIteration > list.length) {
            stop();
            isSorted = true;
            return;
        }

        compare1 = j;
        compare2 = k;
        noCompare++;
        if (j == m) {
            S.add(list[k]);
            k++;
        } else if (k == n) {
            S.add(list[j]);
            j++;
        } else if (list[j] <= list[k]) {
            S.add(list[j]);
            j++;
        } else {
            S.add(list[k]);
            k++;
        }
        cap++;
        if (j == m && k == n) {
            for (int i = 0; i < cap; i++) {
                list[i + start] = S.removeFirst();
            }
            if (list.length == n) {
                noIteration *= 2;
                p = 0;
                j = p * noIteration * 2;
                n = (p + 1) * noIteration * 2;
                m = j + noIteration;
                k = m;
                start = 0;
                cap = 0;
                if (n > list.length) {
                    n = list.length;
                }
                return;
            }
            p++;
            j = p * noIteration * 2;
            n = (p + 1) * noIteration * 2;
            m = j + noIteration;
            k = m;
            start = j;
            cap = 0;
            if (m > list.length) {
                noIteration *= 2;
                p = 0;
                j = p * noIteration * 2;
                n = (p + 1) * noIteration * 2;
                m = j + noIteration;
                k = m;
                start = 0;
                cap = 0;
            }
            if (n > list.length) {
                n = list.length;
            }
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
                return;
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
            int child = start * 2 + 1;           //get left child of root
            //if root has 2 children and right > left point to right else remain left
            if (child + 1 <= end && list[child] < list[child + 1]) {
                child = child + 1;
            }
            compare1 = start;
            compare2 = child;
            if (list[start] < list[child]) {     //out of max-heap order
                swap(start, child);
                j = child;
            } else {
                root--;
                j = root;
                return;
            }
        } else {
            isHeap = false;
        }
    }
}
