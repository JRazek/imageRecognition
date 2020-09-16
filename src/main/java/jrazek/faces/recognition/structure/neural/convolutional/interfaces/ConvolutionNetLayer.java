package jrazek.faces.recognition.structure.neural.convolutional.interfaces;

import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.utils.Utils;

import java.util.LinkedList;

public interface ConvolutionNetLayer {
    LinkedList<Utils.Matrix2D> getOutputBox();
    int getOutputBoxWantedSize();

}
