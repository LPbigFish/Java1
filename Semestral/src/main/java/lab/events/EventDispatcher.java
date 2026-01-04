package lab.events;

import lab.interfaces.Event;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.function.Consumer;

public class EventDispatcher {
    final static EnumMap<EventType, List<Consumer<Event>>> consumers = new EnumMap<>(EventType.class);

    public static void listen(EventType eventType, Consumer<Event> consumer) {
        consumers.computeIfAbsent(eventType, x -> new ArrayList<>()).add(consumer);
    }

    public static void publish(@NotNull EventType eventType, Event eventData) {
        if (!eventType.getEventClass().isInstance(eventData)) return;

        List<Consumer<Event>> listeners = consumers.get(eventType);
        if (listeners == null) return;
        listeners.forEach(c -> c.accept(eventData));
    }

    public static void clearListeners(EventType eventType) {
        consumers.get(eventType).clear();
    }
}
