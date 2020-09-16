package jrazek.faces.recognition.structure;

public abstract class Layer {
    protected Layer(Net net, int index){
        this.indexInNet = index;
        this.net = net;
    }
    int indexInNet;
    Net net;

    public int getIndexInNet() {
        return indexInNet;
    }

    public Net getNet() {
        return net;
    }
    public abstract void run();
}
