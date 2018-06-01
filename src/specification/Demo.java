package specification;

import com.sun.tools.javac.util.List;

import java.util.stream.Stream;

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

interface Filter<T> {
  Stream<T> filter(List<T> items, Specification<T> spec);
}

interface Specification<T> {
  boolean isSatisfied(T item);
}

class ColorSpecification implements Specification<Product> {

  private Color color;

  public ColorSpecification(Color color) {
    this.color = color;
  }

  @Override
  public boolean isSatisfied(Product product) {
    return product.getColor() == this.color;
  }
}

class SizeSpecification implements Specification<Product> {

  private Size size;

  public SizeSpecification(Size size) {
    this.size = size;
  }

  @Override
  public boolean isSatisfied(Product product) {
    return product.getSize() == this.size;
  }
}

class BetterProductFilter implements Filter<Product> {
  @Override
  public Stream<Product> filter(List<Product> products, Specification<Product> spec) {
    return products.stream().filter(p -> spec.isSatisfied(p));
  }
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

    // this way the two interfaces are closed for modification,
    // but we can add new classes implementing them so as to be open for extension
    BetterProductFilter bpf = new BetterProductFilter();
    System.out.println("Green products (better):");
    bpf.filter(products, new ColorSpecification(Color.GREEN))
        .forEach(p -> System.out.println(" - " + p.getName() + " is green"));
  }
}
