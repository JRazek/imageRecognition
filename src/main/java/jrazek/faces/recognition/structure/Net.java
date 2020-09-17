package jrazek.faces.recognition.structure;

import jrazek.faces.recognition.Rules;
import jrazek.faces.recognition.structure.functional.ConvolutionalInputLayer;
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
        layers.put(0, new ConvolutionalInputLayer(this, 0));
        for(int i = 1; i < Rules.convolutionLayers; i ++){
            int index = layers.size();
            ConvolutionalLayer l = new ConvolutionalLayer(this, index);//just for tests
            layers.put(index, l);
            l.initRandom();
        }
    }
    public void forwardPass(Utils.Matrix3D input){
        int i = 0;
        for(Map.Entry<Integer, Layer> entry : layers.entrySet()){
            if(i != 0)
                entry.getValue().run();
            i++;
        }
    }
}
