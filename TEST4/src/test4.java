import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class test4 {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Пожалуйста, укажите имя файла в качестве аргумента командной строки.");
            return;
        }

        String filename = args[0];
        ArrayList<String> linesList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("[^\\x20-\\x7E]", "").trim(); // Очищаем строку
                System.out.println("Считанная строка: " + line);
                linesList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return; // Завершаем выполнение, если ошибка чтения файла
        }

        if (linesList.isEmpty()) {
            System.out.println("Файл пуст или не содержит данных.");
            return;
        }

        String[] numberStrings = linesList.get(0).split(",");
        System.out.println("Строки чисел после разбиения: " + Arrays.toString(numberStrings)); // Отладочная информация

        int[] numbersArray = new int[numberStrings.length];

        for (int i = 0; i < numberStrings.length; i++) {
            try {
                numbersArray[i] = Integer.parseInt(numberStrings[i].trim());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка преобразования строки в число: '" + numberStrings[i].trim() + "'");
                return; // Завершаем выполнение, если возникла ошибка преобразования
            }
        }

        System.out.println("Массив чисел: " + Arrays.toString(numbersArray)); // Выводим массив чисел для проверки

        Arrays.sort(numbersArray);
        int median;
        if (numbersArray.length % 2 == 0) {
            median = numbersArray[numbersArray.length / 2 - 1];
        } else {
            median = numbersArray[numbersArray.length / 2];
        }

        // Вычисляем минимальное количество ходов
        int moves = 0;
        for (int num : numbersArray) {
            moves += Math.abs(num - median);
        }

        System.out.println("Минимальное количество ходов: " + moves);
    }
}
