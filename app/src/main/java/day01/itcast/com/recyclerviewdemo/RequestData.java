package day01.itcast.com.recyclerviewdemo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by me2 on 2016/12/10.
 */

public class RequestData {
    private static List<Object> data = new ArrayList<>();

    public static List<Object> getData() {
        data.clear();
        for (int i = 0; i < 30; i++) {

            data.add("小馨馨" + i);
        }
        return data;
    }

    public static List<Object> getNewData() {
        data.clear();
        for (int i = 0; i < 30; i++) {
            data.add("123go" + i);
        }
        return data;
    }

}
