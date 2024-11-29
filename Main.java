import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class Main {
    // Метод для поиска самой длинной последовательности 'X' с использованием регулярных выражений
    public static int findLongestXSequence(String input) {
        // Регулярное выражение для поиска последовательностей 'X'
        Pattern pattern = Pattern.compile("X+");
        Matcher matcher = pattern.matcher(input);
        int longest = 0;

        // Перебор всех найденных последовательностей 'X'
        while (matcher.find()) {
            int currentLength = matcher.end() - matcher.start();
            longest = Math.max(longest, currentLength);
        }

        return longest;
    }

    public static void main(String[] args) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("24_demo.txt"));
             ExecutorService threadPool = Executors.newFixedThreadPool(5)) { //создаем 5 потоков

            // Список для хранения результатов выполнения потоков
            List<Future<Integer>> results = new ArrayList<>();

            String currentLine;

            // Чтение файла построчно
            while ((currentLine = bufferedReader.readLine()) != null) {
                // Использование многопоточности для ускорения обработки
                String lineToProcess = currentLine;
                Future<Integer> result = threadPool.submit(() -> findLongestXSequence(lineToProcess));
                results.add(result);
            }

            // Ожидание завершения всех потоков и нахождение максимального значения
            int maxSequenceLength = 0;
            for (Future<Integer> result : results) {
                maxSequenceLength = Math.max(maxSequenceLength, result.get());
            }

            System.out.println("Длина самой длинной последовательности X: " + maxSequenceLength);

        } catch (IOException | InterruptedException | ExecutionException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
