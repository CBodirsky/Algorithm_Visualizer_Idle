package UnitTesting;
import algorithms.BubbleSort;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestBubbleSort {
    @Test
    public void testBubbleSortSortsCorrectly() {
        int[] arr = {5, 3, 1, 4, 2};
        BubbleSort sort = new BubbleSort(arr);

        while (!sort.isFinished()) {
            sort.step();
        }

        assertArrayEquals(new int[]{1,2,3,4,5}, sort.getArray());
    }

}