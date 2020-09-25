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
    double getConvolutionChain(ConvolutionWeight weight){
        double chain = 1;
        ConvolutionNeuron neuron = weight.getNeuron();
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
            Utils.Matrix2D zMatrix = weight.getNeuron().getBeforeActivation();
            double [] vector = zMatrix.getAsVector();
            double tmp = 0;
            ConvolutionalLayer l = ((ConvolutionalLayer)net.getLayers().get(neuron.getLayer().getIndexInNet()+1));
            for(Map.Entry<Integer, ConvolutionNeuron> entry : l.getNeurons().entrySet()){
                ConvolutionNeuron nextLayersNeuron = entry.getValue();
                for(int i = 0; i < vector.length; i++){
                    double z = vector[i];
                    KernelBox kernelBox = nextLayersNeuron.getKernelBox();
                    //todo...
                }
            }
            chain *= tmp;
        }
        return chain;
    }
}
