package jrazek.faces.recognition.structure.neural;

import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.Net;

import java.util.LinkedList;
import java.util.List;

public abstract class NeuralLayer<T extends Neuron> extends Layer {
    public List<T> neurons;

    protected NeuralLayer(Net net, int index) {
        super(net, index);
        neurons = new LinkedList<>();
    }

    public abstract void initRandom();

    @Override
    public void run() {
        for(Neuron n : neurons){
            n.run();
        }
    }
}
