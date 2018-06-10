package patterns.singleton;

import java.io.*;

class BasicSingleton implements Serializable {
  /*
   * NOT best practice, because
   * 1. the private constructor can be defeated via reflections
   * 2. the singleton instance can still be copied serialization
   * */
  private static final BasicSingleton INSTANCE = new BasicSingleton();
  private int value = 0;

  private BasicSingleton() {
  }

  public static BasicSingleton getInstance() {
    return INSTANCE;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  protected Object readResolve() {
    /*
    * this method gives JVM a hint that whenever (de)serialization happens,
    * it has to happen on this INSTANCE, as oppose to making a new object
    * */
    return INSTANCE;
  }
}

public class BasicSingletonDemo {
  private static void saveToFile(BasicSingleton singleton, String filename) throws Exception {
    try (FileOutputStream fileOut = new FileOutputStream(filename);
         ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
      out.writeObject(singleton);
    }
  }

  private static BasicSingleton readFromFile(String filename) throws Exception {
    try (FileInputStream fileIn = new FileInputStream(filename);
         ObjectInputStream in = new ObjectInputStream(fileIn)) {
      return (BasicSingleton) in.readObject();
    }
  }

  public static void main(String[] args) throws Exception {
    BasicSingleton singleton = BasicSingleton.getInstance();
    singleton.setValue(123);
    String filename = "singleton.bin";
    saveToFile(singleton, filename);
    singleton.setValue(456);
    BasicSingleton anotherSingleton = readFromFile(filename);

    System.out.println(singleton.getValue());
    System.out.println(anotherSingleton.getValue());
  }
}
