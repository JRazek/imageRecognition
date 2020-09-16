package jrazek.faces.recognition.utils;


import javax.management.RuntimeErrorException;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Utils {
    public static class Vector2D{
        private double x, y;
        public Vector2D(double x, double y){
            this.x = x; this.y = y;
        }
        public double getX() {
            return x;
        }
        public double getY() {
            return y;
        }
        public void setX(double x) {
            this.x = x;
        }
        public void setY(double y) {
            this.y = y;
        }
        public void add(Vector2D adder){
            x = x + adder.getX();
            y = y + adder.getY();
        }
    }

    public static class Vector3I{
        private int x, y, z;
        public Vector3I(int x, int y, int z){
            this.x = x; this.y = y; this.z = z;
        }
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
        public int getZ(){
            return z;
        }
        public void setX(int x) {
            this.x = x;
        }
        public void setY(int y) {
            this.y = y;
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
        return min + (max - min) * r.nextDouble();
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
}
