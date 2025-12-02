package lab.manufacture;

import java.util.ArrayList;
import java.util.List;

public class AppManufacture {

	/**
	 * Vytvořte výčtový typ (enumeration) Operation s konstantami: CUTTING -
	 * "Řezání", délka trvání 2 minuty, SANDING - "Broušení", délka 20min, PAINTING
	 * - "Natírání" délka 30min), DRILLING - "Vrtání" délka 5min)
	 *
	 * Každá konstanda bude mít proměnnou name (string - český název) a duration (int - délka
	 * výrobní operace v minutách) a také metody getName a getDuration.
	 *
	 * Dále vytvořte třídu ManufacturingProcess, která bude mít proměnné:
	 *
	 * productName - typu String
	 *
	 * operations - list operací (Operation)
	 *
	 * Třída ManufacturingProcess bude mít také:
	 *
	 * překrytou metodu toString
	 *
	 * Konstruktor s parametrem productName a proměnným počtem parametrů typu Operation.
	 */
	public static void main(String[] args) {
		createManufacturingProcess();
		calculateCircleArea(10);
	}

	/**
	 * Metoda vytvoří a vrátí objekt třídy ManufacturingProcess, který obsahuje
	 * alesppoň dvě výrobní operace
	 *
	 * @return new object of type ManufacturingProcess
	 */
	public static Object createManufacturingProcess() {
		return new ManufacturingProcess("Matice", Operation.SANDING, Operation.DRILLING);
	}

	/**
	 * Metoda spočítá poloměr kruhu π*r²
	 *
	 * Použijte konstantu π definovanou v jazyce Java
	 *
	 * @param r - poloměr kruhu.
	 * @return obsah kruhu s daným poloměrem
	 */
	public static double calculateCircleArea(double r) {
		return r * r * Math.PI;
	}
}
