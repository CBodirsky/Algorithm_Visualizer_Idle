package algorithms;

public interface SortAlgorithm {

    /** Perform one step of the algorithm.
     *  Return true if a swap occurred (so animations can trigger).
     */
    boolean step();

    /** Return true when the algorithm is finished. */
    boolean isFinished();

    /** Return the currently active indices being compared. */
    int[] getActiveIndices();

    /** Return the indices that were swapped on the last step. */
    int[] getSwapIndices();

    int[] getArray();
}
