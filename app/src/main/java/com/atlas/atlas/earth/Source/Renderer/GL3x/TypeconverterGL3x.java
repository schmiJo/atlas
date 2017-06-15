package com.atlas.atlas.earth.Source.Renderer.GL3x;

import android.opengl.GLES31;


public class TypeconverterGL3x {

public static int convert(byte flag){
    switch (flag){
        //Depth testing
        case   1: return GLES31.GL_NEVER;
        case   2: return GLES31.GL_LESS;
        case   3: return GLES31.GL_EQUAL;
        case   4: return GLES31.GL_LEQUAL;
        case   5: return GLES31.GL_GREATER;
        case   6: return GLES31.GL_NOTEQUAL;
        case   7: return GLES31.GL_GEQUAL;
        case   8: return GLES31.GL_ALWAYS;
        //Facet culling
        case   9: return GLES31.GL_FRONT;
        case   10: return GLES31.GL_BACK;
        case  11: return GLES31.GL_FRONT_AND_BACK;
      //case  11: return GLES31.GL_;
      //case  12: return GLES31.GL_;
      //case  13: return GLES31.GL_;
      //case  14: return GLES31.GL_;
      //case  15: return GLES31.GL_;
      //case  16: return GLES31.GL_;
      //case  17: return GLES31.GL_;
      //case  18: return GLES31.GL_;
      //case  19: return GLES31.GL_;
      //case  20: return GLES31.GL_;
      //case  21: return GLES31.GL_;
      //case  22: return GLES31.GL_;
      //case  23: return GLES31.GL_;
      //case  24: return GLES31.GL_;
      //case  25: return GLES31.GL_;
      //case  26: return GLES31.GL_;
      //case  27: return GLES31.GL_;
      //case  28: return GLES31.GL_;
      //case  29: return GLES31.GL_;
      //case  30: return GLES31.GL_;
      //case  31: return GLES31.GL_;
      //case  32: return GLES31.GL_;
      //case  33: return GLES31.GL_;
      //case  34: return GLES31.GL_;
      //case  35: return GLES31.GL_;
      //case  36: return GLES31.GL_;
      //case  37: return GLES31.GL_;
      //case  38: return GLES31.GL_;
      //case  39: return GLES31.GL_;
      //case  40: return GLES31.GL_;
      //case  41: return GLES31.GL_;
      //case  42: return GLES31.GL_;
      //case  43: return GLES31.GL_;
      //case  44: return GLES31.GL_;
      //case  45: return GLES31.GL_;
      //case  46: return GLES31.GL_;
      //case  47: return GLES31.GL_;
      //case  48: return GLES31.GL_;
      //case  49: return GLES31.GL_;
        case  50: return GLES31.GL_ARRAY_BUFFER;
        case  51: return GLES31.GL_ELEMENT_ARRAY_BUFFER;
        case  52: return GLES31.GL_PIXEL_PACK_BUFFER;
        case  53: return GLES31.GL_PIXEL_UNPACK_BUFFER;
        case  54: return GLES31.GL_UNIFORM_BUFFER;
      //case  55: return GLES31.GL_TEXTURE_BUFFER;
        case  56: return GLES31.GL_TRANSFORM_FEEDBACK_BUFFER;
        case  57: return GLES31.GL_COPY_READ_BUFFER;
        case  58: return GLES31.GL_COPY_WRITE_BUFFER;
        case  59: return GLES31.GL_STREAM_DRAW;
        case  60: return GLES31.GL_STREAM_READ;
        case  61: return GLES31.GL_STREAM_COPY;
        case  62: return GLES31.GL_STATIC_DRAW;
        case  63: return GLES31.GL_STATIC_READ;
        case  64: return GLES31.GL_STATIC_COPY;
        case  65: return GLES31.GL_DYNAMIC_DRAW;
        case  66: return GLES31.GL_DYNAMIC_READ;
        case  67: return GLES31.GL_DYNAMIC_COPY;
      //case  68: return GLES31.GL_;
      //case  69: return GLES31.GL_;
      //case  70: return GLES31.GL_;
      //case  71: return GLES31.GL_;
      //case  72: return GLES31.GL_;
      //case  73: return GLES31.GL_;
      //case  74: return GLES31.GL_;
      //case  75: return GLES31.GL_;
      //case  76: return GLES31.GL_;
      //case  77: return GLES31.GL_;
      //case  78: return GLES31.GL_;
      //case  79: return GLES31.GL_;
      //case  80: return GLES31.GL_;
      //case  81: return GLES31.GL_;
      //case  82: return GLES31.GL_;
      //case  83: return GLES31.GL_;
      //case  84: return GLES31.GL_;
      //case  85: return GLES31.GL_;
      //case  86: return GLES31.GL_;
      //case  87: return GLES31.GL_;
      //case  88: return GLES31.GL_;
      //case  89: return GLES31.GL_;
      //case  90: return GLES31.GL_;
      //case  91: return GLES31.GL_;
      //case  92: return GLES31.GL_;
      //case  93: return GLES31.GL_;
      //case  94: return GLES31.GL_;
      //case  95: return GLES31.GL_;
      //case  96: return GLES31.GL_;
      //case  97: return GLES31.GL_;
        case  98: return GLES31.GL_CW;
        case  99: return GLES31.GL_CCW;
        case 100: return GLES31.GL_SHORT;
        case 101: return GLES31.GL_FLOAT;
        case 102: return GLES31.GL_FLOAT_VEC2;
        case 103: return GLES31.GL_FLOAT_VEC3;
        case 104: return GLES31.GL_FLOAT_VEC4;
      //case 105: return GLES31.GL_;
      //case 106: return GLES31.GL_;
      //case 107: return GLES31.GL_;
      //case 108: return GLES31.GL_;
      //case 109: return GLES31.GL_;
      //case 110: return GLES31.GL_;
      //case 111: return GLES31.GL_;
      //case 112: return GLES31.GL_;
      //case 113: return GLES31.GL_;
      //case 114: return GLES31.GL_;
      //case 115: return GLES31.GL_;
      //case 116: return GLES31.GL_;
      //case 117: return GLES31.GL_;
      //case 118: return GLES31.GL_;
      //case 119: return GLES31.GL_;
        case 120: return GLES31.GL_UNSIGNED_SHORT;
        case 121: return GLES31.GL_UNSIGNED_INT;
      //case 122: return GLES31.GL_;
      //case 123: return GLES31.GL_;
      //case 124: return GLES31.GL_;
        default:  throw new IllegalArgumentException("Type conversion to OpenGL failed!");
    }

}

    public static boolean testForValidity(byte value, byte startingValue, byte endingValue){
        return (startingValue <=value && value <=endingValue);
    }
    public static void testForBiggerZero(int value){
        if(value < 0 ){
            throw new IllegalArgumentException("Value cant be zero");
        }
    }

}
