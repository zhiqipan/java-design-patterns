package patterns.facade;

import java.util.ArrayList;
import java.util.List;

// subsystem of console, should be hidden from end users
class Buffer {
    private char[] characters;
    private int lineWidth;

    public Buffer(int numOfLines, int lineWidth) {
        this.lineWidth = lineWidth;
        characters = new char[lineWidth * numOfLines];
    }

    public char charAt(int x, int y) {
        return characters[x + y * lineWidth];
    }
}

// subsystem of console, should be hidden from end users
class Viewport {
    private final Buffer buffer;
    private final int width; // viewport width
    private final int height; // viewport height
    private final int offsetX; // cursor offset X
    private final int offsetY; // cursor offset Y

    public Viewport(Buffer buffer, int width, int height, int offsetX, int offsetY) {
        this.buffer = buffer;
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public char charAt(int x, int y) {
        return buffer.charAt(x + offsetX, y + offsetY);
    }
}

class Console {
    private List<Viewport> viewports = new ArrayList<>();
    int width, height;

    /* this is the Facade,
     * dealing with all subsystems/internals,
     * and only returning the useful APIs back to users */
    public static Console newConsole(int width, int height) {
        Buffer buffer = new Buffer(width, height);
        Viewport viewport = new Viewport(buffer, width, height, 0, 0);
        Console console = new Console(width, height);
        console.addViewport(viewport);
        return console;
    }

    public Console(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void addViewport(Viewport viewport) {
        viewports.add(viewport);
    }

    public void render() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                for (Viewport vp : viewports) {
                    System.out.println(vp.charAt(x, y));
                }
                System.out.println();
            }
        }
    }
}

public class ConsoleDemo {
    public static void main(String[] args) {
        /* BAD, because end users should not care buffer and console even exist
        Buffer buffer = new Buffer(30, 20);
        Viewport viewport = new Viewport(buffer, 30, 20, 0, 0);
        Console console = new Console(30, 20);
        console.addViewport(viewport);
        console.render();
        */

        Console console = Console.newConsole(30, 20);
        console.render();
    }
}
