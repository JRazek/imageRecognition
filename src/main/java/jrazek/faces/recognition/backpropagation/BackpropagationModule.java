package jrazek.faces.recognition.backpropagation;

import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.structure.neural.convolutional.ConvolutionNeuron;
import jrazek.faces.recognition.structure.neural.convolutional.ConvolutionalLayer;
import jrazek.faces.recognition.utils.Utils;

public class BackpropagationModule {
    private Net net;
    double[] expected;
    public BackpropagationModule(Net net, double[] expected){
        this.net = net;
        this.expected = expected;
    }
    //
    public double differentiateConvolutionWeight(ConvolutionNeuron neuron, Utils.Vector3Num weightPos){
        neuron.getKernel().getValue(weightPos);
        if(net.getLayers().get(neuron.getIndexInLayer()+1) instanceof ConvolutionalLayer){

        }
        return 0;
    }
}
