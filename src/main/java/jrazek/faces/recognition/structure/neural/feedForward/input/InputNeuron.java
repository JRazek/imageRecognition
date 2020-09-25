package jrazek.faces.recognition.structure.neural.feedForward.input;

import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.neural.NeuralLayer;
import jrazek.faces.recognition.structure.neural.Neuron;
import jrazek.faces.recognition.structure.neural.feedForward.FFNeuron;

public class InputNeuron extends FFNeuron {
    InputNeuron(NeuralLayer<?extends Neuron> l, int indexInLayer){
        super(l, indexInLayer);
    }
    @Override
    public void run() {
        //taking from input or previous convolution layer
    }
    //
}
