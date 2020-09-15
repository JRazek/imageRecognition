package jrazek.faces.recognition.structure.activations;

public interface Activation {
    double count(double x);
    double differentiateWRT();//todo wrt what?
}
