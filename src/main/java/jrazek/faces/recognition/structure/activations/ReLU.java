package jrazek.faces.recognition.structure.activations;

public class ReLU implements Activation{
    @Override
    public double count(double x) {
        if(x < 0) return 0;
        else return x;
    }
}
