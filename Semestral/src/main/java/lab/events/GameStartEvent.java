package lab.events;

import lab.interfaces.Event;

public class GameStartEvent extends Event {
    public GameStartEvent() {
        super(EventType.GAME_START);
    }
}
