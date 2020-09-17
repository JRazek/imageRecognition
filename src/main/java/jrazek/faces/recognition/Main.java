package jrazek.faces.recognition;

import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.utils.Utils;

public class Main {
    public static void main(String[] args) {
        Net net = new Net();
        net.randomInit();
        Utils.Matrix2D sampleMatrix = new Utils.Matrix2D(new Utils.Vector2Num<>(9,9));
        Utils.Matrix2D sampleKernel = new Utils.Matrix2D(new Utils.Vector2Num<>(3,3));
        for(int x = 0; x < 9; x ++){
            for(int y = 0; y < 9; y ++){
                sampleMatrix.set(new Utils.Vector2Num<>(x,y), 1);
            }
        }
        for(int x = 0; x < 3; x ++){
            for(int y = 0; y < 3; y ++){
                sampleKernel.set(new Utils.Vector2Num<>(x,y), 1);
            }
        }
        sampleMatrix.set(new Utils.Vector2Num<>(5,5), 100);
        Utils.Matrix2D result = sampleMatrix.convolve(sampleKernel);
        for(int y = 0; y < result.getSize().getY(); y ++){
            for(int x = 0; x < result.getSize().getX(); x ++){
                System.out.print(result.get(new Utils.Vector2Num<>(x,y)) + ", ");
            }
            System.out.println();
        }

    }
}
