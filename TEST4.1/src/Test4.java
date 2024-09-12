import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Test4 {
    public static void main(String[] args) {
        List<Integer> numbersList = new ArrayList<>();

        // Проверка наличия аргументов
        if (args.length == 0) {
            System.out.println("Укажите название файла в качестве аргумента.");
            return;
        }

        // Чтение данных из файла
        try (BufferedReader reader = new BufferedReader(new FileReader(args[0]))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    numbersList.add(Integer.parseInt(line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Ошибка преобразования строки в число");
        }

        // Проверка наличия считанных данных
        if (numbersList.isEmpty()) {
            System.out.println("Данные не найдены в файле.");
            return;
        }

        // Изменяем для поиска минимальных движений
        int minMoves = Integer.MAX_VALUE;

        // Ищем минимальное количество ходов через попытку привести все к каждому значению
        for (int target : numbersList) {
            int movesCount = 0;
            for (int number : numbersList) {
                movesCount += Math.abs(number - target);
            }
            minMoves = Math.min(minMoves, movesCount);
        }

        System.out.println("Минимальное количество ходов: " + minMoves);
    }
}