package jrazek.faces.recognition.structure.functional;

import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.structure.neural.convolutional.interfaces.ConvolutionNetLayer;
import jrazek.faces.recognition.utils.Utils;

import static jrazek.faces.recognition.utils.Utils.randomInt;

public class ConvolutionalInputLayer extends Layer implements ConvolutionNetLayer {
    Utils.Matrix3D tmp = new Utils.Matrix3D(new Utils.Vector3Num<>(20,20,3));
    public ConvolutionalInputLayer(Net net, int index) {
        super(net, index);
        for(int z = 0; z < tmp.getSize().getZ(); z ++){
            for(int y = 0; y < tmp.getSize().getY(); y++){
                for(int x = 0; x < tmp.getSize().getX(); x++){
                    int rand = randomInt(0,255);
                    tmp.set(new Utils.Vector3Num<>(x,y,z), rand);
                }
            }
        }
        //tmp.set(new Utils.Vector3Num<>(50,50,0), 10);
    }
    @Override
    public Utils.Matrix3D getOutputBox() {
        return tmp;
    }


    @Override
    public void run() {

    }
}
