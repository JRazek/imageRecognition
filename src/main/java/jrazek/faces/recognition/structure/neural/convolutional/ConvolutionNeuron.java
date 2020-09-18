package jrazek.faces.recognition.structure.neural.convolutional;

import jrazek.faces.recognition.Rules;
import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.neural.Neuron;
import jrazek.faces.recognition.structure.neural.convolutional.interfaces.ConvolutionNetLayer;
import jrazek.faces.recognition.structure.functional.PoolingLayer;
import jrazek.faces.recognition.utils.Utils;

import javax.management.RuntimeErrorException;

public class ConvolutionNeuron extends Neuron {
    private final Utils.Vector3Num<Integer> size;
    private Utils.Matrix3D kernel;


    public ConvolutionNeuron(Layer l, int indexInLayer, Utils.Vector3Num<Integer> size) throws RuntimeErrorException {
        super(l, indexInLayer, l.getNet().getSettings().getConvolutionActivation());
        if ((size.getX() / 2) * 2 == size.getX() || (size.getY() / 2) * 2 == size.getY() || !size.getX().equals(size.getY()))
            throw new RuntimeErrorException(new Error("the Kernel size must be odd number!"));
        this.size = size;
        kernel = new Utils.Matrix3D(size);
    }

    void initRandomWeights() {
        for (int z = 0; z < size.getZ(); z++) {
            for (int x = 0; x < size.getX(); x++) {
                for (int y = 0; y < size.getY(); y++) {
                    double randomValue  = Utils.randomDouble(-1,1);
                    kernel.set(new Utils.Vector3Num<>(x, y, z), 2);
                }
            }
        }
    }

    Double getWeight(Utils.Vector3Num<Integer> c) throws RuntimeErrorException {
        return kernel.getValue(c);
    }
    public void updateKernel(Utils.Vector3Num<Integer> c, double value){
        kernel.set(c, value);
    }
    @Override
    public void run() {
        if(getLayer().getIndexInNet() != 0) {
            Layer prev = getLayer().getNet().getLayers().get(getLayer().getIndexInNet() - 1);
            if (prev instanceof ConvolutionNetLayer) {
                Utils.Matrix3D givenMatrix = ((ConvolutionNetLayer) prev).getOutputBox();
                System.out.println("outputbox for layer " + prev.getIndexInNet() + " = ");
                Utils.Matrix2D result = new Utils.Matrix2D(givenMatrix.getSize().getX()-2, givenMatrix.getSize().getY()-2);
                for (int i = 0; i < givenMatrix.getSize().getZ(); i++) {
                    int padding = getLayer().getNet().getSettings().getPadding();
                    int stride = getLayer().getNet().getSettings().getStride();
                    Utils.Matrix2D tmp = givenMatrix.getZMatrix(i).convolve(this.kernel.getZMatrix(i), padding, stride);
                    result.add(tmp);
                }
                ((ConvolutionalLayer) getLayer()).addToBox(result);
            } else {
                //todo
                //if taking from feed forward layer. not supported in first versions or sure
            }
        }else throw new RuntimeErrorException(new Error("ERROR234"));
    }
}