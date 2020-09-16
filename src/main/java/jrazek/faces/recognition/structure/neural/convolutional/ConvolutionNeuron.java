package jrazek.faces.recognition.structure.neural.convolutional;

import jrazek.faces.recognition.Rules;
import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.activations.Activation;
import jrazek.faces.recognition.structure.activations.ReLU;
import jrazek.faces.recognition.structure.neural.Neuron;
import jrazek.faces.recognition.structure.neural.functional.PoolingLayer;
import jrazek.faces.recognition.utils.Utils;

import javax.management.RuntimeErrorException;

public class ConvolutionNeuron extends Neuron {
    private Utils.Vector3I size;
    private int indexInLayer;
    private Double[][][] weights;


    public ConvolutionNeuron(Layer l, int indexInLayer, Utils.Vector3I size) throws RuntimeErrorException {
        super(l, indexInLayer, Rules.convolutionActivation);
        if ((size.getX() / 2) * 2 == size.getX() || (size.getY() / 2) * 2 == size.getY() || size.getX() != size.getY())
            throw new RuntimeErrorException(new Error("the Kernel size must be odd number!"));
        this.size = size;
        weights = new Double[size.getX()][size.getY()][size.getZ()];
    }

    void initRandomWeights() {
        for (int z = 0; z < size.getZ(); z++) {
            for (int y = 0; y < size.getZ(); y++) {
                for (int x = 0; x < size.getX(); x++) {
                    weights[x][y][z] = Utils.randomDouble(-1, 1);
                }
            }
        }
    }

    Double getWeight(int x, int y, int z) throws RuntimeErrorException {
        if (x < 0 || x > size.getX() || y < 0 || y > size.getY() || z < 0 || z > size.getZ())
            throw new RuntimeErrorException(new Error("Wrong argument!"));
        return weights[x][y][z];
    }

    @Override
    public void run() {
        Layer prev = getLayer().getNet().getLayers().get(getLayer().getIndexInNet() - 1);
        if (prev instanceof ConvolutionalLayer || prev instanceof PoolingLayer) {
            //prepare chunk to get to here
            double[][][] input;
            input = prev.getOutput();
        } else {
            //if taking from feed forward layer. not supported in first versions or sure
        }
    }
}