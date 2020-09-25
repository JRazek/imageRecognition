package jrazek.faces.recognition.structure.neural.convolutional.interfaces;
import jrazek.faces.recognition.structure.neural.convolutional.ConvolutionNeuron;
import jrazek.faces.recognition.structure.neural.convolutional.ConvolutionWeight;
import jrazek.faces.recognition.structure.neural.convolutional.kernels.Kernel;
import jrazek.faces.recognition.utils.Utils;


/**
 * this is actually an interface that is inherited by
 * every type of layer in convolution part. Why? because
 * theres always an output of some box in here. In ff theres only
 * 1D Vector of output and thats why its not used by ff.
 */
public interface ConvolutionNetLayer {
    Utils.Matrix3D getOutputBox();
    static int afterConvolutionSize(int matrixSize, int kernelSize, int padding, int stride){
        return  ((matrixSize - kernelSize + 2*padding)/stride)+1;
    }
    static Utils.Matrix2D convolve(Utils.Matrix2D matrix, Kernel kernel, int padding, int stride){
        if(kernel.getSize().getX() % 2 == 1 && kernel.getSize().getY() % 2 == 1){
            Utils.Matrix2D result = new Utils.Matrix2D(new Utils.Vector2Num<>(afterConvolutionSize(matrix.getSize().getX(), kernel.getSize().getX(), padding, stride), afterConvolutionSize(matrix.getSize().getY(), kernel.getSize().getY(), padding, stride)));//holy fix in here
            int toCenterX = kernel.getSize().getX()/2;
            int toCenterY = kernel.getSize().getY()/2;
            for(int y = toCenterY; y < matrix.getSize().getY()-toCenterY; y+=stride){
                for(int x = toCenterX; x < matrix.getSize().getX()-toCenterX; x+=stride){
                    double sum = 0;
                    for(int j = 0; j < kernel.getSize().getY(); j++) {
                        for (int i = 0; i < kernel.getSize().getX(); i++) {
                            double factor1 = matrix.get(new Utils.Vector2Num<>(x-toCenterX+i, y-toCenterY+j));
                            double factor2 = kernel.get(new Utils.Vector2Num<>(i,j)).getValue();
                            sum += factor1*factor2;
                        }
                    }
                    //bias todo solve it somehow
                    result.set(new Utils.Vector2Num<>((x-toCenterX)/stride, (y-toCenterY)/stride), sum);
                }
            }
            return result;
        }
        return matrix;
    }

    /**
     * useless shit for now. It was required to use a different approach.
     */
    static double deConvolutionForWeight(ConvolutionWeight weight){
        Kernel kernel = weight.getNeuron().getKernelBox().getZMatrix(weight.getPos().getZ());
        Utils.Matrix2D beforeConvolution = ((ConvolutionNetLayer)weight.getNeuron().getLayer().getNet().getLayers().get((weight.getNeuron().getLayer().getIndexInNet()-1))).getOutputBox().getZMatrix(weight.getPos().getZ());
        int stride = weight.getNeuron().getLayer().getNet().getSettings().getConvolutionStride();
        double sum = 0;
        for (int y = 0; y < kernel.getSize().getY(); y += stride){
            for(int x = 0; x < kernel.getSize().getX(); x += stride){
                int addX = weight.getPos().getX();
                int addY = weight.getPos().getY();
                sum += beforeConvolution.get(new Utils.Vector2Num<>(x + addX, y + addY));
            }
        }
        return sum;
    }
}
