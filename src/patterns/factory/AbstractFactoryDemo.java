package patterns.factory;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

interface HotDrink {
  void consume();
}

class Tea implements HotDrink {
  public void consume() {
    System.out.println("This tea is delicious");
  }
}

class Coffee implements HotDrink {
  public void consume() {
    System.out.println("This coffee is delicious");
  }
}

interface HotDrinkFactory {
  HotDrink prepare(int amount);
}

class TeaFactory implements HotDrinkFactory {
  public HotDrink prepare(int amount) {
    System.out.println("Put in tea bag, boil water, pour "
        + amount + "ml, add lemon, enjoy!");
    return new Tea(); // customize Tea instance here
  }
}

class CoffeeFactory implements HotDrinkFactory {
  public HotDrink prepare(int amount) {
    System.out.println("Put in coffee, boil water, pour "
        + amount + "ml, add milk, enjoy!");
    return new Coffee(); // customize Coffee instance here
  }
}

class HotDrinkMachine {
  private List<Pair<String, HotDrinkFactory>> namedFactories = new ArrayList<>();

  public HotDrinkMachine() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
    Set<Class<? extends HotDrinkFactory>> types = new Reflections("").getSubTypesOf(HotDrinkFactory.class);
    for (Class<? extends HotDrinkFactory> type : types) {
      namedFactories.add(new Pair<>(
          type.getSimpleName().replace("Factory", ""),
          type.getDeclaredConstructor().newInstance()));
    }
  }

  public HotDrink makeDrink() throws IOException {
    System.out.println("Available drinks:");
    for (int i = 0; i < namedFactories.size(); i++) {
      Pair<String, HotDrinkFactory> item = namedFactories.get(i);
      System.out.println(i + ":" + item.getKey());
    }

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    while (true) {
      String s;
      int i, amount;
      s = reader.readLine();
      i = Integer.parseInt(s);
      if (i >= 0 && i < namedFactories.size()) {
        System.out.println("Specify amount:");
        s = reader.readLine();
        amount = Integer.parseInt(s);
        if (amount > 0) {
          return namedFactories.get(i).getValue().prepare(amount);
        }
      }
      System.out.println("Invalid input, try again");
    }
  }
}

public class AbstractFactoryDemo {
  public static void main(String[] args) throws Exception {
    HotDrinkMachine machine = new HotDrinkMachine();
    HotDrink drink = machine.makeDrink();
    drink.consume();
  }
}
