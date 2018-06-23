package patterns.flyweight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

class User {
  // shared storage
  static List<String> cachedNameStrings = new ArrayList<>();

  private int[] names;

  public User(String fullName) {
    Function<String, Integer> getOrAdd = (String s) -> {
      int idx = cachedNameStrings.indexOf(s);
      if (idx != -1) {
        return idx;
      } else {
        cachedNameStrings.add(s);
        return cachedNameStrings.size() - 1;
      }
    };

    names = Arrays.stream(fullName.split(" "))
        .mapToInt(s -> getOrAdd.apply(s))
        .toArray();
  }

  public String getFullName() {
    return Arrays.stream(names)
        .mapToObj(idx -> cachedNameStrings.get(idx))
        .reduce((a, sum) -> a + " " + sum)
        .get();
  }
}

public class UserNameDemo {
  public static void main(String[] args) {
    User john = new User("John Smith");
    User tom = new User("Tom Smith");

    System.out.println(john.getFullName());
    System.out.println(tom.getFullName());
  }
}
