package visuals;

import processing.core.PApplet;

public class BarRenderer {

    public void drawBars(PApplet app, int[] arr, int[] activeIndices,
                         int[] swapIndices, float swapProgress, float topOffset) {

        float vizHeight = app.height - topOffset;
        float barWidth = (float) app.width / arr.length;
        float maxVal = arr.length;

        app.noStroke();

        for (int i = 0; i < arr.length; i++) {

            float x = i * barWidth;

            // Swap animation
            if (swapIndices != null && swapProgress < 1.0f) {
                int a = swapIndices[0];
                int b = swapIndices[1];

                if (i == a) {
                    x = app.lerp(b * barWidth, a * barWidth, swapProgress);
                } else if (i == b) {
                    x = app.lerp(a * barWidth, b * barWidth, swapProgress);
                }
            }

            float h = app.map(arr[i], 1, maxVal, 10, vizHeight - 10);

            // Highlight active indices
            boolean isActive = false;
            if (activeIndices != null) {
                for (int idx : activeIndices) {
                    if (i == idx) {
                        isActive = true;
                        break;
                    }
                }
            }

            if (isActive) {
                app.fill(100,150,255);
            } else {
                app.fill(200);
            }

            app.rect(x, app.height - h, barWidth - 2, h);
        }
    }
}
