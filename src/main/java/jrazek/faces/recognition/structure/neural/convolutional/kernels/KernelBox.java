package jrazek.faces.recognition.structure.neural.convolutional.kernels;

import jrazek.faces.recognition.structure.neural.convolutional.ConvolutionWeight;
import jrazek.faces.recognition.utils.Utils;

public class KernelBox{
    private Utils.Vector3Num<Integer> size;
    Kernel[] kernels;

    public KernelBox(Utils.Vector3Num<Integer> size){
        this.size = size;
        kernels = new Kernel[size.getZ()];
        for(int i = 0; i < size.getZ(); i ++){
            kernels[i] = new Kernel(new Utils.Vector2Num<>(size.getX(), size.getY()));
        }
    }
    public void setZMatrix(int z, Kernel m){
        if(m.getSize().getX().equals(this.size.getX())&&m.getSize().getY().equals(this.size.getY()))
            kernels[z] = m;
        else throw new Error("ERROR2342421");
    }
    public Kernel getZMatrix(int z){
        return kernels[z];
    }
    public Utils.Vector3Num<Integer> getSize() {
        return size;
    }
    public int getTotalSize(){
        return size.getX()*size.getY()*size.getZ();
    }
    public void set(Utils.Vector3Num<Integer> c, double value){
        this.kernels[c.getZ()].get(new Utils.Vector2Num<>(c.getX(), c.getY())).setValue(value);
    }
    public void setWeight(Utils.Vector3Num<Integer> c, ConvolutionWeight value){
        this.kernels[c.getZ()].set(new Utils.Vector2Num<>(c.getX(), c.getY()), value);
    }
    public ConvolutionWeight getWeight(Utils.Vector3Num<Integer> c){
        return this.kernels[c.getZ()].get(new Utils.Vector2Num<>(c.getX(), c.getY()));
    }

}
