package com.atlas.atlas.earth.Source.Core.Scene.Shapefile;

import android.content.Context;
import android.content.res.Resources;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Rectangle2D;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector2D;
import com.atlas.atlas.earth.Source.Core.Scene.Shapefile.Shapes.PointShape;
import com.atlas.atlas.earth.Source.Core.Scene.Shapefile.Shapes.PolygonShape;
import com.atlas.atlas.earth.Source.Core.Scene.Shapefile.Shapes.PolylineShape;
import com.atlas.atlas.earth.Source.Core.Scene.Shapefile.Shapes.Shape;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;


public class Shapefile {

    private byte current_byteOrder;
    private static final byte byteOrder_LittleEndian = 0;
    private static final byte byteOrder_BigEndian = 1;
    private ShapeType shapeType;
    private Rectangle2D extent;
    private List<Shape> shapes;


    public Shapefile(int rawID, Context context) throws InvalidObjectException, Resources.NotFoundException {
        int fileHeaderLength = 100;
        int standardFileCode = 9994;
        int standardVersion = 1000;
        int recordHeaderLength = 8;
        shapeType = new ShapeType();

        if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            current_byteOrder = byteOrder_LittleEndian;
        } else {
            current_byteOrder = byteOrder_BigEndian;
        }
        // File Header:
        //
        // Position  Field         Value        Type     Byte Order
        // --------  -----         -----        ----     ----------
        // Byte 0    File Code     9994         Integer  Big
        // Byte 4    Unused        0            Integer  Big
        // Byte 8    Unused        0            Integer  Big
        // Byte 12   Unused        0            Integer  Big
        // Byte 16   Unused        0            Integer  Big
        // Byte 20   Unused        0            Integer  Big
        // Byte 24   File Length   File Length  Integer  Big
        // Byte 28   Version       1000         Integer  Little
        // Byte 32   Shape Type    Shape Type   Integer  Little
        // Byte 36   Bounding Box  Xmin         Double   Little
        // Byte 44   Bounding Box  Ymin         Double   Little
        // Byte 52   Bounding Box  Xmax         Double   Little
        // Byte 60   Bounding Box  Ymax         Double   Little
        // Byte 68*  Bounding Box  Zmin         Double   Little
        // Byte 76*  Bounding Box  Zmax         Double   Little
        // Byte 84*  Bounding Box  Mmin         Double   Little
        // Byte 92*  Bounding Box  Mmax         Double   Little
        //
        // * Unused, with value 0.0, if not Measured or Z type

        InputStream inputStream = context.getResources().openRawResource(rawID);
        BufferedInputStream buffInputStream = new BufferedInputStream(inputStream);

        byte[] fileHeader = read(buffInputStream, fileHeaderLength);


        if (fileHeader == null) {
            throw new InvalidObjectException("Could not read shapefile header.");
        }


        int fileCode = toInteger(fileHeader, 0, byteOrder_BigEndian);

        if (fileCode != standardFileCode) {
            // Swap byte order and try again.

            current_byteOrder = byteOrder_BigEndian;

            fileCode = toInteger(fileHeader, 0, byteOrder_BigEndian);


            if (fileCode != standardFileCode) {
                throw new InvalidObjectException("Could not read shapefile file code.  Is this a valid shapefile?");
            }

        }

        int fileLengthInBytes = toInteger(fileHeader, 24, byteOrder_BigEndian) * 2;

        int version = toInteger(fileHeader, 28, byteOrder_LittleEndian);

        if (version != standardVersion) {
            throw new InvalidObjectException("Shapefile standardVersion " + version + " is not supported.  Only standardVersion " + standardVersion + " is supported.");
        }

        shapeType.setShapeType((byte) toInteger(fileHeader, 32, byteOrder_LittleEndian));


        double xMin = toDouble(fileHeader, 36, byteOrder_LittleEndian);
        double yMin = toDouble(fileHeader, 44, byteOrder_LittleEndian);
        double xMax = toDouble(fileHeader, 52, byteOrder_LittleEndian);
        double yMax = toDouble(fileHeader, 60, byteOrder_LittleEndian);

        // TODO: Publicly expose these
        //double zMin = NoDataToZero(ToDouble(fileHeader, 68, ByteOrder.LittleEndian));
        //double zMax = NoDataToZero(ToDouble(fileHeader, 76, ByteOrder.LittleEndian));
        //double mMin = NoDataToZero(ToDouble(fileHeader, 84, ByteOrder.LittleEndian));
        //double mMax = NoDataToZero(ToDouble(fileHeader, 92, ByteOrder.LittleEndian));


        if (fileLengthInBytes == fileHeaderLength) {
            // If the shapefile is empty (that is, has no records),
            // the values for xMin, yMin, xMax, and yMax are unspecified.
            xMin = 0.0;
            yMin = 0.0;
            xMax = 0.0;
            yMax = 0.0;
        }

        this.extent = new Rectangle2D(new Vector2D(xMin, yMin), new Vector2D(xMax, yMax));
        //
        // Read each header...
        //

