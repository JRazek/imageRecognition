package jrazek.faces.recognition.structure;

import jrazek.faces.recognition.netSetup.NetSettings;
import jrazek.faces.recognition.structure.activations.ReLU;
import jrazek.faces.recognition.structure.functional.ConvolutionalInputLayer;
import jrazek.faces.recognition.structure.functional.PoolingLayer;
import jrazek.faces.recognition.structure.neural.convolutional.ConvolutionalLayer;
import jrazek.faces.recognition.utils.Utils;

import java.util.Map;
import java.util.TreeMap;

public class Net {
    private Map<Integer, Layer> layers = new TreeMap<>();
    private NetSettings settings;
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
            if(i == 3){
                PoolingLayer l = new PoolingLayer(this, index);
                layers.put(index, l);
            }else {
                ConvolutionalLayer l = new ConvolutionalLayer(this, new ReLU(), index);//just for tests
                layers.put(index, l);
                l.initRandom();
            }
        }
    }
    public void forwardPass(Utils.Matrix3D input){
        int i = 0;
        //todo feeding first layer!
        for(Map.Entry<Integer, Layer> entry : layers.entrySet()){
            entry.getValue().run();
            if(entry.getValue() instanceof PoolingLayer){
                System.out.println("Pooling Layer");
            }
            i++;
        }
    }

    public NetSettings getSettings() {
        return settings;
    }
}
