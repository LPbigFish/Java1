package lab.manufacture;

import java.util.ArrayList;
import java.util.List;

public class ManufacturingProcess {
    final String productName;
    final List<Operation> operations;

    public ManufacturingProcess(String productName, Operation... operations) {
        this.productName = productName;
        this.operations = List.of(operations);
    }

    @Override
    public String toString() {
        return  "ManufacturingProcess [productName=" + productName + ", operations=" + operations;
    }
}
