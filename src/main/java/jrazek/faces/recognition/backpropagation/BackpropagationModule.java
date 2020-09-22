package jrazek.faces.recognition.backpropagation;

import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.structure.neural.convolutional.ConvolutionNeuron;
import jrazek.faces.recognition.structure.neural.convolutional.ConvolutionalLayer;
import jrazek.faces.recognition.utils.Utils;

import java.util.Map;

public class BackpropagationModule {
    private Net net;
    double[] expected;
    public BackpropagationModule(Net net, double[] expected){
        this.net = net;
        this.expected = expected;
    }

    public double differentiateConvolutionWeight(ConvolutionNeuron neuron, Utils.Vector3Num<Integer> weightPos){
        neuron.getKernel().getValue(weightPos);
        if(net.getLayers().get(neuron.getIndexInLayer()+1) instanceof ConvolutionalLayer){
            double tmp = 0;
            for(Map.Entry<Integer, ConvolutionNeuron> entry : ((ConvolutionalLayer) net.getLayers().get(neuron.getIndexInLayer()+1))
                    .getNeurons().entrySet()){
                ConvolutionNeuron nextLayerKernel = entry.getValue();
                
            }
        }
        return 1;
    }
}
