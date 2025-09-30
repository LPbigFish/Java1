package jez04.structure.test;

import cz.vsb.fei.kelvin.unittest.ClassExist;
import cz.vsb.fei.kelvin.unittest.HasMethod;
import cz.vsb.fei.kelvin.unittest.HasProperty;
import cz.vsb.fei.kelvin.unittest.StructureHelper;
import java.util.Random;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

class ClassStructureTest {
    StructureHelper helper = StructureHelper.getInstance(ClassStructureTest.class);

	@Test
	void ufoClassEexistanceTest() {
        assertThat(ClassStructureTest.class, new ClassExist("Ufo"));
	}

    @Test
    void ufoClassPropertiesTest() throws ClassNotFoundException {
        Class<?> ufo = helper.getClass("Ufo");
        Class<?> world = helper.getClass("World", false);
        assertThat(ufo, new HasProperty(".*", world, false));
    }

    @Test
    void ufoClassProperties2Test() throws ClassNotFoundException {
        Class<?> ufo = helper.getClass("Ufo");
        assertThat(ufo, new HasProperty(".*", Random.class, false));
    }

	@Test
	void ufoClassMethodsTest() throws ClassNotFoundException {
        Class<?> ufo = helper.getClass("Ufo");
        assertThat(ufo, new HasMethod("draw", void.class, GraphicsContext.class));
        assertThat(ufo, new HasMethod("simulate", void.class, double.class));
	}


    @Test
    void ufoClassMethods2Test() throws ClassNotFoundException {
        Class<?> ufo = helper.getClass("Ufo");
        assertThat(ufo, new HasMethod("getBoundingBox", Rectangle2D.class));
    }

    @Test
    void ufoClassMethods3Test() throws ClassNotFoundException {
        Class<?> ufo = helper.getClass("Ufo");
        assertThat(ufo, new HasMethod("changeDirection", void.class));
    }

    @Test
	void worldClassPropertyTest() throws ClassNotFoundException {
        Class<?> world = helper.getClass("World", false);
        Class<?> ufo = helper.getClass("Ufo");
        assertThat(world, new HasProperty(".*", ufo.arrayType(), false));
	}

}
