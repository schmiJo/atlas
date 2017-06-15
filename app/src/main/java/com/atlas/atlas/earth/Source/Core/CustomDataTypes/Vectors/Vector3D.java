package com.atlas.atlas.earth.Source.Core.CustomDataTypes.Vectors;



import java.util.ArrayList;
import java.util.List;

public class Vector3D {

    public double x, y, z;

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public Vector3D add(Vector3D value){
        return new Vector3D(x+value.x, y+value.y,z+value.z);
    }
    public Vector3D substract(Vector3D value){
        return new Vector3D(x-value.x,y-value.y,z-value.z);
    }
    public Vector3D multiplyComponents(double value){
        return new Vector3D(x*value, y*value,z*value);
    }
    public Vector3D multiplyComponents(Vector3D scale) {
        return new Vector3D(this.x * scale.x, this.y * scale.y, this.z * scale.z);
    }
    public Vector3D divideComponents(double value) {
      return new Vector3D(x/value, y/value, z/value);
    }
    public Vector3D normalize(){
        return divideComponents(getMagnitude());
    }
    public Vector3D normalize(int length) {
        normalize();
        return multiplyComponents(length);
    }
    public Vector3D cross(Vector3D value){
        return new Vector3D(
                y*value.z - z*value.y,
                z*value.x - x*value.z,
                x*value.y - y*value.x
        );
    }
    public double dot(Vector3D value){
        return (x*value.x + y*value.y + z*value.z);
    }
    public boolean isBigger(Vector3D value){
        return (x > value.x) && (y > value.y) && (z > value.z);
    }

    public double angleBetween(Vector3D value){
        return Math.acos(normalize().dot(value.normalize()));
    }

    public double getMagnitudeSquared() {
        return x * x + y * y + z * z;
    }
    public double getMagnitude(){
         return Math.sqrt(getMagnitudeSquared());
    }

    public Vector3D mostOrthogonalAxis(){
        double x = Math.abs(this.x);
        double y = Math.abs(this.y);
        double z = Math.abs(this.z);

        if ((x < y) && (x < z))
        {
            return new Vector3D(1,0,0);
        }
        else if ((y < x) && (y < z))
        {
             return new Vector3D(0,1,0);
        }
        else
        {
            return new Vector3D(0,0,1);
        }
    }
    public Vector3F toVector3F(){
        return new Vector3F((float) x,(float) y,(float) z);
    }
    public static List<Vector3F> toVector3FArray(List<Vector3D> values){
        List<Vector3F> result = new ArrayList<>(values.size());
        for(Vector3D value : values){
            result.add(value.toVector3F());
        }
        return result;
    }


    @Override
    public String toString() {
        return x + ", " + y + ", " + z;
    }



    public static double[] toArray(List<Vector3D> positions){
        double[] result = new double[positions.size()*3];
        short arrayIndex = 0;
        for(int listIndex = 0; listIndex < positions.size(); listIndex++){
            result[arrayIndex] = positions.get(listIndex).x;
            arrayIndex++;
            result[arrayIndex] = positions.get(listIndex).y;
            arrayIndex++;
            result[arrayIndex] = positions.get(listIndex).z;
            arrayIndex++;
        }
        return result;
    }

}
