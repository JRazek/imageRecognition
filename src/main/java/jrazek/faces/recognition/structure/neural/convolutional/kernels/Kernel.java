package jrazek.faces.recognition.structure.neural.convolutional.kernels;

import jrazek.faces.recognition.structure.neural.convolutional.ConvolutionWeight;
import jrazek.faces.recognition.utils.Utils;
import jrazek.faces.recognition.utils.abstracts.Matrix2;

/**
 * This is just the layer of the kernelBox. The dumb not significant element
 */
public class Kernel extends Matrix2<ConvolutionWeight> {
    ConvolutionWeight[] vector;
    Kernel(Utils.Vector2Num<Integer> size){
        super(new ConvolutionWeight[size.getX()][size.getY()]);
        this.vector = new ConvolutionWeight[size.getY()*size.getY()];
    }

    @Override
    public void set(Utils.Vector2Num<Integer> c, ConvolutionWeight value) {
        vector[c.getX()+super.getSize().getY()*c.getY()] = value;
        super.set(c, value);
    }

    public ConvolutionWeight[] getAsVector(){
        return vector;
    }
}
