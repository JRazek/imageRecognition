package jrazek.faces.recognition.structure.neural.convolutional;

import jrazek.faces.recognition.structure.neural.Weight;
import jrazek.faces.recognition.utils.Utils;

public class ConvolutionWeight extends Weight {
    ConvolutionWeight(ConvolutionNeuron neuron, Utils.Vector3Num<Integer> pos, Double v){
        super(v);
        this.neuron = neuron;
        this.pos = pos;
    }
    private double zLwrtALm1;
    boolean isZLwrtALm1Set = false;
    public double getZLwrtALm1() {
        return zLwrtALm1;
    }
    public boolean isZLwrtALm1Set() {
        return isZLwrtALm1Set;
    }
    public void setZLwrtALm1(double zLwrtALm1) {
        this.zLwrtALm1 = zLwrtALm1;
        isZLwrtALm1Set = true;
    }
    public void reset(){
        isZLwrtALm1Set = false;
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
