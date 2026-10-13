
import java.util.Arrays;

// Strategy interface
interface SortingStrategy {
    void sort(int[] array);
}

// Strategy 1: Bubble Sort
class BubbleSort implements SortingStrategy {
    public void sort(int[] array) {
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
    public void sort(int[] array) {
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

// New Strategy: Insertion Sort
class InsertionSort implements SortingStrategy {
    public void sort(int[] array) {
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

// Context class
class Sorter {
    private SortingStrategy strategy;

    public Sorter(SortingStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(SortingStrategy strategy) {
        this.strategy = strategy;
    }

    public void performSort(int[] array) {
        strategy.sort(array);
    }
}

// Main program
public class StrategyPatternExample {
    public static void main(String[] args) {

        int[] numbers1 = {5, 1, 4, 2, 8};
        int[] numbers2 = {9, 3, 7, 1, 5};
        int[] numbers3 = {6, 2, 8, 4, 1};

        Sorter sorter = new Sorter(new BubbleSort());

        sorter.performSort(numbers1);
        System.out.println(Arrays.toString(numbers1));

        sorter.setStrategy(new QuickSort());
        sorter.performSort(numbers2);
        System.out.println(Arrays.toString(numbers2));

        sorter.setStrategy(new InsertionSort());
        sorter.performSort(numbers3);
        System.out.println(Arrays.toString(numbers3));
    }
}
