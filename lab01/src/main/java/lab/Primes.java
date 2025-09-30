package lab;

import java.util.Arrays;

public class Primes {
    public static void main(String[] args) {
        boolean[] isPrime = new boolean[100];

        for (int i = 2; i < 100; i++) {
            for (int j = i * i; j < 100; j += i) {
                isPrime[j] = true;
            }
        }

        System.out.println("Primes: ");
        for (int i = 2; i < 100; i++) {
            if (!isPrime[i]) {
                System.out.print(i + " ");
            }
        }
    }
}
