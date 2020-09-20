package jrazek.faces.recognition.netSetup;
import jrazek.faces.recognition.utils.Utils;

public class NetSettings {
    private final int convolutionLayersCount = 8;
    private final int feedForwardLayersCount = 0;
    private final int neuronsPerLayer = 10;
    private final Utils.Vector2Num<Integer> convolutionKernelSize = new Utils.Vector2Num<>(3,3);
    private final Utils.Vector2Num<Integer> poolingKernelSize = new Utils.Vector2Num<>(2,2);
    private final int convolutionPadding = 0;
    private final int convolutionStride = 2;


    private final int poolingPadding = 0;
    private final int poolingStride = 1;

    public int getConvolutionStride() {
        return convolutionStride;
    }

    public int getConvolutionPadding() {
        return convolutionPadding;
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

    public int getPoolingStride() {
        return poolingStride;
    }

    public int getPoolingPadding() {
        return poolingPadding;
    }
}
