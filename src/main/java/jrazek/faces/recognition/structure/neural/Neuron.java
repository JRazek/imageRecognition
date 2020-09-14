package jrazek.faces.recognition.structure.neural;


import jrazek.faces.recognition.structure.Layer;

public abstract class Neuron {
    Layer layer;
    public abstract void run();
    public Layer getLayer(){
        return layer;
    }
}
