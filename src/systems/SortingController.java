
//The heart of the system. Controls a given tile's sorting behavior, pacing, and
//the swap animations.
package systems;

import core.Game;
import algorithms.SortAlgorithm;
import visuals.SwapAnimation;

import processing.core.PApplet;

public class SortingController {

        private SortAlgorithm algorithm;
        private SwapAnimation swapAnim;
        private Game game;
        private TileStats stats;

        public int stepCooldown = 0;

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

//        public int[] getActiveIndices() {
//                return algorithm.getActiveIndices();
//        }

        public int[] getSwapIndices() {
                return swapAnim.active ? new int[]{swapAnim.a, swapAnim.b} : null;
        }

        public boolean isFinished() {
                return algorithm.isFinished();
        }

        public void triggerSwapAnimation() {
                int[] swap = algorithm.getSwapIndices();
                if (swap == null) return;
                swapAnim.duration = Math.max(5, stats.sortSpeed);
                swapAnim.start(swap[0], swap[1]);
                stepCooldown = 0;

        }

        public int getSortSpeed() {
                return stats.sortSpeed;
        }

        public int[] getArray() {
                return algorithm.getArray();
        }

        // Advance the algorithm by one comparison or swap.
        // Returns true if a swap occured (so TileGrid can trigger animation).
        public boolean step() {
                return algorithm.step();
        }

        public SortAlgorithm getAlgorithm() {
                return algorithm;
        }

        //Draws the tile's bars, highlight active comparisons and animation swaps.
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

                        //Performs the animation of the swap
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

                        //Coloring of related bars
                        if (isSwapping) {
                                app.fill(80, 255, 80);
                        } else if (isActive) {
                                app.fill(255, 200, 0);
                        } else {
                                app.fill(70, 90, 140);
                        }

                        app.rect(x, h - barH, barW, barH);
                        app.fill(240);
                        app.textSize(14);
                        app.textAlign(PApplet.LEFT, PApplet.TOP);


                }
                app.text(algorithm.getName(), 4, 4);
        }


}
