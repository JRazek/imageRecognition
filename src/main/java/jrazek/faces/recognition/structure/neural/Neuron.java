package jrazek.faces.recognition.structure.neural;


import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.activations.Activation;

public abstract class Neuron {
    private final NeuralLayer<? extends Neuron> layer;
    private final int indexInLayer;
    private double currentChain = 0;
    private boolean isChainSet = false;

    private double bias = 0;

    public abstract void run();
    public NeuralLayer<? extends Neuron> getLayer(){
        return layer;
    }
    public Neuron(NeuralLayer<? extends Neuron> l, int indexInLayer){
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

    public void resetChain(){
        currentChain = 0;
        isChainSet = false;
    }
    public double getCurrentChain() {
        return currentChain;
    }
    public boolean isChainSet() {
        return isChainSet;
    }
    public void setCurrentChain(double currentChain) {
        this.currentChain = currentChain;
        this.isChainSet = true;
    }
}
