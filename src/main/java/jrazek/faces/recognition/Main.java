package jrazek.faces.recognition;

import jrazek.faces.recognition.backpropagation.BackpropagationModule;
import jrazek.faces.recognition.netSetup.NetSettings;
import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.utils.Utils;

public class Main {
    public static void main(String[] args) {
        Net net = new Net(new NetSettings());
        net.randomInit();
        BackpropagationModule backpropagationModule = new BackpropagationModule(net);
        net.forwardPass(new Utils.Matrix3D(new Utils.Vector3Num<>(1,1,1)));
        net.showResultBox();
       /* for(;;) {
            net.forwardPass(new Utils.Matrix3D(new Utils.Vector3Num<>(1,1,1)));
            backpropagationModule.backPropagate(new double[]{0.1, 0.44, 0.2, 0.99, 0.1, 0.7});
            net.reset();
        }*/
        //System.out.println("OK");
    }
}
