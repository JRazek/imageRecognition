package jrazek.faces.recognition.structure.neural.feedForward;

import jrazek.faces.recognition.structure.neural.NeuralLayer;

public abstract class FFLayer extends NeuralLayer<FFNeuron> {

    public FFLayer(int index) {
        super(index);
    }
}