        //
        // Record Header:
        //
        // Position  Field           Value           Type     Byte Order
        // --------  -----           -----           ----     ----------
        // Byte 0    Record Number   Record Number   Integer  Big
        // Byte 4    Content Length  Content Length  Integer  Big
        //
        this.shapes = new ArrayList<>();
        byte[] recordHeader;

        while ((recordHeader = read(buffInputStream, recordHeaderLength)) != null) {
            int recordNumber = toInteger(recordHeader, 0, byteOrder_BigEndian);
            int contextLengthInBytes = toInteger(recordHeader, 4, byteOrder_BigEndian) * 2;
            byte[] record = read(buffInputStream, contextLengthInBytes);

            int recordShapeType = toInteger(record, 0, byteOrder_LittleEndian);
            switch (recordShapeType) {
                case ShapeType.NullShape:
                    //
                    // Filter out null shapes.  Otherwise, every client
                    // would have to deal with them.
                    //
                    break;

                case ShapeType.Point:
                    this.shapes.add(new PointShape(recordNumber,
                            new Vector2D(
                                    toDouble(record, 4, byteOrder_LittleEndian),
                                    toDouble(record, 12, byteOrder_LittleEndian))));
                    break;

                case ShapeType.Polyline:
                case ShapeType.Polygon:
                    Rectangle2D extent = new Rectangle2D(
                            new Vector2D(
                                    toDouble(record, 4, byteOrder_LittleEndian),
                                    toDouble(record, 12, byteOrder_LittleEndian)),
                            new Vector2D(
                                    toDouble(record, 20, byteOrder_LittleEndian),
                                    toDouble(record, 28, byteOrder_LittleEndian)));
                    int numberOfParts = toInteger(record, 36, byteOrder_LittleEndian);
                    int numberOfPoints = toInteger(record, 40, byteOrder_LittleEndian);

                    int[] parts = new int[numberOfParts];
                    Vector2D[] points = new Vector2D[numberOfPoints];

                    //
                    // These two loops can be optimized if the machine is little endian.
                    //
                    for (int i = 0; i < numberOfParts; ++i) {
                        parts[i] = toInteger(record, 44 + (4 * i), byteOrder_LittleEndian);
                    }

                    int pointsOffset = 44 + (4 * numberOfParts);
                    for (int i = 0; i < numberOfPoints; ++i) {
                        points[i] = new Vector2D(
                                toDouble(record, pointsOffset + (16 * i), byteOrder_LittleEndian),
                                toDouble(record, pointsOffset + (16 * i) + 8, byteOrder_LittleEndian));
                    }

                    if (recordShapeType == ShapeType.Polyline) {
                        shapes.add(new PolylineShape(recordNumber, extent, parts, points));
                    } else {
                        shapes.add(new PolygonShape(recordNumber, extent, parts, points));
                    }

                    break;

                default:
                    throw new InvalidObjectException("The shapefile type is not supported.  Only null, point, polyline, and polygon are supported.");
            }
        }
    }


    private int toInteger(byte[] buffer, int offset, int byteOrder) {
        if (byteOrder == current_byteOrder) {
            return ByteBuffer.wrap(buffer).getInt(offset);
        }

        byte[] swapped = new byte[4];
        swapped[0] = buffer[offset + 3];
        swapped[1] = buffer[offset + 2];
        swapped[2] = buffer[offset + 1];
        swapped[3] = buffer[offset];

        return ByteBuffer.wrap(swapped).getInt();
    }

    private double toDouble(byte[] buffer, int offset, int byteOrder) {
        if (byteOrder == current_byteOrder) {
            return ByteBuffer.wrap(buffer).getDouble(offset);
        }

        byte[] swapped = new byte[8];
        swapped[0] = buffer[offset + 7];
        swapped[1] = buffer[offset + 6];
        swapped[2] = buffer[offset + 5];
        swapped[3] = buffer[offset + 4];
        swapped[4] = buffer[offset + 3];
        swapped[5] = buffer[offset + 2];
        swapped[6] = buffer[offset + 1];
        swapped[7] = buffer[offset];

        return ByteBuffer.wrap(swapped).getDouble(0);
    }

    private static byte[] read(BufferedInputStream fileStream, int count) {
        byte[] buffer = new byte[count];

        int bytesRemaining = count;
        int offset = 0;

        while (bytesRemaining > 0) {
            int bytesRead = -1;
            try {
                bytesRead = fileStream.read(buffer, offset, bytesRemaining);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bytesRead <= 0) {
                return null;
            }

            offset += bytesRead;
            bytesRemaining -= bytesRead;
        }

        return (bytesRemaining == 0) ? buffer : null;
    }


    public ShapeType getShapeType() {
        return shapeType;
    }

    public Rectangle2D getExtent() {
        return extent;
    }

    public Shape getShape(int index) {
        return shapes.get(index);
    }

    public int getCount() {
        return shapes.size();
    }

    @Deprecated
    public List<Shape> getShapes() {
        return shapes;
    }
}
