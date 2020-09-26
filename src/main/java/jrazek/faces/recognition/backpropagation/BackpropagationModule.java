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
    public double countLoss(){
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
    private double getConvolutionChain(ConvolutionWeight weight){
        if(weight.isChainSet())
            return weight.getChain();
        double chain = 1;
        ConvolutionNeuron neuron = weight.getNeuron();
        if(net.getLayers().get(neuron.getLayer().getIndexInNet()+1) instanceof FlatteningLayer){
            double tmp = 0;
            FlatteningLayer nextLayer = (FlatteningLayer)net.getLayers().get(neuron.getLayer().getIndexInNet()+1);
            if(nextLayer.getIndexInNet() + 1 == net.getLayers().size()) {
                for (int i = 0; i < nextLayer.getOutput().length; i++) {
                    tmp += 2 * (nextLayer.getOutput()[i] - expected[i]);
                }
                chain *= tmp;
            }//todo consider the first ever layer
            else {
                //proceeding to FFLayers
            }
        }else{
            Utils.Matrix2D zMatrix = weight.getNeuron().getBeforeActivation();
            double [] vectorZ = zMatrix.getAsVector();
            double tmp1 = 0;
            ConvolutionalLayer nextLayer = (ConvolutionalLayer) net.getLayers().get(weight.getNeuron().getLayer().getIndexInNet()+1);
            for(int i = 0; i < vectorZ.length; i ++){
                double z = vectorZ[i];
                tmp1 = weight.getNeuron().getLayer().getActivation().differentiateWRTx(z);
                if(tmp1 == 0)
                    break;
                for(Map.Entry<Integer, ConvolutionNeuron> neuronEntry : nextLayer.getNeurons().entrySet()){
                    //iterating neurons
                    double tmp2 = 0;
                    ConvolutionNeuron weightsBox = neuronEntry.getValue();
                    for(int j = 0; j < weightsBox.getKernelBox().getSize().getZ(); j++){
                        //iterating neurons layers
                        ConvolutionWeight [] weightsVector = weightsBox.getKernelBox().getZMatrix(j).getAsVector();
                        for(int k = 0; k < weightsVector.length; k ++){
                            ConvolutionWeight convolutionWeight = weightsVector[k];
                            tmp2 += convolutionWeight.getValue()*getConvolutionChain(convolutionWeight);
                        }
                    }
                    tmp1 *= tmp2;
                }
            }
            chain *= tmp1;
        }
        weight.setChain(chain);
      //  System.out.println("Current chain = " + chain);
        return chain;
    }
}
