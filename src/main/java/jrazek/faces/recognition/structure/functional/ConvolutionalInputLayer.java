package jrazek.faces.recognition.structure.functional;

import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.structure.neural.convolutional.interfaces.ConvolutionNetLayer;
import jrazek.faces.recognition.utils.Utils;

public class ConvolutionalInputLayer extends Layer implements ConvolutionNetLayer {
    public ConvolutionalInputLayer(Net net, int index) {
        super(net, index);
    }

    @Override
    public Utils.Matrix3D getOutputBox() {
        return new Utils.Matrix3D(new Utils.Vector3Num<>(100,100,3));
    }

    @Override
    public int getOutputBoxWantedSize() {
        return 3;
    }

    @Override
    public void run() {

    }
}
