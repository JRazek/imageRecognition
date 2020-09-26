package jrazek.faces.recognition.structure.functional;

import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.structure.neural.convolutional.interfaces.ConvolutionNetLayer;
import jrazek.faces.recognition.utils.Utils;

public class FlatteningLayer extends Layer {
    private double[] output;
    private Utils.Matrix3D box;
    public FlatteningLayer(Net net, int index) {
        super(net, index);
        this.box = ((ConvolutionNetLayer) net.getLayers().get(index - 1)).getOutputBox();
        if(net.getLayers().get(index-1) instanceof ConvolutionNetLayer) {
            Utils.Vector3Num<Integer> size = this.box.getSize();
            int fullSize = (size.getX() * size.getY() * size.getZ());
            output = new double[fullSize];
        }else throw new Error("Cannot Flatten the layer not belonging to conv net!");
    }
    public void reset(){
        output = new double[output.length];
    }
    @Override
    public void run() {
        int i = 0;
        this.box = ((ConvolutionNetLayer) super.getNet().getLayers().get(super.getIndexInNet() - 1)).getOutputBox();
        for(int z = 0; z < box.getSize().getZ(); z++){
            for(int y = 0; y < box.getSize().getY(); y++){
                for(int x = 0; x < box.getSize().getX(); x++){
                    output[i] = box.getValue(new Utils.Vector3Num<>(x,y,z));
                    i++;
                }
            }
        }
    }

    public double[] getOutput() {
        return output;
    }
}
