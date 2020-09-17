package jrazek.faces.recognition.structure.neural.convolutional;

import jrazek.faces.recognition.Rules;
import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.structure.neural.NeuralLayer;
import jrazek.faces.recognition.structure.neural.convolutional.interfaces.ConvolutionNetLayer;
import jrazek.faces.recognition.utils.Utils;

import javax.management.RuntimeErrorException;
import java.util.LinkedList;
import java.util.Map;

import static jrazek.faces.recognition.Rules.kernelSize;

public class ConvolutionalLayer extends NeuralLayer<ConvolutionNeuron> implements ConvolutionNetLayer {

    private LinkedList<Utils.Matrix2D> outputBox = new LinkedList<>();
    private int outputBoxWantedSize;

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
        for(int i = 0; i < Rules.neuronsPerLayer; i ++){
            int index = getNeurons().size();
            int z = 0;
            if(this.getIndexInNet() == 0){
                z = 3;//RGB
                outputBoxWantedSize = z;
            }
            else {
                Layer l = getNet().getLayers().get(getIndexInNet()-1);
                if(l instanceof ConvolutionNetLayer){//
                    ((ConvolutionNetLayer) l).getOutputBoxWantedSize();
                }
                else throw new RuntimeErrorException(new Error("UNSUPPORTED BEHAVIOUR!"));
            }
            Utils.Vector3Num<Integer> size = new Utils.Vector3Num<>(kernelSize.getX(), kernelSize.getY(), z );
            ConvolutionNeuron neuron = new ConvolutionNeuron(this, index, size);
            //todo width should be calculated from prev...
            super.addNeuron(neuron);
        }
    }

    @Override
    public LinkedList<Utils.Matrix2D> getOutputBox() {
        return outputBox;
    }

    @Override
    public int getOutputBoxWantedSize() {
        return outputBoxWantedSize;
    }
}
