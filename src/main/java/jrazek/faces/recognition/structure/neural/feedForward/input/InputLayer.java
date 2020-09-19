package jrazek.faces.recognition.structure.neural.feedForward.input;

import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.structure.activations.Activation;
import jrazek.faces.recognition.structure.neural.feedForward.FFLayer;

public class InputLayer extends FFLayer {


    protected InputLayer(Net net, Activation a, int index) {
        super(net, a, index);
    }

    @Override
    public void run() {
        //take an input
    }

    @Override
    public void initRandom() {

    }
}
