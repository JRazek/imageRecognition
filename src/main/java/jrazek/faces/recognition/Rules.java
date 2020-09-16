package jrazek.faces.recognition;

import jrazek.faces.recognition.structure.activations.Activation;
import jrazek.faces.recognition.structure.activations.ReLU;
import jrazek.faces.recognition.structure.activations.Sigmoid;

public class Rules {
    public static Class<? extends Activation> convolutionActivation = ReLU.class;
    public static Class<? extends Activation> feedForwardActivation = Sigmoid.class;

}
