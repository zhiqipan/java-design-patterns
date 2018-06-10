package patterns.singleton;

class StaticBlockSingleton {
  private StaticBlockSingleton() {
  }

  private static StaticBlockSingleton instance;

  static {
    instance = new StaticBlockSingleton();
  }

  public static StaticBlockSingleton getInstance() {
    return instance;
  }
}

public class StaticBlockSingletonDemo {
}
