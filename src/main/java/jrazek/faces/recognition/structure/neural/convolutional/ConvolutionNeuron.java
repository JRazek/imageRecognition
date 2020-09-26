package jrazek.faces.recognition.structure.neural.convolutional;

import jdk.jshell.execution.Util;
import jrazek.faces.recognition.structure.Layer;
import jrazek.faces.recognition.structure.neural.NeuralLayer;
import jrazek.faces.recognition.structure.neural.Neuron;
import jrazek.faces.recognition.structure.neural.convolutional.interfaces.ConvolutionNetLayer;
import jrazek.faces.recognition.structure.neural.convolutional.kernels.Kernel;
import jrazek.faces.recognition.structure.neural.convolutional.kernels.KernelBox;
import jrazek.faces.recognition.utils.Utils;

import javax.management.RuntimeErrorException;

public class ConvolutionNeuron extends Neuron {
    private final Utils.Vector3Num<Integer> size;
    private KernelBox kernelBox;
    private Utils.Matrix2D beforeActivation;//wartosc przed aktywacja, biasem oraz normalizacja
    private Utils.Matrix2D output;//z neuronu wychodzi jedynie jeden plaster i idzie do boxa warstwy


    public ConvolutionNeuron(NeuralLayer<?extends Neuron> l, int indexInLayer, Utils.Vector3Num<Integer> size) throws RuntimeErrorException {
        super(l, indexInLayer);
        if ((size.getX() / 2) * 2 == size.getX() || (size.getY() / 2) * 2 == size.getY()/* || !size.getX().equals(size.getY())*/)
            throw new RuntimeErrorException(new Error("the Kernel size must be odd number!"));
        this.size = size;
        kernelBox = new KernelBox(size);
    }

    void initRandomWeights() {
        for (int z = 0; z < size.getZ(); z++) {
            for (int y = 0; y < size.getY(); y++) {
                for (int x = 0; x < size.getX(); x++) {
                    double randomValue  = Utils.randomDouble(0,1);
                    Utils.Vector3Num<Integer> pos = new Utils.Vector3Num<>(x, y, z);
                    ConvolutionWeight weight = new ConvolutionWeight(this, pos, randomValue);
                    kernelBox.setWeight(pos, weight);
                    getLayer().getNet().addWeight(weight);
                }
            }
        }
    }

    public double getWeight(Utils.Vector3Num<Integer> c) throws RuntimeErrorException {
        return kernelBox.getWeight(c).getValue();
    }

    public Kernel getZKernelAsVector(int z) {
        return kernelBox.getZMatrix(z);
    }

    public void updateKernel(Utils.Vector3Num<Integer> c, double value){
        kernelBox.set(c, value);
    }

    @Override
    public void run() {
        if(getLayer().getIndexInNet() != 0) {
            Layer prev = getLayer().getNet().getLayers().get(getLayer().getIndexInNet() - 1);
            if (prev instanceof ConvolutionNetLayer) {
                Utils.Matrix3D givenTensor = ((ConvolutionNetLayer) prev).getOutputBox();
                Utils.Matrix2D finalResult = null;
                int padding = getLayer().getNet().getSettings().getConvolutionPadding();
                int stride = getLayer().getNet().getSettings().getConvolutionStride();
                double maxValue = 0;
                for (int z = 0; z < givenTensor.getSize().getZ(); z++) {
                    //summing all layers
                    Utils.Matrix2D matrix2D = givenTensor.getZMatrix(z);
                    Utils.Matrix2D convolvedMatrix = ConvolutionNetLayer.convolve(matrix2D, kernelBox.getZMatrix(z), padding, stride);
                    if(z == 0)
                        finalResult = new Utils.Matrix2D(convolvedMatrix.getSize());
                    maxValue += convolvedMatrix.getMaxValue();
                    finalResult.add(convolvedMatrix);
                }
                beforeActivation = finalResult;
                output = new Utils.Matrix2D(beforeActivation.getSize());
                for(int y = 0; y < finalResult.getSize().getY(); y++){
                    for(int x = 0; x < finalResult.getSize().getX(); x++){
                        double afterChangesValue = finalResult.get(new Utils.Vector2Num<>(x,y));
                        if(maxValue != 0){
                            afterChangesValue /= maxValue;
                        }
                        afterChangesValue = getLayer().getActivation().count(afterChangesValue + getBias());
                        output.set(new Utils.Vector2Num<>(x,y),afterChangesValue);
                    }
                }
                ((ConvolutionalLayer)getLayer()).addToBox(beforeActivation, output);
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
        return kernelBox;
    }

    public Utils.Matrix2D getBeforeActivation() {
        return beforeActivation;
    }
}