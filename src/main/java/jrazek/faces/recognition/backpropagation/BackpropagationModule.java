package jrazek.faces.recognition.backpropagation;

import jrazek.faces.recognition.structure.Net;

public class BackpropagationModule {
    private Net net;
    double[] expected;
    public BackpropagationModule(Net net, double[] expected){
        this.net = net;
        this.expected = expected;
    }
}
