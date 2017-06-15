package com.atlas.atlas.earth.Source.Renderer.States.RenderStates;


public class StencilTestFace {

    //Stencil Operations
    private byte stencilFailOperation;
    private byte depthFailStencilPassOperation;
    private byte depthPassStencilPassOperation;
    public static final byte ZERO = 0;
    public static final byte INVERT = 1;
    public static final byte KEEP = 2;
    public static final byte REPLACE = 3;
    public static final byte INCREMENT = 4;
    public static final byte DECREMENT = 5;
    public static final byte INCREMENTWRAP = 6;
    public static final byte DECREMENTWRAP = 7;

    //Stencil Test Functions
    private byte function;
    public static final byte NEVER = 8;
    public static final byte LESS = 9;
    public static final byte EQUAL = 10;
    public static final byte LESSTHANOREQUAL = 11;
    public static final byte GREATER = 12;
    public static final byte NOTEQUAL = 13;
    public static final byte GREATERTHANOREQUAL = 14;
    public static final byte ALWAYS = 15;

    private int referenceValue;
    private int mask;

    public StencilTestFace() {
        stencilFailOperation = depthFailStencilPassOperation = depthPassStencilPassOperation = KEEP;
        function = ALWAYS;
        referenceValue = 0;
        mask =~0;
    }

    public int getMask() {
        return mask;
    }

    public void setMask(int mask) {
        this.mask = mask;
    }

    public int getReferenceValue() {
        return referenceValue;
    }

    public void setReferenceValue(int referenceValue) {
        this.referenceValue = referenceValue;
    }

    public byte getStencilFailOperation() {
        return stencilFailOperation;
    }

    public void setStencilFailOperation(byte stencilFailOperation) {
        this.stencilFailOperation = stencilFailOperation;
    }

    public byte getDepthPassStencilPassOperation() {
        return depthPassStencilPassOperation;
    }

    public void setDepthPassStencilPassOperation(byte depthPassStencilPassOperation) {
        this.depthPassStencilPassOperation = depthPassStencilPassOperation;
    }

    public byte getDepthFailStencilPassOperation() {
        return depthFailStencilPassOperation;
    }

    public void setDepthFailStencilPassOperation(byte depthFailStencilPassOperation) {
        this.depthFailStencilPassOperation = depthFailStencilPassOperation;
    }

    public byte getFunction() {
        return function;
    }

    public void setFunction(byte function) {
        this.function = function;
    }
}
