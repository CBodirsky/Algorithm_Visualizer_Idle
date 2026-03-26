
//Selection Sort selects a position and scans the array for the smallest item, then
//swaps the position. This continues until all elements have been positioned.
package algorithms;

public class SelectionSort implements SortAlgorithm {

    private int[] arr;
    private int i = 0;
    private int j = 1;
    private int minIndex = 0;
    private boolean swapping = false;
    private int[] swap = null;

    public SelectionSort(int[] arr) {
        this.arr = arr;
    }

    @Override
    public boolean step() {
        swap = null;

        if (i >= arr.length - 1) return false;

        if (!swapping) {
            if (arr[j] < arr[minIndex]) {
                minIndex = j;
            }

            j++;

            if (j >= arr.length) {
                swapping = true;
            }

            return false;
        }

        if (swapping) {
            if (minIndex != i) {
                swap = new int[]{i, minIndex};

                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }

            i++;
            j = i + 1;
            minIndex = i;
            swapping = false;

            return swap != null;
        }

        return false;
    }

    @Override
    public int[] getArray() {
        return arr;
    }

    @Override
    public int[] getSwapIndices() {
        return swap;
    }

    @Override
    public int[] getActiveIndices() {
        // highlight current scan index and current min
        if (i >= arr.length - 1) return null;
        return new int[]{minIndex, j < arr.length ? j : minIndex};
    }

    @Override
    public String getName() {
        return "Selection";
    }

    @Override
    public boolean isFinished() {
        return i >= arr.length - 1;
    }
}
