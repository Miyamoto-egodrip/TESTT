import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number");
        int n = input.nextInt(); //Ввод числа с консол                  //Ввод данных в консоль
        System.out.println("Enter the Interval");
        int m = input.nextInt(); //ВВод интервала

        int current = 1; //счетчик
        System.out.println("Path");
        do {
            System.out.print(current); //вывод
            current = (current + m - 2) % n + 1; //сдвиг
        } while (current != 1);  //конец цикла

    }
}