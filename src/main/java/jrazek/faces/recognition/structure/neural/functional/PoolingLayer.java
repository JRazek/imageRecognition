package jrazek.faces.recognition.structure.neural.functional;

import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.Net;

public class PoolingLayer extends Layer {
    protected PoolingLayer(Net net, int index) {
        super(net, index);
    }

    @Override
    public double[][][] getOutput() {
        return new double[0][][];
    }

    @Override
    public void run() {

    }
}
