package jrazek.faces.recognition.structure.neural.feedForward.further;

import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.structure.activations.Activation;
import jrazek.faces.recognition.structure.neural.feedForward.FFLayer;

public class FurtherLayer extends FFLayer {

    protected FurtherLayer(Net net, Activation a, int index) {
        super(net, a, index);
    }

    @Override
    public void run() {
        //take outputs from prev layer
    }

    @Override
    public void setRandom() {

    }

}
