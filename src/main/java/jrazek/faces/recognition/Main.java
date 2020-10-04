package jrazek.faces.recognition;

import jrazek.faces.recognition.backpropagation.BackpropagationModule;
import jrazek.faces.recognition.netSetup.NetSettings;
import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.utils.Utils;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        /*net.forwardPass(new Utils.Matrix3D(new Utils.Vector3Num<>(1,1,1)));
        net.showResultBox();*/
        Net net = new Net(new NetSettings());
        net.randomInit();
        BackpropagationModule backpropagationModule = new BackpropagationModule(net);
        double prevLoss = 0;
        for(int i = 0; i < 100; i ++) {
            net.forwardPass(new Utils.Matrix3D(new Utils.Vector3Num<>(1,1,1)));
           // backpropagationModule.backPropagate(new double[]{0.01,0.99});
            backpropagationModule.backPropagate(new double[]{0.1, 0.44, 0.2, 0.99, 0.1, 0.7,0.4,0.64,0.1,0.01,0.1, 0.44, 0.2, 0.99, 0.1, 0.7,0.4,0.64,0.1,0.01, 0.1, 0.7,0.4,0.64,0.1,0.01, 0.1, 0.7,0.4,0.64,0.1,0.01});
            System.out.println("Loss = " + backpropagationModule.countLoss());
            //net.showResultBox();
            net.reset();
            //System.out.println("\n\n\n");
        }
    }
}
