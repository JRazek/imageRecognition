package jrazek.faces.recognition.backpropagation;

import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.structure.functional.FlatteningLayer;
import jrazek.faces.recognition.structure.neural.convolutional.ConvolutionNeuron;
import jrazek.faces.recognition.structure.neural.convolutional.ConvolutionWeight;
import jrazek.faces.recognition.structure.neural.convolutional.ConvolutionalLayer;
import jrazek.faces.recognition.structure.neural.convolutional.interfaces.ConvolutionNetLayer;
import jrazek.faces.recognition.structure.neural.convolutional.kernels.Kernel;
import jrazek.faces.recognition.structure.neural.convolutional.kernels.KernelBox;
import jrazek.faces.recognition.structure.neural.feedForward.FFNeuron;
import jrazek.faces.recognition.structure.neural.feedForward.further.OutputLayer;
import jrazek.faces.recognition.utils.Utils.*;
import jrazek.faces.recognition.utils.abstracts.Matrix2;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class BackpropagationModule {
    private Net net;
    double[] expected;
    public BackpropagationModule(Net net){
        this.net = net;
    }    public double countLoss(){
        Layer last = net.getLayers().get(net.getLayers().size()-1);
        double sum = 0;
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

    public void backPropagate(double[] expected){
        this.expected = expected;
        System.out.println("Loss = " + countLoss());
        //System.out.println("Weight map size = " + net.getWeightMap().size());
        for(Map.Entry<Integer, ConvolutionWeight> entry : net.getWeightMap().entrySet()){
            ConvolutionWeight weight = entry.getValue();
            double delta = -1*differentiateConvolutionWeight(entry.getValue())*net.getSettings().getGradientRate();
            weight.setValue(weight.getValue() + delta);
        }
    }
    private double differentiateConvolutionWeight(ConvolutionWeight weight){
        double chain = 0;
        ConvolutionNeuron neuron = weight.getNeuron();
        Matrix2D a_Lm1 = ((ConvolutionNetLayer)net.getLayers().get(neuron.getLayer().getIndexInNet()-1)).getOutputBox().getZMatrix(weight.getPos().getZ());
        for(int y = 0; y < a_Lm1.getSize().getY(); y+= net.getSettings().getConvolutionStride()){
            for(int x = 0; x < a_Lm1.getSize().getX(); x+= net.getSettings().getConvolutionStride()){
                double tmp;
                tmp = a_Lm1.get(new Vector2Num<>(x + weight.getPos().getX(), y + weight.getPos().getY()));
                tmp *= neuron.getLayer().getActivation().differentiateWRTx(weight.getValue());
                tmp *= getConvolutionChain(neuron);
            }
        }
        return chain;
    }
    private double getConvolutionChain(ConvolutionNeuron neuron){
        double chain = 1;
        Layer nextLayerUnspecified = net.getLayers().get(neuron.getLayer().getIndexInNet()+1);
        if(nextLayerUnspecified instanceof ConvolutionalLayer){
            
        }else if(nextLayerUnspecified instanceof FlatteningLayer){
            //do flatted and error calculus
        }
        return 1;
    }
}
