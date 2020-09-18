package jrazek.faces.recognition.structure.neural;

import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.structure.activations.Activation;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class NeuralLayer<T extends Neuron> extends Layer {
    private Map<Integer, T> neurons;
    private Activation activation;
    protected NeuralLayer(Net net, Activation a, int index) {
        super(net, index);
        neurons = new TreeMap<>();
        this.activation = a;
    }

    public abstract void initRandom();

    public Activation getActivation() {
        return activation;
    }

    public Map<Integer, T> getNeurons() {
        return neurons;
    }

    public void addNeuron(T neuron) {
        this.neurons.put(neurons.size(), neuron);
    }

}
