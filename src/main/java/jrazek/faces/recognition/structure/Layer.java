package jrazek.faces.recognition.structure;

public abstract class Layer {
    protected Layer(int index){
        this.index = index;
    }
    int index;
    public abstract void run();
}
