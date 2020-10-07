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
        if(net.getLayers().get(net.getLayers().size()-1) instanceof FlatteningLayer){
            if(expected.length != ((FlatteningLayer) net.getLayers().get(net.getLayers().size()-1)).getOutput().length)
                throw new Error("THE EXPECTED AND OUTPUT IS NOT THE SAME SIZE!!!"
                        + expected.length + " != " + ((FlatteningLayer) net.getLayers().get(net.getLayers().size()-1)).getOutput().length);
        }else {
            throw new Error("THE LAST LAYER IS NOT A FLATTENING LAYER!!!");
        }
        //System.out.println("Weight map size = " + net.getWeightMap().size());
        for(Map.Entry<Integer, ConvolutionWeight> entry : net.getWeightMap().entrySet()){
            ConvolutionWeight weight = entry.getValue();
            double delta = 1*differentiateConvolutionWeight(entry.getValue())*net.getSettings().getGradientRate();
            double old = weight.getValue();
            weight.setValue(weight.getValue() - delta);
            System.out.println("Delta = " + delta);
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
                double buffer = 1;
                int correspondingX = x/stride;//x in zL.
                int correspondingY = y/stride;//y in zL.
                Vector2Num<Integer> aLm1v = new Vector2Num<>(x + weight.getPos().getX(),y + weight.getPos().getY());
                Vector2Num<Integer> zLv = new Vector2Num<>(correspondingX, correspondingY);
                buffer *= a_Lm1.get(aLm1v);
                buffer *= weight.getNeuron().getLayer().getActivation().differentiateWRTx(z_L.get(zLv));
                buffer *= getConvolutionChain((ConvolutionalLayer)neuron.getLayer(), neuron.getIndexInLayer(), new Vector3Num<>(zLv.getX(), zLv.getY(), weight.getPos().getZ()));
                if(weight.getNeuron().getMaxValue() != 0)
                    buffer /= weight.getNeuron().getMaxValue();
                chain += buffer;
            }
        }
        return chain;
    }
    private double getConvolutionChain(ConvolutionNetLayer layer, int indexInLayer, Vector3Num<Integer> activation){
        double chain = 1;
        if(layer instanceof ConvolutionalLayer){//safety check just
            ConvolutionNeuron neuron = ((ConvolutionalLayer) layer).getNeurons().get(indexInLayer);
           // System.out.println(neuron.getIndexInLayer());
            if(neuron.isChainSet())
                return neuron.getCurrentChain();
            Map<Utils.Vector3Num<Integer>, Map<ConvolutionWeight, List<Utils.Vector3Num<Integer>>>> dependencies = ((ConvolutionalLayer) layer).getDependencies();
            /**
             * map that holds the vector of activation in prev layer, weights that were convoluted with it and the values that are dependent on this weight and activation.
             */
            double tmp = 0;
            for(Map.Entry<ConvolutionWeight, List<Utils.Vector3Num<Integer>>> entry : dependencies.get(activation).entrySet()){
                //check
                ConvolutionWeight weight = entry.getKey();
                List<Vector3Num<Integer>> netValuesInNextLayer = entry.getValue();
                //if next layer is convolutional.
                Layer next = net.getLayers().get(((ConvolutionalLayer) layer).getIndexInNet()+1);
                for(Vector3Num<Integer> netValue : netValuesInNextLayer){
                    double buffer = 1;
                    if(next instanceof ConvolutionalLayer) {
                        buffer *= weight.getValue();
                        buffer *= ((ConvolutionalLayer) next).getActivation().differentiateWRTx(((ConvolutionalLayer) layer).getBeforeActivationBox().getValue(netValue));
                        buffer *= getConvolutionChain((ConvolutionalLayer) next, netValue.getZ(), netValue);
                        if(weight.getNeuron().getMaxValue() != 0)
                            buffer /= weight.getNeuron().getMaxValue();
                    }
                    //if next layer is flattening
                    else if(next instanceof FlatteningLayer){
                        buffer *= countFinalChain((FlatteningLayer)next);
                    }
                    tmp += buffer;
                }
            }
            chain *= tmp;
            neuron.setCurrentChain(chain);
        }
        return chain;
    }
    private double countFinalChain(FlatteningLayer layer){
        //do flatted and error calculus
        double chain = 0;
        double[] output = layer.getOutput();
        if(output.length == this.expected.length){
            for(int i = 0; i < output.length; i ++)
                chain += 2*(output[i] - expected[i]);
        }
        return chain;
    }
}
