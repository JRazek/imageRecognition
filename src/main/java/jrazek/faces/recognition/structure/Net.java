package jrazek.faces.recognition.structure;

import java.util.Map;
import java.util.TreeMap;

public class Net {
    Map<Integer, Layer> layers = new TreeMap<>();

    public Map<Integer, Layer> getLayers() {
        return layers;
    }
}
