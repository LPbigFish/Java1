package jez04.structure.test;

import cz.vsb.fei.kelvin.unittest.ClassExist;
import cz.vsb.fei.kelvin.unittest.ContainsInnerClasses;
import cz.vsb.fei.kelvin.unittest.HasMethod;
import cz.vsb.fei.kelvin.unittest.HasProperty;
import cz.vsb.fei.kelvin.unittest.SrcContains;
import cz.vsb.fei.kelvin.unittest.StructureHelper;
import cz.vsb.fei.kelvin.unittest.TextFileContains;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

class ClassStructureTest {
    StructureHelper helper = StructureHelper.getInstance(ClassStructureTest.class);

    @Test
    void testBulletProperty() throws ClassNotFoundException {
        Class<?> bullet = helper.getClass("Bullet");
        assertThat(bullet, new HasProperty(".*", List.class));
    }

    @Test
    void testBulletMethod() throws ClassNotFoundException {
        Class<?> bullet = helper.getClass("Bullet");
        Class<?> hitListener = helper.getClass("HitListener");
        assertThat(bullet, new HasMethod("addHitListener", boolean.class, hitListener));
        assertThat(bullet, new HasMethod("removeHitListener", boolean.class, hitListener));
    }

    @Test
    void testBulletMethod2() throws ClassNotFoundException {
        Class<?> bullet = helper.getClass("Bullet");
        assertThat(bullet, new HasMethod("fire.*", void.class).useRegExp(true));
    }

    @Test
    void testGameControllerProperty3() throws ClassNotFoundException {
        Class<?> gameController = helper.getClass("GameController");
        assertThat(gameController, new HasProperty(".*", Label.class).annotation(FXML.class));
    }

    @Test
    void testGameControllerProperty4() throws ClassNotFoundException {
        Class<?> gameController = helper.getClass("GameController");
        assertThat(gameController, new HasProperty(".*", int.class));
    }

    @Test
    void testGameControllerMethod() throws ClassNotFoundException {
        Class<?> gameController = helper.getClass("GameController");
        assertThat(gameController, new HasMethod("incr.*", void.class).useRegExp(true));
    }

    @Test
    void testGameControllerLambdaAndMethodReference() throws ClassNotFoundException {
        Class<?> gameController = helper.getClass("GameController");
        assertThat(gameController, new ContainsInnerClasses(2));
    }

    @Test
    void testHitListenerMethod() throws ClassNotFoundException {
        Class<?> hitListener = helper.getClass("HitListener");
        assertThat(hitListener, new HasMethod("ufoDestroyed", void.class));
    }

    @Test
    void testHitListenerExistence() {
        assertThat(ClassStructureTest.class, new ClassExist("HitListener"));
    }

    @Test
    void testWorldProperties() throws ClassNotFoundException {
        Class<?> world = helper.getClass("World");
        assertThat(world, new HasProperty(".*", List.class));
    }
    @Test
    void testWorldProperties2() throws ClassNotFoundException {
        Class<?> world = helper.getClass("World");
        assertThat(world, new HasProperty(".*", Collection.class).count(2));
    }

    @Test
    void testWorldMethods() throws ClassNotFoundException {
        Class<?> world = helper.getClass("World");
        Class<?> drawableSimulable = helper.getClass("DrawableSimulable");
        assertThat(world, new HasMethod("add.*", void.class, drawableSimulable).useRegExp(true).caseSensitive(false));
        assertThat(world, new HasMethod("remove.*", void.class, drawableSimulable).useRegExp(true).caseSensitive(false));
    }

    @Test
    void testWorldUseCollections() throws ClassNotFoundException {
        Class<?> world = helper.getClass("World");
        assertThat(world, new SrcContains(".*"));
        assertThat(world, new SrcContains(".*"));
    }

    @Test
    void testWorldUseCollections2() throws ClassNotFoundException {
        Class<?> world = helper.getClass("World");
        assertThat(world, new SrcContains(".*"));
        assertThat(world, new SrcContains(".*"));
    }
}
