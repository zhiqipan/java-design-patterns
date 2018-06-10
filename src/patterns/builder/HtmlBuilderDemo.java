package patterns.builder;

import java.util.ArrayList;
import java.util.Collections;

public class HtmlBuilderDemo {
  public static void main(String[] args) {
    HtmlBuilder builder = new HtmlBuilder("ul");
    builder
        .addChild("li", "hello")
        .addChild("li", "world");
    System.out.println(builder);
  }
}

/**
 * Assuming an element either has children or has plain text
 */
class HtmlElement {
  public String name, text;
  public ArrayList<HtmlElement> children = new ArrayList<>(); // recursive structure

  public HtmlElement() {
  }

  public HtmlElement(String name, String text) {
    this.name = name;
    this.text = text;
  }

  private String toStringImpl(int indent) {
    final int INDENT_SIZE = 2;
    final String NEW_LINE = System.lineSeparator();
    StringBuilder sb = new StringBuilder();
    String i = String.join("", Collections.nCopies(indent * INDENT_SIZE, " "));
    sb.append(String.format("%s<%s>%s", i, name, NEW_LINE));
    if (text != null && !text.isEmpty()) {
      sb.append(String.join("", Collections.nCopies((indent + 1) * INDENT_SIZE, " ")))
          .append(text)
          .append(NEW_LINE);
    }
    for (HtmlElement e : children) {
      sb.append(e.toStringImpl(indent + 1));
    }
    sb.append(String.format("%s</%s>%s", i, name, NEW_LINE));

    return sb.toString();
  }

  public String toString() {
    return toStringImpl(0);
  }
}

class HtmlBuilder {
  private String rootName;
  private HtmlElement root = new HtmlElement();

  public HtmlBuilder(String rootName) {
    this.rootName = rootName;
    root.name = rootName;
  }

  public HtmlBuilder addChild(String childName, String childText) {
    HtmlElement e = new HtmlElement(childName, childText);
    root.children.add(e);
    return this;
  }

  public void clear() {
    root = new HtmlElement();
    root.name = rootName;
  }

  public String toString() {
    return root.toString();
  }
}
