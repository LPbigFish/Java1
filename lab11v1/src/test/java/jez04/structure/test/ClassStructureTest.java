package jez04.structure.test;

import cz.vsb.fei.kelvin.unittest.ClassExist;
import cz.vsb.fei.kelvin.unittest.HasConstructor;
import cz.vsb.fei.kelvin.unittest.HasMethod;
import cz.vsb.fei.kelvin.unittest.HasProperty;
import cz.vsb.fei.kelvin.unittest.IsDescendatOf;
import cz.vsb.fei.kelvin.unittest.StructureHelper;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import lab.score.Score;
import lab.score.ScoreException;
import lab.score.ScoreRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;

class ClassStructureTest {
    StructureHelper helper = StructureHelper.getInstance(ClassStructureTest.class);

    @Test
    void testGetConnection() throws NoSuchMethodException, InvocationTargetException,
        IllegalAccessException {
        Method getConnection = ScoreRepository.class.getDeclaredMethod("getConnection");
        getConnection.setAccessible(true);
        Object connection = getConnection.invoke(null);
        assertThat(connection, Matchers.notNullValue());
    }
    @Test
    void testGetConnection2() throws NoSuchMethodException, InvocationTargetException,
        IllegalAccessException {
        Method getConnection = ScoreRepository.class.getDeclaredMethod("getConnection");
        getConnection.setAccessible(true);
        Object connection = getConnection.invoke(null);
        assertThat(connection, Matchers.notNullValue());
        assertThat(connection, Matchers.equalTo(getConnection.invoke(null)));
    }

    @Test
    void testInit() {
        ScoreRepository.init();
    }
    @Test
    void testInit2() {
        ScoreRepository.init();
        ScoreRepository.init();
    }

    @Test
    void testSave() throws ScoreException {
        ScoreRepository.init();
        ScoreRepository.save(Score.generate());
    }

    @Test
    void testSaveCol() throws ScoreException {
        ScoreRepository.init();
        ScoreRepository.save(List.of(Score.generate(),Score.generate()));
    }
    @Test
    void testLoad() throws ScoreException {
        ScoreRepository.init();
        assertThat( ScoreRepository.load(), Matchers.notNullValue());
    }

    @Test
    void testDelete() throws ScoreException {
        ScoreRepository.init();
        deleteAll();
        assertThat( ScoreRepository.load(), Matchers.empty());
    }

    @Test
    void testDeleteSaveLoad() throws ScoreException {
        ScoreRepository.init();
        deleteAll();
        ScoreRepository.save(List.of(Score.generate(),Score.generate()));
        assertThat( ScoreRepository.load(), Matchers.hasSize(2));
    }

    @Test
    void testDeleteSaveAllLoad() throws ScoreException {
        ScoreRepository.init();
        deleteAll();
        ScoreRepository.save(Score.generate());
        assertThat( ScoreRepository.load(), Matchers.hasSize(1));
    }

    private static void deleteAll() throws ScoreException {
        for (Score score : ScoreRepository.load()) {
            ScoreRepository.delete(score);
        }
    }


}

