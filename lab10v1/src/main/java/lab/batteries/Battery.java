package lab.batteries;

import java.util.List;

public class Battery {

	public static final List<String> types = List.of("AA", "AAA", "CR2025", "C", "D", "9V");

	private String type;
	private int initialCapacity;
	private double actualCapacity;
	private double price;

	public Battery(String type, int initialCapacity, double price) {
		this.type = type;
		this.initialCapacity = initialCapacity;
		this.actualCapacity = Math.random()*initialCapacity;
		this.price = price;
	}

	public Battery discharge() {
		this.actualCapacity = Math.random()*initialCapacity/2;
		return this;
	}
	public String getType() {
		return type;
	}

	public int getInitialCapacity() {
		return initialCapacity;
	}

	public double getActualCapacity() {
		return actualCapacity;
	}

	public double getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "Battery [type=" + type + ", initialCapacity=" + initialCapacity + ", actualCapacity=" + actualCapacity + ", price=" + price + "]";
	}

	public static void print(List<Battery> batteries) {
		for (Battery battery : batteries) {
			System.out.println(battery);
		}
	}
}
