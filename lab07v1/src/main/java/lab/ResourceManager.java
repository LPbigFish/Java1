package lab;

import javafx.scene.image.Image;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class ResourceManager {

    private static Map<String, Image> resources =  new HashMap<>();

    private static final Random random = new Random();

    public static Image getImage(Class<?> clazz, String resource) {
        if (!resources.containsKey(resource)) {
            resources.put(resource, new Image(Objects.requireNonNull(clazz.getResourceAsStream(resource))));
        }
        return resources.get(resource);
    }

    public static <T> T getRandomElement(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }
}
