package lab.batteries;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Vector;

public class AppBattery {
    static class DischargeEvaluator implements BatteryShop.BatteryEvaluator {
        final Writer writer;

        public DischargeEvaluator(Writer writer) {
            this.writer = writer;
        }

        @Override
        public void log(Battery battery) throws IOException {
            if (check(battery)) {
                writer.write(battery.getType() + ": " + battery.getInitialCapacity() + " -> " + battery.getActualCapacity() + "\n");
                System.out.println(battery.getType() +  ": " + battery.getActualCapacity() + " -> " + battery.getType() + "\n");
            }
        }

        @Override
        public boolean check(Battery battery) {
            return battery.getActualCapacity() < (battery.getInitialCapacity() / 2.0f);
        }
    }

    public static final Random  RANDOM = new Random();

	public static void main(String[] args) {

		List<Battery> batteries = generateBatteries(10);
		sortByPrice(batteries);
		Battery.print(batteries);
		sortByTypeAndCapacity(batteries);
		Battery.print(batteries);
		logDischargedBatteries();
		logShopPriceBatteries();
		uniqueHeapForStrings(new String[] { "a", "a", "b", "a", "c", "d", "d", "a" });
		uniqueHeapForInt(new int[] { 1, 2, 1, 1, 3, 3, 2, 1, 2, 4 });

	}

	/**
	 * Metoda vytvoří kolekci a do ní vygeneruje zadaný počet baterií (Battery)
	 *
     * Vytvořte statickou třídní proměnnou ve třídě AppBattery ve které, bude uložena
	 * instance náhodného generátoru čísel.
	 *
	 * @param count - počet baterií, které se mají vygenerovat do kolekce
	 * @return vytvořená kolekce baterií.
	 */
	public static List<Battery> generateBatteries(int count) {
        List<Battery> batteries = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            batteries.add(new Battery(Battery.types.get(RANDOM.nextInt(Battery.types.size())), RANDOM.nextInt(1000), RANDOM.nextDouble(300.0f)));
        }
        return batteries;
	}

	/**
	 * Metoda setřídí kolekci baterií podle ceny od nejlevnější po nejdražší.
	 * Využijte k setřídění lambda výraz nebo refrenci na metodu (method referenc)
	 *
	 * @param batteries - kolekce batterií k setřídění
	 */
	public static void sortByPrice(List<Battery> batteries) {
        batteries.sort(Comparator.comparingDouble(Battery::getPrice));
	}

	/**
	 * Metoda setřídí kolekci baterií podle typu (abecedně od A po Z) a pokud je typ
	 * stejný tak podle počáteční kapacity (initialCapacity) od nejnižší po
	 * nejvyšší. Využijte k setřídění lambda výraz nebo refrenci na metodu (method
	 * referenc)
	 *
	 * @param batteries - kolekce batterií k setřídění
	 */
	public static void sortByTypeAndCapacity(List<Battery> batteries) {
        batteries.sort((x, y) -> {
            if (x.getType().equals(y.getType())) {
                return Integer.compare(x.getInitialCapacity(), y.getInitialCapacity());
            }
            return x.getType().compareTo(y.getType());
        });
	}

	/**
	 * Ve třídě AppBattery vytvořte vnořenou pojmenovanou třídu DischargeEvaluator,
     * která implementuje rozhraní BatteryShop.BatteryEvaluator. Třída má také
     * konstruktor, kterým se jí předáva "nějaký writer" pro zápis do souboru.
	 *
	 *
	 * Vytvořte instanci třídy BatteryShop a pomocí její metody iterateBatteries a
	 * instance třídy DischargeEvaluator:
	 *
	 * vypište do souboru discharged.txt a do konzole typ, počáteční kapacitu
	 * (initialCapacity) a aktuální kapacitu (actualCapacity) pro baterie, jejichž
     * aktuální kapacita je nižší než polovina výchozí kapacity.
     * Použijte tvar výpisu: TYP_BAT: INIT_CAP -> CURRENT_CAP
     * Pokud metoda
	 * proběhne úspěšně vrací TRUE, pokud dojde při zápisu do souboru k výjimce,
	 * metoda vypíše messge vyjímky do konzole a vrátí FALSE
	 *
	 * Metoda log se zavolá jen pro baterie, pro které metoda check vrátí true.
	 */
	public static boolean logDischargedBatteries() {
        BatteryShop bs = new BatteryShop();
        File f = new File("discharged.txt");

        try (
            Writer w = new OutputStreamWriter(new FileOutputStream(f))
        ) {
            bs.iterateBatteries(new DischargeEvaluator(w));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
	}

	/**
	 * Vytvořte instanci třídy BatteryShop a pomocí její metody iterateBatteries a
	 * vnořené anonymní třídy, která implementuje rozhraní
	 * BatteryShop.BatteryEvaluator, vypište pro všechny baterie jejich cenu.
	 *
	 * Metoda log se zavolá jen pro baterie, pro které metoda check vrátí true.
	 * Metoda check musí tedy v tomto případě vždy vracet true.
	 */
	public static void logShopPriceBatteries() {
        BatteryShop bs = new BatteryShop();
        try {
            bs.iterateBatteries(new BatteryShop.BatteryEvaluator() {
                @Override
                public void log(Battery battery) throws IOException {
                    if (check(battery)) {
                        System.out.println(battery.getType() + ": " + battery.getPrice());
                    }
                }

                @Override
                public boolean check(Battery battery) {
                    return true;
                }
            });
        } catch (IOException ignored) {

        }
    }

	/**
	 * Vytvořte generickou třídu UniqueHeap, která budemít instanční proměnnou typu
     * množina a instanční proměnnou počítadlo (count - int).
	 *
	 * Třída bude mít:
	 *
	 * metodu add, pro přidání elementu do množiny. Metoda bude počíta počet volání
	 * metody add.
	 *
	 * metodu getCount, která vrátí hodnotu počítadla
	 *
	 * metodu grabAll, která vrátí množinu unikátních vložených prvků a zároveň ve
	 * třídě UniqueHeap, vymaže množinu a počítadlo nastaví na nulu.
     * Dejte pozor/zajistěte aby smazání množiny nesmazalo také vrácené prvky.
     *
	 * @param words- slova pro vložení do UniqueHeap
	 * @return objekt typu UniqueHeap
	 */
	public static Object uniqueHeapForStrings(String[] words) {
        UniqueHeap<String> res = new UniqueHeap<>();
        for (String word : words) {
            res.add(word);
        }
        return res;
	}

	/**
	 * Stejnou generickou třídu UniqueHeap, popsanou u předchozí metody, použijte
	 * pro uložeí celých čísel.
	 *
	 * @param numbers - čísla pro vložení do UniqueHeap
	 * @return objekt tzpu UniqueHeap
	 */
	public static Object uniqueHeapForInt(int[] numbers) {
        UniqueHeap<Integer> res = new UniqueHeap<>();
        for (int i : numbers) {
            res.add(i);
        }
        return res;
	}

}
