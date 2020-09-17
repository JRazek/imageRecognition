package jrazek.faces.recognition.structure.functional;

import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.structure.neural.convolutional.interfaces.ConvolutionNetLayer;
import jrazek.faces.recognition.utils.Utils;

public class ConvolutionalInputLayer extends Layer implements ConvolutionNetLayer {
    Utils.Matrix3D tmp = new Utils.Matrix3D(new Utils.Vector3Num<>(100,100,3));
    public ConvolutionalInputLayer(Net net, int index) {
        super(net, index);
        for(int z = 0; z < 3; z ++){
            for(int y = 0; y < 100; y++){
                for(int x = 0; x < 100; x++){
                    tmp.set(new Utils.Vector3Num<>(x,y,z), 10);
                }
            }
        }
    }
    @Override
    public Utils.Matrix3D getOutputBox() {
        return tmp;
    }

    @Override
    public int getOutputBoxWantedSize() {
        return 3;
    }

    @Override
    public void run() {

    }
}
