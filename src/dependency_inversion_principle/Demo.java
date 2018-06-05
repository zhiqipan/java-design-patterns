package dependency_inversion_principle;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

enum Relationship {
    PARENT, CHILD, SIBLING
}

class Person {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Person(String name) {
        this.name = name;
    }
}

class Relationships { // low-level, related to data store, no biz logic
    private List<Triplet<Person, Relationship, Person>> relations
            = new ArrayList<>();

    public List<Triplet<Person, Relationship, Person>> getRelations() {
        return relations;
    }

    public void addParentAndChild(Person parent, Person child) {
        relations.add(new Triplet<>(parent, Relationship.PARENT, child));
        relations.add(new Triplet<>(child, Relationship.CHILD, parent));
    }
}

class Triplet<R, S, T> {
    private R value0;
    private S value1;
    private T value2;

    public R getValue0() {
        return value0;
    }

    public S getValue1() {
        return value1;
    }

    public T getValue2() {
        return value2;
    }

    public Triplet(R r, S s, T t) {
        this.value0 = r;
        this.value1 = s;
        this.value2 = t;
    }
}

class Research { // high-level, should NOT depend on low-level module
    public Research(Relationships relationships) { // this breaks the dependency inversion principle
        // to fix it, we should depend on abstractions instead
        List<Triplet<Person, Relationship, Person>> relations = relationships.getRelations();
        relations.stream()
                .filter(x -> x.getValue0().getName().equals("John")
                        && x.getValue1() == Relationship.PARENT)
                .forEach(x -> System.out.println(
                        "John has a child called " + x.getValue2().getName()
                ));
    }
}

class DemoOld {
//    public static void main(String[] args) {
//        Person parent = new Person("John");
//        Person child1 = new Person("Chris");
//        Person child2 = new Person("Matt");
//        Relationships relationships = new Relationships();
//        relationships.addParentAndChild(parent, child1);
//        relationships.addParentAndChild(parent, child2);
//
//        new Research(relationships);
//    }
}

interface RelationshipBrowser {
    List<Person> findAllChildrenOf(String name);
}

class Relationships2 implements RelationshipBrowser {
    private List<Triplet<Person, Relationship, Person>> relations
            = new ArrayList<>();

    public List<Triplet<Person, Relationship, Person>> getRelations() {
        return relations;
    }

    public void addParentAndChild(Person parent, Person child) {
        relations.add(new Triplet<>(parent, Relationship.PARENT, child));
        relations.add(new Triplet<>(child, Relationship.CHILD, parent));
    }

    @Override
    public List<Person> findAllChildrenOf(String name) {
        return relations.stream()
                .filter(x -> x.getValue0().getName().equals("John")
                        && x.getValue1() == Relationship.PARENT)
                .map(x -> x.getValue2())
                .collect(Collectors.toList());
    }
}

class Research2 { // high-level, should NOT depend on low-level module
    public Research2(RelationshipBrowser browser) {
        /* depends on abstraction (interface in this case)
         * so as not to depend on implementation details,
         * such as data store (array or list or ...) */
        List<Person> children = browser.findAllChildrenOf("John");
        for (Person child : children) {
            System.out.println("John has a child called " + child.getName());
        }
    }
}

public class Demo {
    public static void main(String[] args) {
        Person parent = new Person("John");
        Person child1 = new Person("Chris");
        Person child2 = new Person("Matt");
        Relationships2 relationships = new Relationships2();
        relationships.addParentAndChild(parent, child1);
        relationships.addParentAndChild(parent, child2);

        new Research2(relationships);
    }
}