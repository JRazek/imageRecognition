package jrazek.faces.recognition.structure.neural.convolutional;

import jrazek.faces.recognition.structure.neural.Weight;
import jrazek.faces.recognition.utils.Utils;

public class ConvolutionWeight extends Weight {
    ConvolutionWeight(Double v){
        super(v);
    }
    ConvolutionNeuron neuron;
    Utils.Vector3Num<Integer> pos;
    public ConvolutionNeuron getNeuron() {
        return neuron;
    }
    public Utils.Vector3Num<Integer> getPos() {
        return pos;
    }
}
