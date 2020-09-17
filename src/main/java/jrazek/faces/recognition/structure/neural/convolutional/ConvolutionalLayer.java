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

import static jrazek.faces.recognition.Rules.kernelSize;

public class ConvolutionalLayer extends NeuralLayer<ConvolutionNeuron> implements ConvolutionNetLayer {

    private Utils.Matrix3D outputBox;
    private int outputBoxCurrentSize;
    private int outputBoxWantedSize;//this layers output depth
    private int kernelDepth;

    public ConvolutionalLayer(Net net, int index) {
        super(net, index);
        outputBoxCurrentSize = 0;
    }

    @Override
    public void run() {
        for(Map.Entry<Integer, ConvolutionNeuron> entry : super.getNeurons().entrySet()){
            entry.getValue().run();
        }
    }

    @Override
    public void initRandom() {
        int wantedNeuronsCount = Rules.neuronsPerLayer;
        int index = getNeurons().size();
        Layer prev = getNet().getLayers().get(getIndexInNet()-1);
        if(prev instanceof ConvolutionNetLayer){//
            kernelDepth = ((ConvolutionNetLayer) prev).getOutputBoxWantedSize();
        }
        else throw new RuntimeErrorException(new Error("UNSUPPORTED BEHAVIOUR!"));

        Utils.Vector3Num<Integer> neuronSize = new Utils.Vector3Num<>(kernelSize.getX(), kernelSize.getY(), kernelDepth);
        Utils.Vector3Num<Integer> outputBoxSize = ((ConvolutionNetLayer) prev).getOutputBox().getSize();
        outputBoxSize = new Utils.Vector3Num<>(outputBoxSize.getX()-2, outputBox.getSize().getY(), wantedNeuronsCount);
        outputBox = new Utils.Matrix3D(outputBoxSize);

        for(int i = 0; i < wantedNeuronsCount; i ++){
            System.out.println("Here3s");
            ConvolutionNeuron neuron = new ConvolutionNeuron(this, index, neuronSize);
            neuron.initRandomWeights();
            super.addNeuron(neuron);
        }
        outputBoxWantedSize = wantedNeuronsCount;

    }
    protected void addToBox(Utils.Matrix2D m){
        if(outputBoxCurrentSize != outputBoxWantedSize) {
            outputBox.setZMatrix(outputBoxCurrentSize, m);
            outputBoxCurrentSize++;
        }else throw new RuntimeException(new Error("ERROR12123 " + outputBoxCurrentSize + "==" +outputBoxWantedSize));
    }
    @Override
    public Utils.Matrix3D getOutputBox() {
        return outputBox;
    }

    @Override
    public int getOutputBoxWantedSize() {
        return outputBoxWantedSize;
    }
}
