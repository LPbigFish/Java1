package lab.cars;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.List;

public class CarTasks {

    public static void main(String[] args) {
        System.out.println("Starting Java programing test - part Car Tasks.");
        CarTasks carTasks = new CarTasks();
        carTasks.printAllCarProducers();
        carTasks.generateTwoCars();
        carTasks.store("cars.bin");
        carTasks.load("cars.bin");
    }

    /**
     * Vytvořte enum CarProducer se třemi konstantami:BMW, PORSCHE a SKODA.
     * Každá konstanta bude mít dvě hodnoty/parametry name a slogan:
     * BMW -> Sheer Driving Pleasure
     * Porsche -> Driven by Dreams
     * Škoda -> Let´s Explore
     * Kromě jiného bude mít enum gettry pro jméno a slogan a
     * toString metodu, která bude vracet jméno.
     *
     * Vypište do konzole všechny konstanty z CarProducer ve formátu jméno - slogan
     */
    public void printAllCarProducers(){
        for (CarProducer value : CarProducer.values()) {
            System.out.println(value.name + " " +  value.slogan);
        }
    }

    /**
     *Vytvořte třídu Car s instančními proměnnými:
     * CarProducer producer
     * String model
     * float maxSpeed
     *
     * Vytvořte třídě konstruktor se třemi parametry a toString metodu,
     * která vypíše auto ve formátu:
     * výrobce model (max_rychlost_km/h)
     * Např:Škoda Rapid (180.0km/h)
     *
     * Vytvořte kolekci, vložte do ní dvě auta a kolekci vraťte.
     *
     * Neměňte typ návratové hodnoty.
     */
    public List<Car> generateTwoCars() {
        return Arrays.asList(
            new Car(CarProducer.BMW, "X5", 253.3f),
            new Car(CarProducer.PORSCHE, "Macan", 312.1f)
        );
    }

    /**
     * Metoda uloží do souboru fileName obsah kolekce vrácené metodou generateTwoCars.
     * Obsah se uloží jako binární objektová data.
     * Pokud ukládání selže metoda vrací false a do konzoly se vypíše příčina chyby.
     */
    public boolean store(String fileName){
        File file = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(file); BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos))) {
            for (Car car : generateTwoCars()) {
                bw.write(car.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Metoda načte obsah souboru fileName vytvořeného v metodě store.
     * Obsah je uložen jako binární objektová data.
     *
     * Načtená auta se vypíší do konzole.
     *
     * Pokud ukládání selže metoda vrací false a do konzoly se vypíše příčina chyby.
     */
    public boolean load(String fileName){
        File file = new File(fileName);
        try (FileInputStream fos = new FileInputStream(file); BufferedReader bw = new BufferedReader(new InputStreamReader(fos))) {
            bw.lines().forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
