import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Test3 {
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Использование: java Test3 <values.json> <tests.json> <report.json>");
            return;
        }

        String valuesFilePath = args[0];
        String testsFilePath = args[1];
        String reportFilePath = args[2];

        try {
            String valuesJson = new String(Files.readAllBytes(Paths.get(valuesFilePath)));
            String testsJson = new String(Files.readAllBytes(Paths.get(testsFilePath)));

            JSONObject valuesObject = new JSONObject(valuesJson);
            JSONObject testsStructure = new JSONObject(testsJson);

            Map<String, Object> valuesMap = jsonToMap(valuesObject);
            fillReportStructure(testsStructure, valuesMap);

            Files.write(Paths.get(reportFilePath), testsStructure.toString(4).getBytes());
            System.out.println("Отчет сохранен в " + reportFilePath);

        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
        }
    }

    private static void fillReportStructure(JSONObject testsStructure, Map<String, Object> values) {
        if (testsStructure.has("id")) {
            Object idObject = testsStructure.get("id");
            String id = null;

            if (idObject instanceof String) {
                id = (String) idObject;
            } else if (idObject instanceof Integer) {
                id = String.valueOf(idObject);
            }

            if (id != null && values.containsKey(id)) {
                testsStructure.put("value", values.get(id));
            }
        }

        for (String key : testsStructure.keySet()) {
            Object value = testsStructure.get(key);
            if (value instanceof JSONObject) {
                fillReportStructure((JSONObject) value, values);
            } else if (value instanceof JSONArray) {
                for (Object item : (JSONArray) value) {
                    if (item instanceof JSONObject) {
                        fillReportStructure((JSONObject) item, values);
                    }
                }
            }
        }
    }

    private static Map<String, Object> jsonToMap(JSONObject jsonObject) {
        Map<String, Object> map = new HashMap<>();
        for (String key : jsonObject.keySet()) {
            map.put(key, jsonObject.get(key));
        }
        return map;
    }
}
