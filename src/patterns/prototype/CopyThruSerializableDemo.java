package patterns.prototype;

import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

class Foo implements Serializable {
  public int number;
  public String whatever;

  public Foo(int number, String whatever) {
    this.number = number;
    this.whatever = whatever;
  }

  @Override
  public String toString() {
    return "Foo{" +
        "number=" + number +
        ", whatever='" + whatever + '\'' +
        '}';
  }
}

public class CopyThruSerializableDemo {
  public static void main(String[] args) {
    Foo foo = new Foo(123, "hello");
    Foo anotherFoo = SerializationUtils.roundtrip(foo);
    anotherFoo.whatever = "world";

    System.out.println(foo);
    System.out.println(anotherFoo);
  }
}
