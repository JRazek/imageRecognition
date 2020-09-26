package jrazek.faces.recognition.structure.neural;

import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.structure.activations.Activation;

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

    public abstract void setRandom();

    public Activation getActivation() {
        return activation;
    }

    public void resetNeuronsChains(){
        for(Map.Entry<Integer, T> entry : neurons.entrySet()){
            entry.getValue().resetChain();
        }
    }
    public abstract void reset();
    public Map<Integer, T> getNeurons() {
        return neurons;
    }

    public void addNeuron(T neuron) {
        this.neurons.put(neurons.size(), neuron);
    }

}
