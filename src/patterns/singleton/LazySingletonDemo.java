package patterns.singleton;

class LazySingleton {
  private static LazySingleton instance;

  private LazySingleton() {
  }

  /*
  // this has performance issue due to 'synchronized',
  // because every time we get an instance, we need to synchronize
  public static synchronized LazySingleton getInstance() {
    if (instance == null) {
      instance = new LazySingleton();
    }
    return instance;
  }
  */

  /*
   * double-checked locking
   * the sync lock doesn't apply in normal execution branch
   */
  public static LazySingleton getInstance() {
    if (instance == null) {
      synchronized (LazySingleton.class) {
        if (instance == null) {
          instance = new LazySingleton();
        }
      }
    }
    return instance;
  }
}

public class LazySingletonDemo {
}
