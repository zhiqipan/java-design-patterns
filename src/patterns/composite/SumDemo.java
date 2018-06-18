package patterns.composite;

import java.util.*;
import java.util.function.Consumer;

interface ValueContainer extends Iterable<Integer> {
    int sum();
}

class SingleValue implements ValueContainer {
    public int value;

    public SingleValue(int value) {
        this.value = value;
    }

    public int sum() {
        return this.value;
    }

    @Override
    public Iterator<Integer> iterator() {
        return Collections.singleton(this.value).iterator();
    }

    @Override
    public void forEach(Consumer<? super Integer> action) {
        action.accept(this.value);
    }

    @Override
    public Spliterator<Integer> spliterator() {
        return Collections.singleton(this.value).spliterator();
    }
}

class ManyValues extends ArrayList<Integer> implements ValueContainer {
    @Override
    public int sum() {
        int sum = 0;
        for (Object value : this.toArray()) {
            sum += (Integer)value;
        }
        return sum;
    }
}

class MyList extends ArrayList<ValueContainer> {
    public int getSum() {
        int sum = 0;
        for (Object container : this.toArray()) {
            sum += ((ValueContainer)container).sum();
        }
        return sum;
    }
}

public class SumDemo {
    public static void main(String[] args) {
        SingleValue integer = new SingleValue(1);
        ManyValues integers = new ManyValues();
        integers.add(2);
        integers.add(3);
        integers.add(4);
        MyList list = new MyList();
        list.add(integer);
        list.add(integers);
        System.out.println(list.getSum());
    }
}
