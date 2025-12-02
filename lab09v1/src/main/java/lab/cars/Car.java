package lab.cars;

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
 */
public class Car {
    CarProducer producer;
    String model;
    float maxSpeed;
    public Car(CarProducer producer, String model, float maxSpeed) {
        this.producer = producer;
        this.model = model;
        this.maxSpeed = maxSpeed;
    }

    @Override
    public String toString() {
        return producer.toString() + " " + model + "("  + maxSpeed + ")";
    }
}
