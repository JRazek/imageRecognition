package jrazek.faces.recognition.structure.neural.convolutional;

import jrazek.faces.recognition.structure.neural.Neuron;

import javax.management.RuntimeErrorException;
import java.util.LinkedList;

public class Kernel extends Neuron {
    private int sizeX;
    private int sizeY;
    private LinkedList<Double> weights;
    public Kernel(int x, int y) throws RuntimeErrorException {
        if((x/2)*2 == x || (y/2)*2 == y)throw new RuntimeErrorException(new Error("the Kernel size must be odd number!"));
        weights = new LinkedList<>();
    }

}
