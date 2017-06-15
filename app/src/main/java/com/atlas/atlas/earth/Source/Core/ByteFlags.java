package com.atlas.atlas.earth.Source.Core;




public class ByteFlags {

    public static final byte NULL                        =   0;

    //------Render States------//
    //DepthTest
    public static final byte NEVER                       =   1; //The depth test never passes.
    public static final byte LESS                        =   2; //Passes if the fragment's depth value is less than the stored depth value.
    public static final byte EQUAL                       =   3; //Passes if the fragment's depth value is equal to the stored depth value.
    public static final byte LESSTHANOREQUAL             =   4; //Passes if the fragment's depth value is less than or equal to the stored depth value.
    public static final byte GREATER                     =   5; //Passes if the fragment's depth value is greater than the stored depth value.
    public static final byte NOTEQUAL                    =   6; //Passes if the fragment's depth value is not equal to the stored depth value.
    public static final byte GREATERTHANOREQUAL          =   7; //Passes if the fragment's depth value is greater than or equal to the stored depth value.
    public static final byte ALWAYS                      =   8; //The depth test always passes.
    //~DepthTest

    //FaceCulling
    public static final byte FRONT                       =   9;
    public static final byte BACK                        =  10;
    public static final byte FRONTANDBACK                =  11;
    //~FaceCulling


    //~-----Render States------//

    //------Buffer------//
    //Buffer types
    public static final byte ARRAY_BUFFER                =  50;
    public static final byte ELEMENTARRAY_BUFFER         =  51;
    public static final byte PIXELPACK_BUFFER            =  52;
    public static final byte PIXELUNPACK_BUFFER          =  53;
    public static final byte UNIFORM_BUFFER              =  54;
    public static final byte TEXTURE_BUFFER              =  55;
    public static final byte TRANSFORMFEEDBACK_BUFFER    =  56;
    public static final byte COPYREAD_BUFFER             =  57;
    public static final byte COPYWRITE_BUFFER            =  58;
    //~Buffer types
    //Buffer usage hints
    public static final byte STREAM_DRAW                 =  59;
    public static final byte STREAM_READ                 =  60;
    public static final byte STREAM_COPY                 =  61;
    public static final byte STATIC_DRAW                 =  62;
    public static final byte STATIC_READ                 =  63;
    public static final byte STATIC_COPY                 =  64;
    public static final byte DYNAMIC_DRAW                =  65;
    public static final byte DYNAMIC_READ                =  66;
    public static final byte DYNAMIC_COPY                =  67;
    //~Buffer usage hints
    //~-----Buffer------//

    //------Mesh------//
    //Winding Order
    public static final byte CLOCKWISE                   =  98;
    public static final byte COUNTERCLOCKWISE            =  99;
    //~-----Mesh------//

    //------Data Types------//
    //Vertex Attribute Data Type
    public static final byte SHORT                       = 100;
    public static final byte FLOAT                       = 101;
    public static final byte FLOAT_VEC2                  = 102;
    public static final byte FLOAT_VEC3                  = 103;
    public static final byte FLOAT_VEC4                  = 104;
    public static final byte INT                         = 105;



    //~Vertex Attribute Data Type
    //Indices Type
    public static final byte UNSIGNED_SHORT              = 120;
    public static final byte UNSIGNED_INT                = 121;
    //~Indices Type

    //~-----Mesh------//


    /**
     * All negative values are not implemented in the GL3x Converter!!!
     */
    //-----ShapeFiles------//
    //ShapeType
    public static final byte NULL_SHAPE                 =  -1;
    public static final byte POINT                      =  -2;
    public static final byte POLYLINE                   =  -3;
    public static final byte POLYGON                    =  -4;
    public static final byte MULTITOUCH                 =  -5;
    public static final byte POINT_Z                    =  -6;
    public static final byte POLYLINE_Z                 =  -7;
    public static final byte POLYGON_Z                  =  -8;
    public static final byte MULTI_POINT_Z              =  -9;
    public static final byte POINT_M                    = -10;
    public static final byte POLYLINE_M                 = -11;
    public static final byte POLYGON_M                  = -12;
    public static final byte MULTI_POINT_M              = -13;
    public static final byte MULTIPATCH                 = -14;
    //~ShapeType
    //~-----ShapeFiles------//

    //-----MeshDataTypes-----//

    //~----MeshDataTypes-----//
}
