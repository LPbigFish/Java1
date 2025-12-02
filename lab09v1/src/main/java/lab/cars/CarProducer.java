package lab.cars;

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
public enum CarProducer {
    BMW("BMW", "Sheer Driving Pleasure"),
    PORSCHE("Porsche", "Driven by Dreams"),
    SKODA("Škoda", "Let´s Explore");

    final String name;
    final String slogan;

    CarProducer(String name, String slogan) {
        this.name = name;
        this.slogan = slogan;
    }

    public String getName() {
        return name;
    }
    public String getSlogan() {
        return slogan;
    }

    @Override
    public String toString() {
        return name;
    }
}
