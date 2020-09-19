package jrazek.faces.recognition.structure.neural.convolutional.interfaces;

import jdk.jshell.execution.Util;
import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.utils.Utils;

import java.util.LinkedList;

/**
 * this is actually an interface that is inherited by
 * every type of layer in convolution part. Why? because
 * theres always an output of some box in here. In ff theres only
 * 1D Vector of output and thats why its not used by ff.
 */
public interface ConvolutionNetLayer {
    Utils.Matrix3D getOutputBox();
    static int afterConvolutionSize(int matrixSize, int kernelSize, int padding, int stride){
        return  ((matrixSize - kernelSize + 2*padding)/stride)+1;
    }
    static Utils.Matrix2D convolve(Utils.Matrix2D matrix, Utils.Matrix2D kernel, int padding, int stride){
        if(kernel.getSize().getX() % 2 == 1 && kernel.getSize().getY() % 2 == 1){
            Utils.Matrix2D result = new Utils.Matrix2D(new Utils.Vector2Num<>(afterConvolutionSize(matrix.getSize().getX(), kernel.getSize().getX(), padding, stride), afterConvolutionSize(matrix.getSize().getY(), kernel.getSize().getY(), padding, stride)));//holy fix in here
            int toCenterX = kernel.getSize().getX()/2;
            int toCenterY = kernel.getSize().getY()/2;
            for(int y = toCenterY; y < matrix.getSize().getY()-toCenterY; y+=stride){
                for(int x = toCenterX; x < matrix.getSize().getX()-toCenterX; x+=stride){
                    double sum = 0;
                    for(int j = 0; j < kernel.getSize().getY(); j++) {
                        for (int i = 0; i < kernel.getSize().getX(); i++) {
                            double factor1 = matrix.get(new Utils.Vector2Num<>(x-toCenterX+i, y-toCenterY+j));
                            double factor2 = kernel.get(new Utils.Vector2Num<>(i,j));
                            sum += factor1*factor2;
                        }
                    }
                    //bias todo solve it somehow
                    result.set(new Utils.Vector2Num<>((x-toCenterX)/stride, (y-toCenterY)/stride), sum);
                }
            }
            return result;
        }
        return matrix;
    }
}
