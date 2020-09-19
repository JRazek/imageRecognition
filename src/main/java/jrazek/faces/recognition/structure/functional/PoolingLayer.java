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
        //int width = afterConvolutionSize(msize,kernelsize,padding,stride);
        Vector3Num<Integer> prevLayerOutputBoxSize = ((ConvolutionNetLayer)getNet().getLayers().get(getIndexInNet()-1)).getOutputBox().getSize();
        this.kernelSize = net.getSettings().getPoolingKernelSize();

        int width = afterConvolutionSize(prevLayerOutputBoxSize.getX(),kernelSize.getX(),net.getSettings().getPadding(),net.getSettings().getStride());
        int height = afterConvolutionSize(prevLayerOutputBoxSize.getY(),kernelSize.getY(),net.getSettings().getPadding(),net.getSettings().getStride());
        outputBox = new Matrix3D(new Vector3Num(width, height, prevLayerOutputBoxSize.getZ()));
    }

    @Override
    public void run() {
        if(getIndexInNet() != 0){
            Matrix3D givenMatrix = ((ConvolutionNetLayer) getNet().getLayers().get(getIndexInNet()-1)).getOutputBox();

        }
    }

    @Override
    public Matrix3D getOutputBox() {
        return outputBox;
    }

}
