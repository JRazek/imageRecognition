package jrazek.faces.recognition.structure.neural.convolutional;

import jrazek.faces.recognition.structure.neural.Neuron;
import jrazek.faces.recognition.utils.Utils;

import javax.management.RuntimeErrorException;

public class ConvolutionNeuron extends Neuron {
    private int sizeX;
    private int sizeY;
    private int sizeZ;
    private Double[][][] weights;
    public ConvolutionNeuron(int x, int y, int z) throws RuntimeErrorException {
        if((x/2)*2 == x || (y/2)*2 == y)throw new RuntimeErrorException(new Error("the Kernel size must be odd number!"));
        weights = new Double[x][y][z];
    }
    void initRandomWeights() {
        for(int z = 0; z < sizeX; z++){
            for (int i = 0; i < sizeX; i++) {
                for (int j = 0; j < sizeY; j++) {
                    weights[j][i][z] = Utils.randomDouble(-1, 1);
                }
            }
        }
    }
    Double getWeight(int x, int y, int z) throws RuntimeErrorException{
        if(x < 0 || x > sizeX || y < 0 || y > sizeY || z < 0 || z > sizeZ)throw new RuntimeErrorException(new Error("Wrong argument!"));
        return weights[x][y][z];
    }

    @Override
    public void run() {

    }
}
