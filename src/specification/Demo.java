package specification;

import com.sun.tools.javac.util.List;

import java.util.stream.Stream;

enum Color {RED, GREEN, BLUE}

enum Size {SMALL, MEDIUM, LARGE, HUGE}

class Product {
  private String name;
  private Color color;
  private Size size;

  public Product(String name, Color color, Size size) {
    this.name = name;
    this.color = color;
    this.size = size;
  }

  /* getters and setters */

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public Size getSize() {
    return size;
  }

  public void setSize(Size size) {
    this.size = size;
  }
}

class ProductFiler {
  public Stream<Product> filterByColor(List<Product> products, Color color) {
    return products.stream().filter(
        p -> p.getColor() == color
    );
  }

  public Stream<Product> filterBySize(List<Product> products, Size size) {
    return products.stream().filter(
        p -> p.getSize() == size
    );
  }

  public Stream<Product> filterByColorAndSize(List<Product> products, Color color, Size size) {
    return products.stream().filter(
        p -> p.getColor() == color && p.getSize() == size
    );
  }

  // breaking the open/closed principle because every time we want to add a new filter,
  // we have to go into the class of ProductFilter,
  // or in other words we have to touch the source code that may have already been compiled and shipped
}

class Demo {
  public static void main(String[] args) {
    Product apple = new Product("Apple", Color.GREEN, Size.SMALL);
    Product tree = new Product("Tree", Color.GREEN, Size.MEDIUM);
    Product house = new Product("House", Color.BLUE, Size.LARGE);
    List<Product> products = List.of(apple, tree, house);
    ProductFiler pf = new ProductFiler();
    System.out.println("Green products (old):");
    pf.filterByColor(products, Color.GREEN)
        .forEach(p -> System.out.println(" - " + p.getName() + " is green"));
  }
}
