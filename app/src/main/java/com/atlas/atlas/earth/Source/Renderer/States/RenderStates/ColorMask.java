package com.atlas.atlas.earth.Source.Renderer.States.RenderStates;


public class ColorMask {

    private boolean red;
    private boolean green;
    private boolean blue;
    private boolean alpha;

    public ColorMask(boolean red, boolean green, boolean blue, boolean alpha) {

        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;

    }

    public boolean isAlpha() {
        return alpha;
    }

    public boolean isBlue() {
        return blue;
    }

    public boolean isGreen() {
        return green;
    }

    public boolean isRed() {
        return red;
    }


    public boolean equals(ColorMask other) {
        return this.red == other.red && this.blue == other.blue && this.green == other.green && this.alpha == other.alpha;
    }


}
