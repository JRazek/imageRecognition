package jrazek.faces.recognition.structure.neural.feedForward;

import jrazek.faces.recognition.Rules;
import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.neural.NeuralLayer;
import jrazek.faces.recognition.structure.neural.Neuron;

public abstract class FFNeuron extends Neuron {
    public FFNeuron(NeuralLayer<?extends Neuron> l, int indexInLayer) {
        super(l, indexInLayer);
    }
}
