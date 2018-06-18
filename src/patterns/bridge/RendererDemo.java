package patterns.bridge;

/*
 * Cartesian product complexity explosion
 *  Shape -> Circle, Square
 *  Rendering -> Vector, Raster
 *   - VectorCircleRenderer
 *   - VectorSquareRenderer
 *   - RasterCircleRenderer
 *   - RasterSquareRenderer
 */

// this is indeed dependency injection,
// could be done better using DI framework (eg Spring)

interface Renderer {
    void renderCircle(float radius);
}

class VectorRenderer implements Renderer {
    @Override
    public void renderCircle(float radius) {
        System.out.println("Rendering a circle vector of radius " + radius);
    }
}

class RasterRenderer implements Renderer {
    @Override
    public void renderCircle(float radius) {
        System.out.println("Rendering the pixels for a circle of radius " + radius);
    }
}

abstract class Shape {
    protected Renderer renderer;

    public Shape(Renderer r) {
        this.renderer = r;
    }

    public abstract void draw();

    public abstract void resize(float factor);
}

class Circle extends Shape {

    public float radius;

    public Circle(Renderer r) {
        super(r);
    }

    public Circle(Renderer r, float radius) {
        super(r);
        this.radius = radius;
    }

    @Override
    public void draw() {
        renderer.renderCircle(radius);
    }

    @Override
    public void resize(float factor) {
        radius *= factor;
    }
}

public class RendererDemo {
    public static void main(String[] args) {
        RasterRenderer raster = new RasterRenderer();
        VectorRenderer vector = new VectorRenderer();
        Circle circle = new Circle(vector, 5);
        circle.draw();
        circle.resize(2);
        circle.draw();

        Circle circle2 = new Circle(raster, 5);
        circle2.draw();
        circle2.resize(2);
        circle2.draw();
    }
}
