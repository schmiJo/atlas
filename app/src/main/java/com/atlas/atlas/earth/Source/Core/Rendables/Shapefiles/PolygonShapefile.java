package com.atlas.atlas.earth.Source.Core.Rendables.Shapefiles;

import android.graphics.Color;
import android.opengl.GLES31;
import android.util.Log;

import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector2D;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3D;
import com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors.Vector3F;
import com.atlas.atlas.earth.Source.Core.Geometry.CSConverter;
import com.atlas.atlas.earth.Source.Core.Geometry.geographicCS.Ellipsoid;
import com.atlas.atlas.earth.Source.Core.Geometry.geographicCS.Geodetic3D;
import com.atlas.atlas.earth.Source.Core.Matrices.MatricesUtility;
import com.atlas.atlas.earth.Source.Core.Rendables.OutlinedPolylineTexture;
import com.atlas.atlas.earth.Source.Core.Scene.Shapefile.Polygon;
import com.atlas.atlas.earth.Source.Core.Scene.Shapefile.ShapeType;
import com.atlas.atlas.earth.Source.Core.Scene.Shapefile.Shapefile;
import com.atlas.atlas.earth.Source.Core.Scene.Shapefile.ShapefileAppearance;
import com.atlas.atlas.earth.Source.Core.Scene.Shapefile.Shapes.PolygonShape;
import com.atlas.atlas.earth.Source.Core.Scene.Shapefile.Shapes.Shape;
import com.atlas.atlas.earth.Source.Core.Scene.Shapefile.Shapes.ShapePart;
import com.atlas.atlas.earth.Source.Renderer.GL3x.NamesGL3x.VertexArrayNameGL3x;
import com.atlas.atlas.earth.Source.Renderer.GL3x.ShaderGL3x.ShaderProgramGL3x;
import com.atlas.atlas.earth.Source.Renderer.Shader.ShapefileShaderProgram;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PolygonShapefile {


    private List<Polygon> polygons;
    private OutlinedPolylineTexture polyline;


    public PolygonShapefile(Shapefile shapefile, Ellipsoid globeShape, ShapefileAppearance appearance) throws InvalidObjectException {
        if (shapefile == null || globeShape == null || appearance == null) {
            throw new IllegalArgumentException("Value can't be null!");
        }

        polyline = new OutlinedPolylineTexture();
        polygons = new ArrayList<>();

        List<Integer> indices = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();

        Random r = new Random(3);


        for (Shape shape : shapefile.getShapes()) {

            boolean test = shape.getShapeType() != ShapeType.Polygon;
            if (test) {
                throw new InvalidObjectException("The type of an individual shape does not match the Shapefile type.");
            }
            PolygonShape polygonShape = (PolygonShape) shape;

            List<Vector3D> positions = new ArrayList<>();

            for (int j = 0; j < polygonShape.size(); ++j) {
                int color = Color.argb(127, r.nextInt(256), r.nextInt(256), r.nextInt(256));

                positions.clear();

                //Completely Accurate
                ShapePart part = polygonShape.getPart(j);

                for (int i = 0; i < part.size(); ++i) {
                    Vector2D point = part.getPosition(i);

                    //Completely Accurate
                    Geodetic3D pointInRadiant = CSConverter.toRadians(new Geodetic3D(point.x, point.y));
                    Vector3D pointInWGS84 = globeShape.ToVector3D(pointInRadiant);

                    positions.add(pointInWGS84);

                    //
                    // For polyline
                    //
                    //positions.add(globeShape.ToVector3D(CSConverter.toRadians(new Geodetic3D(point.x, point.y))));
                    colors.add(color);

                    if (i != 0) {
                        indices.add(positions.size() - 2);
                        indices.add(positions.size() - 1);
                    }
                }

                try {
                    Polygon p = new Polygon(globeShape, positions);
                    polygons.add(p);
                } catch (ArrayIndexOutOfBoundsException e) // Not enough positions after cleaning
                {
                    e.printStackTrace();
                }

            }
            if (polygons.size() >= 2) {
                break;
            }

        }


        Log.d("debug", "Ready");

    }

    public List<Polygon> getPolygons() {
        return polygons;
    }

    public void render(ShaderProgramGL3x shaderProgram, Vector3F position, float rotX, float rotY, float rotZ, float scale) {
        ShapefileShaderProgram shapefileShaderProgram = (ShapefileShaderProgram) shaderProgram;
        for (Polygon polygon : polygons) {

            shapefileShaderProgram.loadColor(polygon.getColor());

            shapefileShaderProgram.loadTransformationMatrix(MatricesUtility.createTransformationMatrix(
                    position,
                    rotX,
                    rotY,
                    rotZ,
                    scale));

            polygon.getMesh().getVertexArray().bindAndEnableVAO();
            polygon.getMesh().getIndicesBuffer().bind();

            GLES31.glDrawElements(GLES31.GL_TRIANGLES, polygon.getMesh().getVertexCount(), polygon.getMesh().getIndicesBuffer().getDataType(), 0);

            polygon.getMesh().getIndicesBuffer().unbind();
            VertexArrayNameGL3x.unbindAndDisableVAO();

        }
    }
}


