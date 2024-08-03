package com.kostik.store.service.userData;

import java.util.HashMap;
import java.util.Map;

public class UserDataUtils {
	
	public static String mapToString(Map<String, String> data) {
		String result = "";
		for (Map.Entry<String, String> entry : data.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            result += key + ":" + value + ";";
        }
		return result;
	}
	
	public static Map<String, String> stringToMap(String input){
        Map<String, String> map = new HashMap<>();
        String[] pairs = input.split(";");
        for (String pair : pairs) {
            String[] keyValue = pair.split(":");
            if (keyValue.length == 2) {
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();
                map.put(key, value);
            }
        }

        return map;
	}

}
