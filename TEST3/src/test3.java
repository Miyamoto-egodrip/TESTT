
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class test3 {
    public static void main(String[] args) throws IOException {
        String currentDirectory = System.getProperty("user.dir");
        System.out.println("Текущая рабочая директория: " + currentDirectory);
        if (args.length != 3) {
            System.out.println("Использование: java ReportGenerator <values.json> <tests.json> <report.json>");
            return;
        }

        String valuesFilePath = args[0];
        String testsFilePath = args[1];
        String reportFilePath = args[2];

        try {
            // Чтение JSON из файлов
            String valuesJson = new String(Files.readAllBytes(Paths.get(valuesFilePath)));
            String testsJson = new String(Files.readAllBytes(Paths.get(testsFilePath)));

            // Парсинг JSON
            JSONObject valuesObject = new JSONObject(valuesJson);
            JSONObject testsStructure = new JSONObject(testsJson);

            // Заполнение структуры значениями
            Map<String, Object> valuesMap = jsonToMap(valuesObject);
            fillReportStructure(testsStructure, valuesMap);

            // Сохранение результата в report.json
            Files.write(Paths.get(reportFilePath), testsStructure.toString(4).getBytes());
            System.out.println("Отчет сохранен в " + reportFilePath);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fillReportStructure(JSONObject testsStructure, Map<String, Object> values) {
        // Проверка на наличие id и заполнение value
        if (testsStructure.has("id")) {
            String id = testsStructure.getString("id");
            if (values.containsKey(id)) {
                testsStructure.put("value", values.get(id));
            }
        }

        // Рекурсивный обход вложенных объектов и массивов
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
