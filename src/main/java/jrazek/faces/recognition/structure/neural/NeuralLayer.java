package jrazek.faces.recognition.structure.neural;

import jrazek.faces.recognition.structure.Layer;

import java.util.LinkedList;
import java.util.List;

public abstract class NeuralLayer<T extends Neuron> extends Layer {
    public List<T> neurons;
    public abstract void initRandom();
    public NeuralLayer(int index){
        super(index);
        neurons = new LinkedList<>();
    }

    @Override
    public void run() {
        for(Neuron n : neurons){
            n.run();
        }
    }
}
