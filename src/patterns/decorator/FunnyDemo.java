package patterns.decorator;

class Bird {
    public int age;

    public String fly() {
        return age < 10 ? "flying" : "too old";
    }
}

class Lizard {
    public int age;

    public String crawl() {
        return (age > 1) ? "crawling" : "too young";
    }
}

class Dragon { // static decorator - instantiates Bird and Lizard, users won't know this
    private int age;
    private Bird bird;
    private Lizard lizard;

    Dragon() {
        this.bird = new Bird();
        this.lizard = new Lizard();
    }

    public void setAge(int age) {
        bird.age = age;
        lizard.age = age;
    }

    public String fly() {
        return bird.fly();
    }

    public String crawl() {
        return lizard.crawl();
    }
}

public class FunnyDemo {
}
