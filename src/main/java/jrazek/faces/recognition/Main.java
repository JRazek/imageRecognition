package jrazek.faces.recognition;

import jrazek.faces.recognition.backpropagation.BackpropagationModule;
import jrazek.faces.recognition.netSetup.NetSettings;
import jrazek.faces.recognition.structure.Net;
import jrazek.faces.recognition.utils.Utils;

public class Main {
    public static void main(String[] args) {
        Net net = new Net(new NetSettings());
        net.randomInit();
        net.forwardPass(new Utils.Matrix3D(new Utils.Vector3Num<>(1,1,1)));
        BackpropagationModule backpropagationModule = new BackpropagationModule(net);
        for(int i = 0; i < 1000; i ++)
            backpropagationModule.backPropagate(new double[]{0.1,0.44,0.2,0.99,0.1,0.7,0.3,0.876,0.6,0.21});
        //net.showResultBox();
        /*for(int i = 0; i < 100; i ++){
            System.out.println(Utils.randomDouble(-10,10));
        }*/
        System.out.println("OK");
    }
}
