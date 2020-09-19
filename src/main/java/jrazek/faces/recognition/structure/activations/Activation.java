package jrazek.faces.recognition.structure.activations;

public interface Activation {
    double count(double x);
    double differentiateWRTx(double x);
}
