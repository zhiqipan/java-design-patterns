package patterns.factory;

enum CoordinateSystem {
  CARTESIAN, POLAR
}

class Point {
  private double x, y;

  /* NOT allowed in Java
  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Point(double rho, double theta) {
    x = rho * Math.cos(theta);
    y = rho * Math.sin(theta);
  }
  * *********************************/

  /* this is what Java allows, but this is a bad practice
  /**
   *
   * @param a is x if cartesian or tho if polar
   * @param b is y if cartesian or theta if polar
   * @param cs coordinate system, either cartesian or polar
   *//*
  public Point(double a, double b, CoordinateSystem cs) {
    switch (cs) {
      case CARTESIAN:
        this.x = a;
        this.y = b;
        break;
      case POLAR:
        this.x = a * Math.cos(b);
        this.y = a * Math.sin(b);
        break;
      default:
        break;
    }
  }
  * *********************************/

  /* Best practice - use factory pattern */

  // made private: force the users to explicitly use factory methods
  private Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public static Point newCartesianPoint(double x, double y) {
    return new Point(x, y);
  }

  public static Point newPolarPoint(double rho, double theta) {
    return new Point(rho * Math.cos(theta), rho * Math.sin(theta));
  }
}

public class PointDemo {
  public static void main(String[] args) {
    Point pointA = Point.newCartesianPoint(2, 3);
    Point pointB = Point.newPolarPoint(10, 30);
  }
}
