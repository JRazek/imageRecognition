package jrazek.faces.recognition.structure.neural.feedForward;

import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.structure.activations.Activation;
import jrazek.faces.recognition.structure.neural.NeuralLayer;

public abstract class FFLayer extends NeuralLayer<FFNeuron> {


    protected FFLayer(Net net, Activation a, int index) {
        super(net, a, index);
    }
}
