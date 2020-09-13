package jrazek.faces.recognition.structure.neural;

import jrazek.faces.recognition.structure.Layer;

import java.util.LinkedList;
import java.util.List;

public abstract class NeuralLayer<T extends Neuron> implements Layer {
    public List<T> neurons;
    public abstract void initRandom();
    public NeuralLayer(){
        neurons = new LinkedList<>();
    }
}
