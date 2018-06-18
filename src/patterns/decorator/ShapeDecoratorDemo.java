package patterns.decorator;

import java.util.function.Supplier;

interface Shape {
    String info();
}

class Circle implements Shape {
    private float radius;

    public Circle() {}

    public Circle(float radius) {
        this.radius = radius;
    }

    void resize(float factor) {
        this.radius *= factor;
    }

    @Override
    public String info() {
        return "A circle of radius " + radius;
    }
}

class Square implements Shape {
    private float side;

    public Square() {}

    public Square(float side) {
        this.side = side;
    }

    void resize(float factor) {
        this.side *= factor;
    }

    @Override
    public String info() {
        return "A square of side " + side;
    }
}

class ColoredShape implements Shape { // dynamic decorator

    private Shape shape; // shape to decorate
    private String color;

    public ColoredShape(Shape shape, String color) {
        this.shape = shape;
        this.color = color;
    }

    @Override
    public String info() {
        return shape.info() + " has " + color + " color";
    }
}

class TransparentShape implements Shape { // dynamic decorator

    private Shape shape;
    private int transparency;

    public TransparentShape(Shape shape, int transparency) {
        this.shape = shape;
        this.transparency = transparency;
    }

    @Override
    public String info() {
        return shape.info() + " with " + transparency + "% transparency";
    }
}

class BorderedShape<T extends Shape> implements Shape { // static decorator

    private Shape shape;
    private boolean hasBorder;

    public BorderedShape(Supplier<T> constructor, boolean hasBorder) {
        /*
         * static decorator creates the instance
         * instead of accepting an instance like what dynamic decorator does
         * */
        shape = constructor.get();
        this.hasBorder = hasBorder;
    }

    @Override
    public String info() {
        return shape.info() + " with " + (hasBorder ? "" : "no ") + "border";
    }
}

public class ShapeDecoratorDemo {
    public static void main(String[] args) {
        Circle circle = new Circle(10);
        System.out.println(circle.info());

        ColoredShape blueSquare = new ColoredShape(
            new Square(20),
            "blue"
        );
        System.out.println(blueSquare.info());

        TransparentShape transparentCircle = new TransparentShape(
            new Circle(20),
            50
        );
        System.out.println(transparentCircle.info());

        TransparentShape transparentGreenCircle = new TransparentShape(
            new ColoredShape(new Circle(30), "green"),
            80
        );
        System.out.println(transparentGreenCircle.info());

        ColoredShape redTransparentSquare = new ColoredShape(
            new TransparentShape(new Square(30), 80),
            "red"
        );
        System.out.println(redTransparentSquare.info());

        /* CANNOT call resize method because we're not inheriting
         * and resize method is not defined in Shape interface
         * so we don't implement it in decorator class
         * // redTransparentSquare.resize();
         */

        ///////////////////////////////////////////////////

        BorderedShape<Square> borderedSquare = new BorderedShape<>(
            () -> new Square(20), true
        );
        System.out.println(borderedSquare.info());
    }
}
