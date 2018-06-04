package interface_segregation_principle;

// interface segregation - use many small interfaces instead of putting everything in a big one

interface Printer {
    void print(Document d);
}

interface Scanner {
    void scan(Document d);
}

class JustAPrinter implements Printer {
    @Override
    public void print(Document d) {

    }
}

class Photocopier implements Printer, Scanner {
    @Override
    public void print(Document d) {

    }

    @Override
    public void scan(Document d) {

    }
}

// if you really want to (re)use a big interface
interface MultiFunctionMachine extends Printer, Scanner {
}

class MultiFunctionDevice implements MultiFunctionMachine {
    /* this uses decorator design pattern */
    private Printer printer;
    private Scanner scanner;

    public MultiFunctionDevice(Printer p, Scanner s) {
        printer = p;
        scanner = s;
    }

    @Override
    public void print(Document d) {
        printer.print(d); // delegate to printer
    }

    @Override
    public void scan(Document d) {
        scanner.scan(d); // delegate to scanner
    }
}

class GoodDemo {
}
