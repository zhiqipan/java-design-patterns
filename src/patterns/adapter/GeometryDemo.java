package patterns.adapter;

import java.util.*;
import java.util.function.Consumer;

class Point {
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Point point = (Point)o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return x * 31 + y;
    }

    @Override
    public String toString() {
        return "Point{" +
            "x=" + x +
            ", y=" + y +
            '}';
    }
}

class Line {
    Point start, end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Line line = (Line)o;
        return start != null
            && start.equals(line.start)
            && end != null
            && end.equals(line.end);
    }

    public int hashCode() {
        int startHash = start != null ? start.hashCode() : 0;
        int endHash = end != null ? end.hashCode() : 0;
        return 31 * startHash + endHash;
    }
}

class VectorObject extends ArrayList<Line> {

}

class VectorRectangle extends VectorObject {
    public VectorRectangle(int x, int y, int w, int h) {
        add(new Line(new Point(x, y), new Point(x + w, y)));
        add(new Line(new Point(x + w, y), new Point(x + w, y + h)));
        add(new Line(new Point(x, y), new Point(x, y + h)));
        add(new Line(new Point(x, y + h), new Point(x + w, y + h)));
    }
}

class LineToPointAdapter implements Iterable<Point> {
    private static int count = 0;
    private static Map<Integer, List<Point>> cache = new HashMap<>();
    private int hash;

    public LineToPointAdapter(Line line) {
        hash = line.hashCode();
        if (cache.get(hash) != null) {
            return;
        }

        System.out.println(
            String.format("%d: Generating points for line [%d,%d]-[%d,%d] (with caching)",
                ++count, line.start.x, line.start.y, line.end.x, line.end.y));

        ArrayList<Point> points = new ArrayList<>();

        int left = Math.min(line.start.x, line.end.x);
        int right = Math.max(line.start.x, line.end.x);
        int top = Math.min(line.start.y, line.end.y);
        int bottom = Math.max(line.start.y, line.end.y);
        int dx = right - left;
        int dy = line.end.y - line.start.y;

        if (dx == 0) {
            for (int y = top; y <= bottom; y++) {
                points.add(new Point(left, y));
            }
        } else if (dy == 0) {
            for (int x = left; x <= right; x++) {
                points.add(new Point(x, top));
            }
        }
        cache.put(hash, points);
    }

    @Override
    public Iterator<Point> iterator() {
        return cache.get(hash).iterator();
    }

    @Override
    public void forEach(Consumer<? super Point> action) {
        cache.get(hash).forEach(action);
    }

    @Override
    public Spliterator<Point> spliterator() {
        return cache.get(hash).spliterator();
    }
}

public class GeometryDemo {
    private final static List<VectorObject> vectorObjects
        = new ArrayList<>(Arrays.asList(
        new VectorRectangle(1, 1, 10, 10),
        new VectorRectangle(3, 3, 6, 6)
    ));

    private static void drawPoint(Point p) {
        System.out.println("Drawing point " + p);
    }

    private static void draw() {
        for (VectorObject vo : vectorObjects) {
            for (Line line : vo) {
                LineToPointAdapter adapter = new LineToPointAdapter(line);
                adapter.forEach(p -> drawPoint(p));
            }
        }
    }

    public static void main(String[] args) {
        draw();
        draw();
    }
}
