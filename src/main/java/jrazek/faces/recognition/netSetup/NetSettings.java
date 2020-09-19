package jrazek.faces.recognition.netSetup;

import jrazek.faces.recognition.structure.activations.Activation;
import jrazek.faces.recognition.structure.activations.ReLU;
import jrazek.faces.recognition.structure.activations.Sigmoid;
import jrazek.faces.recognition.utils.Utils;

public class NetSettings {
    private final Class<? extends Activation> convolutionActivation = ReLU.class;
    private final Class<? extends Activation> feedForwardActivation = Sigmoid.class;
    private final int convolutionLayersCount = 1;
    private final int feedForwardLayersCount = 0;
    private final int neuronsPerLayer = 3;
    private final Utils.Vector2Num<Integer> convolutionKernelSize = new Utils.Vector2Num<>(5,5);
    private final Utils.Vector2Num<Integer> poolingKernelSize = new Utils.Vector2Num<>(2,2);
    private final int padding = 0;
    private final int stride = 2;

    public Class<? extends Activation> getConvolutionActivation() {
        return convolutionActivation;
    }

    public int getStride() {
        return stride;
    }

    public int getPadding() {
        return padding;
    }

    public int getFeedForwardLayersCount() {
        return feedForwardLayersCount;
    }

    public int getConvolutionLayersCount() {
        return convolutionLayersCount;
    }

    public Class<? extends Activation> getFeedForwardActivation() {
        return feedForwardActivation;
    }

    public int getNeuronsPerLayer() {
        return neuronsPerLayer;
    }

    public Utils.Vector2Num<Integer> getConvolutionKernelSize() {
        return convolutionKernelSize;
    }

    public Utils.Vector2Num<Integer> getPoolingKernelSize() {
        return poolingKernelSize;
    }
}
