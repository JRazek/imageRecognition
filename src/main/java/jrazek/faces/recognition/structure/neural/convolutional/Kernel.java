package jrazek.faces.recognition.structure.neural.convolutional;

import jrazek.faces.recognition.structure.neural.Neuron;

import java.util.LinkedList;

public class Kernel extends Neuron {
    int sizeX;
    int sizeY;
    LinkedList<Double> weights;
    public Kernel(){

    }
}
