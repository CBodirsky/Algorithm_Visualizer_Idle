
// Bubble Sort performs a comparison at each step, swapping if the second item
// is smaller, pushing larger items right to the end.
package algorithms;

public class BubbleSort implements SortAlgorithm {
    private int i = 0;
    private int j = 0;
    private int swapA;
    private int swapB;
    private boolean finished = false;
    private boolean didSwap = false;
    int[] arr;

    public int getI() { return i; }
    public int getJ() { return j; }
    public boolean didSwap() { return didSwap; }

    @Override
    public boolean isFinished() { return finished; }

    @Override
    public int[] getActiveIndices() {
        //stay in bounds, don't compare what's already sorted
//        if (j >= arr.length - i - 1) return null;
        //Currently active pair being compared
        return new int[] { j, j+1 };
    }

    @Override
    public int[] getSwapIndices() {
        //get swapping pair
        return didSwap ? new int[] {swapA, swapB} : null;
    }

    public BubbleSort(int[] arr) {
        this.arr = arr;
    }

    public int[] getArray() {
        return arr;
    }

    @Override
    public String getName() {
        return "Bubble";
    }

    // returns true when finished
    @Override
    public boolean step() {
//        System.out.println("Sorting array hash: " + System.identityHashCode(arr));
        didSwap = false;
        if (finished) return false;

        if (i < arr.length - 1) {
            if (j < arr.length - i - 1) {

                // Compare
                if (arr[j] > arr[j + 1]) {

                    swapA = j;
                    swapB = j + 1;
                    // Swap
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    didSwap = true;

                    return true;
                }

                j++;
                return false;
            } else {
                j = 0;
                i++;
            }
        } else {
            finished = true;
        }


        return false;
    }
}
