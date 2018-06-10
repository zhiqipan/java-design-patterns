package patterns.prototype;

class Location {
  public String street, city, country;

  public Location(String street, String city, String country) {
    this.street = street;
    this.city = city;
    this.country = country;
  }

  public Location(Location other) {
    this(other.street, other.city, other.country);
  }

  @Override
  public String toString() {
    return "Location{" +
        "street='" + street + '\'' +
        ", city='" + city + '\'' +
        ", country='" + country + '\'' +
        '}';
  }
}

class Employee {
  public String name;
  public Location location;

  public Employee(String name, Location location) {
    this.name = name;
    this.location = location;
  }

  public Employee(Employee other) {
    this(other.name, new Location(other.location));
  }

  @Override
  public String toString() {
    return "Employee{" +
        "name='" + name + '\'' +
        ", location=" + location +
        '}';
  }
}

public class CopyConstructorDemo {
  public static void main(String[] args) {
    Location johnLocation = new Location("123 Fake St.", "London", "UK");
    Employee john = new Employee("John", johnLocation);
    Employee tom = new Employee(john);
    tom.name = "tom";
    tom.location.street = "135 Fake St.";

    System.out.println(john);
    System.out.println(tom);
  }
}
