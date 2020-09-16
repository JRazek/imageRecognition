package jrazek.faces.recognition.structure.neural.feedForward;

import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.activations.Activation;
import jrazek.faces.recognition.structure.neural.Neuron;

public abstract class FFNeuron extends Neuron {
    FFNeuron(Layer l, int indexInLayer, Activation a){
        super(l, indexInLayer, a);

    }
}
