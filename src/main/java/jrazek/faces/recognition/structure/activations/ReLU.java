package jrazek.faces.recognition.structure.activations;

public class ReLU implements Activation{
    @Override
    public double count(double x) {
        if(x < 0) return 0;
        else return x;
    }

    @Override
    public double differentiateWRTx(double x) {
        if(x >= 0)
            return 1;
        return 0;
    }
}
