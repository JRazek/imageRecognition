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
import jrazek.faces.recognition.structure.neural.feedForward.further.OutputLayer;
import jrazek.faces.recognition.utils.Utils;
import jrazek.faces.recognition.utils.Utils.*;

import java.util.*;

import static java.lang.Double.*;

public class BackpropagationModule {
    private Net net;
    double[] expected;
    int i = 0;//debug
    public BackpropagationModule(Net net){
        this.net = net;
    }
    public double countLoss(){
        Layer last = net.getLayers().get(net.getLayers().size()-1);
        double sum = 0;
        if(last instanceof OutputLayer){
            //do your job here
        }
        else if(last instanceof FlatteningLayer){
            FlatteningLayer flatteningLayer = ((FlatteningLayer) last);
            if(expected.length != flatteningLayer.getOutput().length) {
                throw new Error("THE EXPECTED AND OUTPUT IS NOT THE SAME SIZE!!!"
                + expected.length + " != " + flatteningLayer.getOutput().length);
            }
            for(int i = 0; i < expected.length; i ++){
                double curr = Math.pow(flatteningLayer.getOutput()[i] - expected[i], 2);
                sum += curr;
            }
        }
        return sum;
    }

    public void backPropagate(double[] expected){
        this.expected = expected;
        //System.out.println("Weight map size = " + net.getWeightMap().size());
        for(Map.Entry<Integer, ConvolutionWeight> entry : net.getWeightMap().entrySet()){
            ConvolutionWeight weight = entry.getValue();
            double delta = -1*differentiateConvolutionWeight(entry.getValue())*net.getSettings().getGradientRate();
            double old = weight.getValue();
            weight.setValue(weight.getValue() + delta);
           // System.out.println(weight.getValue() + delta);
        }
    }
    private double differentiateConvolutionWeight(ConvolutionWeight weight){
        double chain = 0;
        ConvolutionNeuron neuron = weight.getNeuron();
        int stride = net.getSettings().getConvolutionStride();
        Matrix2D a_Lm1 = ((ConvolutionNetLayer)net.getLayers().get(neuron.getLayer().getIndexInNet()-1)).getOutputBox().getZMatrix(weight.getPos().getZ());
        Matrix2D z_L = weight.getNeuron().getBeforeActivation();
        for(int y = 0; y < a_Lm1.getSize().getY()-weight.getNeuron().getKernelBox().getSize().getY(); y+= stride){
            for(int x = 0; x < a_Lm1.getSize().getX()-weight.getNeuron().getKernelBox().getSize().getX(); x+= stride){
                int correspondingX = x/stride;//x in zL
                int correspondingY = y/stride;//y in zL.
                Vector2Num<Integer> aLm1v = new Vector2Num<>(x + weight.getPos().getX(),y + weight.getPos().getY());
                Vector2Num<Integer> zLv = new Vector2Num<>(correspondingX, correspondingY);
                chain += a_Lm1.get(aLm1v)*weight.getNeuron().getLayer().getActivation().differentiateWRTx(z_L.get(zLv))*getConvolutionChain();
            }
        }
        return chain;
    }
    private double getConvolutionChain(ConvolutionNetLayer layer, int zActivation, Vector3Num<Integer> activation){
        double chain = 1;
        //the layer of activation. that means we consider the next layer and split into different options.
        if(layer instanceof ConvolutionalLayer){
            ConvolutionNeuron  neuron = ((ConvolutionalLayer) layer).getNeurons().get(zActivation);
            System.out.println(neuron.getIndexInLayer());
            if(neuron.isChainSet())
                return neuron.getCurrentChain();
            Map<Utils.Vector3Num<Integer>, Map<ConvolutionWeight, List<Utils.Vector3Num<Integer>>>> dependencies = neuron.getDependencies();
            double tmp = 0;
            for(Map.Entry<ConvolutionWeight, List<Utils.Vector3Num<Integer>>> entry : dependencies.get(activation).entrySet()){

            }
            chain *=tmp;
        }else if(layer instanceof FlatteningLayer){
            //do flatted and error calculus
            double[] output = ((FlatteningLayer) layer).getOutput();
            if(output.length == this.expected.length){
                for(int i = 0; i < output.length; i ++)
                    chain += 2*(output[i] - expected[i]);
            }
        }
        return chain;
    }
}
