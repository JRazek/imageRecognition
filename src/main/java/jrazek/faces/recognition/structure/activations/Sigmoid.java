package jrazek.faces.recognition.structure.activations;

public class Sigmoid implements Activation{
    @Override
    public double count(double x) {
        return 1d/(1 + Math.exp(-x));
    }

    @Override
    public double differentiateWRTx(double x) {
        x = count(x);
        return x*(1-x);
    }
}
