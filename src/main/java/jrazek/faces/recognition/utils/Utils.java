package jrazek.faces.recognition.utils;
import jrazek.faces.recognition.structure.neural.Weight;
import jrazek.faces.recognition.structure.neural.convolutional.ConvolutionWeight;
import jrazek.faces.recognition.utils.abstracts.Matrix2;

import javax.management.RuntimeErrorException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Utils {
    public static class Vector2Num<T extends Number>{
        private T x, y;
        public Vector2Num(T x, T y){
            this.x = x; this.y = y;
        }
        public T getX() {
            return x;
        }
        public T getY() {
            return y;
        }
        public void setX(T x) {
            this.x = x;
        }
        public void setY(T y) {
            this.y = y;
        }
    }

    public static class Vector3Num<T extends Number>{
        private T x, y, z;
        public Vector3Num(T x, T y, T z){
            this.x = x; this.y = y; this.z = z;
        }
        public T getX() {
            return x;
        }
        public T getY() {
            return y;
        }
        public T getZ(){
            return z;
        }
        public void setX(T x) {
            this.x = x;
        }
        public void setY(T y) {
            this.y = y;
        }
        public void setZ(T z) {
            this.z = z;
        }
    }
    public static double randomDouble(){
        Random r = new Random();
        int m = 1;
        if(randomBoolean())
            m = -1;
        return r.nextDouble()*m;
    }
    public static double randomDouble(double min, double max){
        Random r = new Random();
        double x = min + (max - min) * r.nextDouble();
        //System.out.println(x);
        return x;
    }
    public static int valuesMapSize(Map<?, List<?>> map){
        int sum = 0;
        for(Map.Entry<?, List<?>> entry : map.entrySet()){
            sum += entry.getValue().size();
        }
        return sum;
    }
    public static float randomFloat(float min, float max){
        Random r = new Random();
        return min + (max - min) * r.nextFloat();
    }
    public static int randomInt(){
        return randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    public static int randomInt(int min, int max){
        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }
    public static double sigmoid(double x) throws RuntimeErrorException{
        double c = (1d/(1d+Math.exp(-x)));
        if(c >= 1 || c < 0)
            throw new RuntimeErrorException(new Error("INVALID ACTIVATION VALUE!" + c + " from " + x));
        //System.out.println("sigmoid: " + c);
        return c;
    }
    public static float toGrayscale(int p){

        int a = (p>>24)&0xff;
        int r = (p>>16)&0xff;
        int g = (p>>8)&0xff;
        int b = p&0xff;
        //making it proportional for human eye
        return (0.3f * r) + (0.59f * g) + (0.11f * b);
    }
    public static double round(double num, int n) {
        num *= Math.pow(10, n);
        num = Math.round(num);
        return num / Math.pow(10, n);
    }
    public static boolean randomBoolean(){
        Random rand = new Random();
        return rand.nextBoolean();
    }
    public static class Domain{
        float min, max;
        public Domain(float min, float max){
            this.min = min;
            this.max = max;
        }
        public float getMin() {
            return min;
        }
        public float getMax() {
            return max;
        }
    }
    public static class Matrix2D extends Matrix2<Double> {
        public Matrix2D(Vector2Num<Integer> size){
            super(createArray(size));
        }
        public void add(Matrix2D other){
            if(other.getSize().getX().equals(this.getSize().getX()) && other.getSize().getY().equals(this.getSize().getY())){
                for(int y = 0; y < other.getSize().getY(); y++){
                    for(int x = 0; x < other.getSize().getX(); x++){
                        super.getValues()[x][y] += other.get(new Vector2Num<>(x,y));
                    }
                }
            }else throw new RuntimeException(new Error("ERROR121s "+ this.getSize() + " != "+ other.getSize()));
        }
        public Matrix3D maxPooling(){
            return null;
        }
    }
    public static class Matrix3D{
        private Vector3Num<Integer> size;
        private Matrix2D [] values;
        public Matrix3D(Vector3Num<Integer> size){
            this.size = size;
            values = new Matrix2D[size.getZ()];
            for(int i = 0; i < size.getZ(); i ++){
                values[i] = new Matrix2D(new Vector2Num<>(size.getX(), size.getY()));
            }
        }
        public void setZMatrix(int z, Matrix2D m){
            if(m.getSize().getX().equals(this.size.getX())&&m.getSize().getY().equals(this.size.getY()))
                values[z] = m;
            else throw new Error("ERROR2342421");
        }
        public Matrix2D getZMatrix(int z){
            return values[z];
        }

        public Vector3Num<Integer> getSize() {
            return size;
        }

        public void set(Vector3Num<Integer> c, double value){
            this.values[c.getZ()].set(new Vector2Num<>(c.getX(), c.getY()), value);
        }
        public double getValue(Vector3Num<Integer> c){
            return this.values[c.getZ()].get(new Vector2Num<>(c.getX(), c.getY()));
        }
    }
    public static Double [][] createArray(Vector2Num<Integer> size) {
        Double[][] arr = new Double[size.getX()][size.getY()];
        for(int x = 0; x < size.getX(); x++){
            for (int y = 0; y < size.getY(); y ++) {
                arr[x][y] = 0d;
            }
        }
        return arr;
    }
}
