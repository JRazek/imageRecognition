package jrazek.faces.recognition.structure.neural.convolutional;

import jrazek.faces.recognition.structure.neural.Weight;
import jrazek.faces.recognition.utils.Utils;

import java.util.*;

public class ConvolutionWeight extends Weight {
    private ConvolutionNeuron neuron;
    private Utils.Vector3Num<Integer> pos;
    double chain;
    boolean isChainSet = false;
    ConvolutionWeight(ConvolutionNeuron neuron, Utils.Vector3Num<Integer> pos, Double v){
        super(v);
        this.neuron = neuron;
        this.pos = pos;
    }

    @Override
    public int hashCode() {
        return Objects.hash(neuron, pos);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass().equals(this.getClass())){
            ConvolutionWeight w = (ConvolutionWeight)obj;
            return (w.getNeuron().equals(this.neuron) && w.getPos().equals(this.getPos()));
        }
        return false;
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
    public ConvolutionNeuron getNeuron() {
        return neuron;
    }
    public Utils.Vector3Num<Integer> getPos() {
        return pos;
    }
}
