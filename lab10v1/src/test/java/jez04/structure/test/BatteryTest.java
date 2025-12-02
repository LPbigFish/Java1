package jez04.structure.test;

import cz.vsb.fei.kelvin.unittest.ClassExist;
import cz.vsb.fei.kelvin.unittest.ContainsInnerClasses;
import cz.vsb.fei.kelvin.unittest.HasMethod;
import cz.vsb.fei.kelvin.unittest.HasProperty;
import cz.vsb.fei.kelvin.unittest.OutputContains;
import cz.vsb.fei.kelvin.unittest.StructureHelper;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import lab.batteries.AppBattery;
import lab.batteries.Battery;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BatteryTest {

    StructureHelper helper = StructureHelper.getInstance(BatteryTest.class);
	private static String class1Name = "UniqueHeap";
	private static String class2Name = "AppBattery";

	List<Battery> batteries = new ArrayList<Battery>(List.of(
	// @formatter:off
			new Battery("AA", 0, 205.8),
			new Battery("C", 50, 100),
			new Battery("D", 78, 50.1),
			new Battery("AAA", 0, 50.05),
			new Battery("9V", 5, 5),
			new Battery("9V", 5, 1),
			new Battery("AA", 0, 80))
		);
	// @formatter:on

	@Test
	void generateBateriesTest() {
		List<Battery> batteries = AppBattery.generateBatteries(10);
        assertThat("Collection of bateries", batteries, Matchers.notNullValue());
        assertThat("Collection of bateries", batteries, Matchers.hasSize(10));
        assertThat(AppBattery.class, new HasProperty(".*", Random.class));
		List<Field> randoms = List.of(AppBattery.class.getDeclaredFields()).stream()
				.filter(field -> Random.class.equals(field.getType())).toList();
		assertFalse(randoms.isEmpty(), "No field for random generator found.");
		assertTrue(randoms.stream().anyMatch(field -> Modifier.isStatic(field.getModifiers())),
				"Field for random generator is not static");
	}

	@Test
	void sortByPriceTest() {
		AppBattery.sortByPrice(batteries);
		for (int i = 0; i < batteries.size() - 1; i++) {
			assertTrue(batteries.get(i).getPrice() < batteries.get(i + 1).getPrice(),
					"Collection is not sorted properly");
		}

		long innerClassCount = helper.countClassesRegexp("AppBattery\\$.*");
		assertEquals(innerClassCount, 0, "Do not use inner class for sorting use lambda or method reference");
	}

	@Test
	void sortByPriceMethodReferenceTest() {
		AppBattery.sortByPrice(batteries);
		for (int i = 0; i < batteries.size() - 1; i++) {
			assertTrue(batteries.get(i).getPrice() < batteries.get(i + 1).getPrice(),
					"Collection is not sorted properly");
		}
        assertThat("Use lambda or method reference for sorting no inner classes.", AppBattery.class,
            Matchers.allOf(
                Matchers.not(new ContainsInnerClasses().countLambdaExpressions(false).countMethodReferences(false).countInnerClassesOnlyAnonymous(true)),
                new ContainsInnerClasses().countInnerClasses(false)
            )
        );
    }

	@Test
	void sortByTypeAndCapacityTest() {
		AppBattery.sortByTypeAndCapacity(batteries);
		for (int i = 0; i < batteries.size() - 1; i++) {
			int comp = batteries.get(i).getType().compareTo(batteries.get(i + 1).getType());
			if (comp == 0) {
				comp = Integer.compare(batteries.get(i).getInitialCapacity(),
						batteries.get(i + 1).getInitialCapacity());
			}
			assertTrue(comp < 0 || comp == 0, "Collection is not sorted properly");
		}
		long innerClassCount = helper.countClassesRegexp("AppBattery\\$.*");
		assertEquals(innerClassCount, 0, "Do not use inner class for sorting use lambda or method reference");
	}

	@Test
	void sortByTypeAndCapacityMethodReferenceTest() {
		AppBattery.sortByTypeAndCapacity(batteries);
		for (int i = 0; i < batteries.size() - 1; i++) {
			int comp = batteries.get(i).getType().compareTo(batteries.get(i + 1).getType());
			if (comp == 0) {
				comp = Integer.compare(batteries.get(i).getInitialCapacity(),
						batteries.get(i + 1).getInitialCapacity());
			}
			assertTrue(comp < 0 || comp == 0, "Collection is not sorted properly");
		}
        assertThat("Use lambda or method reference for sorting no inner classes.", AppBattery.class,
            Matchers.allOf(
                Matchers.not(new ContainsInnerClasses().countLambdaExpressions(false).countMethodReferences(false).countInnerClassesOnlyAnonymous(true)),
                new ContainsInnerClasses().countInnerClasses(false)
            )
        );
	}

	@Test
	void logDischargedConsolaBatteriesTest() {
        assertThat(BatteryTest.class, new ClassExist("AppBattery$DischargeEvaluator"));
        assertThat(AppBattery::logDischargedBatteries , new OutputContains("->"));
	}

	@Test
	void logDischargedFileBatteriesTest() throws IOException {
        assertThat(BatteryTest.class, new ClassExist("AppBattery$DischargeEvaluator"));
		assertTrue(AppBattery.logDischargedBatteries(), "Method logDischargedBatteries do not return true.");
		assertTrue(Files.readAllLines(Paths.get("discharged.txt")).size() >= 1,"File discharged.txt has no line.");
		try{
			new File("discharged.txt").setReadOnly();
			assertFalse(AppBattery.logDischargedBatteries(), "Method logDischargedBatteries do not return false because file is open and locked.");
		} finally {
			new File("discharged.txt").setWritable(true);
		}
	}

	@Test
	void logShopPriceBatteriesTest() {
        assertThat(AppBattery.class, new ContainsInnerClasses().countLambdaExpressions(false).countMethodReferences(false));
		assertNotEquals(0, helper.countClassesRegexp(".*AppBattery\\$.*"), "No anonymous inner class found.");
		PrintStream original = System.out;
		ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
		System.setOut(new PrintStream(dataStream));
		try {
			AppBattery.logShopPriceBatteries();
			assertNotEquals(0, dataStream.toByteArray().length, "No data written to output consola");
		} finally {
			System.setOut(original);
			try {
				System.out.write(dataStream.toByteArray());
				dataStream.close();
			} catch (IOException e) {
				/* ignore it */}
		}
	}

	@Test
	void uniqueHeapExistenceTest() throws ClassNotFoundException {
        assertThat(BatteryTest.class, new ClassExist("UniqueHeap"));
		Class<?> uniqueHeap = helper.getClass("UniqueHeap");
        assertNotEquals(0, uniqueHeap.getTypeParameters().length, "No generic parameter found");
        assertThat(uniqueHeap, new HasProperty(".*", Set.class));
        assertThat(uniqueHeap, new HasProperty(".*", int.class));
        assertThat(uniqueHeap, new HasMethod("add", void.class, Object.class));
        assertThat(uniqueHeap, new HasMethod("getCount", int.class));
        assertThat(uniqueHeap, new HasMethod("grabAll", Set.class));
	}

	@Test
	void uniqueHeapStringTest() throws IllegalAccessException, InvocationTargetException, ClassNotFoundException,
        NoSuchMethodException {
        Class<?> uniqueHeap = helper.getClass("UniqueHeap");
		Object o = AppBattery.uniqueHeapForStrings(new String[] { "a", "a", "b", "a", "c", "d", "d", "a" });
		Method getCount = helper.getMethod(uniqueHeap, "getCount", int.class);
		Method grabAll = helper.getMethod(uniqueHeap, "grabAll", Set.class);
		assertEquals(8, getCount.invoke(o), "getCount should return int 8.");
		if(grabAll.invoke(o) instanceof Set set) {
			assertEquals(4, set.size());
			assertTrue(set.stream().allMatch(e -> e instanceof String), "all element in set have to be Strings");
		}
		assertEquals(0, getCount.invoke(o), "getCount should return int 0 after grabAll.");
	}

	@Test
	void uniqueHeapIntTest() throws IllegalAccessException, InvocationTargetException, ClassNotFoundException,
        NoSuchMethodException {
        Class<?> uniqueHeap = helper.getClass("UniqueHeap");
		Object o = AppBattery.uniqueHeapForInt(new int[] { 1, 2, 1, 1, 3, 3, 2, 1, 2, 4 });
		Method getCount = helper.getMethod(uniqueHeap, "getCount", int.class);
		Method grabAll = helper.getMethod(uniqueHeap, "grabAll", Set.class);
		assertEquals(10, getCount.invoke(o), "getCount should return int 10.");
		if(grabAll.invoke(o) instanceof Set set) {
			assertEquals(4, set.size());
			assertTrue(set.stream().allMatch(e -> e instanceof Integer), "all element in set have to be Integer");
		}
		assertEquals(0, getCount.invoke(o), "getCount should return int 0 after grabAll.");
	}
}
