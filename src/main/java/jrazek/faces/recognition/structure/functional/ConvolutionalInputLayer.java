package jrazek.faces.recognition.structure.functional;

import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.structure.neural.convolutional.interfaces.ConvolutionNetLayer;
import jrazek.faces.recognition.utils.Utils;

public class ConvolutionalInputLayer extends Layer implements ConvolutionNetLayer {
    Utils.Matrix3D tmp = new Utils.Matrix3D(new Utils.Vector3Num<>(2000,1000,3));
    public ConvolutionalInputLayer(Net net, int index) {
        super(net, index);
        for(int z = 0; z < tmp.getSize().getZ(); z ++){
            for(int y = 0; y < tmp.getSize().getY(); y++){
                for(int x = 0; x < tmp.getSize().getX(); x++){
                    tmp.set(new Utils.Vector3Num<>(x,y,z), 1);
                }
            }
        }
        tmp.set(new Utils.Vector3Num<>(5,6,2), 2);
    }
    @Override
    public Utils.Matrix3D getOutputBox() {
        return tmp;
    }


    @Override
    public void run() {

    }
}
