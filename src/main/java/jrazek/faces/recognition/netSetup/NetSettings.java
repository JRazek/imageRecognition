package jrazek.faces.recognition.netSetup;
import jrazek.faces.recognition.utils.Utils;

public class NetSettings {
    private final int convolutionLayersCount = 5;
    private final int feedForwardLayersCount = 0;
    private final int neuronsPerLayer = 3;
    private final Utils.Vector2Num<Integer> convolutionKernelSize = new Utils.Vector2Num<>(5,5);
    private final Utils.Vector2Num<Integer> poolingKernelSize = new Utils.Vector2Num<>(2,2);
    private final int padding = 0;
    private final int stride = 2;

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
