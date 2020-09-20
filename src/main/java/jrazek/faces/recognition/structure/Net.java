package jrazek.faces.recognition.structure;

import jrazek.faces.recognition.netSetup.NetSettings;
import jrazek.faces.recognition.structure.activations.ReLU;
import jrazek.faces.recognition.structure.functional.ConvolutionalInputLayer;
import jrazek.faces.recognition.structure.functional.FlatteningLayer;
import jrazek.faces.recognition.structure.functional.PoolingLayer;
import jrazek.faces.recognition.structure.neural.convolutional.ConvolutionalLayer;
import jrazek.faces.recognition.structure.neural.convolutional.interfaces.ConvolutionNetLayer;
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
            if(i == 2 || i == 4 || i == 6){
                PoolingLayer l = new PoolingLayer(this, index);
                layers.put(index, l);
            }else {
                ConvolutionalLayer l = new ConvolutionalLayer(this, new ReLU(), index);//just for tests
                layers.put(index, l);
                l.setRandom();
            }
        }
        layers.put(layers.size(), new FlatteningLayer(this, layers.size()));
        System.out.println();
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
                System.out.println("Flattening Layer");
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
        }
    }

    public NetSettings getSettings() {
        return settings;
    }
}
