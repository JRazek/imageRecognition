package jrazek.faces.recognition.structure.functional;

import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.structure.neural.convolutional.interfaces.ConvolutionNetLayer;
import jrazek.faces.recognition.utils.Utils;

public class PoolingLayer extends Layer implements ConvolutionNetLayer {
    public PoolingLayer(Net net, int index) {
        super(net, index);
    }

    @Override
    public void run() {
        if(getIndexInNet() != 0){
            Utils.Matrix3D givenMatrix = ((ConvolutionNetLayer) getNet().getLayers().get(getIndexInNet()-1)).getOutputBox();
            //Utils.Matrix2D result;//todo Max/Avg pooling
        }
    }

    @Override
    public Utils.Matrix3D getOutputBox() {
        return null;
    }
}
