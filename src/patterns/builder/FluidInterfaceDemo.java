package patterns.builder;

class SimplePerson {
  public String name;
  public String position;

  @Override
  public String toString() {
    return "SimplePerson{" +
        "name='" + name + '\'' +
        ", position='" + position + '\'' +
        '}';
  }
}

class SimplePersonBuilder<SELF extends SimplePersonBuilder> {
  protected SimplePerson person = new SimplePerson();

  public SELF withName(String name) {
    person.name = name;
    return (SELF) this;
  }

  public SimplePerson build() {
    return person;
  }
}

class EmployeeBuilder extends SimplePersonBuilder<EmployeeBuilder> { // preserve the fluid interface
  public EmployeeBuilder worksAs(String position) {
    person.position = position;
    return this;
  }
}

public class FluidInterfaceDemo {
  public static void main(String[] args) {
    EmployeeBuilder eb = new EmployeeBuilder();
    SimplePerson person = eb
        .withName("Thomas") // this method returns an instance of EmployeeBuilder
        .worksAs("Software engineer")
        .build();
    System.out.println(person);
  }
}
