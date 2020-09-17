package jrazek.faces.recognition.structure.neural.convolutional.interfaces;

import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.utils.Utils;

import java.util.LinkedList;

/**
 * this is actually an interface that is inherited by
 * every type of layer in convolution part. Why? because
 * theres always an output of some box in here. In ff theres only
 * 1D Vector of output and thats why its not used by ff.
 */
public interface ConvolutionNetLayer {
    Utils.Matrix3D getOutputBox();
    int getOutputBoxWantedSize();
}
