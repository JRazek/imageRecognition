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
import jrazek.faces.recognition.utils.Utils.*;

import java.util.*;

import static java.lang.Double.*;

public class BackpropagationModule {
    private Net net;
    double[] expected;

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
                int correspondingY = y/stride;//y in zL
                Vector2Num<Integer> aLm1v = new Vector2Num<>(x + weight.getPos().getX(),y + weight.getPos().getY());
                Vector2Num<Integer> zLv = new Vector2Num<>(correspondingX, correspondingY);
               // chain += a_Lm1.get(aLm1v)*weight.getNeuron().getLayer().getActivation().differentiateWRTx(z_L.get(zLv))*getConvolutionChain();
            }
        }
        return chain;
    }
    private double getConvolutionChain(ConvolutionNeuron neuron, Vector3Num<Integer> activation){
        double chain = 1;
        if(neuron.isChainSet())
            return neuron.getCurrentChain();
        Layer nextLayerUnspecified = net.getLayers().get(neuron.getLayer().getIndexInNet()+1);
        if(nextLayerUnspecified instanceof ConvolutionalLayer){

            Matrix2D aL = neuron.getOutput();

            Matrix3D zLp1 = ((ConvolutionalLayer) nextLayerUnspecified).getBeforeActivationBox();

            Vector2Num<Integer> kernelSize = neuron.getLayer().getNet().getSettings().getConvolutionKernelSize();
            int stride = net.getSettings().getConvolutionStride();

            for(Map.Entry<Integer, ConvolutionNeuron> entry : ((ConvolutionalLayer) nextLayerUnspecified).getNeurons().entrySet()){
                ConvolutionNeuron nextLayerNeuron = entry.getValue();
                if(nextLayerNeuron.isChainSet()){
                    //return nextLayerNeuron.getCurrentChain();
                }
                KernelBox kernelBox = nextLayerNeuron.getKernelBox();
                double tmp1 = 0;
                for(int z = 0; z < kernelBox.getSize().getZ(); z ++){
                    //warstwy kernela w jednym neuronie
                    Kernel kernel = kernelBox.getZMatrix(z);
                    for (int y = 0; y < zLp1.getSize().getY(); y++){
                        for (int x = 0; x < zLp1.getSize().getX(); x++){
                            //iteracja po z w nastepnej warstwie.
                            int toCenterX = kernelSize.getX()/2;
                            int toCenterY = kernelSize.getY()/2;

                            //int correspondingX = x*stride + toCenterX;
                            //int correspondingY = y*stride + toCenterY;
                            //
                        }
                    }
                    chain *= tmp1;
                }
            }
        }else if(nextLayerUnspecified instanceof FlatteningLayer){
            //do flatted and error calculus
            double[] output = ((FlatteningLayer) nextLayerUnspecified).getOutput();
            if(output.length == this.expected.length){
                for(int i = 0; i < output.length; i ++)
                    chain += 2*(output[i] - expected[i]);
            }
        }
        neuron.setCurrentChain(chain);
        return chain;
    }
}
