package jrazek.faces.recognition.structure.neural.functional;

import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.structure.neural.convolutional.interfaces.ConvolutionNetLayer;
import jrazek.faces.recognition.utils.Utils;

import java.util.LinkedList;

public class PoolingLayer extends Layer implements ConvolutionNetLayer {
    protected PoolingLayer(Net net, int index) {
        super(net, index);
    }

    @Override
    public void run() {

    }

    @Override
    public LinkedList<Utils.Matrix2D> getOutputBox() {
        return null;
    }

    @Override
    public int getOutputBoxWantedSize() {
        return 0;
    }
}
