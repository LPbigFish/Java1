package jez04.structure.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;

import cz.vsb.fei.kelvin.unittest.ClassExist;
import cz.vsb.fei.kelvin.unittest.HasConstructor;
import cz.vsb.fei.kelvin.unittest.HasMethod;
import cz.vsb.fei.kelvin.unittest.HasProperty;
import cz.vsb.fei.kelvin.unittest.IsDescendatOf;
import cz.vsb.fei.kelvin.unittest.IsInterface;
import cz.vsb.fei.kelvin.unittest.StructureHelper;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import lab.geom3d.AppGeom3D;

class Geom3DTest {

    private static final String BODY_WITH_BASE = "BodyWithBase";
    private static final String BODY3D = "Body3D";
    StructureHelper helper = StructureHelper.getInstance(Geom3DTest.class);

    @Test
    void body3DExistenceTest() throws ClassNotFoundException {
        assertThat(Geom3DTest.class, new ClassExist(BODY3D));
        Class<?> c = helper.getClass(BODY3D);
        assertThat(c, new IsInterface());
        assertThat(c, new HasMethod("getVolume", double.class));
    }

    @Test
    void bodyWithBaseExistenceTest() throws ClassNotFoundException, NoSuchMethodException {
        assertThat(Geom3DTest.class, new ClassExist(BODY_WITH_BASE));
        Class<?> c = helper.getClass(BODY_WITH_BASE);
        assertThat(c, new IsInterface());
        assertThat(c, new IsDescendatOf("Body3D"));
        assertThat(c, new HasMethod("getHeight", double.class));
        assertThat(c, new HasMethod("getSurfaceOfBase", double.class));
        assertThat(c, new HasMethod("getVolume", double.class));
        assertTrue(helper.getMethod(c, "getVolume", double.class).isDefault(), "Method getVolume have to be default");
    }

    @Test
    void sphereExistenceTest() throws ClassNotFoundException {
        assertThat(Geom3DTest.class, new ClassExist("Sphere"));
        Class<?> c = helper.getClass("Sphere");
        assertThat(c, new IsDescendatOf("Body3D"));
        assertThat(c, new HasProperty("radius", double.class));
        assertThat(c, new HasConstructor(double.class));
    }

    @Test
    void cylinderExistenceTest() throws ClassNotFoundException {
        assertThat(Geom3DTest.class, new ClassExist("Cylinder"));
        Class<?> c = helper.getClass("Cylinder");
        assertThat(c, new IsDescendatOf(BODY_WITH_BASE));
        assertThat(c, new HasProperty("radius", double.class));
        assertThat(c, new HasProperty("height", double.class));
        assertThat(c, new HasConstructor(double.class, double.class));
    }

    @Test
    void blockExistenceTest() throws ClassNotFoundException {
        assertThat(Geom3DTest.class, new ClassExist("Block"));
        Class<?> c = helper.getClass("Block");
        assertThat(c, new IsDescendatOf(BODY_WITH_BASE));
        assertThat(c, new HasProperty("width", double.class));
        assertThat(c, new HasProperty("height", double.class));
        assertThat(c, new HasProperty("depth", double.class));
        assertThat(c, new HasConstructor(double.class, double.class, double.class));
    }

    @Test
    void createBodiesTest() throws ClassNotFoundException {
        Class<?> sphere = helper.getClass("Sphere");
        Class<?> cylinder = helper.getClass("Cylinder");
        Class<?> block = helper.getClass("Block");
        List<?> bodies = AppGeom3D.createBodies();
        assertTrue(bodies.stream().anyMatch(b -> sphere.isAssignableFrom(b.getClass())),
            "Some element of collection hacve to be type Sphere");
        assertTrue(bodies.stream().anyMatch(b -> cylinder.isAssignableFrom(b.getClass())),
            "Some element of collection hacve to be type Cylinder");
        assertTrue(bodies.stream().anyMatch(b -> block.isAssignableFrom(b.getClass())),
            "Some element of collection hacve to be type Block");
    }

    @Test
    void calculateVolumeTest() throws InstantiationException, IllegalAccessException, IllegalArgumentException,
        InvocationTargetException, ClassNotFoundException, NoSuchMethodException {
        List<Object> bodies = new ArrayList<>();
        Class<?> sphere = helper.getClass("Sphere");
        Class<?> cylinder = helper.getClass("Cylinder");
        Class<?> block = helper.getClass("Block");

        bodies.add(helper.getConstructor(sphere, double.class).newInstance(1.0));
        bodies.add(helper.getConstructor(cylinder, double.class, double.class).newInstance(1.0, 2 / 3.0));
        bodies.add(helper.getConstructor(block, double.class, double.class, double.class).newInstance(1.0, 1.0, 1.0));
        double sumVolume1 = AppGeom3D.calculateVolume(bodies);

        bodies.add(helper.getConstructor(sphere, double.class).newInstance(1.0));
        bodies.add(helper.getConstructor(cylinder, double.class, double.class).newInstance(2 / 3.0, 1.0));
        bodies.add(helper.getConstructor(block, double.class, double.class, double.class).newInstance(1.0, 1.0, 1.0));
        double sumVolume2 = AppGeom3D.calculateVolume(bodies);
        assertThat("Probablly wrong calculations of volumes.", 2 * 3.141592653589793 + 1, Matchers.anyOf(
            Matchers.equalTo(sumVolume1),
            Matchers.equalTo(sumVolume2)
        ));
    }
}
