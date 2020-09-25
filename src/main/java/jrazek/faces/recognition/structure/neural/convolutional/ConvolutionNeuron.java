package jrazek.faces.recognition.structure.neural.convolutional;

import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.neural.NeuralLayer;
import jrazek.faces.recognition.structure.neural.Neuron;
import jrazek.faces.recognition.structure.neural.convolutional.interfaces.ConvolutionNetLayer;
import jrazek.faces.recognition.structure.neural.convolutional.kernels.KernelBox;
import jrazek.faces.recognition.utils.Utils;

import javax.management.RuntimeErrorException;
import java.util.HashMap;
import java.util.Map;

public class ConvolutionNeuron extends Neuron {
    private final Utils.Vector3Num<Integer> size;
    private KernelBox kernel;
    private Utils.Matrix2D beforeActivation;
    private Utils.Matrix2D output;

    Map<Utils.Vector3Num<Integer>, ConvolutionWeight> correspondingValues = new HashMap<>();


    public ConvolutionNeuron(NeuralLayer<?extends Neuron> l, int indexInLayer, Utils.Vector3Num<Integer> size) throws RuntimeErrorException {
        super(l, indexInLayer);
        if ((size.getX() / 2) * 2 == size.getX() || (size.getY() / 2) * 2 == size.getY()/* || !size.getX().equals(size.getY())*/)
            throw new RuntimeErrorException(new Error("the Kernel size must be odd number!"));
        this.size = size;
        kernel = new KernelBox(size);
    }

    void initRandomWeights() {
        for (int z = 0; z < size.getZ(); z++) {
            for (int x = 0; x < size.getX(); x++) {
                for (int y = 0; y < size.getY(); y++) {
                    double randomValue  = Utils.randomDouble(-1,1);
                    Utils.Vector3Num<Integer> pos = new Utils.Vector3Num<>(x, y, z);
                    kernel.setWeight(pos, new ConvolutionWeight(this, pos,1d));
                }
            }
        }
    }

    Double getWeight(Utils.Vector3Num<Integer> c) throws RuntimeErrorException {
        return kernel.getWeight(c).getValue();
    }
    public void updateKernel(Utils.Vector3Num<Integer> c, double value){
        kernel.set(c, value);
    }
    @Override
    public void run() {
        if(getLayer().getIndexInNet() != 0) {
            Layer prev = getLayer().getNet().getLayers().get(getLayer().getIndexInNet() - 1);
            if (prev instanceof ConvolutionNetLayer) {
                Utils.Matrix3D givenMatrix = ((ConvolutionNetLayer) prev).getOutputBox();
                Utils.Matrix2D result = null;
                for (int z = 0; z < givenMatrix.getSize().getZ(); z++) {
                    int padding = getLayer().getNet().getSettings().getConvolutionPadding();
                    int stride = getLayer().getNet().getSettings().getConvolutionStride();
                    this.output = ConvolutionNetLayer.convolve(givenMatrix.getZMatrix(z), this.kernel.getZMatrix(z), padding, stride);
                    this.beforeActivation = output;
                    if(z == 0){
                        result = new Utils.Matrix2D(output.getSize());
                    }
                    for(int y = 0; y < result.getSize().getY(); y++){
                        for(int x = 0; x < result.getSize().getX(); x++){
                            Utils.Vector2Num<Integer> c = new Utils.Vector2Num<>(x,y);
                            double afterActivation = (getLayer()).getActivation().count( output.get(c) + getBias());
                            output.set(c, afterActivation);
                        }
                    }
                    result.add(output);
                }
                ((ConvolutionalLayer) getLayer()).addToBox(result);
            } else {
                //todo
                //if taking from feed forward layer. not supported in first versions for sure
            }
        }else throw new RuntimeErrorException(new Error("ERROR234"));
    }
    public Utils.Matrix2D getOutput() {
        return output;
    }

    public KernelBox getKernelBox() {
        return kernel;
    }

    public Utils.Matrix2D getBeforeActivation() {
        return beforeActivation;
    }
}