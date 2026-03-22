package systems;

import core.Game;
import algorithms.SortAlgorithm;
import visuals.SwapAnimation;

public class SortingController {

        private SortAlgorithm algorithm;
        private SwapAnimation swapAnim;
        private Game game;

        public SortingController(SortAlgorithm algorithm, SwapAnimation swapAnim, Game game) {
                this.algorithm = algorithm;
                this.swapAnim = swapAnim;
                this.game = game;
        }

        public boolean isAnimating() {
                return swapAnim.active;
        }

        public void updateAnimation() {
                swapAnim.update();
        }

        public int[] getActiveIndices() {
                return algorithm.getActiveIndices();
        }

        public int[] getSwapIndices() {
                return swapAnim.active ? new int[]{swapAnim.a, swapAnim.b} : null;
        }

        public boolean isFinished() {
                return algorithm.isFinished();
        }

        public void triggerSwapAnimation() {
                int[] swap = algorithm.getSwapIndices();
                if (swap == null) return;
                int stepDelay = game.getStepDelay();
                int base = Math.max(1, stepDelay);

                swapAnim.duration = Math.max(3, (int)(base * 1.0f));
                swapAnim.start(swap[0], swap[1]);
//                if (swap != null) {
//                        swapAnim.start(swap[0], swap[1]);
//                }
        }

        public int[] getArray() {
                return algorithm.getArray();
        }

        /**
         * Performs one algorithm step.
         * Returns true if a swap occurred (so Game knows to animate).
         */
        public boolean step() {
                return algorithm.step();
        }

        public SortAlgorithm getAlgorithm() {
                return algorithm;
        }
}
