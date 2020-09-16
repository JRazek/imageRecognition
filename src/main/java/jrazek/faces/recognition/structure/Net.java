package jrazek.faces.recognition.structure;

import jrazek.faces.recognition.Rules;
import jrazek.faces.recognition.structure.neural.convolutional.ConvolutionalLayer;

import java.util.Map;
import java.util.TreeMap;

public class Net {
    Map<Integer, Layer> layers = new TreeMap<>();

    public Map<Integer, Layer> getLayers() {
        return layers;
    }
    public Net(){

    }
    public void randomInit(){
        for(int i = 0; i < Rules.convolutionLayers; i ++){
            int index = layers.size();
            ConvolutionalLayer l = new ConvolutionalLayer(this, index);
            layers.put(index, l);
            l.initRandom();
        }
    }
}
