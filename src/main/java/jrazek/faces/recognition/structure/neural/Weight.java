package jrazek.faces.recognition.structure.neural;

public abstract class Weight {
    private double value;
    protected Weight(double v){
        this.value = v;
    }
    public double getValue(){
        return value;
    }
    public void setValue(double v){
        value = v;
    }
}
