package jrazek.faces.recognition.structure.neural;


import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.activations.Activation;

public abstract class Neuron {
    private final Layer layer;
    private final int indexInLayer;

    private double bias;

    public abstract void run();
    public Layer getLayer(){
        return layer;
    }
    public Neuron(Layer l, int indexInLayer){
        this.layer = l;
        this.indexInLayer = indexInLayer;
    }

    public int getIndexInLayer() {
        return indexInLayer;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    public double getBias() {
        return bias;
    }

}
