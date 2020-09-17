package jrazek.faces.recognition.structure;

import jrazek.faces.recognition.Rules;
import jrazek.faces.recognition.structure.neural.convolutional.ConvolutionalLayer;
import jrazek.faces.recognition.utils.Utils;

import java.util.Map;
import java.util.TreeMap;

public class Net {
    Map<Integer, Layer> layers = new TreeMap<>();

    public Map<Integer, Layer> getLayers() {
        return layers;
    }
    public void randomInit(){
        for(int i = 0; i < Rules.convolutionLayers; i ++){
            int index = layers.size();
            ConvolutionalLayer l = new ConvolutionalLayer(this, index);
            layers.put(index, l);
            l.initRandom();
        }
    }
    public void forwardPass(Utils.Matrix3D input){
        for(Map.Entry<Integer, Layer> entry : layers.entrySet()){
            entry.getValue().run();
        }
    }
}
