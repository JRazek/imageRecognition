package jrazek.faces.recognition.structure.neural.convolutional;

import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.structure.neural.NeuralLayer;

public class ConvolutionalLayer extends NeuralLayer<ConvolutionNeuron> {


    protected ConvolutionalLayer(Net net, int index) {
        super(net, index);
    }

    @Override
    public void initRandom() {

    }

    @Override
    public double[][][] getOutput() {
        return new double[0][][];
    }
}
