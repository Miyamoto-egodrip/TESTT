import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class task2 {

    static class Circle {
        double centerX;
        double centerY;
        double radius;

        Circle(double centerX, double centerY, double radius) {
            this.centerX = centerX;
            this.centerY = centerY;
            this.radius = radius;
        }
    }

    static class Point {
        double x;
        double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: java task2 <circleFile> <pointsFile>");
            return;
        }

        String circleFile = args[0];
        String pointsFile = args[1];

        Circle circle = readCircleFromFile(circleFile);              //прием аргументов при запуске программы
        List<Point> points = readPointsFromFile(pointsFile);

        for (Point point : points) {
            System.out.println(getPosition(circle, point));
        }
    }

    private static Circle readCircleFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line1 = br.readLine();
            String[] centerCoordinates = line1.split(" ");
            double centerX = Double.parseDouble(centerCoordinates[0]);
            double centerY = Double.parseDouble(centerCoordinates[1]);
            double radius = Double.parseDouble(br.readLine().trim());
            return new Circle(centerX, centerY, radius);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<Point> readPointsFromFile(String filename) {
        List<Point> points = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] coordinates = line.split(" ");
                double x = Double.parseDouble(coordinates[0]);
                double y = Double.parseDouble(coordinates[1]);
                points.add(new Point(x, y));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return points;
    }

    private static int getPosition(Circle circle, Point point) {
        double distanceSquared = Math.pow(point.x - circle.centerX, 2) + Math.pow(point.y - circle.centerY, 2);
        double radiusSquared = Math.pow(circle.radius, 2);

        if (distanceSquared < radiusSquared) {
            return 1; // точка внутри окружности
        } else if (distanceSquared == radiusSquared) {
            return 0; // точка на окружности
        } else {
            return 2; // точка вне окружности
        }
    }
}
