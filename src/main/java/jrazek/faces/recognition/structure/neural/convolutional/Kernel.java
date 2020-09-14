package jrazek.faces.recognition.structure.neural.convolutional;

import jrazek.faces.recognition.structure.neural.Neuron;
import jrazek.faces.recognition.utils.Utils;

import javax.management.RuntimeErrorException;
import java.util.LinkedList;

public class Kernel extends Neuron {
    private int sizeX;
    private int sizeY;
    private Double[][] weights;
    public Kernel(int x, int y) throws RuntimeErrorException {
        if((x/2)*2 == x || (y/2)*2 == y)throw new RuntimeErrorException(new Error("the Kernel size must be odd number!"));
        weights = new Double[x][y];
    }
    void initRandomWeights(){
        for(int i = 0; i < sizeX; i++){
            for (int j = 0; j < sizeY; j ++){
                weights[j][i] = Utils.randomDouble(-1,1);
            }
        }
    }
    Double getWeight(int x, int y) throws RuntimeErrorException{
        if(x < 0 || x > sizeX || y < 0 || y > sizeY)throw new RuntimeErrorException(new Error("Wrong argument!"));
        return weights[x][y];
    }
}
