package jrazek.faces.recognition.structure.neural.convolutional;

import jrazek.faces.recognition.structure.neural.Weight;
import jrazek.faces.recognition.utils.Utils;

public class ConvolutionWeight extends Weight {
    double chain;
    boolean isChainSet = false;
    ConvolutionWeight(ConvolutionNeuron neuron, Utils.Vector3Num<Integer> pos, Double v){
        super(v);
        this.neuron = neuron;
        this.pos = pos;
    }

    public void setChain(double chain) {
        this.chain = chain;
        isChainSet = true;
    }

    public boolean isChainSet() {
        return isChainSet;
    }

    public double getChain() {
        return chain;
    }

    public void reset(){
        isChainSet = false;
        chain = 0;
    }
    private ConvolutionNeuron neuron;
    private Utils.Vector3Num<Integer> pos;
    public ConvolutionNeuron getNeuron() {
        return neuron;
    }
    public Utils.Vector3Num<Integer> getPos() {
        return pos;
    }
}
