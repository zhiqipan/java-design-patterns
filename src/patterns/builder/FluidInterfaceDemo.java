package patterns.builder;

class Person {
  public String name;
  public String position;

  @Override
  public String toString() {
    return "Person{" +
        "name='" + name + '\'' +
        ", position='" + position + '\'' +
        '}';
  }
}

class PersonBuilder<SELF extends PersonBuilder> {
  protected Person person = new Person();

  public SELF withName(String name) {
    person.name = name;
    return (SELF) this;
  }

  public Person build() {
    return person;
  }
}

class EmployeeBuilder extends PersonBuilder<EmployeeBuilder> { // preserve the fluid interface
  public EmployeeBuilder worksAs(String position) {
    person.position = position;
    return this;
  }
}

public class FluidInterfaceDemo {
  public static void main(String[] args) {
    EmployeeBuilder eb = new EmployeeBuilder();
    Person person = eb
        .withName("Thomas")
        .worksAs("Software engineer")
        .build();
    System.out.println(person);
  }
}
