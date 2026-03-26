
//Insertion Sort attempts to move a selected element right until it is larger than the compared
//element.
package algorithms;

public class InsertionSort implements SortAlgorithm {

    private int[] arr;
    private int i = 1;
    private int j = 1;
    private boolean shifting = false;
    private int[] swap = null;

    public InsertionSort(int[] arr) {
        this.arr = arr;
    }

    @Override
    public boolean step() {
        swap = null;

        if (i >= arr.length) return false;

        if (!shifting) {
            j = i;
            shifting = true;
        }

        if (j > 0 && arr[j - 1] > arr[j]) {
            swap = new int[]{j - 1, j};

            int temp = arr[j];
            arr[j] = arr[j - 1];
            arr[j - 1] = temp;

            j--;
            return true;
        } else {
            i++;
            shifting = false;
            return false;
        }
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
        // highlight the pair being compared / shifted
        if (!shifting || j <= 0) return null;
        return new int[]{j - 1, j};
    }

    @Override
    public String getName() {
        return "Insertion";
    }

    @Override
    public boolean isFinished() {
        return i >= arr.length;
    }
}
