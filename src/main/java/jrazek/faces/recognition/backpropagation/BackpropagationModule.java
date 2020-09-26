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
import jrazek.faces.recognition.utils.Utils;

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
        Kernel kernel = weight.getNeuron().getKernelBox().getZMatrix(weight.getPos().getZ());
        Utils.Matrix2D beforeConvolution = ((ConvolutionNetLayer)weight.getNeuron().getLayer().getNet().getLayers().get((weight.getNeuron().getLayer().getIndexInNet()-1))).getOutputBox().getZMatrix(weight.getPos().getZ());
        int stride = weight.getNeuron().getLayer().getNet().getSettings().getConvolutionStride();
        for (int y = 0; y < kernel.getSize().getY(); y += stride){
            for(int x = 0; x < kernel.getSize().getX(); x += stride){
                int addX = weight.getPos().getX();
                int addY = weight.getPos().getY();
                chain += beforeConvolution.get(new Utils.Vector2Num<>(x + addX, y + addY)) * getConvolutionChain(weight);
            }
        }
        return chain;
    }
    private double getConvolutionChain(ConvolutionNeuron neuron){
        double chain = 1;
        double[] zVector = neuron.getBeforeActivation().getAsVector();
        double tmp1 = 0;
        for(int i = 0; i < zVector.length; i++){
            double z = zVector[i];
            double tmp2 = 0;
            //get corresponding aL-1 to z set
            neuron.getLayer().getActivation().differentiateWRTx(z);
            tmp1*=tmp2;
        }
        chain *= tmp1;
        return 1;
    }
}
