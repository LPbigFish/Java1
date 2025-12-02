package jez04.structure.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;

import cz.vsb.fei.kelvin.unittest.ClassExist;
import cz.vsb.fei.kelvin.unittest.HasConstructor;
import cz.vsb.fei.kelvin.unittest.HasMethod;
import cz.vsb.fei.kelvin.unittest.HasProperty;
import cz.vsb.fei.kelvin.unittest.StructureHelper;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import lab.manufacture.AppManufacture;

class ManufactureTest {
    StructureHelper helper = StructureHelper.getInstance(ManufactureTest.class);
	private static String class1Name = "Operation";
	private static String class2Name = "ManufacturingProcess";

	@Test
	void operationExistTest() {
        assertThat(ManufactureTest.class, new ClassExist(class1Name));
	}

	@Test
	void operationIsEnumerationTest() throws ClassNotFoundException {
		operationExistTest();
		Class<?> c = helper.getClass(class1Name);
		assertTrue(c.isEnum(), class1Name + " have to be enumaration.");
	}

	@Test
	void operationPropertyExistenceTest() throws ClassNotFoundException {
		manufacturingProcessExistenceTest();
		Class<?> c = helper.getClass(class1Name);
        assertThat(c, new HasProperty("name", String.class));
        assertThat(c, new HasProperty("duration", int.class));
	}

	@Test
	void operationMethodExistenceTest() throws ClassNotFoundException {
        manufacturingProcessExistenceTest();
        Class<?> c = helper.getClass(class1Name);
        assertThat(c, new HasMethod("getName", String.class));
        assertThat(c, new HasMethod("getDuration", int.class));
        assertThat(c, new HasConstructor(String.class, int.class));
	}

	@Test
	void operationHasElementsTest() throws ClassNotFoundException {
		operationExistTest();
		Class<?> c = helper.getClass(class1Name);
		assertTrue(c.getEnumConstants().length > 0, "operation has not some enumeration constants");
	}

	@Test
	void manufacturingProcessExistenceTest() {
        assertThat(ManufactureTest.class, new ClassExist(class2Name));
	}

	@Test
	void manufacturingProcessPropertyExistenceTest() throws ClassNotFoundException {
        assertThat(ManufactureTest.class, new ClassExist(class2Name));
		Class<?> c = helper.getClass(class2Name);
        assertThat(c, new HasProperty("productName", String.class));
        assertThat(c, new HasProperty("operations", List.class));
	}

	@Test
	void manufacturingProcessMethodExistenceTest() throws ClassNotFoundException {
        assertThat(ManufactureTest.class, new ClassExist(class2Name));
        Class<?> c = helper.getClass(class2Name);
		assertThat(c, new HasMethod("toString", String.class));
	}

	@Test
	void manufacturingProcessConstructorExistenceTest() throws ClassNotFoundException {
        Class<?> c = helper.getClass(class2Name);
		Class<?> operation = helper.getClass(class1Name);
        assertThat(c, new HasConstructor(String.class, operation.arrayType()));
	}

	@Test
	void createManufacturingProcessTest() throws ClassNotFoundException {
		Object o = AppManufacture.createManufacturingProcess();
		Class<?> operation = helper.getClass(class1Name);
		Class<?> manufacturingProcess = helper.getClass(class2Name);
		assertEquals(manufacturingProcess, o.getClass(), "Returned object should be type manufacturingProcess");
	}


	@Test
	void calculateCircleAreaTest() {
		assertEquals(3.141592653589793, AppManufacture.calculateCircleArea(1), 0.0000000000000001, "Use proper value of π");
	}
}
