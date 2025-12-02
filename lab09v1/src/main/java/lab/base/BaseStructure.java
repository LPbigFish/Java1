package lab.base;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BaseStructure {
    public static void main(String[] args) {
        System.out.println("Starting Java programing test - part Base Structure.");
        BaseStructure baseStructure = new BaseStructure();
        baseStructure.varArgTask();
    }

    /**
     *  Zavolejte metodu calcSum s alespoň dvěmi parametry.
     *  Výsledek vypište do konzole.
     */
    public void varArgTask() {
        System.out.println("Result: " + calcSum(1, 2, -3));
    }

    /**
     * Vytvořte veřejnou metodu calcSum, která vrací řetězec a má
     * jako argument celá čísla (libovolný počet).
     * Metoda čísla sečte a do řetězce který bude vracet vypíše
     * rovnici pro součet čísel. Např:
     * 2 + 8 + 7 + (-9) = 8
     */
    public String calcSum(int... d) {
        return Arrays.stream(d).mapToObj(
            x -> x < 0 ? "(" + x + ")" : Integer.toString(x)).collect(Collectors.joining(" + ")
        ) + " = " + Arrays.stream(d).sum();
    }
}
