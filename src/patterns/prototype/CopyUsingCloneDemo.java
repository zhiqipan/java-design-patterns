package patterns.prototype;

import java.util.Arrays;

class Address implements Cloneable {
  public String streetName;
  public int houseNumber;

  public Address(String streetName, int houseNumber) {
    this.streetName = streetName;
    this.houseNumber = houseNumber;
  }

  @Override
  public String toString() {
    return "Address{" +
        "streetName='" + streetName + '\'' +
        ", houseNumber=" + houseNumber +
        '}';
  }

  @Override
  public Address clone() throws CloneNotSupportedException {
    // deep copy
    return new Address(streetName, houseNumber);
  }
}

class Person implements Cloneable {
  public String[] names;
  public Address address;

  public Person(String[] names, Address address) {
    this.names = names;
    this.address = address;
  }

  @Override
  public String toString() {
    return "Person{" +
        "names=" + Arrays.toString(names) +
        ", address=" + address +
        '}';
  }

  @Override
  public Person clone() throws CloneNotSupportedException {
    return new Person(names.clone(), address.clone());
  }
}

public class CopyUsingCloneDemo {
  public static void main(String[] args) throws CloneNotSupportedException {
    Person john = new Person(
        new String[]{"John", "Smith"},
        new Address("Fake St.", 123)
    );
    Person tom = john.clone(); // Tom lives at next door
    tom.names[0] = "Tom";
    tom.address.houseNumber = 124;

    System.out.println(john);
    System.out.println(tom);
  }
}
