package algorithms;

public class CocktailShakerSort implements SortAlgorithm {
    private int left = 0;
    private int right;
    private int i = 0;
    private int j = 0;
    private int swapA;
    private int swapB;
    private boolean finished = false;
    private boolean didSwap = false;
    private boolean forward = true;

    int[] arr;

    public CocktailShakerSort(int[] arr) {
        this.arr = arr;
        this.right = arr.length - 1;
        this.j = left;
    }

//        public int getI() { return i; }
//        public int getJ() { return j; }
//        public boolean didSwap() { return didSwap; }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public int[] getActiveIndices() {
        //Currently active pair being compared
        return new int[]{j, j + 1};
    }

    @Override
    public int[] getSwapIndices() {
        //get swapping pair
        return didSwap ? new int[]{swapA, swapB} : null;
    }


    public int[] getArray() {
        return arr;
    }

    // returns true when finished
    @Override
    public boolean step() {
//        System.out.println("Sorting array hash: " + System.identityHashCode(arr));
        didSwap = false;
        if (finished) return false;

        if (forward) {
            if (j < right) {
                if (arr[j] > arr[j + 1]) {
                    swapA = j;
                    swapB = j + 1;

                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                    didSwap = true;
                    return true;
                }

                j++;
                return false;
                //end forward pass
            } else {
                right--;
                forward = false;
                j = right - 1;
            }
        }

        //Backward pass
        else {

            if (j >= left) {
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

                j--;
                return false;
            } else {
                left++;
                forward = true;
                j = left;

                if (left >= right) {
                    finished = true;
                }
            }
        }
        return false;
    }
}
