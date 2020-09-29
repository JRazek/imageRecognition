package jrazek.faces.recognition.structure;

import jrazek.faces.recognition.netSetup.NetSettings;
import jrazek.faces.recognition.structure.activations.ReLU;
import jrazek.faces.recognition.structure.functional.ConvolutionalInputLayer;
import jrazek.faces.recognition.structure.functional.FlatteningLayer;
import jrazek.faces.recognition.structure.functional.PoolingLayer;
import jrazek.faces.recognition.structure.neural.NeuralLayer;
import jrazek.faces.recognition.structure.neural.convolutional.ConvolutionNeuron;
import jrazek.faces.recognition.structure.neural.convolutional.ConvolutionWeight;
import jrazek.faces.recognition.structure.neural.convolutional.ConvolutionalLayer;
import jrazek.faces.recognition.structure.neural.convolutional.interfaces.ConvolutionNetLayer;
import jrazek.faces.recognition.utils.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Net {
    private Map<Integer, Layer> layers = new TreeMap<>();
    private Map<Integer, ConvolutionWeight> weightMap = new HashMap<>();
    private NetSettings settings;//
    public Net(NetSettings netSettings){
        this.settings = netSettings;
    }
    public Map<Integer, Layer> getLayers() {
        return layers;
    }
    public void randomInit(){
        layers.put(0, new ConvolutionalInputLayer(this, 0));//todo change not always the first!
        for(int i = 0; i < settings.getConvolutionLayersCount(); i ++){
            int index = layers.size();
            if(i == -2){
                PoolingLayer l = new PoolingLayer(this, index);
                layers.put(index, l);
            }else {
                ConvolutionalLayer l = new ConvolutionalLayer(this, new ReLU(), index);//just for tests
                layers.put(index, l);
                l.setRandom();
            }
        }
        layers.put(layers.size(), new FlatteningLayer(this, layers.size()));
    }
    public void forwardPass(Utils.Matrix3D input){
        int i = 0;
        //todo feeding first layer!
        for(Map.Entry<Integer, Layer> entry : layers.entrySet()){
            entry.getValue().run();
            if(entry.getValue() instanceof PoolingLayer){
                System.out.println("Pooling Layer");
            }
            if(entry.getValue() instanceof FlatteningLayer){
               // System.out.println("Flattening Layer");
            }
            i++;
        }
    }
    public void showResultBox(){
        if(layers.get(layers.size()-1) instanceof ConvolutionNetLayer){
            Utils.Matrix3D box = ((ConvolutionNetLayer) layers.get(layers.size()-1)).getOutputBox();
            for(int z = 0; z < box.getSize().getZ(); z++){
                for(int y = 0; y < box.getSize().getY(); y++){
                    for(int x = 0; x < box.getSize().getX(); x++){
                        System.out.print(box.getValue(new Utils.Vector3Num<>(x,y,z)) + " ");
                    }
                    System.out.println();
                }
                System.out.println("\n\n");
            }
        }else
            if(layers.get(layers.size()-1) instanceof FlatteningLayer){
                double [] vector = ((FlatteningLayer) layers.get(layers.size()-1)).getOutput();
                for (double v : vector) {
                    System.out.println(v);
                }
            }
    }
    public void addWeight(ConvolutionWeight c){
        weightMap.put(weightMap.size(), c);
    }
    public Map<Integer, ConvolutionWeight> getWeightMap() {
        return weightMap;
    }

    public void reset(){
        for(Map.Entry<Integer, Layer> entry : layers.entrySet()){
            if(entry.getValue() instanceof NeuralLayer){
                NeuralLayer<ConvolutionNeuron> layer = ((NeuralLayer<ConvolutionNeuron>) entry.getValue());
                layer.resetNeuronsChains();
                layer.reset();
            }
            if(entry.getValue() instanceof FlatteningLayer)
                ((FlatteningLayer) entry.getValue()).reset();
        }
    }

    public NetSettings getSettings() {
        return settings;
    }
}
