package test.research.sjsu.heraprototypev_10;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Steven on 10/13/2017.
 */

public class HERA {
    private int H;
    private double alpha, beta, gamma;
    private Map<String, List<Double>> reachabilityMatrix = new HashMap<>();
    public HERA(int maxHop, double agingConstant, double intrinsicConfidence, double weight) {
        H = maxHop;
        alpha = agingConstant;
        beta = intrinsicConfidence;
        gamma = weight;
    }
    public double fowardingDecision(String destination) {
        if (!reachabilityMatrix.containsKey(destination)) {
           reachabilityMatrix.put(destination, new ArrayList<Double>(H));
        }
        List<Double> destinationReachability = reachabilityMatrix.get(destination);
        double weightedSum = 0;
        for (int i = 0; i < H; i++) {
            weightedSum += gamma * destinationReachability.get(i);
        }
        return weightedSum;
    }

    public void updateMatrix() {

    }

}
