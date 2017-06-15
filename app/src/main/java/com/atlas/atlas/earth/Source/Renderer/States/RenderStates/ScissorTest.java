package com.atlas.atlas.earth.Source.Renderer.States.RenderStates;

import android.support.constraint.solver.widgets.Rectangle;


public class ScissorTest {

    private boolean enabled;
    private Rectangle rectangle;

    public ScissorTest() {
        enabled = false;
        rectangle = new Rectangle();
        rectangle.setBounds(0, 0, 0, 0);
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
