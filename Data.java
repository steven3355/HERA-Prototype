package test.research.sjsu.heraprototypev_10;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;
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
                    byte[] toSend = new byte[Datasize + 2];
                    toSend[0] = (byte) ID;
                    toSend[1] = (byte) (ID == segmentCounts - 1 ? 0 : 1);
                    for (int i = 0; i < Datasize; i++) {
                        if (ID*Datasize + i >= data.length)
                            break;
                        toSend[i + 2] = data[ID*Datasize + i];
                    }
                    System.out.println("Data prepared, sequence: " + ID + " Length: " + toSend.length);
                    System.out.println("The toSend Array is " + new String(Arrays.copyOfRange(toSend, 2, toSend.length - 1)));
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
        segmentCounts = data.length / Datasize + (data.length % Datasize == 0 ? 0 : 1);
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
