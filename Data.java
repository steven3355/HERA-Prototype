package test.research.sjsu.heraprototypev_10;

import java.util.UUID;

/**
 * Created by Steven on 10/11/2017.
 */

public class Data {
    static String CharData = new String("No data has been set");
    public static byte[] getData(UUID uuid){
        String id = new String(uuid.toString().substring(4,8));
        System.out.println(id);
        switch(id){
            case "3000":
                return CharData.getBytes();
        }
        return null;
    }

    public static void setData(String str) {
        CharData = str;
    }
}
