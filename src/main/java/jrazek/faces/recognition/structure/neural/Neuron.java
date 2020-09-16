package jrazek.faces.recognition.structure.neural;


import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.activations.Activation;

public abstract class Neuron {
    private final Layer layer;
    private final Class<? extends Activation> activation;
    private final int indexInLayer;
    public abstract void run();
    public Layer getLayer(){
        return layer;
    }
    public Neuron(Layer l, int indexInLayer, Class<? extends Activation> a){
        this.layer = l;
        this.indexInLayer = indexInLayer;
        this.activation = a;
    }
}
