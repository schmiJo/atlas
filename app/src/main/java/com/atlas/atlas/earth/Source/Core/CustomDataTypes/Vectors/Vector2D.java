package com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors;



import java.util.List;

public class Vector2D {

    public double x, y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }


    public Vector2D add(Vector2D value){
        return new Vector2D(x+value.x, y+value.y);
    }
    public Vector2D substract(Vector2D value){
        return new Vector2D(x-value.x, y-value.y);
    }
    public Vector2D multiplyComponents(double value){
        return new Vector2D(x*value, y*value);
    }
    public Vector2D multiplyComponents(Vector2D scale) {
        return new Vector2D(this.x * scale.x, this.y * scale.y);
    }
    public Vector2D divideComponents(double value) {
      return new Vector2D(x/value, y/value);
    }
    public float dot(Vector2D value){
        //ScalarProdukt
        return (float)(x*value.x+y*value.y);
    }
    public Vector2D normalize(){
        return divideComponents(getMagnitude());
    }
    public Vector2D normalize(int length) {
        normalize();
        return multiplyComponents(length);
    }


    public double getMagnitudeSquared() {
        return x * x + y * y;
    }
    public double getMagnitude(){
         return Math.sqrt(getMagnitudeSquared());
    }


    @Override
    public String toString() {
        return x + ", " + y ;
    }
    public Vector2F toVector2F(){
        return new Vector2F((float)x, (float)y);
    }


    public static double[] toArray(List<Vector2D> positions){
        double[] result = new double[positions.size()*3];
        short arrayIndex = 0;
        for(int listIndex = 0; listIndex < positions.size(); listIndex++){
            result[arrayIndex] = positions.get(listIndex).x;
            arrayIndex++;
            result[arrayIndex] = positions.get(listIndex).y;
            arrayIndex++;
        }
        return result;
    }

}
