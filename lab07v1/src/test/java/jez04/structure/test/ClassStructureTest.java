package jez04.structure.test;

import cz.vsb.fei.kelvin.unittest.ClassExist;
import cz.vsb.fei.kelvin.unittest.ContainsInnerClasses;
import cz.vsb.fei.kelvin.unittest.HasMethod;
import cz.vsb.fei.kelvin.unittest.HasProperty;
import cz.vsb.fei.kelvin.unittest.IsDescendatOf;
import cz.vsb.fei.kelvin.unittest.SrcContains;
import cz.vsb.fei.kelvin.unittest.StructureHelper;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javafx.scene.image.Image;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

class ClassStructureTest {
    StructureHelper helper = StructureHelper.getInstance(ClassStructureTest.class);

    @Test
    void testResourceManagerExists() {
        assertThat(ClassStructureTest.class, new ClassExist(".*Res+ourceManager").useRegExp(true));
    }

    @Test
    void testResourceManagerProperties() throws ClassNotFoundException {
        Class<?> resourceManager = helper.getClassRegexp(".*Res+ourceManager", false);
        assertThat(resourceManager, new HasProperty(".*", Map.class));
    }

    @Test
    void testResourceManagerMethods() throws ClassNotFoundException {
        Class<?> resourceManager = helper.getClassRegexp(".*Res+ourceManager");
        assertThat(resourceManager, new HasMethod(".*", Image.class, Class.class, String.class).useRegExp(true));
    }

    @Test
    void testResourceManagerMethods2() throws ClassNotFoundException {
        Class<?> resourceManager = helper.getClassRegexp(".*Res+ourceManager");
        assertThat(resourceManager, new HasMethod("getRandomElement", Object.class, List.class));
    }

    @Test
    void testResourceManagerMethods3() throws ClassNotFoundException, NoSuchMethodException {
        Class<?> resourceManager = helper.getClassRegexp(".*Res+ourceManager");
        Method method = helper.getMethod(resourceManager, "getRandomElement", Object.class, List.class);
        Type type = method.getGenericReturnType();
        Assertions.assertTrue(type instanceof TypeVariable,
            "Return type of method getRandomElement shoud be generic 'T'");
        Type[] parmaTypes = method.getGenericParameterTypes();
        if (parmaTypes[0] instanceof ParameterizedType pType) {
            Type[] types = pType.getActualTypeArguments();
            Assertions.assertEquals(type, types[0],
                "Parametrized return type T have to be same with parametrized type of List<T>");
        } else {
            Assertions.fail("Parameter of type List should use generic type List<T>");
        }
    }
    @Test
    void testResourceManagerUsage() throws ClassNotFoundException {
        Class<?> ufo = helper.getClass("Ufo");
        Class<?> bullet = helper.getClass("BulletAnimated");
        assertThat(ufo, new SrcContains(".*Res+ourceManager\\.getRandomElement"));
        assertThat(bullet, new SrcContains(".*Res+ourceManager\\.getRandomElement"));
    }

    @Test
    void ufoSpawnerExistanceTest() {
        assertThat(ClassStructureTest.class, new ClassExist("UfoSpawner"));
    }

    @Test
    void ufoSpawnerTest() throws ClassNotFoundException {
        Class<?> ufoSpawner = helper.getClass("UfoSpawner");
        assertThat(ufoSpawner, new IsDescendatOf("DrawableSimulable"));
    }
    void ufoSpawnerRandomTest() throws ClassNotFoundException {
        Class<?> ufoSpawner = helper.getClass("UfoSpawner");
        assertThat(ufoSpawner, new HasProperty(".*", Random.class));
    }

    @Test
    void testWorldComparatorExists() throws ClassNotFoundException {
        Class<?> world = helper.getClass("World");
        assertThat(world, new HasProperty(".*", Comparator.class));
    }

    @Test
    void testWorldNewComparatorExists() throws ClassNotFoundException {
        Class<?> world = helper.getClass("World");
        assertThat(world, new SrcContains("new Comparator"));
    }

    @Test
    void testWorldNewComparatorInnerExists() throws ClassNotFoundException {
        Class<?> world = helper.getClass("World");
        assertThat(world, new ContainsInnerClasses());
    }

    @Test
    void testWorldSortExists() throws ClassNotFoundException {
        Class<?> world = helper.getClass("World");
        assertThat(world, new SrcContains("entities\\.sort"));
    }

}
