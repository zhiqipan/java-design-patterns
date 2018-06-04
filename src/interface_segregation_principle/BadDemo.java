package interface_segregation_principle;

class Document {
}

interface Machine {
    void print(Document d);

    void scan(Document d);

    void fax(Document d);
}

class MultiFunctionPrinter implements Machine { // okay
    @Override
    public void print(Document d) {

    }

    @Override
    public void scan(Document d) {

    }

    @Override
    public void fax(Document d) {

    }
}

class OldFashionedPrinter implements Machine {
    @Override
    public void print(Document d) {

    }

    @Override
    public void scan(Document d) { // not meaningful for old fashioned printer as it can't scan

    }

    @Override
    public void fax(Document d) { // same as scan method

    }
}

class BadDemo {
}