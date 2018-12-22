package cz.org.appointment.api;

import java.util.HashMap;
import java.util.Map;

public class ParamMapUtil {

    public static Map<String, Integer> userMap() {
        Map<String, Integer> map = new HashMap<>();
        return map;
    }

    public static Map<String,Integer> pageMap(int pageNum , int pageSize){
        Map<String, Integer> map = new HashMap<>();
        map.put("pageNum", pageNum);
        map.put("pageSize", pageSize);
        return map;
    }
}
