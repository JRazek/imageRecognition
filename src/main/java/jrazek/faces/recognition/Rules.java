package jrazek.faces.recognition;

import jrazek.faces.recognition.structure.activations.Activation;
import jrazek.faces.recognition.structure.activations.ReLU;
import jrazek.faces.recognition.structure.activations.Sigmoid;
import jrazek.faces.recognition.utils.Utils;

public class Rules {
    public static Class<? extends Activation> convolutionActivation = ReLU.class;
    public static Class<? extends Activation> feedForwardActivation = Sigmoid.class;
    public static int convolutionLayers = 5;//for now in rules. later in json file with full structure
    public static int feedForwardLayers = 3;
    public static int neuronsPerLayer = 10;
    public static Utils.Vector2Num<Integer> kernelSize = new Utils.Vector2Num<>(3,3);
}
