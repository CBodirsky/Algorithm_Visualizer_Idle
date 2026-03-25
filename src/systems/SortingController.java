package systems;

import core.Game;
import algorithms.SortAlgorithm;
import visuals.SwapAnimation;
import algorithms.BubbleSort;

import processing.core.PApplet;

public class SortingController {

        private SortAlgorithm algorithm;
        private SwapAnimation swapAnim;
        private Game game;
        private TileStats stats;

        public SortingController(SortAlgorithm algorithm, SwapAnimation swapAnim, Game game, TileStats stats) {
                this.algorithm = algorithm;
                this.swapAnim = swapAnim;
                this.game = game;
                this.stats = stats;
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
                swapAnim.duration = 8;
                swapAnim.start(swap[0], swap[1]);
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

        public void draw(PApplet app, float w, float h) {
                int[] arr = algorithm.getArray();
                int n = arr.length;

                float barW = w / n;

                int[] active = algorithm.getActiveIndices();
                int[] swap = getSwapIndices();

                float progress = swapAnim.active ? swapAnim.progress() : 1.0f;

                for (int i = 0; i < n; i++) {

                        float barH = (arr[i] / (float)n) * h;

                        //position of animation
                        float x = i * barW;

                        if (swap != null && swapAnim.active) {
                                int a = swap[0];
                                int b = swap[1];

                                if (i == a) {
                                        x = app.lerp(b * barW, a * barW, progress);
                                }else if (i == b) {
                                        x = app.lerp(a * barW, b * barW, progress);
                                }
                        }

                        boolean isActive = false;
                        boolean isSwapping = false;

                        if (active != null) {
                                for (int idx : active) {
                                        if (idx == i) {
                                                isActive = true;
                                                break;
                                        }
                                }
                        }

                        if (swap != null) {
                                if (swap[0] == i || swap[1] == i) {
                                        isSwapping = true;
                                }
                        }

                        if (isSwapping) {
                                app.fill(255, 100, 100);
                        } else if (isActive) {
                                app.fill(100, 150, 255);
                        } else {
                                app.fill(200);
                        }

                        app.rect(x, h - barH, barW, barH);
                }
        }


}
