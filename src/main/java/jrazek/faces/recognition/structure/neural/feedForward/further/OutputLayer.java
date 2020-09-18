package jrazek.faces.recognition.structure.neural.feedForward.further;

import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.structure.activations.Activation;

public class OutputLayer extends FurtherLayer{

    protected OutputLayer(Net net, Activation a, int index) {
        super(net, a, index);
    }
}
