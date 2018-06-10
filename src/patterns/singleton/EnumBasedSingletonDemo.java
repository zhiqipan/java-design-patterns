package patterns.singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/*
 * advantages:
 * - enum is serializable by default, but its fields are not serialized,
 *    so it avoids serialization issue
 * - no reflection attack
 * - enum is thread safe by default (this relates to JVM)
 *
 * drawbacks:
 * - enum is not inheritable
 */
enum EnumBasedSingleton {
  INSTANCE;

  EnumBasedSingleton() {
  }

  private int value = 0;

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }
}

public class EnumBasedSingletonDemo {
  private static void saveToFile(EnumBasedSingleton singleton, String filename) throws Exception {
    try (FileOutputStream fileOut = new FileOutputStream(filename);
         ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
      out.writeObject(singleton);
    }
  }

  private static EnumBasedSingleton readFromFile(String filename) throws Exception {
    try (FileInputStream fileIn = new FileInputStream(filename);
         ObjectInputStream in = new ObjectInputStream(fileIn)) {
      return (EnumBasedSingleton) in.readObject();
    }
  }

  public static void main(String[] args) throws Exception {
    String filename = "file.bin";
    EnumBasedSingleton singleton = EnumBasedSingleton.INSTANCE;
    singleton.setValue(123);
    saveToFile(singleton, filename);
    singleton.setValue(456);
    EnumBasedSingleton anotherSingleton = readFromFile(filename);

    System.out.println(singleton.getValue());
    System.out.println(anotherSingleton.getValue());

  }
}
