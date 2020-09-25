package jrazek.faces.recognition.structure.neural.convolutional.kernels;

import jrazek.faces.recognition.structure.neural.convolutional.ConvolutionWeight;
import jrazek.faces.recognition.utils.Utils;
import jrazek.faces.recognition.utils.abstracts.Matrix2;

/**
 * This is just the layer of the kernelBox. The dumb not significant element
 */
public class Kernel extends Matrix2<ConvolutionWeight> {
    Kernel(Utils.Vector2Num<Integer> size){
        super(new ConvolutionWeight[size.getX()][size.getY()]);
    }
}
