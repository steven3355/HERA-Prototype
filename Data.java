package test.research.sjsu.heraprototypev_10;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Steven on 10/11/2017.
 */
public class Data{
    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    static String CharData = new String("No data has been set");
    static byte[] data;
    static int segmentCounts;
    static int Datasize;
    public static byte[] getData(UUID uuid, int ID){
        String id = new String(uuid.toString().substring(4,8));
        System.out.println(id);

        switch(id){
            case "3000":
                if (ID < segmentCounts) {
                    List<Byte> temp = new ArrayList<>();
                    temp.add((byte) ID);
                    temp.add((byte) (ID == segmentCounts - 1 ? 0 : 1));
                    for (int i = 0; i < 300; i++) {
                        if (ID*300 + i >= data.length)
                            break;
                        temp.add(data[ID*300 + i]);
                    }
                    byte[] toSend = new byte[temp.size()];
                    for (int i = 0; i < temp.size(); i++) {
                        toSend[i] = temp.get(i);
                    }
                    System.out.println("Data prepared, sequence: " + ID + " Length: " + toSend.length);
                    System.out.println("The toSend Array is " + bytesToHex(toSend));
                    return toSend;
                }
        }
        return new byte[0];
    }

    public static void setReachabilityMatrixData(Map<String, List<Double>> map, int mtu) throws IOException{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(map);
        Datasize = mtu - 2;
        data = bos.toByteArray();
        System.out.println("Successfully converted Map to Byte Array, size : " + data.length);
        oos.close();
        segmentCounts = data.length / 300 + (data.length % 300 == 0 ? 0 : 1);

        System.out.println("Converted Reachability Matrix: " + bytesToHex(data));
        ObjectInputStream input = null;
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
            input = new ObjectInputStream(inputStream);
            Map<String, List<Double>> neighborReachabilityMatrix = (Map<String, List<Double>>) input.readObject();
            System.out.println("Test: " + neighborReachabilityMatrix.toString());
        } catch (Exception e) {
            System.out.println("Reconstruct map exception" + e.fillInStackTrace());
        }
    }

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
