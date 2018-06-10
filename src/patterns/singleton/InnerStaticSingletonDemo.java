package patterns.singleton;

class InnerStaticSingleton {
  private InnerStaticSingleton() {
  }

  private static class Impl {
    private static final InnerStaticSingleton INSTANCE = new InnerStaticSingleton();
  }

  public InnerStaticSingleton getInstance() {
    return Impl.INSTANCE;
  }
}
/*
 * both thread safe and lazy-loaded
 * According to the JVM specification, static inner class only loads when needed (so it's lazy-loaded by default),
 * and since it's static it loads only once, so when you call getInstance(), it loads the INSTANCE once and only once
 * - this avoids the problem of synchronization, but in a very simpler way
 * - this may cause confusion due to its bad readability
 */

public class InnerStaticSingletonDemo {
}
