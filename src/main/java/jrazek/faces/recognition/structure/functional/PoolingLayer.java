package jrazek.faces.recognition.structure.functional;

import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.structure.neural.convolutional.interfaces.ConvolutionNetLayer;
import static jrazek.faces.recognition.structure.neural.convolutional.interfaces.ConvolutionNetLayer.afterConvolutionSize;
import jrazek.faces.recognition.utils.Utils.*;

public class PoolingLayer extends Layer implements ConvolutionNetLayer {
    private Matrix3D outputBox;
    private Vector2Num<Integer> kernelSize;
    public PoolingLayer(Net net, int index) {
        super(net, index);
        Vector3Num<Integer> prevLayerOutputBoxSize = ((ConvolutionNetLayer)getNet().getLayers().get(getIndexInNet()-1)).getOutputBox().getSize();
        this.kernelSize = net.getSettings().getPoolingKernelSize();
        int width = afterConvolutionSize(prevLayerOutputBoxSize.getX(),kernelSize.getX(),net.getSettings().getPadding(),net.getSettings().getStride());
        int height = afterConvolutionSize(prevLayerOutputBoxSize.getY(),kernelSize.getY(),net.getSettings().getPadding(),net.getSettings().getStride());
        this.outputBox = new Matrix3D(new Vector3Num<>(width, height, prevLayerOutputBoxSize.getZ()));
    }

    @Override
    public void run() {
        if(getIndexInNet() != 0){
            Matrix3D givenMatrix = ((ConvolutionNetLayer) getNet().getLayers().get(getIndexInNet()-1)).getOutputBox();
            for(int z = 0; z < givenMatrix.getSize().getZ(); z++){
                for(int y = 0; y < givenMatrix.getSize().getY(); y++){
                    for(int x = 0; x < givenMatrix.getSize().getX(); x++){

                        for(int j = 0; j < kernelSize.getY(); j++){
                            for(int i = 0; i < kernelSize.getX(); i++){

                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Matrix3D getOutputBox() {
        return outputBox;
    }

}
