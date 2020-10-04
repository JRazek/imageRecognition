package jrazek.faces.recognition.structure.neural.convolutional;

import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.structure.activations.Activation;
import jrazek.faces.recognition.structure.neural.NeuralLayer;
import jrazek.faces.recognition.structure.neural.convolutional.interfaces.ConvolutionNetLayer;
import jrazek.faces.recognition.utils.Utils;
import static jrazek.faces.recognition.structure.neural.convolutional.interfaces.ConvolutionNetLayer.afterConvolutionSize;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConvolutionalLayer extends NeuralLayer<ConvolutionNeuron> implements ConvolutionNetLayer {

    private Utils.Vector3Num<Integer> inputBoxSize;
    private Utils.Matrix3D outputBox = null;
    private Utils.Matrix3D beforeActivationBox = null;
    private int filledOutputBoxCount;
    Map<Utils.Vector3Num<Integer>, Map<ConvolutionWeight, List<Utils.Vector3Num<Integer>>>> dependencies = new HashMap<>();
    /**
     * map that holds the vector of activation in prev layer, weights that were convoluted with it and the values that are dependent on this weight and activation.
     */

    public void addDependence(Utils.Vector3Num<Integer> aLm1, ConvolutionWeight w,  Utils.Vector3Num<Integer> zL){
        if(dependencies.get(aLm1) == null){
            dependencies.put(aLm1, new HashMap<>());
        }
        if(dependencies.get(aLm1).get(w) == null){
            dependencies.get(aLm1).put(w, new LinkedList<>());
        }
        dependencies.get(aLm1).get(w).add(zL);
    }

    public Map<Utils.Vector3Num<Integer>, Map<ConvolutionWeight, List<Utils.Vector3Num<Integer>>>> getDependencies() {
        return dependencies;
    }

    public ConvolutionalLayer(Net net, Activation a, int index) {
        super(net, a, index);
    }

    @Override
    public void reset(){
        outputBox.clear();
        beforeActivationBox.clear();
        filledOutputBoxCount = 0;
    }

    @Override
    public void run() {
        for(Map.Entry<Integer, ConvolutionNeuron> entry : super.getNeurons().entrySet()){
            entry.getValue().run();
        }
    }

    @Override
    public Map<Integer, ConvolutionNeuron> getNeurons() {
        return super.getNeurons();
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
        beforeActivationBox = new Utils.Matrix3D(outputBoxSize);
        filledOutputBoxCount = 0;
    }
    protected void addToBox(Utils.Matrix2D beforeActivation, Utils.Matrix2D afterActivation){
        if(filledOutputBoxCount < outputBox.getSize().getZ()) {
            beforeActivationBox.setZMatrix(filledOutputBoxCount, beforeActivation);
            outputBox.setZMatrix(filledOutputBoxCount, afterActivation);
            filledOutputBoxCount++;
        }
        else throw new Error("ERROR12314");
    }
    @Override
    public Utils.Matrix3D getOutputBox() {
        return outputBox;
    }

    public Utils.Matrix3D getBeforeActivationBox() {
        return beforeActivationBox;
    }
}
