package jrazek.faces.recognition.utils.abstracts;

import jrazek.faces.recognition.utils.Utils;

public class Matrix2 <T>{
    private Utils.Vector2Num<Integer> size;
    private T [][] values;
    public Matrix2(T[][] matrix){
        size = new Utils.Vector2Num<>(matrix.length, matrix[0].length);
        values = matrix;
    }
    public void setTotalValue(T[][] v){
        this.values = v;
    }
    public void set(Utils.Vector2Num<Integer> c, T value){
        if(c.getX() < values.length && c.getY() < values[0].length && c.getX() >= 0  && c.getY() >= 0)
            this.values[c.getX()][c.getY()] = value;
        else throw new Error("ERROR43575");
        //else System.out.println(c.getX() +" " + c.getY());
    }

    public T get(Utils.Vector2Num<Integer> c){
        return this.values[c.getX()][c.getY()];
    }
    public Utils.Vector2Num<Integer> getSize(){
        return size;
    }
    public int getTotalSize(){
        return size.getX()*size.getY();
    }
    public T[][] getValues() {
        return values;
    }

}
