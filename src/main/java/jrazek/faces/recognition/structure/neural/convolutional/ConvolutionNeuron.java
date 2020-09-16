package jrazek.faces.recognition.structure.neural.convolutional;

import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.neural.Neuron;
import jrazek.faces.recognition.structure.neural.functional.PoolingLayer;
import jrazek.faces.recognition.utils.Utils;

import javax.management.RuntimeErrorException;

public class ConvolutionNeuron extends Neuron {
    private Utils.Vector3I size;
    private int indexInLayer;
    private Double[][][] weights;


    public ConvolutionNeuron(Utils.Vector3I size) throws RuntimeErrorException {
        super();
        if ((size.getX() / 2) * 2 == size.getX() || (size.getY() / 2) * 2 == size.getY() || size.getX() != size.getY())
            throw new RuntimeErrorException(new Error("the Kernel size must be odd number!"));
        this.size = size;
        weights = new Double[size.getX()][size.getY()][size.getZ()];
    }

    void initRandomWeights() {
        for (int z = 0; z < sizeX; z++) {
            for (int i = 0; i < sizeX; i++) {
                for (int j = 0; j < sizeY; j++) {
                    weights[j][i][z] = Utils.randomDouble(-1, 1);
                }
            }
        }
    }

    Double getWeight(int x, int y, int z) throws RuntimeErrorException {
        if (x < 0 || x > sizeX || y < 0 || y > sizeY || z < 0 || z > sizeZ)
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