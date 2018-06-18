package patterns.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class GraphicObject {
    String name = "Group";
    String color;
    List<GraphicObject> children = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GraphicObject() {
    }

    private void toStringWithDepth(StringBuilder sb, int depth) {
        sb.append(String.join("", Collections.nCopies(depth, "* ")));
        if (children.isEmpty()) {
            sb.append(String.format("%s %s\n", color, name));
            return;
        }
        sb.append("Group\n");
        for (GraphicObject child : children) {
            child.toStringWithDepth(sb, depth + 1);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        toStringWithDepth(sb, 0);
        return sb.toString();
    }
}

class Circle extends GraphicObject {
    public Circle(String color) {
        this.name = "Circle";
        this.color = color;
    }
}

class Square extends GraphicObject {
    public Square(String color) {
        this.name = "Square";
        this.color = color;
    }
}

public class GraphicObjectDemo {
    public static void main(String[] args) {
        GraphicObject drawing = new GraphicObject();
        drawing.setName("My drawing");
        drawing.children.add(new Square("Red"));
        drawing.children.add(new Circle("Yellow"));

        GraphicObject group = new GraphicObject();
        group.children.add(new Circle("Blue"));
        group.children.add(new Square("Blue"));

        drawing.children.add(group);

        System.out.println(drawing);
    }
}
