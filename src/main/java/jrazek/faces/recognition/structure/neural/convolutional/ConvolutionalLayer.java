package jrazek.faces.recognition.structure.neural.convolutional;

import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.structure.activations.Activation;
import jrazek.faces.recognition.structure.neural.NeuralLayer;
import jrazek.faces.recognition.structure.neural.convolutional.interfaces.ConvolutionNetLayer;
import jrazek.faces.recognition.utils.Utils;
import static jrazek.faces.recognition.structure.neural.convolutional.interfaces.ConvolutionNetLayer.afterConvolutionSize;

import java.util.Map;

public class ConvolutionalLayer extends NeuralLayer<ConvolutionNeuron> implements ConvolutionNetLayer {

    private Utils.Vector3Num<Integer> inputBoxSize;
    private Utils.Matrix3D outputBox = null;
    private int filledOutputBoxCount;

    public ConvolutionalLayer(Net net, Activation a, int index) {
        super(net, a, index);
    }

    @Override
    public void run() {
        for(Map.Entry<Integer, ConvolutionNeuron> entry : super.getNeurons().entrySet()){
            entry.getValue().run();
        }
    }

    @Override
    public void setRandom() {
        init();
        for(int i = 0; i < outputBox.getSize().getZ(); i ++){
            Utils.Vector3Num<Integer> kernelSize = new Utils.Vector3Num<>(getNet().getSettings().getConvolutionKernelSize().getX(), getNet().getSettings().getConvolutionKernelSize().getY(), inputBoxSize.getZ());
            ConvolutionNeuron cNeuron = new ConvolutionNeuron(this, i, kernelSize);
            cNeuron.initRandomWeights();
            addNeuron(cNeuron);
        }
        System.out.println();
    }
    private void init(){
        Utils.Vector3Num<Integer> outputBoxSize;
        this.inputBoxSize = ((ConvolutionNetLayer)getNet().getLayers().get(this.getIndexInNet()-1)).getOutputBox().getSize();
        int width = afterConvolutionSize(this.inputBoxSize.getX(), getNet().getSettings().getConvolutionKernelSize().getX(), getNet().getSettings().getConvolutionPadding(), getNet().getSettings().getConvolutionStride());
        int height = afterConvolutionSize(this.inputBoxSize.getY(), getNet().getSettings().getConvolutionKernelSize().getY(), getNet().getSettings().getConvolutionPadding(), getNet().getSettings().getConvolutionStride());
        outputBoxSize = new Utils.Vector3Num<>(width, height, getNet().getSettings().getNeuronsPerLayer());
        outputBox = new Utils.Matrix3D(outputBoxSize);
        filledOutputBoxCount = 0;
    }
    protected void addToBox(Utils.Matrix2D m){
        if(filledOutputBoxCount < outputBox.getSize().getZ()) {
            outputBox.setZMatrix(filledOutputBoxCount, m);
            filledOutputBoxCount++;
        }
        else throw new Error("ERROR12314");
    }
    @Override
    public Utils.Matrix3D getOutputBox() {
        return outputBox;
    }
}
