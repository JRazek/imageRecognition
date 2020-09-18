package jrazek.faces.recognition.structure.neural.convolutional;

import jrazek.faces.recognition.Rules;
import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.structure.functional.ConvolutionalInputLayer;
import jrazek.faces.recognition.structure.neural.NeuralLayer;
import jrazek.faces.recognition.structure.neural.convolutional.interfaces.ConvolutionNetLayer;
import jrazek.faces.recognition.utils.Utils;

import javax.management.RuntimeErrorException;
import java.util.LinkedList;
import java.util.Map;

public class ConvolutionalLayer extends NeuralLayer<ConvolutionNeuron> implements ConvolutionNetLayer {

    private Utils.Vector3Num<Integer> inputBoxSize;
    private Utils.Matrix3D outputBox;
    private int filledOutputBoxCount;
    public ConvolutionalLayer(Net net, int index) {
        super(net, index);
    }

    @Override
    public void run() {
        for(Map.Entry<Integer, ConvolutionNeuron> entry : super.getNeurons().entrySet()){
            entry.getValue().run();
        }
    }

    @Override
    public void initRandom() {
        init();
        for(int i = 0; i < outputBox.getSize().getZ(); i ++){
            Utils.Vector3Num<Integer> kernelSize = new Utils.Vector3Num<>(getNet().getSettings().getKernelSize().getX(), getNet().getSettings().getKernelSize().getY(), inputBoxSize.getZ());
            ConvolutionNeuron cNeuron = new ConvolutionNeuron(this, i, kernelSize);
            cNeuron.initRandomWeights();
            addNeuron(cNeuron);
        }
    }
    private void init(){
        Utils.Vector3Num<Integer> outputBoxSize;
        this.inputBoxSize = ((ConvolutionNetLayer)getNet().getLayers().get(this.getIndexInNet()-1)).getOutputBox().getSize();
        int width = (this.inputBoxSize.getX() - getNet().getSettings().getKernelSize().getX() + 2*getNet().getSettings().getPadding()+1)/getNet().getSettings().getStride();
        int height = (this.inputBoxSize.getY() - getNet().getSettings().getKernelSize().getY() + 2*getNet().getSettings().getPadding()+1)/getNet().getSettings().getStride();
        outputBoxSize = new Utils.Vector3Num<>(width, height, getNet().getSettings().getNeuronsPerLayer());
        outputBox = new Utils.Matrix3D(outputBoxSize);
        filledOutputBoxCount = 0;
    }
    protected void addToBox(Utils.Matrix2D m){
        if(filledOutputBoxCount < outputBox.getSize().getZ()) {
            outputBox.setZMatrix(filledOutputBoxCount, m);
        }
        else throw new Error("ERROR12314");
    }
    @Override
    public Utils.Matrix3D getOutputBox() {

        return outputBox;
    }

}
