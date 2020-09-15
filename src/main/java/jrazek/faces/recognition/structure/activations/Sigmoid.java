package jrazek.faces.recognition.structure.activations;

public class Sigmoid implements Activation{
    @Override
    public double count(double x) {
        return 1d/(1 + Math.exp(-x));
    }
}
