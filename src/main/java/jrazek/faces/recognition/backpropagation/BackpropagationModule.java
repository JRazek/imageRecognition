package jrazek.faces.recognition.backpropagation;

import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.structure.functional.ConvolutionalInputLayer;
import jrazek.faces.recognition.structure.functional.FlatteningLayer;
import jrazek.faces.recognition.structure.functional.PoolingLayer;
import jrazek.faces.recognition.structure.neural.Neuron;
import jrazek.faces.recognition.structure.neural.convolutional.ConvolutionNeuron;
import jrazek.faces.recognition.structure.neural.convolutional.ConvolutionWeight;
import jrazek.faces.recognition.structure.neural.convolutional.ConvolutionalLayer;
import jrazek.faces.recognition.structure.neural.convolutional.interfaces.ConvolutionNetLayer;
import jrazek.faces.recognition.structure.neural.convolutional.kernels.Kernel;
import jrazek.faces.recognition.structure.neural.feedForward.FFNeuron;
import jrazek.faces.recognition.structure.neural.feedForward.further.OutputLayer;
import jrazek.faces.recognition.utils.Utils;

import java.util.Map;

public class BackpropagationModule {
    private Net net;
    double[] expected;
    public BackpropagationModule(Net net, double[] expected){
        this.net = net;
        this.expected = expected;
    }
    double countLoss(){
        Layer last = net.getLayers().get(net.getLayers().size()-1);
        int sum = 0;
        if(last instanceof OutputLayer){
            //do your job here
        }
        else if(last instanceof FlatteningLayer){
            FlatteningLayer flatteningLayer = ((FlatteningLayer) last);
            if(expected.length != flatteningLayer.getOutput().length)
                throw new Error("THE EXPECTED AND OUTPUT IS NOT THE SAME SIZE!!!");
            for(int i = 0; i < expected.length; i ++){
                sum += Math.pow(flatteningLayer.getOutput()[i] - expected[i], 2);
            }
        }
        return sum;
    }
    public double differentiateConvolutionWeight(ConvolutionWeight weight){
        double chain;
        if(weight.isZLwrtALm1Set())
            chain = weight.getZLwrtALm1();
        else {
            chain = ConvolutionNetLayer.deConvolutionForWeight(weight);
            weight.setZLwrtALm1(chain);
        }
        chain *= getChain(weight.getNeuron());
        return chain;
    }
    double getChain(Neuron neuron){
        double chain = 1;
        if(neuron instanceof ConvolutionNeuron){
            if(net.getLayers().get(neuron.getLayer().getIndexInNet()+1) instanceof FlatteningLayer){
                FlatteningLayer nextLayer = (FlatteningLayer)net.getLayers().get(neuron.getLayer().getIndexInNet()+1);
                if(nextLayer.getIndexInNet() + 1 == net.getLayers().size()) {
                    double tmp = 0;
                    for (int i = 0; i < nextLayer.getOutput().length; i++) {
                        tmp += 2 * (nextLayer.getOutput()[i] - expected[i]);
                    }
                    chain *= tmp;
                }//todo consider the first ever layer
                else {
                    //proceeding to FFLayers
                }
            }else{
                double tmp = 0;
                for(Map.Entry<Integer, ConvolutionNeuron> entry : ((ConvolutionalLayer)(neuron.getLayer().getNet().getLayers().get(neuron.getLayer().getIndexInNet()+1))).getNeurons().entrySet()){
                    Utils.Matrix2D afterConvolution = ((ConvolutionNeuron) neuron).getOutput();
                    ConvolutionNetLayer prev = (ConvolutionNetLayer)net.getLayers().get((neuron.getLayer().getIndexInNet()-1));
                    Utils.Matrix3D beforeConvolution = (prev).getOutputBox();
                    if(prev instanceof ConvolutionalLayer){
                        
                    }else if(prev instanceof ConvolutionalInputLayer){

                    }else if(prev instanceof PoolingLayer){

                    }
                    //here
                }
                chain *= tmp;
            }
        }
        else if(neuron instanceof FFNeuron){

        }
        return 0;
    }
}
