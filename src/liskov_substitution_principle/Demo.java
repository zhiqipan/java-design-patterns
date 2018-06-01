package liskov_substitution_principle;

class Rectangle {
  private int width;
  private int height;

  public Rectangle() {
  }

  public Rectangle(int w, int h) {
    width = w;
    height = h;
  }

  public int getArea() {
    return width * height;
  }

  /* generated methods */

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  @Override
  public String toString() {
    return "Rectangle{" +
        "width=" + width +
        ", height=" + height +
        '}';
  }
}

class Square extends Rectangle {
  public Square() {
  }

  public Square(int size) {
    super(size, size);
  }


  public void setWidth(int width) {
    super.setWidth(width);
    super.setHeight(width);
  }

  public void setHeight(int height) {
    super.setWidth(height);
    super.setHeight(height);
  }
}

class Demo {
  private static void useIt(Rectangle r) {
    // this function doesn't know whether r is rectangle or square,
    // and it expects width remains unchanged after calling setHeight
    int width = r.getWidth();
    r.setHeight(10);
    // area = width * 10
    System.out.println("Expected area of " + (width * 10) + ", got " + r.getArea());
  }

  public static void main(String[] args) {
    Rectangle rect = new Rectangle(2, 3);
    useIt(rect);

    Square sq = new Square(5);
    useIt(sq);
  }
}
