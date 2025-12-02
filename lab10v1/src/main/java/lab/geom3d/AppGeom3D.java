package lab.geom3d;

import java.util.LinkedList;
import java.util.List;

public class AppGeom3D {

	/**
     * Třídy vytvářejte v balíku geom3d.
	 * Vytvořte rozhraní Body3D (3D těleso) s metodou getVolume, která vrací double
	 *
	 * Vytvořte třídu Sphere, která implementuje rozhraní Body3D a má jako instanční
     * proměnnou poloměr radius typu double. Má kostruktor s jedním parametrem a
	 * implementuje potřebné metody. Objem koule se spočítá 4/3*π*r³
	 *
	 * Vytvořte rozhraní BodyWithBase (těleso s podstavou), které je potomkem
	 * prozhraní Body3D. Navíc má metody:
	 *
	 * double getHeight() - vrací výšku tělesa
	 *
	 * double getSurfaceOfBase() - vrací obsah podstavy
	 *
	 * double getVolume() - která vrací objem tělesa a má defaultní impemntaci,
     * která vynásobí výšku a obje podstavy (surfaceOfBase).
	 *
	 * Vytvořte třídu Cylinder (válec), která implementuje rozhraní BodyWithBase a
     * má jako instanční proměnnou poloměr radius a height (výšku) obojí typu double. Má
	 * kostruktor s dvěmi parametry a implementuje potřebné metody. Objem kruhu se
	 * spočítá π*r²
	 *
	 * Vytvořte třídu Block (kvádr), která implementuje rozhraní BodyWithBase a má
	 * jako instanční proměnnou poloměr width (šířku), depth (hloubku) a height
	 * (výšku) vše typu double. Má kostruktor se třemi parametry a implementuje
	 * potřebné metody. Objem podstavy se spočítá width*depth
	 */
	public static void main(String[] args) {
		createBodies();
		System.out.println(calculateVolume(createBodies()));
	}

	/**
	 * Vytvořte kolekci (vázaný seznam) objektů implementujících rozhraní Body3D. Do
	 * kolekce vložte jednu kouli (Sphere), jeden válec (Cylinder) a jeden kvádr
	 * (Block). Kolekci vraťte jako návratovou hodnotu.
	 *
	 * @return - kolekce 3D těles
	 */
	public static List<?> createBodies() {
        LinkedList<Body3D> bodies = new LinkedList<>();
        bodies.add(new Sphere(20.0));
        bodies.add(new Cylinder(10.0, 21.0));
        bodies.add(new Block(20.0, 30.0, 40.0));

        return bodies;
	}

	/**
	 * Metoda pro všechyn objekty typu Body3D spočítá obsah a vrátí součet všech
	 * obsahů.
	 *
	 * Projděte kolekci a pokud je prvek kolekce typu Body3D spočítejte jeho obsah a
	 * všechny obsahy všech těles sečtěte.
	 *
	 * @param bodies - koelkce objektů
	 * @return součet všech obsahů 3D těles.
	 */
	public static double calculateVolume(List<?> bodies) {
		return bodies.stream().filter(Body3D.class::isInstance).map(Body3D.class::cast).mapToDouble(Body3D::getVolume).sum();
	}

}
