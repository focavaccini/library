package library.utils;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Utils {

    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        if (obj instanceof String) {
            return ((String) obj).trim().isEmpty();
        }
        if (obj instanceof Collection) {
            return ((Collection<?>) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map<?, ?>) obj).isEmpty();
        }

        return false;
    }

    /**
     * Retorna o primeiro valor não nulo dos argumentos.
     *
     * @param originalInstance valor prioritário
     * @param returnIfNull     valor a ser utilizado se {@code originalInstance} não existir
     * @return um dos argumentos
     */
    public static <T> T nvl(Object originalInstance, T returnIfNull) {
        if (originalInstance instanceof String) {
            String a = (String) originalInstance;
            if (a.trim().isEmpty()) {
                return returnIfNull;
            }
        }
        return (T) ((originalInstance == null) ? returnIfNull : originalInstance);
    }

    public static Map<String, Object> convertXmlToJson(JSONObject jsonObject) {
        return jsonToMap(jsonObject);
    }

    public static String readFile(File file) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
        }
        return content.toString();
    }

    private static Map<String, Object> jsonToMap(JSONObject jsonObject) {
        Map<String, Object> map = new HashMap<>();

        for (String key : jsonObject.keySet()) {
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                map.put(key, jsonToMap((JSONObject) value));
            } else {
                map.put(key, value);
            }
        }
        return map;
    }

}
