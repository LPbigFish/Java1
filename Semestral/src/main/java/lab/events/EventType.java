package lab.events;

import lab.interfaces.Event;

public enum EventType {
    UPDATE_SCORE(ScoreUpdateEvent.class),
    GAME_OVER(GameOverEvent.class),
    GAME_START(GameStartEvent.class),
    MOVE_LEFT(MoveEvent.class),
    MOVE_RIGHT(MoveEvent.class),
    SWITCH_SCENES(SwitchScenesEvent.class);
    private final Class<? extends Event> eventClass;
    EventType(Class<? extends Event> event) {
        this.eventClass = event;
    }
    public Class<? extends Event> getEventClass() {
        return eventClass;
    }
}
