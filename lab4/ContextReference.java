
import java.util.Arrays;


interface SortingStrategy {
    void sort();
}


class BubbleSort implements SortingStrategy {
    private Sorter context;

    public BubbleSort(Sorter context) {
        this.context = context;
    }

    public void sort() {
        int[] array = context.getData();

        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array.length - i - 1; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
        System.out.println("Sorting using Bubble Sort");
    }
}

// Strategy 2: Quick Sort
class QuickSort implements SortingStrategy {
    private Sorter context;

    public QuickSort(Sorter context) {
        this.context = context;
    }

    public void sort() {
        int[] array = context.getData();
        quickSort(array, 0, array.length - 1);
        System.out.println("Sorting using Quick Sort");
    }

    private void quickSort(int[] array, int low, int high) {
        if (low < high) {
            int pivot = partition(array, low, high);
            quickSort(array, low, pivot - 1);
            quickSort(array, pivot + 1, high);
        }
    }

    private int partition(int[] array, int low, int high) {
        int pivot = array[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (array[j] < pivot) {
                i++;
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;
            }
        }

        int temp = array[i + 1];
        array[i + 1] = array[high];
        array[high] = temp;

        return i + 1;
    }
}
class InsertionSort implements SortingStrategy {
    private Sorter context;

    public InsertionSort(Sorter context) {
        this.context = context;
    }

    public void sort() {
        int[] array = context.getData();

        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
        System.out.println("Sorting using Insertion Sort");
    }
}


class Sorter {
    private SortingStrategy strategy;
    private int[] data;

    public Sorter(int[] data) {
        this.data = data;
    }

    public void setStrategy(SortingStrategy strategy) {
        this.strategy = strategy;
    }

    public int[] getData() {
        return data;
    }

    public void performSort() {
        strategy.sort();
    }
}

public class ContextReference {
    public static void main(String[] args) {

        int[] numbers = {5, 1, 4, 2, 8};

        Sorter sorter = new Sorter(numbers);

        sorter.setStrategy(new BubbleSort(sorter));
        sorter.performSort();
        System.out.println(Arrays.toString(numbers));

        sorter.setStrategy(new QuickSort(sorter));
        sorter.performSort();
        System.out.println(Arrays.toString(numbers));

        sorter.setStrategy(new InsertionSort(sorter));
        sorter.performSort();
        System.out.println(Arrays.toString(numbers));
    }
}
