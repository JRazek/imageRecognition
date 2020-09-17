package jrazek.faces.recognition.structure.neural;

import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.Net;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class NeuralLayer<T extends Neuron> extends Layer {
    public Map<Integer, T> neurons;

    protected NeuralLayer(Net net, int index) {
        super(net, index);
        neurons = new TreeMap<>();
    }

    public abstract void initRandom();

    public Map<Integer, T> getNeurons() {
        return neurons;
    }

    public void addNeuron(T neuron) {
        this.neurons.put(neurons.size(), neuron);
    }

}
