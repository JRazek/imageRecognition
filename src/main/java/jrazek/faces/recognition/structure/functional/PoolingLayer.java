package jrazek.faces.recognition.structure.functional;

import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.structure.neural.convolutional.interfaces.ConvolutionNetLayer;
import static jrazek.faces.recognition.structure.neural.convolutional.interfaces.ConvolutionNetLayer.afterConvolutionSize;
import jrazek.faces.recognition.utils.Utils.*;

public class PoolingLayer extends Layer implements ConvolutionNetLayer {
    private Matrix3D outputBox;
    private int filledOutputCount;
    private Vector2Num<Integer> kernelSize;
    public PoolingLayer(Net net, int index) {
        super(net, index);
        Vector3Num<Integer> prevLayerOutputBoxSize = ((ConvolutionNetLayer)getNet().getLayers().get(getIndexInNet()-1)).getOutputBox().getSize();
        this.kernelSize = net.getSettings().getPoolingKernelSize();
        int width = afterConvolutionSize(prevLayerOutputBoxSize.getX(),kernelSize.getX(),net.getSettings().getConvolutionPadding(),net.getSettings().getConvolutionStride());
        int height = afterConvolutionSize(prevLayerOutputBoxSize.getY(),kernelSize.getY(),net.getSettings().getConvolutionPadding(),net.getSettings().getConvolutionStride());
        this.outputBox = new Matrix3D(new Vector3Num<>(width, height, prevLayerOutputBoxSize.getZ()));
        this.filledOutputCount = 0;
    }

    @Override
    public void run() {
        if(getIndexInNet() != 0){
            Matrix3D givenMatrix = ((ConvolutionNetLayer) getNet().getLayers().get(getIndexInNet()-1)).getOutputBox();
            int stride = getNet().getSettings().getPoolingStride();
            int padding = getNet().getSettings().getPoolingPadding();
            for(int z = 0; z < givenMatrix.getSize().getZ(); z++){
                Matrix2D result = new Matrix2D(new Vector2Num<>(outputBox.getSize().getX(), outputBox.getSize().getY()));
                for(int y = 0; y < outputBox.getSize().getY(); y+=stride){
                    for(int x = 0; x < outputBox.getSize().getX(); x+=stride){
                        double max = 0;
                        for(int j = 0; j < kernelSize.getY(); j++){
                            for(int i = 0; i < kernelSize.getX(); i++){
                                double val = givenMatrix.getZMatrix(z).get(new Vector2Num<>(x+i, y+j));
                                if(val > max)
                                    max = val;
                            }
                        }
                        result.set(new Vector2Num<>(x/stride, y/stride), max);
                    }
                }
                outputBox.setZMatrix(filledOutputCount, result);
                filledOutputCount++;
            }
        }
    }

    @Override
    public Matrix3D getOutputBox() {
        return outputBox;
    }

}
